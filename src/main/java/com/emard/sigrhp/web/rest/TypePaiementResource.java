package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.TypePaiement;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.TypePaiementService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.TypePaiement}.
 */
@RestController
@RequestMapping("/api")
@Timed
public class TypePaiementResource {

    private final Logger log = LoggerFactory.getLogger(TypePaiementResource.class);

    private static final String ENTITY_NAME = "typePaiement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypePaiementService typePaiementService;

    public TypePaiementResource(TypePaiementService typePaiementService) {
        this.typePaiementService = typePaiementService;
    }

    /**
     * {@code POST  /type-paiements} : Create a new typePaiement.
     *
     * @param typePaiement the typePaiement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typePaiement, or with status {@code 400 (Bad Request)} if the typePaiement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-paiements")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<TypePaiement> createTypePaiement(@Valid @RequestBody TypePaiement typePaiement) throws URISyntaxException {
        log.info("REST request to save TypePaiement : {}", typePaiement);
        if (typePaiement.getId() != null) {
            throw new BadRequestAlertException("A new typePaiement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypePaiement result = typePaiementService.save(typePaiement);
        return ResponseEntity.created(new URI("/api/type-paiements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-paiements} : Updates an existing typePaiement.
     *
     * @param typePaiement the typePaiement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typePaiement,
     * or with status {@code 400 (Bad Request)} if the typePaiement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typePaiement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-paiements")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<TypePaiement> updateTypePaiement(@Valid @RequestBody TypePaiement typePaiement) throws URISyntaxException {
        log.info("REST request to update TypePaiement : {}", typePaiement);
        if (typePaiement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypePaiement result = typePaiementService.save(typePaiement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typePaiement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-paiements} : get all the typePaiements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typePaiements in body.
     */
    @GetMapping("/type-paiements")
    public ResponseEntity<List<TypePaiement>> getAllTypePaiements(Pageable pageable) {
        log.info("REST request to get a page of TypePaiements");
        Page<TypePaiement> page = typePaiementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-paiements/:id} : get the "id" typePaiement.
     *
     * @param id the id of the typePaiement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typePaiement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-paiements/{id}")
    public ResponseEntity<TypePaiement> getTypePaiement(@PathVariable Long id) {
        log.info("REST request to get TypePaiement : {}", id);
        Optional<TypePaiement> typePaiement = typePaiementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typePaiement);
    }

    /**
     * {@code DELETE  /type-paiements/:id} : delete the "id" typePaiement.
     *
     * @param id the id of the typePaiement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-paiements/{id}")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Void> deleteTypePaiement(@PathVariable Long id) {
        log.info("REST request to delete TypePaiement : {}", id);
        TypePaiement typePaiement = typePaiementService.findOne(id).get();
        typePaiementService.delete(typePaiement);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
