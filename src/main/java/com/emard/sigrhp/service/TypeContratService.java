package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.*;
import com.emard.sigrhp.repository.TypeContratRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing TypeContrat.
 */
@Service
@Transactional
public class TypeContratService {

    private final Logger log = LoggerFactory.getLogger(TypeContratService.class);

    private final TypeContratRepository typeContratRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    private final UserExtraService userExtraService;

    public TypeContratService(TypeContratRepository typeContratRepository, UserService userService,
                              AuditEventService auditEventService, UserExtraService userExtraService) {
        this.typeContratRepository = typeContratRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
        this.userExtraService = userExtraService;
    }

    /**
     * Save a typeContrat.
     *
     * @param typeContrat the entity to save
     * @return the persisted entity
     */
    public TypeContrat save(TypeContrat typeContrat) {
        log.debug("Request to save TypeContrat : {}", typeContrat);
        String event= "";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        if (typeContrat.getId() != null) {
            event = "MODIFICATION_TYPE_CONTRAT";
        }
        else{
            event = "CREATION_TYPE_CONTRAT";
        }
        typeContrat.structure(userExtra.getStructure());
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        return typeContratRepository.save(typeContrat);
    }

    /**
     * Get all the typeContrats.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeContrat> findAll(Pageable pageable) {
        log.debug("Request to get all TypeContrats");
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return typeContratRepository.findByDeletedFalseAndStructureOrderByLibelle(pageable, structure);
        }
        return typeContratRepository.findByDeletedFalseOrderByLibelle(pageable);
    }


    /**
     * Get one typeContrat by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TypeContrat> findOne(Long id) {
        log.debug("Request to get TypeContrat : {}", id);
        return typeContratRepository.findById(id);
    }

    /**
     * Delete the typeContrat by id.
     *
     * @param id the id of the entity
     */
    public void delete(TypeContrat id) {
        log.debug("Request to delete TypeContrat : {}", id);
        String event= "SUPPRESSION_TYPE_CONTRAT";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        typeContratRepository.save(id);
    }
}
