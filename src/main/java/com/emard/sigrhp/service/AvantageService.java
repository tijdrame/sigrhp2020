package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.*;
import com.emard.sigrhp.repository.AvantageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing Avantage.
 */
@Service
@Transactional
public class AvantageService {

    private final Logger log = LoggerFactory.getLogger(AvantageService.class);

    private final AvantageRepository avantageRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    private final UserExtraService userExtraService;

    public AvantageService(AvantageRepository avantageRepository, UserService userService, AuditEventService auditEventService,
                           UserExtraService userExtraService) {
        this.avantageRepository = avantageRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
        this.userExtraService = userExtraService;
    }

    /**
     * Save a avantage.
     *
     * @param avantage the entity to save
     * @return the persisted entity
     */
    public Avantage save(Avantage avantage) {
        log.debug("Request to save Avantage : {}", avantage);
        String event= "";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        if (avantage.getId() != null) {
            event = "MODIFICATION_AVANTAGE";
        }
        else{
            event = "CREATION_AVANTAGE";
        }
        avantage.structure(userExtra.getStructure());
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        return avantageRepository.save(avantage);
    }

    /**
     * Get all the avantages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Avantage> findAll(Pageable pageable) {
        log.debug("Request to get all Avantages");
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return avantageRepository.findByDeletedFalseAndStructureOrderByLibelle(pageable, structure);
        }
        return avantageRepository.findByDeletedFalseOrderByLibelle(pageable);

    }


    /**
     * Get one avantage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Avantage> findOne(Long id) {
        log.debug("Request to get Avantage : {}", id);
        return avantageRepository.findById(id);
    }

    /**
     * Delete the avantage by id.
     *
     * @param id the id of the entity
     */
    public void delete(Avantage id) {
        log.debug("Request to delete Avantage : {}", id);
        String event= "SUPPRESSION_AVANTAGE";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        avantageRepository.save(id);
    }
}
