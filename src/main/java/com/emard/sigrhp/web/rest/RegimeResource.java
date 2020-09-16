package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.Regime;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.RegimeService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.Regime}.
 */
@RestController
@RequestMapping("/api")
@Timed
public class RegimeResource {

    private final Logger log = LoggerFactory.getLogger(RegimeResource.class);

    private static final String ENTITY_NAME = "regime";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegimeService regimeService;

    public RegimeResource(RegimeService regimeService) {
        this.regimeService = regimeService;
    }

    /**
     * {@code POST  /regimes} : Create a new regime.
     *
     * @param regime the regime to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regime, or with status {@code 400 (Bad Request)} if the regime has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/regimes")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Regime> createRegime(@Valid @RequestBody Regime regime) throws URISyntaxException {
        log.info("REST request to save Regime : {}", regime);
        if (regime.getId() != null) {
            throw new BadRequestAlertException("A new regime cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Regime result = regimeService.save(regime);
        return ResponseEntity.created(new URI("/api/regimes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /regimes} : Updates an existing regime.
     *
     * @param regime the regime to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regime,
     * or with status {@code 400 (Bad Request)} if the regime is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regime couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/regimes")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Regime> updateRegime(@Valid @RequestBody Regime regime) throws URISyntaxException {
        log.info("REST request to update Regime : {}", regime);
        if (regime.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Regime result = regimeService.save(regime);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regime.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /regimes} : get all the regimes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regimes in body.
     */
    @GetMapping("/regimes")
    public ResponseEntity<List<Regime>> getAllRegimes(Pageable pageable) {
        log.info("REST request to get a page of Regimes");
        Page<Regime> page = regimeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /regimes/:id} : get the "id" regime.
     *
     * @param id the id of the regime to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regime, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/regimes/{id}")
    public ResponseEntity<Regime> getRegime(@PathVariable Long id) {
        log.info("REST request to get Regime : {}", id);
        Optional<Regime> regime = regimeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(regime);
    }

    /**
     * {@code DELETE  /regimes/:id} : delete the "id" regime.
     *
     * @param id the id of the regime to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/regimes/{id}")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Void> deleteRegime(@PathVariable Long id) {
        log.info("REST request to delete Regime : {}", id);
        Regime regime = regimeService.findOne(id).get();
        regimeService.delete(regime);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
