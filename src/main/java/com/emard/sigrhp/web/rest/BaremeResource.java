package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.Bareme;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.BaremeService;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.emard.sigrhp.domain.Bareme}.
 */
@RestController
@RequestMapping("/api")
public class BaremeResource {

    private final Logger log = LoggerFactory.getLogger(BaremeResource.class);

    private static final String ENTITY_NAME = "bareme";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BaremeService baremeService;

    public BaremeResource(BaremeService baremeService) {
        this.baremeService = baremeService;
    }

    /**
     * {@code POST  /baremes} : Create a new bareme.
     *
     * @param bareme the bareme to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bareme, or with status {@code 400 (Bad Request)} if the bareme has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/baremes")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Bareme> createBareme(@RequestBody Bareme bareme) throws URISyntaxException {
        log.info("REST request to save Bareme : {}", bareme);
        if (bareme.getId() != null) {
            throw new BadRequestAlertException("A new bareme cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bareme result = baremeService.save(bareme);
        return ResponseEntity.created(new URI("/api/baremes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /baremes} : Updates an existing bareme.
     *
     * @param bareme the bareme to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bareme,
     * or with status {@code 400 (Bad Request)} if the bareme is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bareme couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/baremes")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Bareme> updateBareme(@RequestBody Bareme bareme) throws URISyntaxException {
        log.info("REST request to update Bareme : {}", bareme);
        if (bareme.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Bareme result = baremeService.save(bareme);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bareme.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /baremes} : get all the baremes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of baremes in body.
     */
    @GetMapping("/baremes")
    @Timed
    public ResponseEntity<List<Bareme>> getAllBaremes(Pageable pageable) {
        log.info("REST request to get a page of Baremes");
        Page<Bareme> page = baremeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /baremes/:id} : get the "id" bareme.
     *
     * @param id the id of the bareme to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bareme, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/baremes/{id}")
    public ResponseEntity<Bareme> getBareme(@PathVariable Long id) {
        log.info("REST request to get Bareme : {}", id);
        Optional<Bareme> bareme = baremeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bareme);
    }

    /**
     * {@code DELETE  /baremes/:id} : delete the "id" bareme.
     *
     * @param id the id of the bareme to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/baremes/{id}")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteBareme(@PathVariable Long id) {
        log.info("REST request to delete Bareme : {}", id);
        baremeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /baremesRev/:revenueBrut : get the "revenueBrut" bareme.
     *
     * @param revenueBrut the revenueBrut of the bareme to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bareme, or with status 404 (Not Found)
     */
    @GetMapping("/baremesRev/{revenueBrut}")
    @Timed
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH +   "\")")
    public ResponseEntity<Bareme> getBaremeRev(@PathVariable Double revenueBrut) {
        log.info("REST request to get Bareme : {}", revenueBrut);
        Bareme bareme = baremeService.getBaremeByRevenuBrut(revenueBrut);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bareme));
    }
}
