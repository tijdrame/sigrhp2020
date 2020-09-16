package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.Collaborateur;
import com.emard.sigrhp.domain.MembreFamille;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.MembreFamilleService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.MembreFamille}.
 */
@RestController
@RequestMapping("/api")
@Timed
public class MembreFamilleResource {

    private final Logger log = LoggerFactory.getLogger(MembreFamilleResource.class);

    private static final String ENTITY_NAME = "membreFamille";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MembreFamilleService membreFamilleService;

    public MembreFamilleResource(MembreFamilleService membreFamilleService) {
        this.membreFamilleService = membreFamilleService;
    }

    /**
     * {@code POST  /membre-familles} : Create a new membreFamille.
     *
     * @param membreFamille the membreFamille to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new membreFamille, or with status {@code 400 (Bad Request)} if the membreFamille has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/membre-familles")
    public ResponseEntity<MembreFamille> createMembreFamille(@Valid @RequestBody MembreFamille membreFamille) throws URISyntaxException {
        log.info("REST request to save MembreFamille : {}", membreFamille);
        if (membreFamille.getId() != null) {
            throw new BadRequestAlertException("A new membreFamille cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MembreFamille result = membreFamilleService.save(membreFamille);
        return ResponseEntity.created(new URI("/api/membre-familles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /membre-familles} : Updates an existing membreFamille.
     *
     * @param membreFamille the membreFamille to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated membreFamille,
     * or with status {@code 400 (Bad Request)} if the membreFamille is not valid,
     * or with status {@code 500 (Internal Server Error)} if the membreFamille couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/membre-familles")
    public ResponseEntity<MembreFamille> updateMembreFamille(@Valid @RequestBody MembreFamille membreFamille) throws URISyntaxException {
        log.info("REST request to update MembreFamille : {}", membreFamille);
        if (membreFamille.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MembreFamille result = membreFamilleService.save(membreFamille);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, membreFamille.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /membre-familles} : get all the membreFamilles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of membreFamilles in body.
     */
    @GetMapping("/membre-familles")
    public ResponseEntity<List<MembreFamille>> getAllMembreFamilles(Pageable pageable) {
        log.info("REST request to get a page of MembreFamilles");
        Page<MembreFamille> page = membreFamilleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /membre-familles/:id} : get the "id" membreFamille.
     *
     * @param id the id of the membreFamille to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the membreFamille, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/membre-familles/{id}")
    public ResponseEntity<MembreFamille> getMembreFamille(@PathVariable Long id) {
        log.info("REST request to get MembreFamille : {}", id);
        Optional<MembreFamille> membreFamille = membreFamilleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(membreFamille);
    }

    /**
     * {@code DELETE  /membre-familles/:id} : delete the "id" membreFamille.
     *
     * @param id the id of the membreFamille to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/membre-familles/{id}")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Void> deleteMembreFamille(@PathVariable Long id) {
        log.info("REST request to delete MembreFamille : {}", id);
        MembreFamille membreFamille = membreFamilleService.findOne(id).get();
        membreFamilleService.delete(membreFamille);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /membre-familles/:id : get the "id" membreFamille.
     *
     * @param id the id of the membreFamille to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the membreFamille, or with status 404 (Not Found)
     */
    @GetMapping("/membre-familles-collab/{id}")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<List<MembreFamille>> getMembreFamilleByCollab(@PathVariable Collaborateur id) {
        log.info("REST request to get MembreFamille : {}", id);
        List<MembreFamille> membreFamilles = membreFamilleService.findByCollaborateur(id);
        return new ResponseEntity<>(membreFamilles, HttpStatus.OK);
    }
}
