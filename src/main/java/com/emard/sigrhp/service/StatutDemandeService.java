package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.PersistentAuditEvent;
import com.emard.sigrhp.domain.StatutDemande;
import com.emard.sigrhp.domain.User;
import com.emard.sigrhp.repository.StatutDemandeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing StatutDemande.
 */
@Service
@Transactional
public class StatutDemandeService {

    private final Logger log = LoggerFactory.getLogger(StatutDemandeService.class);

    private final StatutDemandeRepository statutDemandeRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    public StatutDemandeService(StatutDemandeRepository statutDemandeRepository, UserService userService, AuditEventService auditEventService) {
        this.statutDemandeRepository = statutDemandeRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
    }

    /**
     * Save a statutDemande.
     *
     * @param statutDemande the entity to save
     * @return the persisted entity
     */
    public StatutDemande save(StatutDemande statutDemande) {
        log.debug("Request to save StatutDemande : {}", statutDemande);
        String event;
        if(statutDemande.getId()!=null) event= "MODIFICATION_STATUT_DEMANDE";
        else event = "CREATION_STATUT_DEMANDE";
        User user = userService.getUserWithAuthorities().get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, null);
        auditEventService.saveAudit(persistentAuditEvent);
        return statutDemandeRepository.save(statutDemande);
    }

    /**
     * Get all the statutDemandes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StatutDemande> findAll(Pageable pageable) {
        log.debug("Request to get all StatutDemandes");
        return statutDemandeRepository.findByDeletedFalseOrderByLibelle(pageable);
    }


    /**
     * Get one statutDemande by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<StatutDemande> findOne(Long id) {
        log.debug("Request to get StatutDemande : {}", id);
        return statutDemandeRepository.findById(id);
    }

    /**
     * Delete the statutDemande by id.
     *
     * @param id the id of the entity
     */
    public void delete(StatutDemande id) {
        log.debug("Request to delete StatutDemande : {}", id);
        String event= "SUPPRESSION_STATUT_DEMANDE";
        User user = userService.getUserWithAuthorities().get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, null);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        statutDemandeRepository.save(id);
    }

    public StatutDemande findByCode(String encours) {
        return statutDemandeRepository.findByCode(encours);
    }
}
