package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.*;
import com.emard.sigrhp.repository.MotifRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing Motif.
 */
@Service
@Transactional
public class MotifService {

    private final Logger log = LoggerFactory.getLogger(MotifService.class);

    private final MotifRepository motifRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    private final UserExtraService userExtraService;

    public MotifService(MotifRepository motifRepository, UserService userService, AuditEventService auditEventService,
                        UserExtraService userExtraService) {
        this.motifRepository = motifRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
        this.userExtraService = userExtraService;
    }

    /**
     * Save a motif.
     *
     * @param motif the entity to save
     * @return the persisted entity
     */
    public Motif save(Motif motif) {
        log.debug("Request to save Motif : {}", motif);
        String event= "";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        if (motif.getId() != null) {
            event = "MODIFICATION_MOTIF";
        }
        else{
            event = "CREATION_MOTIF";
        }
        motif.structure(userExtra.getStructure());
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        return motifRepository.save(motif);
    }

    /**
     * Get all the motifs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Motif> findAll(Pageable pageable) {
        log.debug("Request to get all Motifs");
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return motifRepository.findByDeletedFalseAndStructureOrderByLibelle(pageable, structure);
        }
        return motifRepository.findByDeletedFalseOrderByLibelle(pageable);
    }


    /**
     * Get one motif by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Motif> findOne(Long id) {
        log.debug("Request to get Motif : {}", id);
        return motifRepository.findById(id);
    }

    /**
     * Delete the motif by id.
     *
     * @param id the id of the entity
     */
    public void delete(Motif id) {
        log.debug("Request to delete Motif : {}", id);
        String event= "SUPPRESSION_MOTIF";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        motifRepository.save(id);
    }

    public Motif findByCode(String p) {
        return motifRepository.findByCode(p);
    }
}
