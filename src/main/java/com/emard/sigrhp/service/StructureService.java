package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.PersistentAuditEvent;
import com.emard.sigrhp.domain.Structure;
import com.emard.sigrhp.domain.User;
import com.emard.sigrhp.domain.UserExtra;
import com.emard.sigrhp.repository.StructureRepository;
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
 * Service Implementation for managing Structure.
 */
@Service
@Transactional
public class StructureService {

    private final Logger log = LoggerFactory.getLogger(StructureService.class);

    private final StructureRepository structureRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    private final UserExtraService userExtraService;

    public StructureService(StructureRepository structureRepository, UserService userService, AuditEventService auditEventService, UserExtraService userExtraService) {
        this.structureRepository = structureRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
        this.userExtraService = userExtraService;
    }

    /**
     * Save a structure.
     *
     * @param structure the entity to save
     * @return the persisted entity
     */
    public Structure save(Structure structure) {
        log.debug("Request to save Structure : {}", structure);
        String event;
        if(structure.getId()!=null) event= "MODIFICATION_STRUCTURE";
        else event = "CREATION_STRUCTURE";
        User user = userService.getUserWithAuthorities().get();

        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, null);
        auditEventService.saveAudit(persistentAuditEvent);
        return structureRepository.save(structure);
    }

    /**
     * Get all the structures.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Structure> findAll(Pageable pageable) {
        log.debug("Request to get all Structures");
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            Optional<Long> id = Optional.of(structure.get().getId());
            return structureRepository.findByDeletedFalseAndId(pageable, id);
        }
        return structureRepository.findByDeletedFalseOrderByDenomination(pageable);
    }


    /**
     * Get one structure by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Structure> findOne(Long id) {
        log.debug("Request to get Structure : {}", id);
        return structureRepository.findById(id);
    }

    /**
     * Delete the structure by id.
     *
     * @param id the id of the entity
     */
    public void delete(Structure id) {
        log.debug("Request to delete Structure : {}", id);
        String event= "SUPPRESSION_STRUCTURE";
        User user = userService.getUserWithAuthorities().get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, null);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        structureRepository.save(id);
    }

    public Structure findByDenomination(String denomination) {
        return structureRepository.findByDenomination(denomination);
    }
}
