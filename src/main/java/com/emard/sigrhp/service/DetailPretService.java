package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.*;
import com.emard.sigrhp.repository.DetailPretRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing DetailPret.
 */
@Service
@Transactional
public class DetailPretService {

    private final Logger log = LoggerFactory.getLogger(DetailPretService.class);

    private final DetailPretRepository detailPretRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    private final UserExtraService userExtraService;

    private final RemboursementService remboursementService;

    public DetailPretService(DetailPretRepository detailPretRepository, UserService userService, AuditEventService auditEventService,
                             UserExtraService userExtraService, RemboursementService remboursementService) {
        this.detailPretRepository = detailPretRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
        this.userExtraService = userExtraService;
        this.remboursementService = remboursementService;
    }

    /**
     * Save a detailPret.
     *
     * @param detailPret the entity to save
     * @return the persisted entity
     */
    public DetailPret save(DetailPret detailPret) {
        log.debug("Request to save DetailPret : {}", detailPret);
        String event= "";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        if (detailPret.getId() != null) {
            event = "MODIFICATION_DETAIL_PRET";
        }
        else{
            event = "CREATION_DETAIL_PRET";
        }

        Integer nbRemb = detailPret.getPret().getNbRemboursement();
        Double montant = detailPret.getMontant()/nbRemb;
        LocalDate ld = detailPret.getPret().getDateDebutRemboursement();
        detailPret = detailPretRepository.save(detailPret);
        for(int i=0; i<nbRemb;i++){
            Remboursement remboursement = new Remboursement();
            remboursement.detailPret(detailPret).montant(montant).
                dateRemboursement(ld).isRembourse(false);
            remboursement.detailPret(detailPret);
            remboursementService.save(remboursement);
            ld = ld.plusMonths(1);
        }

        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        return detailPret;
    }

    /**
     * Get all the detailPrets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DetailPret> findAll(Pageable pageable) {
        log.debug("Request to get all DetailPrets");
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return detailPretRepository.findByDeletedFalseAndCollaborateur_StructureOrderByPret_Libelle(pageable, structure);
        }
        return detailPretRepository.findByDeletedFalseOrderByPret_Libelle(pageable);
    }


    /**
     * Get one detailPret by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DetailPret> findOne(Long id) {
        log.debug("Request to get DetailPret : {}", id);
        return detailPretRepository.findById(id);
    }

    /**
     * Delete the detailPret by id.
     *
     * @param id the id of the entity
     */
    public void delete(DetailPret id) {
        log.debug("Request to delete DetailPret : {}", id);
        String event= "SUPPRESSION_DETAIL_PRET";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        List<Remboursement> remboursements = remboursementService.findByDetailPret(id);
        for (Remboursement remboursement: remboursements){
            remboursement.setDeleted(false);
            remboursement.setDateDeleted(Instant.now());
            remboursement.setUserDeleted(user.getLogin());
            remboursementService.delete(remboursement);
        }
        detailPretRepository.save(id);
    }
}
