package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.TypeContrat;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.TypeContratService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.TypeContrat}.
 */
@RestController
@RequestMapping("/api")
@Timed
public class TypeContratResource {

    private final Logger log = LoggerFactory.getLogger(TypeContratResource.class);

    private static final String ENTITY_NAME = "typeContrat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeContratService typeContratService;

    public TypeContratResource(TypeContratService typeContratService) {
        this.typeContratService = typeContratService;
    }

    /**
     * {@code POST  /type-contrats} : Create a new typeContrat.
     *
     * @param typeContrat the typeContrat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeContrat, or with status {@code 400 (Bad Request)} if the typeContrat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-contrats")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<TypeContrat> createTypeContrat(@Valid @RequestBody TypeContrat typeContrat) throws URISyntaxException {
        log.info("REST request to save TypeContrat : {}", typeContrat);
        if (typeContrat.getId() != null) {
            throw new BadRequestAlertException("A new typeContrat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeContrat result = typeContratService.save(typeContrat);
        return ResponseEntity.created(new URI("/api/type-contrats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-contrats} : Updates an existing typeContrat.
     *
     * @param typeContrat the typeContrat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeContrat,
     * or with status {@code 400 (Bad Request)} if the typeContrat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeContrat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-contrats")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<TypeContrat> updateTypeContrat(@Valid @RequestBody TypeContrat typeContrat) throws URISyntaxException {
        log.info("REST request to update TypeContrat : {}", typeContrat);
        if (typeContrat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeContrat result = typeContratService.save(typeContrat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeContrat.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-contrats} : get all the typeContrats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeContrats in body.
     */
    @GetMapping("/type-contrats")
    public ResponseEntity<List<TypeContrat>> getAllTypeContrats(Pageable pageable) {
        log.info("REST request to get a page of TypeContrats");
        Page<TypeContrat> page = typeContratService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-contrats/:id} : get the "id" typeContrat.
     *
     * @param id the id of the typeContrat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeContrat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-contrats/{id}")
    public ResponseEntity<TypeContrat> getTypeContrat(@PathVariable Long id) {
        log.info("REST request to get TypeContrat : {}", id);
        Optional<TypeContrat> typeContrat = typeContratService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeContrat);
    }

    /**
     * {@code DELETE  /type-contrats/:id} : delete the "id" typeContrat.
     *
     * @param id the id of the typeContrat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-contrats/{id}")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Void> deleteTypeContrat(@PathVariable Long id) {
        log.info("REST request to delete TypeContrat : {}", id);
        TypeContrat typeContrat = typeContratService.findOne(id).get();
        typeContratService.delete(typeContrat);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
