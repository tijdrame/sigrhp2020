package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.Exercice;
import com.emard.sigrhp.domain.PersistentAuditEvent;
import com.emard.sigrhp.domain.User;
import com.emard.sigrhp.repository.ExerciceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing Exercice.
 */
@Service
@Transactional
public class ExerciceService {

    private final Logger log = LoggerFactory.getLogger(ExerciceService.class);

    private final ExerciceRepository exerciceRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    public ExerciceService(ExerciceRepository exerciceRepository, UserService userService, AuditEventService auditEventService) {
        this.exerciceRepository = exerciceRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
    }

    /**
     * Save a exercice.
     *
     * @param exercice the entity to save
     * @return the persisted entity
     */
    public Exercice save(Exercice exercice) {
        log.debug("Request to save Exercice : {}", exercice);
        String event;
        if(exercice.getId()!=null) event= "MODIFICATION_EXERCICE";
        else event = "CREATION_EXERCICE";
        exercice.finExercice(exercice.getDebutExercice()+1).actif(false);
        User user = userService.getUserWithAuthorities().get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, null);
        auditEventService.saveAudit(persistentAuditEvent);
        return exerciceRepository.save(exercice);
    }

    /**
     * Get all the exercices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Exercice> findAll(Pageable pageable) {
        log.debug("Request to get all Exercices");
        return exerciceRepository.findByDeletedFalseOrderByDebutExerciceDesc(pageable);
    }


    /**
     * Get one exercice by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Exercice> findOne(Long id) {
        log.debug("Request to get Exercice : {}", id);
        return exerciceRepository.findById(id);
    }

    /**
     * Delete the exercice by id.
     *
     * @param id the id of the entity
     */
    public void delete(Exercice id) {
        log.debug("Request to delete Exercice : {}", id);
        String event= "SUPPRESSION_EXERCICE";
        User user = userService.getUserWithAuthorities().get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, null);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        exerciceRepository.save(id);
    }

    public Optional<Exercice> findByDebutExercice(Integer debutExercice) {
        return exerciceRepository.findByDebutExerciceAndDeletedFalse(debutExercice);
    }
}
