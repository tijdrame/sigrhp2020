package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.Statut;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.StatutService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.Statut}.
 */
@RestController
@RequestMapping("/api")
@Timed
public class StatutResource {

    private final Logger log = LoggerFactory.getLogger(StatutResource.class);

    private static final String ENTITY_NAME = "statut";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatutService statutService;

    public StatutResource(StatutService statutService) {
        this.statutService = statutService;
    }

    /**
     * {@code POST  /statuts} : Create a new statut.
     *
     * @param statut the statut to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statut, or with status {@code 400 (Bad Request)} if the statut has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/statuts")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Statut> createStatut(@Valid @RequestBody Statut statut) throws URISyntaxException {
        log.info("REST request to save Statut : {}", statut);
        if (statut.getId() != null) {
            throw new BadRequestAlertException("A new statut cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Statut result = statutService.save(statut);
        return ResponseEntity.created(new URI("/api/statuts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /statuts} : Updates an existing statut.
     *
     * @param statut the statut to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statut,
     * or with status {@code 400 (Bad Request)} if the statut is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statut couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/statuts")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Statut> updateStatut(@Valid @RequestBody Statut statut) throws URISyntaxException {
        log.info("REST request to update Statut : {}", statut);
        if (statut.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Statut result = statutService.save(statut);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statut.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /statuts} : get all the statuts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statuts in body.
     */
    @GetMapping("/statuts")
    public ResponseEntity<List<Statut>> getAllStatuts(Pageable pageable) {
        log.info("REST request to get a page of Statuts");
        Page<Statut> page = statutService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /statuts/:id} : get the "id" statut.
     *
     * @param id the id of the statut to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statut, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/statuts/{id}")
    public ResponseEntity<Statut> getStatut(@PathVariable Long id) {
        log.info("REST request to get Statut : {}", id);
        Optional<Statut> statut = statutService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statut);
    }

    /**
     * {@code DELETE  /statuts/:id} : delete the "id" statut.
     *
     * @param id the id of the statut to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/statuts/{id}")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Void> deleteStatut(@PathVariable Long id) {
        log.info("REST request to delete Statut : {}", id);
        Statut statut = statutService.findOne(id).get();
        statutService.delete(statut);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
