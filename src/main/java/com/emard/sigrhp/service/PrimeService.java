package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.*;
import com.emard.sigrhp.repository.PrimeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing Prime.
 */
@Service
@Transactional
public class PrimeService {

    private final Logger log = LoggerFactory.getLogger(PrimeService.class);

    private final PrimeRepository primeRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    private final UserExtraService userExtraService;

    public PrimeService(PrimeRepository primeRepository, UserService userService, AuditEventService auditEventService,
                        UserExtraService userExtraService) {
        this.primeRepository = primeRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
        this.userExtraService = userExtraService;
    }

    /**
     * Save a prime.
     *
     * @param prime the entity to save
     * @return the persisted entity
     */
    public Prime save(Prime prime) {
        log.debug("Request to save Prime : {}", prime);
        String event= "";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        if (prime.getId() != null) {
            event = "MODIFICATION_PRIME";
        }
        else{
            event = "CREATION_PRIME";
        }
        prime.structure(userExtra.getStructure());
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        return primeRepository.save(prime);
    }

    /**
     * Get all the primes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Prime> findAll(Pageable pageable) {
        log.debug("Request to get all Primes");
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return primeRepository.findByDeletedFalseAndStructureOrderByLibelle(pageable, structure);
        }
        return primeRepository.findByDeletedFalseOrderByLibelle(pageable);
    }


    /**
     * Get one prime by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Prime> findOne(Long id) {
        log.debug("Request to get Prime : {}", id);
        return primeRepository.findById(id);
    }

    /**
     * Delete the prime by id.
     *
     * @param id the id of the entity
     */
    public void delete(Prime id) {
        log.debug("Request to delete Prime : {}", id);
        String event= "SUPPRESSION_PRIME";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        primeRepository.save(id);
    }
}
