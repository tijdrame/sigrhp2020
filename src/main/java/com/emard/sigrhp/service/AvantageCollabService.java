package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.*;
import com.emard.sigrhp.repository.AvantageCollabRepository;
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
 * Service Implementation for managing AvantageCollab.
 */
@Service
@Transactional
public class AvantageCollabService {

    private final Logger log = LoggerFactory.getLogger(AvantageCollabService.class);

    private final AvantageCollabRepository avantageCollabRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    private final UserExtraService userExtraService;

    public AvantageCollabService(AvantageCollabRepository avantageCollabRepository, UserService userService, AuditEventService auditEventService, UserExtraService userExtraService) {
        this.avantageCollabRepository = avantageCollabRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
        this.userExtraService = userExtraService;
    }

    /**
     * Save a avantageCollab.
     *
     * @param avantageCollab the entity to save
     * @return the persisted entity
     */
    public AvantageCollab save(AvantageCollab avantageCollab) {
        log.debug("Request to save AvantageCollab : {}", avantageCollab);
        String event= "";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        if (avantageCollab.getId() != null) {
            event = "MODIFICATION_AVANTAGE_COLLAB";
        }
        else{
            event = "CREATION_AVANTAGE_COLLAB";
        }
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        return avantageCollabRepository.save(avantageCollab);
    }

    /**
     * Get all the avantageCollabs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AvantageCollab> findAll(Pageable pageable) {
        log.debug("Request to get all AvantageCollabs");
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return avantageCollabRepository.findByDeletedFalseAndCollaborateur_StructureOrderByAvantage_Libelle(pageable, structure);
        }
        return avantageCollabRepository.findByDeletedFalseOrderByAvantage_Libelle(pageable);
    }


    /**
     * Get one avantageCollab by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AvantageCollab> findOne(Long id) {
        log.debug("Request to get AvantageCollab : {}", id);
        return avantageCollabRepository.findById(id);
    }

    /**
     * Delete the avantageCollab by id.
     *
     * @param id the id of the entity
     */
    public void delete(AvantageCollab id) {
        log.debug("Request to delete AvantageCollab : {}", id);
        String event= "SUPPRESSION_AVANTAGE_COLLAB";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        avantageCollabRepository.save(id);
    }

    @Transactional(readOnly = true)
    public List<AvantageCollab> findByCollaborateur(Collaborateur collaborateur) {
        return  avantageCollabRepository.findByCollaborateurAndDeletedFalse(collaborateur);
    }
}
