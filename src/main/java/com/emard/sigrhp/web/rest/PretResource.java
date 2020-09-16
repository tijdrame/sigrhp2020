package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.Pret;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.PretService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.Pret}.
 */
@RestController
@RequestMapping("/api")
@Timed
public class PretResource {

    private final Logger log = LoggerFactory.getLogger(PretResource.class);

    private static final String ENTITY_NAME = "pret";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PretService pretService;

    public PretResource(PretService pretService) {
        this.pretService = pretService;
    }

    /**
     * {@code POST  /prets} : Create a new pret.
     *
     * @param pret the pret to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pret, or with status {@code 400 (Bad Request)} if the pret has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prets")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Pret> createPret(@Valid @RequestBody Pret pret) throws URISyntaxException {
        log.info("REST request to save Pret : {}", pret);
        if (pret.getId() != null) {
            throw new BadRequestAlertException("A new pret cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pret result = pretService.save(pret);
        return ResponseEntity.created(new URI("/api/prets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prets} : Updates an existing pret.
     *
     * @param pret the pret to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pret,
     * or with status {@code 400 (Bad Request)} if the pret is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pret couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prets")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Pret> updatePret(@Valid @RequestBody Pret pret) throws URISyntaxException {
        log.info("REST request to update Pret : {}", pret);
        if (pret.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pret result = pretService.save(pret);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pret.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prets} : get all the prets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prets in body.
     */
    @GetMapping("/prets")
    public ResponseEntity<List<Pret>> getAllPrets(Pageable pageable) {
        log.info("REST request to get a page of Prets");
        Page<Pret> page = pretService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prets/:id} : get the "id" pret.
     *
     * @param id the id of the pret to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pret, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prets/{id}")
    public ResponseEntity<Pret> getPret(@PathVariable Long id) {
        log.info("REST request to get Pret : {}", id);
        Optional<Pret> pret = pretService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pret);
    }

    /**
     * {@code DELETE  /prets/:id} : delete the "id" pret.
     *
     * @param id the id of the pret to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prets/{id}")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Void> deletePret(@PathVariable Long id) {
        log.info("REST request to delete Pret : {}", id);
        Pret pret = pretService.findOne(id).get();
        pretService.delete(pret);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
