package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.*;
import com.emard.sigrhp.repository.BulletinRepository;
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
 * Service Implementation for managing Bulletin.
 */
@Service
@Transactional
public class BulletinService {

    private final Logger log = LoggerFactory.getLogger(BulletinService.class);

    private final BulletinRepository bulletinRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    private final UserExtraService userExtraService;

    private final RemboursementService remboursementService;

    private final DetailPretService detailPretService;

    private final ExerciceService exerciceService;
    private final MoisConcerneService moisConcerneService;

    public BulletinService(BulletinRepository bulletinRepository, UserService userService,
                           AuditEventService auditEventService, UserExtraService userExtraService,
                           RemboursementService remboursementService, DetailPretService detailPretService,
                           ExerciceService exerciceService, MoisConcerneService moisConcerneService) {
        this.bulletinRepository = bulletinRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
        this.userExtraService = userExtraService;
        this.remboursementService = remboursementService;
        this.detailPretService = detailPretService;
        this.exerciceService = exerciceService;
        this.moisConcerneService = moisConcerneService;
    }

    /**
     * Save a bulletin.
     *
     * @param bulletin the entity to save
     * @return the persisted entity
     */
    public Bulletin save(Bulletin bulletin) {
        log.debug("Request to save Bulletin : {}", bulletin);
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        String event="";
        if (bulletin.getId() != null) {
            event = "MODIFICATION_BULLETIN";
        }
        else{
            event = "CREATION_BULLETIN";
        }
        if(bulletin.getRemboursements()!=null){
            for (Remboursement remboursement: bulletin.getRemboursements()) {
                remboursement.isRembourse(true);
                remboursement.setLastModifiedBy(user.getLogin());
                this.remboursementService.update(remboursement);
                if(!this.pretRembourse(remboursement.getDetailPret())){
                    remboursement.getDetailPret().isRembourse(true);
                    remboursement.setLastModifiedBy(user.getLogin());
                    detailPretService.save(remboursement.getDetailPret());
                }
            }
        }


        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        return bulletinRepository.save(bulletin);
    }

    /**
     * Permet de voir si toutes les remboursement ont été fait pour ce detail Pret
     * @param detailPret
     * @return
     */
    private Boolean pretRembourse(DetailPret detailPret){
        List<Remboursement> list = remboursementService.findByRemboursement(detailPret);
        if (!list.isEmpty()){
            for (Remboursement remboursement: list){
                if(!remboursement.isIsRembourse()) return true;
            }
        }
        return false;
    }

    /**
     * Get all the bulletins.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Bulletin> findAll(Pageable pageable) {
        log.debug("Request to get all Bulletins");
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return bulletinRepository.findByDeletedFalseAndCollaborateur_StructureOrderByCollaborateur_NomAscCollaborateur_PrenomAsc(pageable, structure);
        }
        return bulletinRepository.findByDeletedFalseOrderByCollaborateur_NomAscCollaborateur_PrenomAsc(pageable);
    }

    /**
     * Get all the bulletins.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Bulletin> findAllBis(String prenom, String nom, String matricule, MoisConcerne moisConcerne,
                                     Exercice exercice, Boolean supprime, Pageable pageable) {
        log.debug("Request to get all Bulletins");
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return bulletinRepository.findByCollaborateur_StructureAndCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndCollaborateur_MatriculeLikeIgnoreCaseAndMoisConcerneAndExerciceAndDeletedOrderByCollaborateur_NomAscCollaborateur_PrenomAsc(structure, prenom, nom, matricule, moisConcerne, exercice, supprime, pageable);
        }
        return bulletinRepository.findByCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndCollaborateur_MatriculeLikeIgnoreCaseAndMoisConcerneAndExerciceAndDeletedOrderByCollaborateur_NomAscCollaborateur_PrenomAsc(prenom, nom, matricule, moisConcerne, exercice, supprime, pageable);
    }

    /**
     * Get all the Bulletin with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Bulletin> findAllWithEagerRelationships(Pageable pageable) {
        return bulletinRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one bulletin by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Bulletin> findOne(Long id) {
        log.debug("Request to get Bulletin : {}", id);
        return bulletinRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the bulletin by id.
     *
     * @param id the id of the entity
     */
    public void delete(Bulletin id) {
        log.debug("Request to delete Bulletin : {}", id);
        String event= "SUPPRESSION_BULLETIN";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());
        bulletinRepository.save(id);
    }

    public Page<Bulletin> findAllBis(String prenom, String nom, String matricule, Long idExercice, Long idMoisConcerne,
                                     Boolean deleted, Pageable pageable) {
        log.debug("Request to get search bis Bulletin");
        prenom = "%"+prenom+"%";
        nom = "%"+nom+"%";
        matricule = "%"+matricule+"%";
        Optional<Exercice> exercice= null;
        Optional<MoisConcerne> moisConcerne= null;
        String mot="";
        if(idExercice != null){
            exercice = exerciceService.findOne(Long.valueOf(idExercice));
        }

        if(idMoisConcerne != null) {
            moisConcerne = moisConcerneService.findOne(Long.valueOf(idMoisConcerne));
            mot = moisConcerne.get().getLibelle();
        }
        mot = "%"+mot+"%";
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return bulletinRepository
                .findByCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndCollaborateur_MatriculeLikeIgnoreCaseAndCollaborateur_StructureAndExercice_DebutExerciceAndMoisConcerne_LibelleLikeIgnoreCaseAndDeletedOrderByCreatedDate
                    (prenom, nom, matricule, structure, exercice.get().getDebutExercice(), mot, deleted, pageable);
        }
        return bulletinRepository
            .findByCollaborateur_PrenomLikeIgnoreCaseAndCollaborateur_NomLikeIgnoreCaseAndCollaborateur_MatriculeLikeIgnoreCaseAndExercice_DebutExerciceAndMoisConcerne_LibelleLikeIgnoreCaseAndDeletedOrderByCreatedDate
                (prenom, nom, matricule, exercice.get().getDebutExercice(), mot, deleted, pageable);
    }
}
