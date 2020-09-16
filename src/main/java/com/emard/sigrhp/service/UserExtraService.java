package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.PersistentAuditEvent;
import com.emard.sigrhp.domain.User;
import com.emard.sigrhp.domain.UserExtra;
import com.emard.sigrhp.repository.UserExtraRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing UserExtra.
 */
@Service
@Transactional
public class UserExtraService {

    private final Logger log = LoggerFactory.getLogger(UserExtraService.class);

    private final UserExtraRepository userExtraRepository;

    private final AuditEventService auditEventService;

    public UserExtraService(UserExtraRepository userExtraRepository,  AuditEventService auditEventService
                            ) {
        this.userExtraRepository = userExtraRepository;
        this.auditEventService = auditEventService;
    }

    /**
     * Save a userExtra.
     *
     * @param userExtra the entity to save
     * @return the persisted entity
     */
    public UserExtra save(UserExtra userExtra) {
        log.debug("Request to save UserExtra : {}", userExtra);
        // String event= "";
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return userExtraRepository.save(userExtra);
    }

    /**
     * Get all the userExtras.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserExtra> findAll(Pageable pageable) {
        log.debug("Request to get all UserExtras");
        return userExtraRepository.findByDeletedFalseOrderByUser_LastNameAscUser_FirstNameAsc(pageable);
    }


    /**
     * Get one userExtra by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<UserExtra> findOne(Long id) {
        log.debug("Request to get UserExtra : {}", id);
        return userExtraRepository.findById(id);
    }

    /**
     * Delete the userExtra by id.
     *
     * @param id the id of the entity
     */
    public void delete(UserExtra id) {
        log.debug("Request to delete UserExtra : {}", id);
        /*String event= "SUPPRESSION_ADMIN";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraRepository.getOne(user.getId());
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);

        User user1 = userService.findOne(id.getId()).get();
        user1.setDeleted(true);
        user1.setActivated(false);
        user1.setDateDeleted(Instant.now());*/

        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        //id.setUserDeleted(user.getLogin());
        userExtraRepository.save(id);
    }
}
