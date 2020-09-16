package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.*;
import com.emard.sigrhp.repository.PretRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing Pret.
 */
@Service
@Transactional
public class PretService {

    private final Logger log = LoggerFactory.getLogger(PretService.class);

    private final PretRepository pretRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    private final UserExtraService userExtraService;

    public PretService(PretRepository pretRepository, UserService userService, AuditEventService auditEventService, UserExtraService userExtraService) {
        this.pretRepository = pretRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
        this.userExtraService = userExtraService;
    }

    /**
     * Save a pret.
     *
     * @param pret the entity to save
     * @return the persisted entity
     */
    public Pret save(Pret pret) {
        log.debug("Request to save Pret : {}", pret);
        String event= "";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        if (pret.getId() != null) {
            event = "MODIFICATION_PRET";
        }
        else{
            event = "CREATION_PRET";
        }
        pret.structure(userExtra.getStructure());
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        return pretRepository.save(pret);
    }

    /**
     * Get all the prets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Pret> findAll(Pageable pageable) {
        log.debug("Request to get all Prets");
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return pretRepository.findByDeletedFalseAndStructureOrderByLibelle(pageable, structure);
        }
        return pretRepository.findByDeletedFalseOrderByLibelle(pageable);
    }


    /**
     * Get one pret by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Pret> findOne(Long id) {
        log.debug("Request to get Pret : {}", id);
        return pretRepository.findById(id);
    }

    /**
     * Delete the pret by id.
     *
     * @param id the id of the entity
     */
    public void delete(Pret id) {
        log.debug("Request to delete Pret : {}", id);
        String event= "SUPPRESSION_PRET";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        pretRepository.save(id);
    }
}
