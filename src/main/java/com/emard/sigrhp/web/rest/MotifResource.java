package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.Motif;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.MotifService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.Motif}.
 */
@RestController
@RequestMapping("/api")
@Timed
public class MotifResource {

    private final Logger log = LoggerFactory.getLogger(MotifResource.class);

    private static final String ENTITY_NAME = "motif";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MotifService motifService;

    public MotifResource(MotifService motifService) {
        this.motifService = motifService;
    }

    /**
     * {@code POST  /motifs} : Create a new motif.
     *
     * @param motif the motif to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new motif, or with status {@code 400 (Bad Request)} if the motif has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/motifs")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Motif> createMotif(@Valid @RequestBody Motif motif) throws URISyntaxException {
        log.info("REST request to save Motif : {}", motif);
        if (motif.getId() != null) {
            throw new BadRequestAlertException("A new motif cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Motif result = motifService.save(motif);
        return ResponseEntity.created(new URI("/api/motifs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /motifs} : Updates an existing motif.
     *
     * @param motif the motif to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated motif,
     * or with status {@code 400 (Bad Request)} if the motif is not valid,
     * or with status {@code 500 (Internal Server Error)} if the motif couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/motifs")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Motif> updateMotif(@Valid @RequestBody Motif motif) throws URISyntaxException {
        log.info("REST request to update Motif : {}", motif);
        if (motif.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Motif result = motifService.save(motif);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, motif.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /motifs} : get all the motifs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of motifs in body.
     */
    @GetMapping("/motifs")
    public ResponseEntity<List<Motif>> getAllMotifs(Pageable pageable) {
        log.info("REST request to get a page of Motifs");
        Page<Motif> page = motifService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /motifs/:id} : get the "id" motif.
     *
     * @param id the id of the motif to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the motif, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/motifs/{id}")
    public ResponseEntity<Motif> getMotif(@PathVariable Long id) {
        log.info("REST request to get Motif : {}", id);
        Optional<Motif> motif = motifService.findOne(id);
        return ResponseUtil.wrapOrNotFound(motif);
    }

    /**
     * {@code DELETE  /motifs/:id} : delete the "id" motif.
     *
     * @param id the id of the motif to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/motifs/{id}")
    public ResponseEntity<Void> deleteMotif(@PathVariable Long id) {
        log.info("REST request to delete Motif : {}", id);
        Motif motif = motifService.findOne(id).get();
        motifService.delete(motif);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
