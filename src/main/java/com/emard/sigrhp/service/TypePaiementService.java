package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.*;
import com.emard.sigrhp.repository.TypePaiementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing TypePaiement.
 */
@Service
@Transactional
public class TypePaiementService {

    private final Logger log = LoggerFactory.getLogger(TypePaiementService.class);

    private final TypePaiementRepository typePaiementRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    private final UserExtraService userExtraService;

    public TypePaiementService(TypePaiementRepository typePaiementRepository, UserService userService,
                               AuditEventService auditEventService, UserExtraService userExtraService) {
        this.typePaiementRepository = typePaiementRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
        this.userExtraService = userExtraService;
    }

    /**
     * Save a typePaiement.
     *
     * @param typePaiement the entity to save
     * @return the persisted entity
     */
    public TypePaiement save(TypePaiement typePaiement) {
        log.debug("Request to save TypePaiement : {}", typePaiement);
        String event= "";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        if (typePaiement.getId() != null) {
            event = "MODIFICATION_TYPE_PAIEMENT";
        }
        else{
            event = "CREATION_TYPE_PAIEMENT";
        }
        typePaiement.structure(userExtra.getStructure());
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        return typePaiementRepository.save(typePaiement);
    }

    /**
     * Get all the typePaiements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypePaiement> findAll(Pageable pageable) {
        log.debug("Request to get all TypePaiements");
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return typePaiementRepository.findByDeletedFalseAndStructureOrderByLibelle(pageable, structure);
        }
        return typePaiementRepository.findByDeletedFalseOrderByLibelle(pageable);
    }


    /**
     * Get one typePaiement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TypePaiement> findOne(Long id) {
        log.debug("Request to get TypePaiement : {}", id);
        return typePaiementRepository.findById(id);
    }

    /**
     * Delete the typePaiement by id.
     *
     * @param id the id of the entity
     */
    public void delete(TypePaiement id) {
        log.debug("Request to delete TypePaiement : {}", id);
        String event= "SUPPRESSION_AVANTAGE";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        typePaiementRepository.save(id);
    }
}
