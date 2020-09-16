package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.Nationalite;
import com.emard.sigrhp.domain.PersistentAuditEvent;
import com.emard.sigrhp.domain.User;
import com.emard.sigrhp.repository.NationaliteRepository;
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
 * Service Implementation for managing Nationalite.
 */
@Service
@Transactional
public class NationaliteService {

    private final Logger log = LoggerFactory.getLogger(NationaliteService.class);

    private final NationaliteRepository nationaliteRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    public NationaliteService(NationaliteRepository nationaliteRepository, UserService userService, AuditEventService auditEventService) {
        this.nationaliteRepository = nationaliteRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
    }

    /**
     * Save a nationalite.
     *
     * @param nationalite the entity to save
     * @return the persisted entity
     */
    public Nationalite save(Nationalite nationalite) {
        log.debug("Request to save Nationalite : {}", nationalite);
        String event;
        if(nationalite.getId()!=null) event= "MODIFICATION_NATIONALITE";
        else event = "CREATION_NATIONALITE";
        User user = userService.getUserWithAuthorities().get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, null);
        auditEventService.saveAudit(persistentAuditEvent);
        return nationaliteRepository.save(nationalite);
    }

    /**
     * Get all the nationalites.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Nationalite> findAll(Pageable pageable) {
        log.debug("Request to get all Nationalites");
        return nationaliteRepository.findByDeletedFalseOrderByLibelle(pageable);
    }


    /**
     * Get one nationalite by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Nationalite> findOne(Long id) {
        log.debug("Request to get Nationalite : {}", id);
        return nationaliteRepository.findById(id);
    }

    /**
     * Delete the nationalite by id.
     *
     * @param id the id of the entity
     */
    public void delete(Nationalite id) {
        log.debug("Request to delete Nationalite : {}", id);
        String event= "SUPPRESSION_NATIONALITE";
        User user = userService.getUserWithAuthorities().get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, null);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        nationaliteRepository.save(id);
    }

    public List<Nationalite> findAllBis(Pageable pageable) {
        return nationaliteRepository.findByDeletedFalseOrderByLibelle();
    }
}
