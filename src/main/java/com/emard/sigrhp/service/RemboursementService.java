package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.*;
import com.emard.sigrhp.repository.RemboursementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Remboursement.
 */
@Service
@Transactional
public class RemboursementService {

    private final Logger log = LoggerFactory.getLogger(RemboursementService.class);

    private final RemboursementRepository remboursementRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    private final UserExtraService userExtraService;

    public RemboursementService(RemboursementRepository remboursementRepository, UserService userService, AuditEventService auditEventService, UserExtraService userExtraService) {
        this.remboursementRepository = remboursementRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
        this.userExtraService = userExtraService;
    }

    /**
     * Save a remboursement.
     *
     * @param remboursement the entity to save
     * @return the persisted entity
     */
    public Remboursement save(Remboursement remboursement) {
        log.debug("Request to save Remboursement : {}", remboursement);
        return remboursementRepository.save(remboursement);
    }

    /**
     * Get all the remboursements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Remboursement> findAll(Pageable pageable) {
        log.debug("Request to get all Remboursements");
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return remboursementRepository.findByDeletedFalseAndDetailPret_Collaborateur_StructureOrderByDetailPret_Pret_Libelle(pageable, structure);
        }
        return remboursementRepository.findByDeletedFalseOrderByDetailPret_Pret_Libelle(pageable);
    }


    /**
     * Get one remboursement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Remboursement> findOne(Long id) {
        log.debug("Request to get Remboursement : {}", id);
        return remboursementRepository.findById(id);
    }

    /**
     * Delete the remboursement by id.
     *
     * @param id the id of the entity
     */
    public void delete(Remboursement id) {
        log.debug("Request to delete Remboursement : {}", id);
        String event= "SUPPRESSION_REMBOURSEMENT";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        remboursementRepository.save(id);
    }

    public List<Remboursement> findByDetailPret(DetailPret detailPret){
        return remboursementRepository.findByDetailPret(detailPret);
    }

    public List<Remboursement> findByRemboursement(DetailPret detailPret) {
        return remboursementRepository.findByDetailPret(detailPret);
    }

    /**
     * Save a remboursement.
     *
     * @param remboursement the entity to save
     * @return the persisted entity
     */
    public Remboursement update(Remboursement remboursement) {
        log.debug("Request to save Remboursement : {}", remboursement);
        remboursement.setDeleted(false);
        return remboursementRepository.save(remboursement);
    }

    public List<Remboursement> getRemboursementByCollaborateur(Collaborateur collaborateur){
        log.debug("Request get remboursement by collab : {}", collaborateur);
        return remboursementRepository.findByDetailPret_CollaborateurAndDeletedFalseAndIsRembourseFalse(collaborateur);
    }

    @Transactional(readOnly = true)
    public Page<Remboursement> findAllBis(String prenom, String nom, String matricule, Pageable pageable) {

        log.debug("Request to get search Remboursements");
        prenom = "%"+prenom+"%";
        nom = "%"+nom+"%";
        matricule = "%"+matricule+"%";
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return remboursementRepository
                .findByDetailPret_Collaborateur_PrenomLikeIgnoreCaseAndDetailPret_Collaborateur_NomLikeIgnoreCaseAndDetailPret_Collaborateur_MatriculeLikeIgnoreCaseAndDetailPret_Collaborateur_StructureAndDeletedFalseOrderByCreatedDate
                (prenom, nom, matricule, structure, pageable);
        }
        return remboursementRepository
            .findByDeletedFalseAndDetailPret_Collaborateur_PrenomLikeIgnoreCaseAndDetailPret_Collaborateur_NomLikeIgnoreCaseAndDetailPret_Collaborateur_MatriculeLikeIgnoreCaseOrderByCreatedDate
            (prenom, nom, matricule, pageable);

    }
}
