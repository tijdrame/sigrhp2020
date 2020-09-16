package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.Avantage;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.AvantageService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.Avantage}.
 */
@RestController
@RequestMapping("/api")
public class AvantageResource {

    private final Logger log = LoggerFactory.getLogger(AvantageResource.class);

    private static final String ENTITY_NAME = "avantage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvantageService avantageService;

    public AvantageResource(AvantageService avantageService) {
        this.avantageService = avantageService;
    }

    /**
     * {@code POST  /avantages} : Create a new avantage.
     *
     * @param avantage the avantage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avantage, or with status {@code 400 (Bad Request)} if the avantage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/avantages")
    @Timed
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Avantage> createAvantage(@Valid @RequestBody Avantage avantage) throws URISyntaxException {
        log.info("REST request to save Avantage : {}", avantage);
        if (avantage.getId() != null) {
            throw new BadRequestAlertException("A new avantage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Avantage result = avantageService.save(avantage);
        return ResponseEntity.created(new URI("/api/avantages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /avantages} : Updates an existing avantage.
     *
     * @param avantage the avantage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avantage,
     * or with status {@code 400 (Bad Request)} if the avantage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avantage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/avantages")
    @Timed
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Avantage> updateAvantage(@Valid @RequestBody Avantage avantage) throws URISyntaxException {
        log.info("REST request to update Avantage : {}", avantage);
        if (avantage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Avantage result = avantageService.save(avantage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avantage.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /avantages} : get all the avantages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avantages in body.
     */
    @GetMapping("/avantages")
    @Timed
    public ResponseEntity<List<Avantage>> getAllAvantages(Pageable pageable) {
        log.info("REST request to get a page of Avantages");
        Page<Avantage> page = avantageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /avantages/:id} : get the "id" avantage.
     *
     * @param id the id of the avantage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avantage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/avantages/{id}")
    @Timed
    public ResponseEntity<Avantage> getAvantage(@PathVariable Long id) {
        log.info("REST request to get Avantage : {}", id);
        Optional<Avantage> avantage = avantageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(avantage);
    }

    /**
     * {@code DELETE  /avantages/:id} : delete the "id" avantage.
     *
     * @param id the id of the avantage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/avantages/{id}")
    @Timed
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Void> deleteAvantage(@PathVariable Long id) {
        log.info("REST request to delete Avantage : {}", id);
        Avantage avantage = avantageService.findOne(id).get();
        avantageService.delete(avantage);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
