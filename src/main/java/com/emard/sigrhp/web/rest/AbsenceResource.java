package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.Absence;
import com.emard.sigrhp.service.AbsenceService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.Absence}.
 */
@RestController
@RequestMapping("/api")
public class AbsenceResource {

    private final Logger log = LoggerFactory.getLogger(AbsenceResource.class);

    private static final String ENTITY_NAME = "absence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AbsenceService absenceService;

    public AbsenceResource(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    /**
     * {@code POST  /absences} : Create a new absence.
     *
     * @param absence the absence to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new absence, or with status {@code 400 (Bad Request)} if the absence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/absences")
    public ResponseEntity<Absence> createAbsence(@Valid @RequestBody Absence absence) throws URISyntaxException {
        log.debug("REST request to save Absence : {}", absence);
        if (absence.getId() != null) {
            throw new BadRequestAlertException("A new absence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Absence result = absenceService.save(absence);
        return ResponseEntity.created(new URI("/api/absences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /absences} : Updates an existing absence.
     *
     * @param absence the absence to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated absence,
     * or with status {@code 400 (Bad Request)} if the absence is not valid,
     * or with status {@code 500 (Internal Server Error)} if the absence couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/absences")
    public ResponseEntity<Absence> updateAbsence(@Valid @RequestBody Absence absence) throws URISyntaxException {
        log.debug("REST request to update Absence : {}", absence);
        if (absence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Absence result = absenceService.save(absence);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, absence.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /absences} : get all the absences.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of absences in body.
     */
    @GetMapping("/absences")
    public ResponseEntity<List<Absence>> getAllAbsences(Pageable pageable) {
        log.debug("REST request to get a page of Absences");
        Page<Absence> page = absenceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /absences/:id} : get the "id" absence.
     *
     * @param id the id of the absence to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the absence, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/absences/{id}")
    public ResponseEntity<Absence> getAbsence(@PathVariable Long id) {
        log.debug("REST request to get Absence : {}", id);
        Optional<Absence> absence = absenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(absence);
    }

    @GetMapping("/absencesBis")
    @Timed
    public ResponseEntity<List<Absence>> search(@RequestParam(required = false) String prenom,
                                                      @RequestParam(required = false) String nom,
                                                      @RequestParam(required = false) String matricule,
                                                      @RequestParam(required = false) Long exercice,
                                                      @RequestParam(required = false) Long motif,
                                                      Pageable pageable) {
        log.debug("REST request to get a page of Remboursements");
        if(prenom.equals("undefined")) prenom="";
        if(nom.equals("undefined")) nom="";
        if(matricule.equals("undefined")) matricule="";
        if(exercice ==0 ) exercice=null;
        if(motif ==0) motif=null;
        log.info("Exercice==="+ exercice);
        log.info("Motif==="+ motif);
        prenom = prenom.trim();
        nom = nom.trim();
        matricule = matricule.trim();
        Page<Absence> page = absenceService.findAllBis(prenom, nom, matricule, exercice, motif, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code DELETE  /absences/:id} : delete the "id" absence.
     *
     * @param id the id of the absence to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/absences/{id}")
    public ResponseEntity<Void> deleteAbsence(@PathVariable Long id) {
        log.debug("REST request to delete Absence : {}", id);
        Absence absence = absenceService.findOne(id).get();
        absenceService.delete(absence);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
