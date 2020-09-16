package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.Convention;
import com.emard.sigrhp.domain.PersistentAuditEvent;
import com.emard.sigrhp.domain.User;
import com.emard.sigrhp.domain.UserExtra;
import com.emard.sigrhp.repository.ConventionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing Convention.
 */
@Service
@Transactional
public class ConventionService {

    private final Logger log = LoggerFactory.getLogger(ConventionService.class);

    private final ConventionRepository conventionRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    public ConventionService(ConventionRepository conventionRepository, UserService userService, AuditEventService auditEventService) {
        this.conventionRepository = conventionRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
    }

    /**
     * Save a convention.
     *
     * @param convention the entity to save
     * @return the persisted entity
     */
    public Convention save(Convention convention) {
        log.debug("Request to save Convention : {}", convention);
        String event;
        if(convention.getId()!=null) event= "MODIFICATION_CONVENTION";
        else event = "CREATION_CONVENTION";
        User user = userService.getUserWithAuthorities().get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, null);
        auditEventService.saveAudit(persistentAuditEvent);
        return conventionRepository.save(convention);
    }

    /**
     * Get all the conventions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Convention> findAll(Pageable pageable) {
        log.debug("Request to get all Conventions");
        return conventionRepository.findByDeletedFalseOrderByLibelle(pageable);
    }


    /**
     * Get one convention by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Convention> findOne(Long id) {
        log.debug("Request to get Convention : {}", id);
        return conventionRepository.findById(id);
    }

    /**
     * Delete the convention by id.
     *
     * @param convention the id of the entity
     */
    public void delete(Convention convention) {
        log.debug("Request to delete Convention : {}", convention);
        String event= "SUPPRESSION_CONVENTION";
        User user = userService.getUserWithAuthorities().get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, null);
        auditEventService.saveAudit(persistentAuditEvent);
        convention.setDeleted(true);
        convention.setDateDeleted(Instant.now());
        convention.setUserDeleted(user.getLogin());
        conventionRepository.save(convention);
    }
}
