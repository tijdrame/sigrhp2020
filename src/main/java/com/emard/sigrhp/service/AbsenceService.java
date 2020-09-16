package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.*;
import com.emard.sigrhp.repository.AbsenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing Absence.
 */
@Service
@Transactional
public class AbsenceService {

    private final Logger log = LoggerFactory.getLogger(AbsenceService.class);

    private final AbsenceRepository absenceRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    private final UserExtraService userExtraService;

    private final ExerciceService exerciceService;

    private final MotifService motifService;

    public AbsenceService(AbsenceRepository absenceRepository, UserService userService, AuditEventService auditEventService,
                          UserExtraService userExtraService, ExerciceService exerciceService, MotifService motifService) {
        this.absenceRepository = absenceRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
        this.userExtraService = userExtraService;
        this.exerciceService = exerciceService;
        this.motifService = motifService;
    }

    /**
     * Save a absence.
     *
     * @param absence the entity to save
     * @return the persisted entity
     */
    public Absence save(Absence absence) {
        log.debug("Request to save Absence : {}", absence);
        String event= "";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        if (absence.getId() != null) {
            event = "MODIFICATION_ABSENCE";
        }
        else{
            event = "CREATION_ABSENCE";
        }
        //membreFamille.structure(userExtra.getStructure());
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        return absenceRepository.save(absence);
    }

    /**
     * Get all the absences.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Absence> findAll(Pageable pageable) {
        log.debug("Request to get all Absences");
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return absenceRepository.findByDeletedFalseAndCollaborateur_StructureOrderByDateAbsenceDesc(pageable, structure);
        }
        return absenceRepository.findByDeletedFalseOrderByDateAbsenceDesc(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Absence> findAllBis(String prenom, String nom, String matricule, Long idExercice,
                                    Long idMotif, Pageable pageable) {

        log.debug("Request to get search Remboursements");
        prenom = "%"+prenom+"%";
        nom = "%"+nom+"%";
        matricule = "%"+matricule+"%";
        Optional<Exercice> exercice= null;
        Optional<Motif> motif= null;
        String mot="";
        if(idExercice != null){
            exercice = exerciceService.findOne(Long.valueOf(idExercice));
        }

        if(idMotif != null) {
            motif = motifService.findOne(Long.valueOf(idMotif));
            mot = motif.get().getLibelle();
        }
        mot = "%"+mot+"%";
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return absenceRepository
                .findByCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndCollaborateur_MatriculeLikeIgnoreCaseAndCollaborateur_StructureAndExercice_DebutExerciceAndMotif_LibelleLikeIgnoreCaseAndDeletedFalseOrderByDateAbsenceDesc
                    (prenom, nom, matricule, structure, exercice.get().getDebutExercice(), mot, pageable);
        }
        return absenceRepository
            .findByDeletedFalseAndCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndCollaborateur_MatriculeLikeIgnoreCaseAndExercice_DebutExerciceAndMotif_LibelleLikeIgnoreCaseOrderByDateAbsenceDesc
                (prenom, nom, matricule, exercice.get().getDebutExercice(), mot, pageable);

    }


    /**
     * Get one absence by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Absence> findOne(Long id) {
        log.debug("Request to get Absence : {}", id);
        return absenceRepository.findById(id);
    }

    /**
     * Delete the absence by id.
     *
     * @param id the id of the entity
     */
    public void delete(Absence id) {
        log.debug("Request to delete Absence : {}", id);
        String event= "SUPPRESSION_ABSENCE";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        absenceRepository.save(id);
    }
}
