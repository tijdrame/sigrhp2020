package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.Collaborateur;
import com.emard.sigrhp.domain.Remboursement;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.RemboursementService;
import com.emard.sigrhp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.emard.sigrhp.domain.Remboursement}.
 */
@RestController
@RequestMapping("/api")
@Timed
public class RemboursementResource {

    private final Logger log = LoggerFactory.getLogger(RemboursementResource.class);

    private static final String ENTITY_NAME = "remboursement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RemboursementService remboursementService;

    public RemboursementResource(RemboursementService remboursementService) {
        this.remboursementService = remboursementService;
    }

    /**
     * {@code POST  /remboursements} : Create a new remboursement.
     *
     * @param remboursement the remboursement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new remboursement, or with status {@code 400 (Bad Request)}
     *         if the remboursement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/remboursements")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Remboursement> createRemboursement(@Valid @RequestBody Remboursement remboursement)
            throws URISyntaxException {
        log.info("REST request to save Remboursement : {}", remboursement);
        if (remboursement.getId() != null) {
            throw new BadRequestAlertException("A new remboursement cannot already have an ID", ENTITY_NAME,
                    "idexists");
        }
        Remboursement result = remboursementService.save(remboursement);
        return ResponseEntity
                .created(new URI("/api/remboursements/" + result.getId())).headers(HeaderUtil
                        .createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /remboursements} : Updates an existing remboursement.
     *
     * @param remboursement the remboursement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated remboursement, or with status {@code 400 (Bad Request)}
     *         if the remboursement is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the remboursement couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/remboursements")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Remboursement> updateRemboursement(@Valid @RequestBody Remboursement remboursement)
            throws URISyntaxException {
        log.info("REST request to update Remboursement : {}", remboursement);
        if (remboursement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Remboursement result = remboursementService.save(remboursement);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
                remboursement.getId().toString())).body(result);
    }

    /**
     * {@code GET  /remboursements} : get all the remboursements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of remboursements in body.
     */
    @GetMapping("/remboursements")
    public ResponseEntity<List<Remboursement>> getAllRemboursements(Pageable pageable) {
        log.info("REST request to get a page of Remboursements");
        Page<Remboursement> page = remboursementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/remboursementsBis")
    public ResponseEntity<List<Remboursement>> search(@RequestParam(required = false) String prenom,
            @RequestParam(required = false) String nom, @RequestParam(required = false) String matricule,
            Pageable pageable) {
        log.info("REST request to get a page of Remboursements");
        if (prenom.equals("undefined"))
            prenom = "";
        if (nom.equals("undefined"))
            nom = "";
        if (matricule.equals("undefined"))
            matricule = "";
        prenom = prenom.trim();
        nom = nom.trim();
        matricule = matricule.trim();
        Page<Remboursement> page = remboursementService.findAllBis(prenom, nom, matricule, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /remboursements/:id} : get the "id" remboursement.
     *
     * @param id the id of the remboursement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the remboursement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/remboursements/{id}")
    public ResponseEntity<Remboursement> getRemboursement(@PathVariable Long id) {
        log.info("REST request to get Remboursement : {}", id);
        Optional<Remboursement> remboursement = remboursementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(remboursement);
    }

    /**
     * {@code DELETE  /remboursements/:id} : delete the "id" remboursement.
     *
     * @param id the id of the remboursement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/remboursements/{id}")
    public ResponseEntity<Void> deleteRemboursement(@PathVariable Long id) {
        log.info("REST request to delete Remboursement : {}", id);
        Remboursement remboursement = remboursementService.findOne(id).get();
        remboursementService.delete(remboursement);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }

    /**
     * GET  /remboursements-collab/:id : get all the remboursements.
     *
     * @param id the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of remboursements in body
     */
    @GetMapping("/remboursements-collab/{id}")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH +   "\")")
    public ResponseEntity<List<Remboursement>> getRemboursementsByCollab(@PathVariable Collaborateur id) {
        log.info("REST request to get a page of Remboursements");
        List<Remboursement> list = remboursementService.getRemboursementByCollaborateur(id);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(list, "/api/remboursements");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
