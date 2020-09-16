package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.*;
import com.emard.sigrhp.repository.MembreFamilleRepository;
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
 * Service Implementation for managing MembreFamille.
 */
@Service
@Transactional
public class MembreFamilleService {

    private final Logger log = LoggerFactory.getLogger(MembreFamilleService.class);

    private final MembreFamilleRepository membreFamilleRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    private final UserExtraService userExtraService;

    public MembreFamilleService(MembreFamilleRepository membreFamilleRepository, UserService userService, AuditEventService auditEventService, UserExtraService userExtraService) {
        this.membreFamilleRepository = membreFamilleRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
        this.userExtraService = userExtraService;
    }

    /**
     * Save a membreFamille.
     *
     * @param membreFamille the entity to save
     * @return the persisted entity
     */
    public MembreFamille save(MembreFamille membreFamille) {
        log.debug("Request to save MembreFamille : {}", membreFamille);
        String event= "";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        if (membreFamille.getId() != null) {
            event = "MODIFICATION_MEMBRE_FAMILLE";
        }
        else{
            event = "CREATION_MEMBRE_FAMILLE";
        }
        //membreFamille.structure(userExtra.getStructure());
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        return membreFamilleRepository.save(membreFamille);
    }

    /**
     * Get all the membreFamilles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MembreFamille> findAll(Pageable pageable) {
        log.debug("Request to get all MembreFamilles");
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return membreFamilleRepository.findByDeletedFalseAndCollaborateur_StructureOrderByNomAscPrenomAsc(pageable, structure);
        }
        return membreFamilleRepository.findByDeletedFalseOrderByNomAscPrenomAsc(pageable);
    }


    /**
     * Get one membreFamille by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MembreFamille> findOne(Long id) {
        log.debug("Request to get MembreFamille : {}", id);
        return membreFamilleRepository.findById(id);
    }

    /**
     * Delete the membreFamille by id.
     *
     * @param id the id of the entity
     */
    public void delete(MembreFamille id) {
        log.debug("Request to delete MembreFamille : {}", id);
        String event= "SUPPRESSION_MEMBRE_FAMILLE";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        membreFamilleRepository.save(id);
    }

    public List<MembreFamille> findByCollaborateur(Collaborateur collaborateur) {
        return membreFamilleRepository.findByCollaborateur(collaborateur);
    }
}
