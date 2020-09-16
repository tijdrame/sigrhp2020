package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.*;
import com.emard.sigrhp.repository.CollaborateurRepository;
import com.emard.sigrhp.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing Collaborateur.
 */
@Service
@Transactional
public class CollaborateurService {

    private final Logger log = LoggerFactory.getLogger(CollaborateurService.class);

    private final CollaborateurRepository collaborateurRepository;

    private final UserService userService;

    private final AuditEventService auditEventService;

    private final UserExtraService userExtraService;

    private final MailService mailService;

    public CollaborateurService(CollaborateurRepository collaborateurRepository, UserService userService, AuditEventService auditEventService, UserExtraService userExtraService, MailService mailService) {
        this.collaborateurRepository = collaborateurRepository;
        this.userService = userService;
        this.auditEventService = auditEventService;
        this.userExtraService = userExtraService;
        this.mailService = mailService;
    }

    /**
     * Save a collaborateur.
     *
     * @param collaborateur the entity to save
     * @return the persisted entity
     */
    public Collaborateur save(Collaborateur collaborateur) {
        log.debug("Request to save Collaborateur : {}", collaborateur);
        String event= "";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        if (collaborateur.getId() != null) {
            event = "MODIFICATION_COLLABORATEUR";
        }
        else{
            event = "CREATION_COLLABORATEUR";
            UserDTO userDTO = new UserDTO();
            userDTO.setLogin(collaborateur.getLogin());
            userDTO.setFirstName(collaborateur.getPrenom());
            userDTO.setLastName(collaborateur.getNom());
            userDTO.setEmail(collaborateur.getEmail());
            User us = userService.createUser(userDTO);
            Authority authority = new Authority();
            authority.setName("ROLE_USER");
            us.getAuthorities().add(authority);
            collaborateur.userCollab(us);
            mailService.sendCreationEmail(us);
        }
        collaborateur.structure(userExtra.getStructure());
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        return collaborateurRepository.save(collaborateur);
    }

    /**
     * Get all the collaborateurs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Collaborateur> findAll(Pageable pageable) {
        log.debug("Request to get all Collaborateurs");
        User user = userService.getUserWithAuthorities().get();
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        Optional<Structure> structure;
        if(userExtra.isPresent()) {
            structure = Optional.of(userExtra.get().getStructure());
            return collaborateurRepository.findByDeletedFalseAndStructureOrderByNomAscPrenomAsc(pageable, structure);
        }
        return collaborateurRepository.findByDeletedFalseOrderByNomAscPrenomAsc(pageable);
    }


    /**
     * Get one collaborateur by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Collaborateur> findOne(Long id) {
        log.debug("Request to get Collaborateur : {}", id);
        return collaborateurRepository.findById(id);
    }

    /**
     * Delete the collaborateur by id.
     *
     * @param id the id of the entity
     */
    public void delete(Collaborateur id) {
        log.debug("Request to delete Collaborateur : {}", id);
        String event= "SUPPRESSION_AVANTAGE";
        User user = userService.getUserWithAuthorities().get();
        UserExtra userExtra = userExtraService.findOne(user.getId()).get();
        PersistentAuditEvent persistentAuditEvent = auditEventService.logEvent(event, user, userExtra);
        auditEventService.saveAudit(persistentAuditEvent);
        id.setDeleted(true);
        id.setDateDeleted(Instant.now());
        id.setUserDeleted(user.getLogin());

        User user1 = id.getUserCollab();
        user1.setActivated(false);
        userService.updateUs(user1);
        collaborateurRepository.save(id);
    }

    @Transactional
    public Collaborateur finbByUserCollab(User user){
        return collaborateurRepository.findByUserCollab(user);
    }
}
