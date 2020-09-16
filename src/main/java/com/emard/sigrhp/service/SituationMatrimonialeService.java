package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.*;
import com.emard.sigrhp.repository.SituationMatrimonialeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing SituationMatrimoniale.
 */
@Service
@Transactional
public class SituationMatrimonialeService {

    private final Logger log = LoggerFactory.getLogger(SituationMatrimonialeService.class);

    private final SituationMatrimonialeRepository situationMatrimonialeRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    private final UserExtraService userExtraService;

    public SituationMatrimonialeService(SituationMatrimonialeRepository situationMatrimonialeRepository, UserService userService, AuditEventService auditEventService, UserExtraService userExtraService) {
        this.situationMatrimonialeRepository = situationMatrimonialeRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
        this.userExtraService = userExtraService;
    }

    /**
     * Save a situationMatrimoniale.
     *
     * @param situationMatrimoniale the entity to save
     * @return the persisted entity
     */
    public SituationMatrimoniale save(SituationMatrimoniale situationMatrimoniale) {
        log.debug("Request to save SituationMatrimoniale : {}", situationMatrimoniale);
        String event= "";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        if (situationMatrimoniale.getId() != null) {
            event = "MODIFICATION_SITUATION_MATRIMONIALE";
        }
        else{
            event = "CREATION_SITUATION_MATRIMONIALE";
        }
        situationMatrimoniale.structure(userExtra.getStructure());
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        return situationMatrimonialeRepository.save(situationMatrimoniale);
    }

    /**
     * Get all the situationMatrimoniales.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SituationMatrimoniale> findAll(Pageable pageable) {
        log.debug("Request to get all SituationMatrimoniales");
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return situationMatrimonialeRepository.findByDeletedFalseAndStructureOrderByLibelle(pageable, structure);
        }
        return situationMatrimonialeRepository.findByDeletedFalseOrderByLibelle(pageable);
    }


    /**
     * Get one situationMatrimoniale by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SituationMatrimoniale> findOne(Long id) {
        log.debug("Request to get SituationMatrimoniale : {}", id);
        return situationMatrimonialeRepository.findById(id);
    }

    /**
     * Delete the situationMatrimoniale by id.
     *
     * @param id the id of the entity
     */
    public void delete(SituationMatrimoniale id) {
        log.debug("Request to delete SituationMatrimoniale : {}", id);
        String event= "SUPPRESSION_SITUATION_MATRIMONIALE";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        situationMatrimonialeRepository.save(id);
    }
}
