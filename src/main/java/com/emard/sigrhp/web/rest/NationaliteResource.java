package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.Nationalite;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.NationaliteService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.Nationalite}.
 */
@RestController
@RequestMapping("/api")
@Timed
public class NationaliteResource {

    private final Logger log = LoggerFactory.getLogger(NationaliteResource.class);

    private static final String ENTITY_NAME = "nationalite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NationaliteService nationaliteService;

    public NationaliteResource(NationaliteService nationaliteService) {
        this.nationaliteService = nationaliteService;
    }

    /**
     * {@code POST  /nationalites} : Create a new nationalite.
     *
     * @param nationalite the nationalite to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nationalite, or with status {@code 400 (Bad Request)} if the nationalite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nationalites")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Nationalite> createNationalite(@Valid @RequestBody Nationalite nationalite) throws URISyntaxException {
        log.info("REST request to save Nationalite : {}", nationalite);
        if (nationalite.getId() != null) {
            throw new BadRequestAlertException("A new nationalite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Nationalite result = nationaliteService.save(nationalite);
        return ResponseEntity.created(new URI("/api/nationalites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nationalites} : Updates an existing nationalite.
     *
     * @param nationalite the nationalite to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nationalite,
     * or with status {@code 400 (Bad Request)} if the nationalite is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nationalite couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nationalites")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Nationalite> updateNationalite(@Valid @RequestBody Nationalite nationalite) throws URISyntaxException {
        log.info("REST request to update Nationalite : {}", nationalite);
        if (nationalite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Nationalite result = nationaliteService.save(nationalite);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nationalite.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nationalites} : get all the nationalites.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nationalites in body.
     */
    @GetMapping("/nationalites")
    public ResponseEntity<List<Nationalite>> getAllNationalites(Pageable pageable) {
        log.info("REST request to get a page of Nationalites");
        Page<Nationalite> page = nationaliteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /nationalites : get all the nationalites.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of nationalites in body
     */
    @GetMapping("/nationalitesBis")
    public ResponseEntity<List<Nationalite>> getAllNationalitesBis(Pageable pageable) {
        log.info("REST request to get a page of Nationalites");
        List<Nationalite> listNations = nationaliteService.findAllBis(pageable);
        return new ResponseEntity<>(listNations, HttpStatus.OK);
    }

    /**
     * {@code GET  /nationalites/:id} : get the "id" nationalite.
     *
     * @param id the id of the nationalite to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nationalite, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nationalites/{id}")
    public ResponseEntity<Nationalite> getNationalite(@PathVariable Long id) {
        log.info("REST request to get Nationalite : {}", id);
        Optional<Nationalite> nationalite = nationaliteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nationalite);
    }

    /**
     * {@code DELETE  /nationalites/:id} : delete the "id" nationalite.
     *
     * @param id the id of the nationalite to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nationalites/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteNationalite(@PathVariable Long id) {
        log.info("REST request to delete Nationalite : {}", id);
        Nationalite nationalite = nationaliteService.findOne(id).get();
        nationaliteService.delete(nationalite);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
