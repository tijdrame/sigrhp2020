package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.PersistentAuditEvent;
import com.emard.sigrhp.domain.TypeRelation;
import com.emard.sigrhp.domain.User;
import com.emard.sigrhp.repository.TypeRelationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing TypeRelation.
 */
@Service
@Transactional
public class TypeRelationService {

    private final Logger log = LoggerFactory.getLogger(TypeRelationService.class);

    private final TypeRelationRepository typeRelationRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    public TypeRelationService(TypeRelationRepository typeRelationRepository, UserService userService, AuditEventService auditEventService) {
        this.typeRelationRepository = typeRelationRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
    }

    /**
     * Save a typeRelation.
     *
     * @param typeRelation the entity to save
     * @return the persisted entity
     */
    public TypeRelation save(TypeRelation typeRelation) {
        log.debug("Request to save TypeRelation : {}", typeRelation);
        String event;
        if(typeRelation.getId()!=null) event= "MODIFICATION_TYPE_RELATION";
        else event = "CREATION__TYPE_RELATION";
        User user = userService.getUserWithAuthorities().get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, null);
        auditEventService.saveAudit(persistentAuditEvent);
        return typeRelationRepository.save(typeRelation);
    }

    /**
     * Get all the typeRelations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeRelation> findAll(Pageable pageable) {
        log.debug("Request to get all TypeRelations");
        return typeRelationRepository.findByDeletedFalseOrderByLibelle(pageable);
    }


    /**
     * Get one typeRelation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TypeRelation> findOne(Long id) {
        log.debug("Request to get TypeRelation : {}", id);
        return typeRelationRepository.findById(id);
    }

    /**
     * Delete the typeRelation by id.
     *
     * @param id the id of the entity
     */
    public void delete(TypeRelation id) {
        log.debug("Request to delete TypeRelation : {}", id);
        String event= "SUPPRESSION_TYPE_RELATION";
        User user = userService.getUserWithAuthorities().get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, null);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        typeRelationRepository.save(id);
    }
}
