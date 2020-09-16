package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.TypeRelation;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.TypeRelationService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.TypeRelation}.
 */
@RestController
@RequestMapping("/api")
@Timed
public class TypeRelationResource {

    private final Logger log = LoggerFactory.getLogger(TypeRelationResource.class);

    private static final String ENTITY_NAME = "typeRelation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeRelationService typeRelationService;

    public TypeRelationResource(TypeRelationService typeRelationService) {
        this.typeRelationService = typeRelationService;
    }

    /**
     * {@code POST  /type-relations} : Create a new typeRelation.
     *
     * @param typeRelation the typeRelation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeRelation, or with status {@code 400 (Bad Request)} if the typeRelation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-relations")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<TypeRelation> createTypeRelation(@Valid @RequestBody TypeRelation typeRelation) throws URISyntaxException {
        log.info("REST request to save TypeRelation : {}", typeRelation);
        if (typeRelation.getId() != null) {
            throw new BadRequestAlertException("A new typeRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeRelation result = typeRelationService.save(typeRelation);
        return ResponseEntity.created(new URI("/api/type-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-relations} : Updates an existing typeRelation.
     *
     * @param typeRelation the typeRelation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeRelation,
     * or with status {@code 400 (Bad Request)} if the typeRelation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeRelation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-relations")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<TypeRelation> updateTypeRelation(@Valid @RequestBody TypeRelation typeRelation) throws URISyntaxException {
        log.info("REST request to update TypeRelation : {}", typeRelation);
        if (typeRelation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeRelation result = typeRelationService.save(typeRelation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeRelation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-relations} : get all the typeRelations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeRelations in body.
     */
    @GetMapping("/type-relations")
    public ResponseEntity<List<TypeRelation>> getAllTypeRelations(Pageable pageable) {
        log.info("REST request to get a page of TypeRelations");
        Page<TypeRelation> page = typeRelationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-relations/:id} : get the "id" typeRelation.
     *
     * @param id the id of the typeRelation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeRelation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-relations/{id}")
    public ResponseEntity<TypeRelation> getTypeRelation(@PathVariable Long id) {
        log.info("REST request to get TypeRelation : {}", id);
        Optional<TypeRelation> typeRelation = typeRelationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeRelation);
    }

    /**
     * {@code DELETE  /type-relations/:id} : delete the "id" typeRelation.
     *
     * @param id the id of the typeRelation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-relations/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteTypeRelation(@PathVariable Long id) {
        log.info("REST request to delete TypeRelation : {}", id);
        TypeRelation typeRelation = typeRelationService.findOne(id).get();
        typeRelationService.delete(typeRelation);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
