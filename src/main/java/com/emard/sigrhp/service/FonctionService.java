package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.*;
import com.emard.sigrhp.repository.FonctionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing Fonction.
 */
@Service
@Transactional
public class FonctionService {

    private final Logger log = LoggerFactory.getLogger(FonctionService.class);

    private final FonctionRepository fonctionRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    private final UserExtraService userExtraService;

    public FonctionService(FonctionRepository fonctionRepository, UserService userService,
                           AuditEventService auditEventService, UserExtraService userExtraService) {
        this.fonctionRepository = fonctionRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
        this.userExtraService = userExtraService;
    }

    /**
     * Save a fonction.
     *
     * @param fonction the entity to save
     * @return the persisted entity
     */
    public Fonction save(Fonction fonction) {
        log.debug("Request to save Fonction : {}", fonction);
        String event= "";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        if (fonction.getId() != null) {
            event = "MODIFICATION_FONCTION";
        }
        else{
            event = "CREATION_FONCTION";
        }
        fonction.structure(userExtra.getStructure());
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        return fonctionRepository.save(fonction);
    }

    /**
     * Get all the fonctions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Fonction> findAll(Pageable pageable) {
        log.debug("Request to get all Fonctions");
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return fonctionRepository.findByDeletedFalseAndStructureOrderByLibelle(pageable, structure);
        }
        return fonctionRepository.findByDeletedFalseOrderByLibelle(pageable);
    }


    /**
     * Get one fonction by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Fonction> findOne(Long id) {
        log.debug("Request to get Fonction : {}", id);
        return fonctionRepository.findById(id);
    }

    /**
     * Delete the fonction by id.
     *
     * @param id the id of the entity
     */
    public void delete(Fonction id) {
        log.debug("Request to delete Fonction : {}", id);
        String event= "SUPPRESSION_FONCTION";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        fonctionRepository.save(id);
    }
}
