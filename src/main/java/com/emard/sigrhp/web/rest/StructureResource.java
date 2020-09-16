package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.Structure;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.StructureService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.Structure}.
 */
@RestController
@RequestMapping("/api")
@Timed
public class StructureResource {

    private final Logger log = LoggerFactory.getLogger(StructureResource.class);

    private static final String ENTITY_NAME = "structure";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StructureService structureService;

    public StructureResource(StructureService structureService) {
        this.structureService = structureService;
    }

    /**
     * {@code POST  /structures} : Create a new structure.
     *
     * @param structure the structure to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new structure, or with status {@code 400 (Bad Request)} if the structure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/structures")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Structure> createStructure(@Valid @RequestBody Structure structure) throws URISyntaxException {
        log.info("REST request to save Structure : {}", structure);
        if (structure.getId() != null) {
            throw new BadRequestAlertException("A new structure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Structure result = structureService.save(structure);
        return ResponseEntity.created(new URI("/api/structures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /structures} : Updates an existing structure.
     *
     * @param structure the structure to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated structure,
     * or with status {@code 400 (Bad Request)} if the structure is not valid,
     * or with status {@code 500 (Internal Server Error)} if the structure couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/structures")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Structure> updateStructure(@Valid @RequestBody Structure structure) throws URISyntaxException {
        log.info("REST request to update Structure : {}", structure);
        if (structure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Structure result = structureService.save(structure);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, structure.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /structures} : get all the structures.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of structures in body.
     */
    @GetMapping("/structures")
    public ResponseEntity<List<Structure>> getAllStructures(Pageable pageable) {
        log.info("REST request to get a page of Structures");
        Page<Structure> page = structureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /structures/:id} : get the "id" structure.
     *
     * @param id the id of the structure to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the structure, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/structures/{id}")
    public ResponseEntity<Structure> getStructure(@PathVariable Long id) {
        log.info("REST request to get Structure : {}", id);
        Optional<Structure> structure = structureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(structure);
    }

    /**
     * {@code DELETE  /structures/:id} : delete the "id" structure.
     *
     * @param id the id of the structure to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/structures/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteStructure(@PathVariable Long id) {
        log.info("REST request to delete Structure : {}", id);
        Structure structure = structureService.findOne(id).get();
        structureService.delete(structure);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /structures/:id : get the "id" structure.
     *
     * @param denomination the id of the structure to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the structure, or with status 404 (Not Found)
     */
    @GetMapping("/structuresDenom/{denomination}")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH +   "\")")
    public ResponseEntity<Structure> getStructureByDenomination(@PathVariable String denomination) {
        log.info("REST request to get Structure : {}", denomination);
        Structure structure = structureService.findByDenomination(denomination);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(structure));
    }
}
