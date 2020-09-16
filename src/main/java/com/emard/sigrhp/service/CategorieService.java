package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.*;
import com.emard.sigrhp.repository.CategorieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing Categorie.
 */
@Service
@Transactional
public class CategorieService {

    private final Logger log = LoggerFactory.getLogger(CategorieService.class);

    private final CategorieRepository categorieRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    private final UserExtraService userExtraService;


    public CategorieService(CategorieRepository categorieRepository, UserService userService, AuditEventService auditEventService,
                            UserExtraService userExtraService) {
        this.categorieRepository = categorieRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
        this.userExtraService = userExtraService;
    }

    /**
     * Save a categorie.
     *
     * @param categorie the entity to save
     * @return the persisted entity
     */
    public Categorie save(Categorie categorie) {
        log.debug("Request to save Categorie : {}", categorie);
        String event= "";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        if (categorie.getId() != null) {
            event = "MODIFICATION_CATEGORIE";
        }
        else{
            event = "CREATION_CATEGORIE";
        }
        categorie.structure(userExtra.getStructure());
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        return categorieRepository.save(categorie);
    }

    /**
     * Get all the categories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Categorie> findAll(Pageable pageable) {
        log.debug("Request to get all Categories");
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return categorieRepository.findByDeletedFalseAndStructureOrderByLibelle(pageable, structure);
        }
        return categorieRepository.findByDeletedFalseOrderByLibelle(pageable);
    }


    /**
     * Get one categorie by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Categorie> findOne(Long id) {
        log.debug("Request to get Categorie : {}", id);
        return categorieRepository.findById(id);
    }

    /**
     * Delete the categorie by id.
     *
     * @param categorie the id of the entity
     */
    public void delete(Categorie categorie) {
        log.debug("Request to delete Categorie : {}", categorie);
        String event= "SUPPRESSION_CATEGORIE";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        categorie.setDeleted(true);
        categorie.setDateDeleted(Instant.now());
        categorie.setUserDeleted(user.getLogin());
        categorieRepository.save(categorie);
    }
}
