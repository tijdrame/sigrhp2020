package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.domain.Pieces;
import com.emard.sigrhp.security.AuthoritiesConstants;
import com.emard.sigrhp.service.PiecesService;
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
 * REST controller for managing {@link com.emard.sigrhp.domain.Pieces}.
 */
@RestController
@RequestMapping("/api")
@Timed
public class PiecesResource {

    private final Logger log = LoggerFactory.getLogger(PiecesResource.class);

    private static final String ENTITY_NAME = "pieces";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PiecesService piecesService;

    public PiecesResource(PiecesService piecesService) {
        this.piecesService = piecesService;
    }

    /**
     * {@code POST  /pieces} : Create a new pieces.
     *
     * @param pieces the pieces to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new pieces, or with status {@code 400 (Bad Request)} if the
     *         pieces has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pieces")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Pieces> createPieces(@Valid @RequestBody Pieces pieces) throws URISyntaxException {
        log.info("REST request to save Pieces : {}", pieces);
        if (pieces.getId() != null) {
            throw new BadRequestAlertException("A new pieces cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pieces result = piecesService.save(pieces);
        return ResponseEntity
                .created(new URI("/api/pieces/" + result.getId())).headers(HeaderUtil
                        .createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /pieces} : Updates an existing pieces.
     *
     * @param pieces the pieces to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated pieces, or with status {@code 400 (Bad Request)} if the
     *         pieces is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the pieces couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pieces")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Pieces> updatePieces(@Valid @RequestBody Pieces pieces) throws URISyntaxException {
        log.info("REST request to update Pieces : {}", pieces);
        if (pieces.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pieces result = piecesService.save(pieces);
        return ResponseEntity.ok().headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pieces.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /pieces} : get all the pieces.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of pieces in body.
     */
    @GetMapping("/pieces")
    public ResponseEntity<List<Pieces>> getAllPieces(Pageable pageable) {
        log.info("REST request to get a page of Pieces");
        Page<Pieces> page = piecesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/piecesBis")
    public ResponseEntity<List<Pieces>> search(@RequestParam(required = false) String prenom,
            @RequestParam(required = false) String nom, @RequestParam(required = false) String matricule,
            @RequestParam(required = false) Boolean deleted, Pageable pageable) {
        log.debug("REST request to get a page of Pieces search!!!");
        if (prenom.equals("undefined"))
            prenom = "";
        if (nom.equals("undefined"))
            nom = "";
        if (matricule.equals("undefined"))
            matricule = "";
        prenom = prenom.trim();
        nom = nom.trim();
        matricule = matricule.trim();
        Page<Pieces> page = piecesService.findAllBis(prenom, nom, matricule, deleted, pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pieces/:id} : get the "id" pieces.
     *
     * @param id the id of the pieces to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the pieces, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pieces/{id}")
    public ResponseEntity<Pieces> getPieces(@PathVariable Long id) {
        log.info("REST request to get Pieces : {}", id);
        Optional<Pieces> pieces = piecesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pieces);
    }

    /**
     * {@code DELETE  /pieces/:id} : delete the "id" pieces.
     *
     * @param id the id of the pieces to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pieces/{id}")
    @PreAuthorize(" hasRole(\"" + AuthoritiesConstants.ADMINST + "\") OR hasRole(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Void> deletePieces(@PathVariable Long id) {
        log.info("REST request to delete Pieces : {}", id);
        Pieces pieces = piecesService.findOne(id).get();
        piecesService.delete(pieces);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }
}
