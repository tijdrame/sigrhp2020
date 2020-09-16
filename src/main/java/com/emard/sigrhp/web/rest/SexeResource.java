package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.Sexe;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.SexeService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.Sexe}.
 */
@RestController
@RequestMapping("/api")
@Timed
public class SexeResource {

    private final Logger log = LoggerFactory.getLogger(SexeResource.class);

    private static final String ENTITY_NAME = "sexe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SexeService sexeService;

    public SexeResource(SexeService sexeService) {
        this.sexeService = sexeService;
    }

    /**
     * {@code POST  /sexes} : Create a new sexe.
     *
     * @param sexe the sexe to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sexe, or with status {@code 400 (Bad Request)} if the sexe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sexes")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Sexe> createSexe(@Valid @RequestBody Sexe sexe) throws URISyntaxException {
        log.info("REST request to save Sexe : {}", sexe);
        if (sexe.getId() != null) {
            throw new BadRequestAlertException("A new sexe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sexe result = sexeService.save(sexe);
        return ResponseEntity.created(new URI("/api/sexes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sexes} : Updates an existing sexe.
     *
     * @param sexe the sexe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sexe,
     * or with status {@code 400 (Bad Request)} if the sexe is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sexe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sexes")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Sexe> updateSexe(@Valid @RequestBody Sexe sexe) throws URISyntaxException {
        log.info("REST request to update Sexe : {}", sexe);
        if (sexe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sexe result = sexeService.save(sexe);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sexe.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sexes} : get all the sexes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sexes in body.
     */
    @GetMapping("/sexes")
    public ResponseEntity<List<Sexe>> getAllSexes(Pageable pageable) {
        log.info("REST request to get a page of Sexes");
        Page<Sexe> page = sexeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sexes/:id} : get the "id" sexe.
     *
     * @param id the id of the sexe to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sexe, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sexes/{id}")
    public ResponseEntity<Sexe> getSexe(@PathVariable Long id) {
        log.info("REST request to get Sexe : {}", id);
        Optional<Sexe> sexe = sexeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sexe);
    }

    /**
     * {@code DELETE  /sexes/:id} : delete the "id" sexe.
     *
     * @param id the id of the sexe to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sexes/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteSexe(@PathVariable Long id) {
        log.info("REST request to delete Sexe : {}", id);
        Sexe sexe = sexeService.findOne(id).get();
        sexeService.delete(sexe);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
