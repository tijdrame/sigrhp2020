package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.Fonction;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.FonctionService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.Fonction}.
 */
@RestController
@RequestMapping("/api")
@Timed
public class FonctionResource {

    private final Logger log = LoggerFactory.getLogger(FonctionResource.class);

    private static final String ENTITY_NAME = "fonction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FonctionService fonctionService;

    public FonctionResource(FonctionService fonctionService) {
        this.fonctionService = fonctionService;
    }

    /**
     * {@code POST  /fonctions} : Create a new fonction.
     *
     * @param fonction the fonction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fonction, or with status {@code 400 (Bad Request)} if the fonction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fonctions")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH +   "\") ")
    public ResponseEntity<Fonction> createFonction(@Valid @RequestBody Fonction fonction) throws URISyntaxException {
        log.info("REST request to save Fonction : {}", fonction);
        if (fonction.getId() != null) {
            throw new BadRequestAlertException("A new fonction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Fonction result = fonctionService.save(fonction);
        return ResponseEntity.created(new URI("/api/fonctions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fonctions} : Updates an existing fonction.
     *
     * @param fonction the fonction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fonction,
     * or with status {@code 400 (Bad Request)} if the fonction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fonction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fonctions")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH +   "\") ")
    public ResponseEntity<Fonction> updateFonction(@Valid @RequestBody Fonction fonction) throws URISyntaxException {
        log.info("REST request to update Fonction : {}", fonction);
        if (fonction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Fonction result = fonctionService.save(fonction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fonction.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fonctions} : get all the fonctions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fonctions in body.
     */
    @GetMapping("/fonctions")
    public ResponseEntity<List<Fonction>> getAllFonctions(Pageable pageable) {
        log.info("REST request to get a page of Fonctions");
        Page<Fonction> page = fonctionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fonctions/:id} : get the "id" fonction.
     *
     * @param id the id of the fonction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fonction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fonctions/{id}")
    public ResponseEntity<Fonction> getFonction(@PathVariable Long id) {
        log.info("REST request to get Fonction : {}", id);
        Optional<Fonction> fonction = fonctionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fonction);
    }

    /**
     * {@code DELETE  /fonctions/:id} : delete the "id" fonction.
     *
     * @param id the id of the fonction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fonctions/{id}")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\") ")
    public ResponseEntity<Void> deleteFonction(@PathVariable Long id) {
        log.info("REST request to delete Fonction : {}", id);
        Fonction fonction = fonctionService.findOne(id).get();
        fonctionService.delete(fonction);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
