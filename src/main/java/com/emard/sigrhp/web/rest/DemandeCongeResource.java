package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.DemandeConge;
import com.emard.sigrhp.service.DemandeCongeService;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.emard.sigrhp.domain.DemandeConge}.
 */
@RestController
@RequestMapping("/api")
@Timed
public class DemandeCongeResource {

    private final Logger log = LoggerFactory.getLogger(DemandeCongeResource.class);

    private static final String ENTITY_NAME = "demandeConge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandeCongeService demandeCongeService;

    public DemandeCongeResource(DemandeCongeService demandeCongeService) {
        this.demandeCongeService = demandeCongeService;
    }

    /**
     * {@code POST  /demande-conges} : Create a new demandeConge.
     *
     * @param demandeConge the demandeConge to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new demandeConge, or with status {@code 400 (Bad Request)}
     *         if the demandeConge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demande-conges")
    public ResponseEntity<DemandeConge> createDemandeConge(@Valid @RequestBody DemandeConge demandeConge)
            throws URISyntaxException {
        log.info("REST request to save DemandeConge : {}", demandeConge);
        if (demandeConge.getId() != null) {
            throw new BadRequestAlertException("A new demandeConge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemandeConge result = demandeCongeService.save(demandeConge);
        return ResponseEntity
                .created(new URI("/api/demande-conges/" + result.getId())).headers(HeaderUtil
                        .createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /demande-conges} : Updates an existing demandeConge.
     *
     * @param demandeConge the demandeConge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated demandeConge, or with status {@code 400 (Bad Request)} if
     *         the demandeConge is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the demandeConge couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/demande-conges")
    public ResponseEntity<DemandeConge> updateDemandeConge(@Valid @RequestBody DemandeConge demandeConge)
            throws URISyntaxException {
        log.info("REST request to update DemandeConge : {}", demandeConge);
        if (demandeConge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DemandeConge result = demandeCongeService.save(demandeConge);
        return ResponseEntity.ok().headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeConge.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /demande-conges} : get all the demandeConges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of demandeConges in body.
     */
    @GetMapping("/demande-conges")
    public ResponseEntity<List<DemandeConge>> getAllDemandeConges(Pageable pageable) {
        log.info("REST request to get a page of DemandeConges");
        Page<DemandeConge> page = demandeCongeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET /demande-conges : get all the demandeConges.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of demandeConges
     *         in body
     */
    @GetMapping("/demande-congesBis")
    public ResponseEntity<List<DemandeConge>> getAllDemandeCongesByCriteria(
            @RequestParam(required = false) String prenom, @RequestParam(required = false) String nom,
            @RequestParam(required = false) String matricule, Pageable pageable) {
        log.debug("REST request to get a page of DemandeConges");
        if (prenom.equals("undefined"))
            prenom = "";
        if (nom.equals("undefined"))
            nom = "";
        if (matricule.equals("undefined"))
            matricule = "";
        prenom = prenom.trim();
        nom = nom.trim();
        matricule = matricule.trim();
        log.info("matricule=" + matricule);
        log.info("prenom=" + prenom);
        log.info("nom=" + nom);
        Page<DemandeConge> page = demandeCongeService.findAllBis(prenom, nom, matricule, pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /demande-conges/:id} : get the "id" demandeConge.
     *
     * @param id the id of the demandeConge to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the demandeConge, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/demande-conges/{id}")
    public ResponseEntity<DemandeConge> getDemandeConge(@PathVariable Long id) {
        log.info("REST request to get DemandeConge : {}", id);
        Optional<DemandeConge> demandeConge = demandeCongeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demandeConge);
    }

    /**
     * {@code DELETE  /demande-conges/:id} : delete the "id" demandeConge.
     *
     * @param id the id of the demandeConge to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/demande-conges/{id}")
    public ResponseEntity<Void> deleteDemandeConge(@PathVariable Long id) {
        log.debug("REST request to delete DemandeConge : {}", id);
        DemandeConge demandeConge = demandeCongeService.findOne(id).get();
        demandeCongeService.delete(demandeConge);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }
}
