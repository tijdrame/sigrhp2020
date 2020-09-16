package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.Recap;
import com.emard.sigrhp.repository.RecapRepository;
import com.emard.sigrhp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.emard.sigrhp.domain.Recap}.
 */
/*@RestController
@RequestMapping("/api")
@Transactional*/
public class RecapResource {

    /*private final Logger log = LoggerFactory.getLogger(RecapResource.class);

    private static final String ENTITY_NAME = "recap";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecapRepository recapRepository;

    public RecapResource(RecapRepository recapRepository) {
        this.recapRepository = recapRepository;
    }

    /**
     * {@code POST  /recaps} : Create a new recap.
     *
     * @param recap the recap to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recap, or with status {@code 400 (Bad Request)} if the recap has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    /*@PostMapping("/recaps")
    public ResponseEntity<Recap> createRecap(@RequestBody Recap recap) throws URISyntaxException {
        log.debug("REST request to save Recap : {}", recap);
        if (recap.getId() != null) {
            throw new BadRequestAlertException("A new recap cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Recap result = recapRepository.save(recap);
        return ResponseEntity.created(new URI("/api/recaps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recaps} : Updates an existing recap.
     *
     * @param recap the recap to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recap,
     * or with status {@code 400 (Bad Request)} if the recap is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recap couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    /*@PutMapping("/recaps")
    public ResponseEntity<Recap> updateRecap(@RequestBody Recap recap) throws URISyntaxException {
        log.debug("REST request to update Recap : {}", recap);
        if (recap.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Recap result = recapRepository.save(recap);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recap.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /recaps} : get all the recaps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recaps in body.
     */
    /*@GetMapping("/recaps")
    public ResponseEntity<List<Recap>> getAllRecaps(Pageable pageable) {
        log.debug("REST request to get a page of Recaps");
        Page<Recap> page = recapRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recaps/:id} : get the "id" recap.
     *
     * @param id the id of the recap to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recap, or with status {@code 404 (Not Found)}.
     */
    /*@GetMapping("/recaps/{id}")
    public ResponseEntity<Recap> getRecap(@PathVariable Long id) {
        log.debug("REST request to get Recap : {}", id);
        Optional<Recap> recap = recapRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(recap);
    }

    /**
     * {@code DELETE  /recaps/:id} : delete the "id" recap.
     *
     * @param id the id of the recap to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    /*@DeleteMapping("/recaps/{id}")
    public ResponseEntity<Void> deleteRecap(@PathVariable Long id) {
        log.debug("REST request to delete Recap : {}", id);
        recapRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }*/
}
