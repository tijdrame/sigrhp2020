package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.*;
import com.emard.sigrhp.repository.PrimeCollabRepository;
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
 * Service Implementation for managing PrimeCollab.
 */
@Service
@Transactional
public class PrimeCollabService {

    private final Logger log = LoggerFactory.getLogger(PrimeCollabService.class);

    private final PrimeCollabRepository primeCollabRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    private final UserExtraService userExtraService;

    public PrimeCollabService(PrimeCollabRepository primeCollabRepository, UserService userService, AuditEventService auditEventService, UserExtraService userExtraService) {
        this.primeCollabRepository = primeCollabRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
        this.userExtraService = userExtraService;
    }

    /**
     * Save a primeCollab.
     *
     * @param primeCollab the entity to save
     * @return the persisted entity
     */
    public PrimeCollab save(PrimeCollab primeCollab) {
        log.debug("Request to save PrimeCollab : {}", primeCollab);
        String event= "";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        if (primeCollab.getId() != null) {
            event = "MODIFICATION_PRIME_COLLAB";
        }
        else{
            event = "CREATION_PRIME_COLLAB";
        }
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        return primeCollabRepository.save(primeCollab);
    }

    /**
     * Get all the primeCollabs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PrimeCollab> findAll(Pageable pageable) {
        log.debug("Request to get all PrimeCollabs");
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return primeCollabRepository.findByDeletedFalseAndCollaborateur_StructureOrderByPrime_Libelle(pageable, structure);
        }
        return primeCollabRepository.findByDeletedFalseOrderByPrime_Libelle(pageable);
    }


    /**
     * Get one primeCollab by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PrimeCollab> findOne(Long id) {
        log.debug("Request to get PrimeCollab : {}", id);
        return primeCollabRepository.findById(id);
    }

    /**
     * Delete the primeCollab by id.
     *
     * @param id the id of the entity
     */
    public void delete(PrimeCollab id) {
        log.debug("Request to delete PrimeCollab : {}", id);
        String event= "SUPPRESSION_PRIME_COLLAB";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        primeCollabRepository.save(id);
    }

    @Transactional(readOnly = true)
    public List<PrimeCollab> findByCollaborateur(Collaborateur collaborateur) {
        return  primeCollabRepository.findByCollaborateurAndDeletedFalse(collaborateur);
    }
}
