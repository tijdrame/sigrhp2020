package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.PersistentAuditEvent;
import com.emard.sigrhp.domain.TypeAbsence;
import com.emard.sigrhp.domain.User;
import com.emard.sigrhp.repository.TypeAbsenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing TypeAbsence.
 */
@Service
@Transactional
public class TypeAbsenceService {

    private final Logger log = LoggerFactory.getLogger(TypeAbsenceService.class);

    private final TypeAbsenceRepository typeAbsenceRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    public TypeAbsenceService(TypeAbsenceRepository typeAbsenceRepository, UserService userService, AuditEventService auditEventService) {
        this.typeAbsenceRepository = typeAbsenceRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
    }

    /**
     * Save a typeAbsence.
     *
     * @param typeAbsence the entity to save
     * @return the persisted entity
     */
    public TypeAbsence save(TypeAbsence typeAbsence) {
        log.debug("Request to save TypeAbsence : {}", typeAbsence);
        String event;
        if(typeAbsence.getId()!=null) event= "MODIFICATION_TYPE_ABSENCE";
        else event = "CREATION__TYPE_ABSENCE";
        User user = userService.getUserWithAuthorities().get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, null);
        auditEventService.saveAudit(persistentAuditEvent);
        return typeAbsenceRepository.save(typeAbsence);
    }

    /**
     * Get all the typeAbsences.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeAbsence> findAll(Pageable pageable) {
        log.debug("Request to get all TypeAbsences");
        return typeAbsenceRepository.findByDeletedFalseOrderByLibelle(pageable);
    }


    /**
     * Get one typeAbsence by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TypeAbsence> findOne(Long id) {
        log.debug("Request to get TypeAbsence : {}", id);
        return typeAbsenceRepository.findById(id);
    }

    /**
     * Delete the typeAbsence by id.
     *
     * @param id the id of the entity
     */
    public void delete(TypeAbsence id) {
        log.debug("Request to delete TypeAbsence : {}", id);
        String event= "SUPPRESSION_TYPE_ABSENCE";
        User user = userService.getUserWithAuthorities().get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, null);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        typeAbsenceRepository.save(id);
    }
}
