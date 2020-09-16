package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.Bulletin;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.BulletinService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.Bulletin}.
 */
@RestController
@RequestMapping("/api")
public class BulletinResource {

    private final Logger log = LoggerFactory.getLogger(BulletinResource.class);

    private static final String ENTITY_NAME = "bulletin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BulletinService bulletinService;

    public BulletinResource(BulletinService bulletinService) {
        this.bulletinService = bulletinService;
    }

    /**
     * {@code POST  /bulletins} : Create a new bulletin.
     *
     * @param bulletin the bulletin to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bulletin, or with status {@code 400 (Bad Request)} if the bulletin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bulletins")
    @Timed
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH +   "\")")
    public ResponseEntity<Bulletin> createBulletin(@Valid @RequestBody Bulletin bulletin) throws URISyntaxException {
        log.info("REST request to save Bulletin : {}", bulletin);
        if (bulletin.getId() != null) {
            throw new BadRequestAlertException("A new bulletin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bulletin result = bulletinService.save(bulletin);
        return ResponseEntity.created(new URI("/api/bulletins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bulletins} : Updates an existing bulletin.
     *
     * @param bulletin the bulletin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bulletin,
     * or with status {@code 400 (Bad Request)} if the bulletin is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bulletin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bulletins")
    @Timed
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH +   "\")")
    public ResponseEntity<Bulletin> updateBulletin(@Valid @RequestBody Bulletin bulletin) throws URISyntaxException {
        log.info("REST request to update Bulletin : {}", bulletin);
        if (bulletin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Bulletin result = bulletinService.save(bulletin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bulletin.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bulletins} : get all the bulletins.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bulletins in body.
     */
    @GetMapping("/bulletins")
    @Timed
    public ResponseEntity<List<Bulletin>> getAllBulletins(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.info("REST request to get a page of Bulletins");
        Page<Bulletin> page;
        if (eagerload) {
            page = bulletinService.findAllWithEagerRelationships(pageable);
        } else {
            page = bulletinService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/bulletinsBis")
    @Timed
    public ResponseEntity<List<Bulletin>> search(@RequestParam(required = false) String prenom,
                                                @RequestParam(required = false) String nom,
                                                @RequestParam(required = false) String matricule,
                                                @RequestParam(required = false) Long exercice,
                                                @RequestParam(required = false) Long moisConcerne,
                                                @RequestParam(required = false) Boolean deleted,
                                                Pageable pageable) {
        log.debug("REST request to get a page of Bulleteins search!!!");
        if(prenom.equals("undefined")) prenom="";
        if(nom.equals("undefined")) nom="";
        if(matricule.equals("undefined")) matricule="";
        if(exercice ==0 ) exercice=null;
        if(moisConcerne ==0) moisConcerne=null;
        prenom = prenom.trim();
        nom = nom.trim();
        matricule = matricule.trim();
        Page<Bulletin> page = bulletinService.findAllBis(prenom, nom, matricule, exercice, moisConcerne, deleted, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bulletins/:id} : get the "id" bulletin.
     *
     * @param id the id of the bulletin to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bulletin, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bulletins/{id}")
    @Timed
    public ResponseEntity<Bulletin> getBulletin(@PathVariable Long id) {
        log.info("REST request to get Bulletin : {}", id);
        Optional<Bulletin> bulletin = bulletinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bulletin);
    }

    /**
     * {@code DELETE  /bulletins/:id} : delete the "id" bulletin.
     *
     * @param id the id of the bulletin to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bulletins/{id}")
    @Timed
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH +   "\")")
    public ResponseEntity<Void> deleteBulletin(@PathVariable Long id) {
        log.info("REST request to delete Bulletin : {}", id);
        Bulletin bulletin = bulletinService.findOne(id).get();
        bulletinService.delete(bulletin);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
