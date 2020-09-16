package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.Prime;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.PrimeService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.Prime}.
 */
@RestController
@RequestMapping("/api")
@Timed
public class PrimeResource {

    private final Logger log = LoggerFactory.getLogger(PrimeResource.class);

    private static final String ENTITY_NAME = "prime";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrimeService primeService;

    public PrimeResource(PrimeService primeService) {
        this.primeService = primeService;
    }

    /**
     * {@code POST  /primes} : Create a new prime.
     *
     * @param prime the prime to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prime, or with status {@code 400 (Bad Request)} if the prime has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/primes")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Prime> createPrime(@Valid @RequestBody Prime prime) throws URISyntaxException {
        log.info("REST request to save Prime : {}", prime);
        if (prime.getId() != null) {
            throw new BadRequestAlertException("A new prime cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Prime result = primeService.save(prime);
        return ResponseEntity.created(new URI("/api/primes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /primes} : Updates an existing prime.
     *
     * @param prime the prime to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prime,
     * or with status {@code 400 (Bad Request)} if the prime is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prime couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/primes")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Prime> updatePrime(@Valid @RequestBody Prime prime) throws URISyntaxException {
        log.info("REST request to update Prime : {}", prime);
        if (prime.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Prime result = primeService.save(prime);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prime.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /primes} : get all the primes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of primes in body.
     */
    @GetMapping("/primes")
    public ResponseEntity<List<Prime>> getAllPrimes(Pageable pageable) {
        log.info("REST request to get a page of Primes");
        Page<Prime> page = primeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /primes/:id} : get the "id" prime.
     *
     * @param id the id of the prime to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prime, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/primes/{id}")
    public ResponseEntity<Prime> getPrime(@PathVariable Long id) {
        log.info("REST request to get Prime : {}", id);
        Optional<Prime> prime = primeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prime);
    }

    /**
     * {@code DELETE  /primes/:id} : delete the "id" prime.
     *
     * @param id the id of the prime to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/primes/{id}")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Void> deletePrime(@PathVariable Long id) {
        log.info("REST request to delete Prime : {}", id);
        Prime prime = primeService.findOne(id).get();
        primeService.delete(prime);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
