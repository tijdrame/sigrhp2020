package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.TypeAbsence;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.TypeAbsenceService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.TypeAbsence}.
 */
@RestController
@RequestMapping("/api")
@Timed
public class TypeAbsenceResource {

    private final Logger log = LoggerFactory.getLogger(TypeAbsenceResource.class);

    private static final String ENTITY_NAME = "typeAbsence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeAbsenceService typeAbsenceService;

    public TypeAbsenceResource(TypeAbsenceService typeAbsenceService) {
        this.typeAbsenceService = typeAbsenceService;
    }

    /**
     * {@code POST  /type-absences} : Create a new typeAbsence.
     *
     * @param typeAbsence the typeAbsence to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeAbsence, or with status {@code 400 (Bad Request)} if the typeAbsence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-absences")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<TypeAbsence> createTypeAbsence(@Valid @RequestBody TypeAbsence typeAbsence) throws URISyntaxException {
        log.info("REST request to save TypeAbsence : {}", typeAbsence);
        if (typeAbsence.getId() != null) {
            throw new BadRequestAlertException("A new typeAbsence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeAbsence result = typeAbsenceService.save(typeAbsence);
        return ResponseEntity.created(new URI("/api/type-absences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-absences} : Updates an existing typeAbsence.
     *
     * @param typeAbsence the typeAbsence to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeAbsence,
     * or with status {@code 400 (Bad Request)} if the typeAbsence is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeAbsence couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-absences")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<TypeAbsence> updateTypeAbsence(@Valid @RequestBody TypeAbsence typeAbsence) throws URISyntaxException {
        log.info("REST request to update TypeAbsence : {}", typeAbsence);
        if (typeAbsence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeAbsence result = typeAbsenceService.save(typeAbsence);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeAbsence.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-absences} : get all the typeAbsences.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeAbsences in body.
     */
    @GetMapping("/type-absences")
    public ResponseEntity<List<TypeAbsence>> getAllTypeAbsences(Pageable pageable) {
        log.info("REST request to get a page of TypeAbsences");
        Page<TypeAbsence> page = typeAbsenceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-absences/:id} : get the "id" typeAbsence.
     *
     * @param id the id of the typeAbsence to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeAbsence, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-absences/{id}")
    public ResponseEntity<TypeAbsence> getTypeAbsence(@PathVariable Long id) {
        log.info("REST request to get TypeAbsence : {}", id);
        Optional<TypeAbsence> typeAbsence = typeAbsenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeAbsence);
    }

    /**
     * {@code DELETE  /type-absences/:id} : delete the "id" typeAbsence.
     *
     * @param id the id of the typeAbsence to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-absences/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteTypeAbsence(@PathVariable Long id) {
        log.info("REST request to delete TypeAbsence : {}", id);
        TypeAbsence typeAbsence = typeAbsenceService.findOne(id).get();
        typeAbsenceService.delete(typeAbsence);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
