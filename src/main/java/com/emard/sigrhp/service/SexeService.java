package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.PersistentAuditEvent;
import com.emard.sigrhp.domain.Sexe;
import com.emard.sigrhp.domain.User;
import com.emard.sigrhp.repository.SexeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing Sexe.
 */
@Service
@Transactional
public class SexeService {

    private final Logger log = LoggerFactory.getLogger(SexeService.class);

    private final SexeRepository sexeRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    public SexeService(SexeRepository sexeRepository, UserService userService, AuditEventService auditEventService) {
        this.sexeRepository = sexeRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
    }

    /**
     * Save a sexe.
     *
     * @param sexe the entity to save
     * @return the persisted entity
     */
    public Sexe save(Sexe sexe) {
        log.debug("Request to save Sexe : {}", sexe);
        String event;
        if(sexe.getId()!=null) event= "MODIFICATION_SEXE";
        else event = "CREATION_SEXE";
        User user = userService.getUserWithAuthorities().get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, null);
        auditEventService.saveAudit(persistentAuditEvent);
        return sexeRepository.save(sexe);
    }

    /**
     * Get all the sexes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Sexe> findAll(Pageable pageable) {
        log.debug("Request to get all Sexes");
        return sexeRepository.findByDeletedFalseOrderByLibelle(pageable);
    }


    /**
     * Get one sexe by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Sexe> findOne(Long id) {
        log.debug("Request to get Sexe : {}", id);
        return sexeRepository.findById(id);
    }

    /**
     * Delete the sexe by id.
     *
     * @param id the id of the entity
     */
    public void delete(Sexe id) {
        log.debug("Request to delete Sexe : {}", id);
        String event= "SUPPRESSION_CONVENTION";
        User user = userService.getUserWithAuthorities().get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, null);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        sexeRepository.save(id);
    }
}
