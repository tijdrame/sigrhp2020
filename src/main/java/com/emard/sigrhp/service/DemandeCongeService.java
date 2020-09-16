package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.*;
import com.emard.sigrhp.repository.DemandeCongeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * Service Implementation for managing DemandeConge.
 */
@Service
@Transactional
public class DemandeCongeService {

    private final Logger log = LoggerFactory.getLogger(DemandeCongeService.class);

    private final DemandeCongeRepository demandeCongeRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    private final UserExtraService userExtraService;

    private final StatutDemandeService statutService;
    private final AbsenceService absenceService;
    private final ExerciceService exerciceService;
    private final MotifService motifService;
    private final CollaborateurService collaborateurService;

    public DemandeCongeService(DemandeCongeRepository demandeCongeRepository, UserService userService,
                               AuditEventService auditEventService, UserExtraService userExtraService,
                               StatutDemandeService statutService, AbsenceService absenceService,
                               ExerciceService exerciceService, MotifService motifService, CollaborateurService collaborateurService) {
        this.demandeCongeRepository = demandeCongeRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
        this.userExtraService = userExtraService;
        this.statutService = statutService;
        this.absenceService = absenceService;
        this.exerciceService = exerciceService;
        this.motifService = motifService;
        this.collaborateurService = collaborateurService;
    }

    /**
     * Save a demandeConge.
     *
     * @param demandeConge the entity to save
     * @return the persisted entity
     */
    public DemandeConge save(DemandeConge demandeConge) {
        log.debug("Request to save DemandeConge : {}", demandeConge);
        StatutDemande statut = statutService.findByCode("ENCOURS");
        demandeConge.statutDG(statut).statutRH(statut)
            .collaborateur(collaborateurService.finbByUserCollab(userService.getUserWithAuthorities().get()));
        String event = "CREATION_DEMANDE_CONGE";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        return demandeCongeRepository.save(demandeConge);
    }

    /**
     * Update a demandeConge.
     *
     * @param demandeConge the entity to update
     * @return the persisted entity
     */
    public DemandeConge update(DemandeConge demandeConge) {
        log.debug("Request to save DemandeConge : {}", demandeConge);
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        String event = "MODIFICATION_DEMANDE_CONGE";
        if(demandeConge.getStatutDG()!=null && demandeConge.getStatutDG().getCode().equals("VALIDE")&&
            demandeConge.getStatutRH()!=null && demandeConge.getStatutRH().getCode().equals("VALIDE")){
            Motif motif = null;
            if(demandeConge.getTypeAbsence().getCode().equals("CONGE"))
                motif = motifService.findByCode("C");
            else motif = motifService.findByCode("P");
            Optional<Exercice> exo = exerciceService.findByDebutExercice(demandeConge.getDateDebut().getYear());
            long numOfDaysBetween = ChronoUnit.DAYS.between(demandeConge.getDateDebut(), demandeConge.getDateFin());
            LocalDate date = demandeConge.getDateDebut();
            for (int i=0; i<=numOfDaysBetween; i++)
            {
                Absence absence = new Absence();
                absence.collaborateur(demandeConge.getCollaborateur()).dateAbsence(date).exercice(exo.get()).motif(motif);
                absenceService.save(absence);
                date = date.plusDays(1);
            }
        }
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        return demandeCongeRepository.save(demandeConge);
    }

    /**
     * Get all the demandeConges.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DemandeConge> findAll(Pageable pageable) {
        log.debug("Request to get all DemandeConges");
        User user = userService.getUserWithAuthorities().get();
        Boolean flag = false, sa=false;
        for(Authority authority: user.getAuthorities()){
            if(authority.getName().equals("ROLE_DG")||authority.getName().equals("ROLE_RH")||authority.getName().equals("ROLE_ADMIN_ST")){
                flag = true;
            }else if(authority.getName().equals("ROLE_ADMIN"))
                sa = true;
        }
        if(!flag && !sa) {
            Collaborateur collaborateur = collaborateurService.finbByUserCollab(userService.getUserWithAuthorities().get());
            return demandeCongeRepository.findByCollaborateurAndDeletedFalseOrderByCreatedDate(collaborateur, pageable);
        }
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return demandeCongeRepository.findByDeletedFalseAndCollaborateur_StructureOrderByCreatedDate(pageable, structure);
        }
        return demandeCongeRepository.findByDeletedFalseOrderByCreatedDate(pageable);
    }

    @Transactional(readOnly = true)
    public Page<DemandeConge> findAllBis(String prenom, String nom, String matricule, Pageable pageable) {
        log.debug("Request to get all DemandeConges");
        prenom = "%"+prenom+"%";
        nom = "%"+nom+"%";
        matricule = "%"+matricule+"%";
        User user = userService.getUserWithAuthorities().get();
        Boolean flag = false, sa=false;
        for(Authority authority: user.getAuthorities()){
            if(authority.getName().equals("ROLE_DG")||authority.getName().equals("ROLE_RH")||authority.getName().equals("ROLE_ADMIN_ST")){
                flag = true;
            }else if(authority.getName().equals("ROLE_ADMIN"))
                sa = true;
        }
        if(!flag && !sa) {
            Collaborateur collaborateur = collaborateurService.finbByUserCollab(userService.getUserWithAuthorities().get());
            return demandeCongeRepository.findByCollaborateurAndDeletedFalseOrderByCreatedDate(collaborateur, pageable);
        }
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return demandeCongeRepository.findByDeletedFalseAndCollaborateur_StructureAndCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndAndCollaborateur_MatriculeLikeIgnoreCaseOrderByCreatedDate(structure, prenom, nom, matricule, pageable);
        }
        return demandeCongeRepository.findByDeletedFalseAndCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndAndCollaborateur_MatriculeLikeIgnoreCaseOrderByCreatedDate(prenom, nom, matricule, pageable);
    }


    /**
     * Get one demandeConge by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DemandeConge> findOne(Long id) {
        log.debug("Request to get DemandeConge : {}", id);
        return demandeCongeRepository.findById(id);
    }

    /**
     * Delete the demandeConge by id.
     *
     * @param id the id of the entity
     */
    public void delete(DemandeConge id) {
        log.debug("Request to delete DemandeConge : {}", id);
        String event= "SUPPRESSION_DEMANDE_CONGE";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        demandeCongeRepository.save(id);
    }
}
