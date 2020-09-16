package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.*;
import com.emard.sigrhp.repository.RegimeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing Regime.
 */
@Service
@Transactional
public class RegimeService {

    private final Logger log = LoggerFactory.getLogger(RegimeService.class);

    private final RegimeRepository regimeRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    private final UserExtraService userExtraService;

    public RegimeService(RegimeRepository regimeRepository, UserService userService,
                         AuditEventService auditEventService, UserExtraService userExtraService) {
        this.regimeRepository = regimeRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
        this.userExtraService = userExtraService;
    }

    /**
     * Save a regime.
     *
     * @param regime the entity to save
     * @return the persisted entity
     */
    public Regime save(Regime regime) {
        log.debug("Request to save Regime : {}", regime);
        String event= "";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        if (regime.getId() != null) {
            event = "MODIFICATION_REGIME";
        }
        else{
            event = "CREATION_REGIME";
        }
        regime.structure(userExtra.getStructure());
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        return regimeRepository.save(regime);
    }

    /**
     * Get all the regimes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Regime> findAll(Pageable pageable) {
        log.debug("Request to get all Regimes");
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return regimeRepository.findByDeletedFalseAndStructureOrderByLibelle(pageable, structure);
        }
        return regimeRepository.findByDeletedFalseOrderByLibelle(pageable);
    }


    /**
     * Get one regime by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Regime> findOne(Long id) {
        log.debug("Request to get Regime : {}", id);
        return regimeRepository.findById(id);
    }

    /**
     * Delete the regime by id.
     *
     * @param id the id of the entity
     */
    public void delete(Regime id) {
        log.debug("Request to delete Regime : {}", id);
        String event= "SUPPRESSION_REGIME";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        regimeRepository.save(id);
    }
}
