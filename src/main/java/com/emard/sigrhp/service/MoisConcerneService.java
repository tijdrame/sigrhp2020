package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.MoisConcerne;
import com.emard.sigrhp.domain.PersistentAuditEvent;
import com.emard.sigrhp.domain.User;
import com.emard.sigrhp.repository.MoisConcerneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing MoisConcerne.
 */
@Service
@Transactional
public class MoisConcerneService {

    private final Logger log = LoggerFactory.getLogger(MoisConcerneService.class);

    private final MoisConcerneRepository moisConcerneRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    public MoisConcerneService(MoisConcerneRepository moisConcerneRepository, UserService userService, AuditEventService auditEventService) {
        this.moisConcerneRepository = moisConcerneRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
    }

    /**
     * Save a moisConcerne.
     *
     * @param moisConcerne the entity to save
     * @return the persisted entity
     */
    public MoisConcerne save(MoisConcerne moisConcerne) {
        log.debug("Request to save MoisConcerne : {}", moisConcerne);
        String event;
        if(moisConcerne.getId()!=null) event= "MODIFICATION_MOIS";
        else event = "CREATION_MOIS";
        User user = userService.getUserWithAuthorities().get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, null);
        auditEventService.saveAudit(persistentAuditEvent);
        return moisConcerneRepository.save(moisConcerne);
    }

    /**
     * Get all the moisConcernes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MoisConcerne> findAll(Pageable pageable) {
        log.debug("Request to get all MoisConcernes");
        return moisConcerneRepository.findByDeletedFalseOrderByCode(pageable);
    }


    /**
     * Get one moisConcerne by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MoisConcerne> findOne(Long id) {
        log.debug("Request to get MoisConcerne : {}", id);
        return moisConcerneRepository.findById(id);
    }

    /**
     * Delete the moisConcerne by id.
     *
     * @param id the id of the entity
     */
    public void delete(MoisConcerne id) {
        log.debug("Request to delete MoisConcerne : {}", id);
        String event= "SUPPRESSION_MOIS";
        User user = userService.getUserWithAuthorities().get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, null);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        moisConcerneRepository.save(id);
    }
}
