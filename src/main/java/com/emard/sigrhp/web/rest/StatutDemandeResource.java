package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.StatutDemande;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.StatutDemandeService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.StatutDemande}.
 */
@RestController
@RequestMapping("/api")
@Timed
public class StatutDemandeResource {

    private final Logger log = LoggerFactory.getLogger(StatutDemandeResource.class);

    private static final String ENTITY_NAME = "statutDemande";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatutDemandeService statutDemandeService;

    public StatutDemandeResource(StatutDemandeService statutDemandeService) {
        this.statutDemandeService = statutDemandeService;
    }

    /**
     * {@code POST  /statut-demandes} : Create a new statutDemande.
     *
     * @param statutDemande the statutDemande to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statutDemande, or with status {@code 400 (Bad Request)} if the statutDemande has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/statut-demandes")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<StatutDemande> createStatutDemande(@Valid @RequestBody StatutDemande statutDemande) throws URISyntaxException {
        log.info("REST request to save StatutDemande : {}", statutDemande);
        if (statutDemande.getId() != null) {
            throw new BadRequestAlertException("A new statutDemande cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatutDemande result = statutDemandeService.save(statutDemande);
        return ResponseEntity.created(new URI("/api/statut-demandes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /statut-demandes} : Updates an existing statutDemande.
     *
     * @param statutDemande the statutDemande to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statutDemande,
     * or with status {@code 400 (Bad Request)} if the statutDemande is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statutDemande couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/statut-demandes")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<StatutDemande> updateStatutDemande(@Valid @RequestBody StatutDemande statutDemande) throws URISyntaxException {
        log.info("REST request to update StatutDemande : {}", statutDemande);
        if (statutDemande.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StatutDemande result = statutDemandeService.save(statutDemande);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statutDemande.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /statut-demandes} : get all the statutDemandes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statutDemandes in body.
     */
    @GetMapping("/statut-demandes")
    public ResponseEntity<List<StatutDemande>> getAllStatutDemandes(Pageable pageable) {
        log.info("REST request to get a page of StatutDemandes");
        Page<StatutDemande> page = statutDemandeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /statut-demandes/:id} : get the "id" statutDemande.
     *
     * @param id the id of the statutDemande to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statutDemande, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/statut-demandes/{id}")
    public ResponseEntity<StatutDemande> getStatutDemande(@PathVariable Long id) {
        log.info("REST request to get StatutDemande : {}", id);
        Optional<StatutDemande> statutDemande = statutDemandeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statutDemande);
    }

    /**
     * {@code DELETE  /statut-demandes/:id} : delete the "id" statutDemande.
     *
     * @param id the id of the statutDemande to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/statut-demandes/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteStatutDemande(@PathVariable Long id) {
        log.info("REST request to delete StatutDemande : {}", id);
        StatutDemande statutDemande = statutDemandeService.findOne(id).get();
        statutDemandeService.delete(statutDemande);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
