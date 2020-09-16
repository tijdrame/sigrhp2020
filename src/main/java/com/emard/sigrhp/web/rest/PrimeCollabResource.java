package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.Collaborateur;
import com.emard.sigrhp.domain.PrimeCollab;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.PrimeCollabService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.PrimeCollab}.
 */
@RestController
@RequestMapping("/api")
@Timed
public class PrimeCollabResource {

    private final Logger log = LoggerFactory.getLogger(PrimeCollabResource.class);

    private static final String ENTITY_NAME = "primeCollab";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrimeCollabService primeCollabService;

    public PrimeCollabResource(PrimeCollabService primeCollabService) {
        this.primeCollabService = primeCollabService;
    }

    /**
     * {@code POST  /prime-collabs} : Create a new primeCollab.
     *
     * @param primeCollab the primeCollab to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new primeCollab, or with status {@code 400 (Bad Request)} if the primeCollab has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prime-collabs")
    public ResponseEntity<PrimeCollab> createPrimeCollab(@Valid @RequestBody PrimeCollab primeCollab) throws URISyntaxException {
        log.info("REST request to save PrimeCollab : {}", primeCollab);
        if (primeCollab.getId() != null) {
            throw new BadRequestAlertException("A new primeCollab cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrimeCollab result = primeCollabService.save(primeCollab);
        return ResponseEntity.created(new URI("/api/prime-collabs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prime-collabs} : Updates an existing primeCollab.
     *
     * @param primeCollab the primeCollab to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated primeCollab,
     * or with status {@code 400 (Bad Request)} if the primeCollab is not valid,
     * or with status {@code 500 (Internal Server Error)} if the primeCollab couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prime-collabs")
    public ResponseEntity<PrimeCollab> updatePrimeCollab(@Valid @RequestBody PrimeCollab primeCollab) throws URISyntaxException {
        log.info("REST request to update PrimeCollab : {}", primeCollab);
        if (primeCollab.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrimeCollab result = primeCollabService.save(primeCollab);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, primeCollab.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prime-collabs} : get all the primeCollabs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of primeCollabs in body.
     */
    @GetMapping("/prime-collabs")
    public ResponseEntity<List<PrimeCollab>> getAllPrimeCollabs(Pageable pageable) {
        log.info("REST request to get a page of PrimeCollabs");
        Page<PrimeCollab> page = primeCollabService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prime-collabs/:id} : get the "id" primeCollab.
     *
     * @param id the id of the primeCollab to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the primeCollab, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prime-collabs/{id}")
    public ResponseEntity<PrimeCollab> getPrimeCollab(@PathVariable Long id) {
        log.info("REST request to get PrimeCollab : {}", id);
        Optional<PrimeCollab> primeCollab = primeCollabService.findOne(id);
        return ResponseUtil.wrapOrNotFound(primeCollab);
    }

    /**
     * {@code DELETE  /prime-collabs/:id} : delete the "id" primeCollab.
     *
     * @param id the id of the primeCollab to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prime-collabs/{id}")
    public ResponseEntity<Void> deletePrimeCollab(@PathVariable Long id) {
        log.info("REST request to delete PrimeCollab : {}", id);
        PrimeCollab primeCollab = primeCollabService.findOne(id).get();
        primeCollabService.delete(primeCollab);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * Get all the primes.
     *
     * @return the list of entities
     */
    @GetMapping("/prime-collabsTer/{id}")
    //@Transactional(readOnly = true)
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH +   "\")")
    public List<PrimeCollab> findByCollab(@PathVariable Collaborateur id) {
        log.info("Request to get all Primes");
        return primeCollabService.findByCollaborateur(id);
    }
}
