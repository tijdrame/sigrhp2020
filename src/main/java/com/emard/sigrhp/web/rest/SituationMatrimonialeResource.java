package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.SituationMatrimoniale;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.SituationMatrimonialeService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.SituationMatrimoniale}.
 */
@RestController
@RequestMapping("/api")
@Timed
public class SituationMatrimonialeResource {

    private final Logger log = LoggerFactory.getLogger(SituationMatrimonialeResource.class);

    private static final String ENTITY_NAME = "situationMatrimoniale";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SituationMatrimonialeService situationMatrimonialeService;

    public SituationMatrimonialeResource(SituationMatrimonialeService situationMatrimonialeService) {
        this.situationMatrimonialeService = situationMatrimonialeService;
    }

    /**
     * {@code POST  /situation-matrimoniales} : Create a new situationMatrimoniale.
     *
     * @param situationMatrimoniale the situationMatrimoniale to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new situationMatrimoniale, or with status {@code 400 (Bad Request)} if the situationMatrimoniale has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/situation-matrimoniales")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<SituationMatrimoniale> createSituationMatrimoniale(@Valid @RequestBody SituationMatrimoniale situationMatrimoniale) throws URISyntaxException {
        log.info("REST request to save SituationMatrimoniale : {}", situationMatrimoniale);
        if (situationMatrimoniale.getId() != null) {
            throw new BadRequestAlertException("A new situationMatrimoniale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SituationMatrimoniale result = situationMatrimonialeService.save(situationMatrimoniale);
        return ResponseEntity.created(new URI("/api/situation-matrimoniales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /situation-matrimoniales} : Updates an existing situationMatrimoniale.
     *
     * @param situationMatrimoniale the situationMatrimoniale to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated situationMatrimoniale,
     * or with status {@code 400 (Bad Request)} if the situationMatrimoniale is not valid,
     * or with status {@code 500 (Internal Server Error)} if the situationMatrimoniale couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/situation-matrimoniales")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<SituationMatrimoniale> updateSituationMatrimoniale(@Valid @RequestBody SituationMatrimoniale situationMatrimoniale) throws URISyntaxException {
        log.info("REST request to update SituationMatrimoniale : {}", situationMatrimoniale);
        if (situationMatrimoniale.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SituationMatrimoniale result = situationMatrimonialeService.save(situationMatrimoniale);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, situationMatrimoniale.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /situation-matrimoniales} : get all the situationMatrimoniales.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of situationMatrimoniales in body.
     */
    @GetMapping("/situation-matrimoniales")
    public ResponseEntity<List<SituationMatrimoniale>> getAllSituationMatrimoniales(Pageable pageable) {
        log.info("REST request to get a page of SituationMatrimoniales");
        Page<SituationMatrimoniale> page = situationMatrimonialeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /situation-matrimoniales/:id} : get the "id" situationMatrimoniale.
     *
     * @param id the id of the situationMatrimoniale to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the situationMatrimoniale, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/situation-matrimoniales/{id}")
    public ResponseEntity<SituationMatrimoniale> getSituationMatrimoniale(@PathVariable Long id) {
        log.info("REST request to get SituationMatrimoniale : {}", id);
        Optional<SituationMatrimoniale> situationMatrimoniale = situationMatrimonialeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(situationMatrimoniale);
    }

    /**
     * {@code DELETE  /situation-matrimoniales/:id} : delete the "id" situationMatrimoniale.
     *
     * @param id the id of the situationMatrimoniale to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/situation-matrimoniales/{id}")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Void> deleteSituationMatrimoniale(@PathVariable Long id) {
        log.info("REST request to delete SituationMatrimoniale : {}", id);
        SituationMatrimoniale situationMatrimoniale = situationMatrimonialeService.findOne(id).get();
        situationMatrimonialeService.delete(situationMatrimoniale);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
