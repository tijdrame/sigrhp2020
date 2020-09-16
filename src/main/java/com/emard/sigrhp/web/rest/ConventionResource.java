package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.Convention;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.ConventionService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.Convention}.
 */
@RestController
@RequestMapping("/api")
@Timed
public class ConventionResource {

    private final Logger log = LoggerFactory.getLogger(ConventionResource.class);

    private static final String ENTITY_NAME = "convention";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConventionService conventionService;

    public ConventionResource(ConventionService conventionService) {
        this.conventionService = conventionService;
    }

    /**
     * {@code POST  /conventions} : Create a new convention.
     *
     * @param convention the convention to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new convention, or with status {@code 400 (Bad Request)} if the convention has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conventions")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Convention> createConvention(@Valid @RequestBody Convention convention) throws URISyntaxException {
        log.info("REST request to save Convention : {}", convention);
        if (convention.getId() != null) {
            throw new BadRequestAlertException("A new convention cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Convention result = conventionService.save(convention);
        return ResponseEntity.created(new URI("/api/conventions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conventions} : Updates an existing convention.
     *
     * @param convention the convention to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated convention,
     * or with status {@code 400 (Bad Request)} if the convention is not valid,
     * or with status {@code 500 (Internal Server Error)} if the convention couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conventions")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Convention> updateConvention(@Valid @RequestBody Convention convention) throws URISyntaxException {
        log.info("REST request to update Convention : {}", convention);
        if (convention.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Convention result = conventionService.save(convention);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, convention.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /conventions} : get all the conventions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conventions in body.
     */
    @GetMapping("/conventions")
    public ResponseEntity<List<Convention>> getAllConventions(Pageable pageable) {
        log.info("REST request to get a page of Conventions");
        Page<Convention> page = conventionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /conventions/:id} : get the "id" convention.
     *
     * @param id the id of the convention to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the convention, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conventions/{id}")
    public ResponseEntity<Convention> getConvention(@PathVariable Long id) {
        log.info("REST request to get Convention : {}", id);
        Optional<Convention> convention = conventionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(convention);
    }

    /**
     * {@code DELETE  /conventions/:id} : delete the "id" convention.
     *
     * @param id the id of the convention to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conventions/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteConvention(@PathVariable Long id) {
        log.debug("REST request to delete Convention : {}", id);
        Convention convention = conventionService.findOne(id).get();
        conventionService.delete(convention);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
