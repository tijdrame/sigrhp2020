package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.AvantageCollab;
import com.emard.sigrhp.domain.Collaborateur;
import com.emard.sigrhp.service.AvantageCollabService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.AvantageCollab}.
 */
@RestController
@RequestMapping("/api")
public class AvantageCollabResource {

    private final Logger log = LoggerFactory.getLogger(AvantageCollabResource.class);

    private static final String ENTITY_NAME = "avantageCollab";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvantageCollabService avantageCollabService;

    public AvantageCollabResource(AvantageCollabService avantageCollabService) {
        this.avantageCollabService = avantageCollabService;
    }

    /**
     * {@code POST  /avantage-collabs} : Create a new avantageCollab.
     *
     * @param avantageCollab the avantageCollab to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avantageCollab, or with status {@code 400 (Bad Request)} if the avantageCollab has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/avantage-collabs")
    public ResponseEntity<AvantageCollab> createAvantageCollab(@Valid @RequestBody AvantageCollab avantageCollab) throws URISyntaxException {
        log.debug("REST request to save AvantageCollab : {}", avantageCollab);
        if (avantageCollab.getId() != null) {
            throw new BadRequestAlertException("A new avantageCollab cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvantageCollab result = avantageCollabService.save(avantageCollab);
        return ResponseEntity.created(new URI("/api/avantage-collabs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /avantage-collabs} : Updates an existing avantageCollab.
     *
     * @param avantageCollab the avantageCollab to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avantageCollab,
     * or with status {@code 400 (Bad Request)} if the avantageCollab is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avantageCollab couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/avantage-collabs")
    public ResponseEntity<AvantageCollab> updateAvantageCollab(@Valid @RequestBody AvantageCollab avantageCollab) throws URISyntaxException {
        log.debug("REST request to update AvantageCollab : {}", avantageCollab);
        if (avantageCollab.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AvantageCollab result = avantageCollabService.save(avantageCollab);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avantageCollab.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /avantage-collabs} : get all the avantageCollabs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avantageCollabs in body.
     */
    @GetMapping("/avantage-collabs")
    public ResponseEntity<List<AvantageCollab>> getAllAvantageCollabs(Pageable pageable) {
        log.debug("REST request to get a page of AvantageCollabs");
        Page<AvantageCollab> page = avantageCollabService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /avantage-collabs/:id} : get the "id" avantageCollab.
     *
     * @param id the id of the avantageCollab to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avantageCollab, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/avantage-collabs/{id}")
    public ResponseEntity<AvantageCollab> getAvantageCollab(@PathVariable Long id) {
        log.debug("REST request to get AvantageCollab : {}", id);
        Optional<AvantageCollab> avantageCollab = avantageCollabService.findOne(id);
        return ResponseUtil.wrapOrNotFound(avantageCollab);
    }

    /**
     * {@code DELETE  /avantage-collabs/:id} : delete the "id" avantageCollab.
     *
     * @param id the id of the avantageCollab to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/avantage-collabs/{id}")
    public ResponseEntity<Void> deleteAvantageCollab(@PathVariable Long id) {
        log.debug("REST request to delete AvantageCollab : {}", id);
        AvantageCollab avantageCollab = avantageCollabService.findOne(id).get();
        avantageCollabService.delete(avantageCollab);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * Get all the Avantages.
     *
     * @return the list of entities
     */
    @GetMapping("/avantage-collabsTer/{id}")
    // @Transactional(readOnly = true)
    @Timed
    public List<AvantageCollab> findByCollab(@PathVariable Collaborateur id) {
        log.debug("Request to get all Avantages by Collab");
        return avantageCollabService.findByCollaborateur(id);
    }
}
