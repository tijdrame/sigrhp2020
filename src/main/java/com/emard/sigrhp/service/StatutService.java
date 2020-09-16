package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.*;
import com.emard.sigrhp.repository.StatutRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing Statut.
 */
@Service
@Transactional
public class StatutService {

    private final Logger log = LoggerFactory.getLogger(StatutService.class);

    private final StatutRepository statutRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    private final UserExtraService userExtraService;

    public StatutService(StatutRepository statutRepository, UserService userService,
                         AuditEventService auditEventService, UserExtraService userExtraService) {
        this.statutRepository = statutRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
        this.userExtraService = userExtraService;
    }

    /**
     * Save a statut.
     *
     * @param statut the entity to save
     * @return the persisted entity
     */
    public Statut save(Statut statut) {
        log.debug("Request to save Statut : {}", statut);
        String event= "";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        if (statut.getId() != null) {
            event = "MODIFICATION_STATUT";
        }
        else{
            event = "CREATION_STATUT";
        }
        statut.structure(userExtra.getStructure());
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        return statutRepository.save(statut);
    }

    /**
     * Get all the statuts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Statut> findAll(Pageable pageable) {
        log.debug("Request to get all Statuts");
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return statutRepository.findByDeletedFalseAndStructureOrderByLibelle(pageable, structure);
        }
        return statutRepository.findByDeletedFalseOrderByLibelle(pageable);
    }


    /**
     * Get one statut by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Statut> findOne(Long id) {
        log.debug("Request to get Statut : {}", id);
        return statutRepository.findById(id);
    }

    /**
     * Delete the statut by id.
     *
     * @param id the id of the entity
     */
    public void delete(Statut id) {
        log.debug("Request to delete Statut : {}", id);
        String event= "SUPPRESSION_STATUT";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        statutRepository.save(id);
    }
}
