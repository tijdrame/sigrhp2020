package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.DetailPret;
import com.emard.sigrhp.service.DetailPretService;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.emard.sigrhp.domain.DetailPret}.
 */
@RestController
@RequestMapping("/api")
@Timed
public class DetailPretResource {

    private final Logger log = LoggerFactory.getLogger(DetailPretResource.class);

    private static final String ENTITY_NAME = "detailPret";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetailPretService detailPretService;

    public DetailPretResource(DetailPretService detailPretService) {
        this.detailPretService = detailPretService;
    }

    /**
     * {@code POST  /detail-prets} : Create a new detailPret.
     *
     * @param detailPret the detailPret to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detailPret, or with status {@code 400 (Bad Request)} if the detailPret has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detail-prets")
    public ResponseEntity<DetailPret> createDetailPret(@Valid @RequestBody DetailPret detailPret) throws URISyntaxException {
        log.info("REST request to save DetailPret : {}", detailPret);
        if (detailPret.getId() != null) {
            throw new BadRequestAlertException("A new detailPret cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetailPret result = detailPretService.save(detailPret);
        return ResponseEntity.created(new URI("/api/detail-prets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detail-prets} : Updates an existing detailPret.
     *
     * @param detailPret the detailPret to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailPret,
     * or with status {@code 400 (Bad Request)} if the detailPret is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detailPret couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detail-prets")
    public ResponseEntity<DetailPret> updateDetailPret(@Valid @RequestBody DetailPret detailPret) throws URISyntaxException {
        log.info("REST request to update DetailPret : {}", detailPret);
        if (detailPret.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DetailPret result = detailPretService.save(detailPret);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailPret.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /detail-prets} : get all the detailPrets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detailPrets in body.
     */
    @GetMapping("/detail-prets")
    public ResponseEntity<List<DetailPret>> getAllDetailPrets(Pageable pageable) {
        log.info("REST request to get a page of DetailPrets");
        Page<DetailPret> page = detailPretService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /detail-prets/:id} : get the "id" detailPret.
     *
     * @param id the id of the detailPret to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detailPret, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detail-prets/{id}")
    public ResponseEntity<DetailPret> getDetailPret(@PathVariable Long id) {
        log.info("REST request to get DetailPret : {}", id);
        Optional<DetailPret> detailPret = detailPretService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detailPret);
    }

    /**
     * {@code DELETE  /detail-prets/:id} : delete the "id" detailPret.
     *
     * @param id the id of the detailPret to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detail-prets/{id}")
    public ResponseEntity<Void> deleteDetailPret(@PathVariable Long id) {
        log.info("REST request to delete DetailPret : {}", id);
        DetailPret detailPret = detailPretService.findOne(id).get();
        detailPretService.delete(detailPret);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
