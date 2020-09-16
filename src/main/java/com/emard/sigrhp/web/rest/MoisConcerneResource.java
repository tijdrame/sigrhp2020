package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.MoisConcerne;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.MoisConcerneService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.MoisConcerne}.
 */
@RestController
@RequestMapping("/api")
@Timed
public class MoisConcerneResource {

    private final Logger log = LoggerFactory.getLogger(MoisConcerneResource.class);

    private static final String ENTITY_NAME = "moisConcerne";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MoisConcerneService moisConcerneService;

    public MoisConcerneResource(MoisConcerneService moisConcerneService) {
        this.moisConcerneService = moisConcerneService;
    }

    /**
     * {@code POST  /mois-concernes} : Create a new moisConcerne.
     *
     * @param moisConcerne the moisConcerne to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new moisConcerne, or with status {@code 400 (Bad Request)} if the moisConcerne has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mois-concernes")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<MoisConcerne> createMoisConcerne(@Valid @RequestBody MoisConcerne moisConcerne) throws URISyntaxException {
        log.info("REST request to save MoisConcerne : {}", moisConcerne);
        if (moisConcerne.getId() != null) {
            throw new BadRequestAlertException("A new moisConcerne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MoisConcerne result = moisConcerneService.save(moisConcerne);
        return ResponseEntity.created(new URI("/api/mois-concernes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mois-concernes} : Updates an existing moisConcerne.
     *
     * @param moisConcerne the moisConcerne to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moisConcerne,
     * or with status {@code 400 (Bad Request)} if the moisConcerne is not valid,
     * or with status {@code 500 (Internal Server Error)} if the moisConcerne couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mois-concernes")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<MoisConcerne> updateMoisConcerne(@Valid @RequestBody MoisConcerne moisConcerne) throws URISyntaxException {
        log.info("REST request to update MoisConcerne : {}", moisConcerne);
        if (moisConcerne.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MoisConcerne result = moisConcerneService.save(moisConcerne);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moisConcerne.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mois-concernes} : get all the moisConcernes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of moisConcernes in body.
     */
    @GetMapping("/mois-concernes")
    public ResponseEntity<List<MoisConcerne>> getAllMoisConcernes(Pageable pageable) {
        log.info("REST request to get a page of MoisConcernes");
        Page<MoisConcerne> page = moisConcerneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mois-concernes/:id} : get the "id" moisConcerne.
     *
     * @param id the id of the moisConcerne to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the moisConcerne, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mois-concernes/{id}")
    public ResponseEntity<MoisConcerne> getMoisConcerne(@PathVariable Long id) {
        log.info("REST request to get MoisConcerne : {}", id);
        Optional<MoisConcerne> moisConcerne = moisConcerneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(moisConcerne);
    }

    /**
     * {@code DELETE  /mois-concernes/:id} : delete the "id" moisConcerne.
     *
     * @param id the id of the moisConcerne to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mois-concernes/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteMoisConcerne(@PathVariable Long id) {
        log.info("REST request to delete MoisConcerne : {}", id);
        MoisConcerne moisConcerne = moisConcerneService.findOne(id).get();
        moisConcerneService.delete(moisConcerne);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
