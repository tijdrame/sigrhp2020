package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.*;
import com.emard.sigrhp.repository.PiecesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing Pieces.
 */
@Service
@Transactional
public class PiecesService {

    private final Logger log = LoggerFactory.getLogger(PiecesService.class);

    private final PiecesRepository piecesRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    private final UserExtraService userExtraService;

    public PiecesService(PiecesRepository piecesRepository, UserService userService, AuditEventService auditEventService, UserExtraService userExtraService) {
        this.piecesRepository = piecesRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
        this.userExtraService = userExtraService;
    }

    /**
     * Save a pieces.
     *
     * @param pieces the entity to save
     * @return the persisted entity
     */
    public Pieces save(Pieces pieces) {
        log.debug("Request to save Pieces : {}", pieces);
        String event= "";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        if (pieces.getId() != null) {
            event = "MODIFICATION_PIECES";
        }
        else{
            event = "CREATION_PIECES";
        }
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        return piecesRepository.save(pieces);
    }

    /**
     * Get all the pieces.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Pieces> findAll(Pageable pageable) {
        log.debug("Request to get all Pieces");
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return piecesRepository.findByDeletedFalseAndCollaborateur_StructureOrderByLibelle(pageable, structure);
        }
        return piecesRepository.findByDeletedFalseOrderByLibelle(pageable);
    }


    /**
     * Get one pieces by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Pieces> findOne(Long id) {
        log.debug("Request to get Pieces : {}", id);
        return piecesRepository.findById(id);
    }

    /**
     * Delete the pieces by id.
     *
     * @param id the id of the entity
     */
    public void delete(Pieces id) {
        log.debug("Request to delete Pieces : {}", id);
        String event= "SUPPRESSION_PIECE";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        piecesRepository.save(id);
    }

    public Page<Pieces> findAllBis(String prenom, String nom, String matricule, Boolean deleted, Pageable pageable) {
        log.debug("Request to get search bis Pieces");
        prenom = "%"+prenom+"%";
        nom = "%"+nom+"%";
        matricule = "%"+matricule+"%";

        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return piecesRepository
                .findByCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndCollaborateur_MatriculeLikeIgnoreCaseAndCollaborateur_StructureAndDeletedOrderByCreatedDate
                    (prenom, nom, matricule, structure, deleted, pageable);
        }
        return piecesRepository
            .findByCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndCollaborateur_MatriculeLikeIgnoreCaseAndDeletedOrderByCreatedDate
                (prenom, nom, matricule, deleted, pageable);
    }
}
