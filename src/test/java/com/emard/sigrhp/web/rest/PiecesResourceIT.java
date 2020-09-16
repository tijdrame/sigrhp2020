package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.SigrhpApp;
import com.emard.sigrhp.domain.Pieces;
import com.emard.sigrhp.domain.Collaborateur;
import com.emard.sigrhp.repository.PiecesRepository;
import com.emard.sigrhp.service.PiecesService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PiecesResource} REST controller.
 */
@SpringBootTest(classes = SigrhpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PiecesResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_EXPIRATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EXPIRATION = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private PiecesRepository piecesRepository;

    @Autowired
    private PiecesService piecesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPiecesMockMvc;

    private Pieces pieces;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pieces createEntity(EntityManager em) {
        Pieces pieces = new Pieces()
            .libelle(DEFAULT_LIBELLE)
            .dateCreation(DEFAULT_DATE_CREATION)
            .dateExpiration(DEFAULT_DATE_EXPIRATION)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        // Add required entity
        Collaborateur collaborateur;
        if (TestUtil.findAll(em, Collaborateur.class).isEmpty()) {
            collaborateur = CollaborateurResourceIT.createEntity(em);
            em.persist(collaborateur);
            em.flush();
        } else {
            collaborateur = TestUtil.findAll(em, Collaborateur.class).get(0);
        }
        pieces.setCollaborateur(collaborateur);
        return pieces;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pieces createUpdatedEntity(EntityManager em) {
        Pieces pieces = new Pieces()
            .libelle(UPDATED_LIBELLE)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateExpiration(UPDATED_DATE_EXPIRATION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        // Add required entity
        Collaborateur collaborateur;
        if (TestUtil.findAll(em, Collaborateur.class).isEmpty()) {
            collaborateur = CollaborateurResourceIT.createUpdatedEntity(em);
            em.persist(collaborateur);
            em.flush();
        } else {
            collaborateur = TestUtil.findAll(em, Collaborateur.class).get(0);
        }
        pieces.setCollaborateur(collaborateur);
        return pieces;
    }

    @BeforeEach
    public void initTest() {
        pieces = createEntity(em);
    }

    @Test
    @Transactional
    public void createPieces() throws Exception {
        int databaseSizeBeforeCreate = piecesRepository.findAll().size();
        // Create the Pieces
        restPiecesMockMvc.perform(post("/api/pieces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pieces)))
            .andExpect(status().isCreated());

        // Validate the Pieces in the database
        List<Pieces> piecesList = piecesRepository.findAll();
        assertThat(piecesList).hasSize(databaseSizeBeforeCreate + 1);
        Pieces testPieces = piecesList.get(piecesList.size() - 1);
        assertThat(testPieces.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testPieces.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testPieces.getDateExpiration()).isEqualTo(DEFAULT_DATE_EXPIRATION);
        assertThat(testPieces.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testPieces.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPiecesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = piecesRepository.findAll().size();

        // Create the Pieces with an existing ID
        pieces.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPiecesMockMvc.perform(post("/api/pieces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pieces)))
            .andExpect(status().isBadRequest());

        // Validate the Pieces in the database
        List<Pieces> piecesList = piecesRepository.findAll();
        assertThat(piecesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = piecesRepository.findAll().size();
        // set the field null
        pieces.setLibelle(null);

        // Create the Pieces, which fails.


        restPiecesMockMvc.perform(post("/api/pieces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pieces)))
            .andExpect(status().isBadRequest());

        List<Pieces> piecesList = piecesRepository.findAll();
        assertThat(piecesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateCreationIsRequired() throws Exception {
        int databaseSizeBeforeTest = piecesRepository.findAll().size();
        // set the field null
        pieces.setDateCreation(null);

        // Create the Pieces, which fails.


        restPiecesMockMvc.perform(post("/api/pieces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pieces)))
            .andExpect(status().isBadRequest());

        List<Pieces> piecesList = piecesRepository.findAll();
        assertThat(piecesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPieces() throws Exception {
        // Initialize the database
        piecesRepository.saveAndFlush(pieces);

        // Get all the piecesList
        restPiecesMockMvc.perform(get("/api/pieces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pieces.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].dateExpiration").value(hasItem(DEFAULT_DATE_EXPIRATION.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getPieces() throws Exception {
        // Initialize the database
        piecesRepository.saveAndFlush(pieces);

        // Get the pieces
        restPiecesMockMvc.perform(get("/api/pieces/{id}", pieces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pieces.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.dateExpiration").value(DEFAULT_DATE_EXPIRATION.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }
    @Test
    @Transactional
    public void getNonExistingPieces() throws Exception {
        // Get the pieces
        restPiecesMockMvc.perform(get("/api/pieces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePieces() throws Exception {
        // Initialize the database
        piecesService.save(pieces);

        int databaseSizeBeforeUpdate = piecesRepository.findAll().size();

        // Update the pieces
        Pieces updatedPieces = piecesRepository.findById(pieces.getId()).get();
        // Disconnect from session so that the updates on updatedPieces are not directly saved in db
        em.detach(updatedPieces);
        updatedPieces
            .libelle(UPDATED_LIBELLE)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateExpiration(UPDATED_DATE_EXPIRATION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restPiecesMockMvc.perform(put("/api/pieces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPieces)))
            .andExpect(status().isOk());

        // Validate the Pieces in the database
        List<Pieces> piecesList = piecesRepository.findAll();
        assertThat(piecesList).hasSize(databaseSizeBeforeUpdate);
        Pieces testPieces = piecesList.get(piecesList.size() - 1);
        assertThat(testPieces.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testPieces.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testPieces.getDateExpiration()).isEqualTo(UPDATED_DATE_EXPIRATION);
        assertThat(testPieces.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testPieces.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPieces() throws Exception {
        int databaseSizeBeforeUpdate = piecesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPiecesMockMvc.perform(put("/api/pieces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pieces)))
            .andExpect(status().isBadRequest());

        // Validate the Pieces in the database
        List<Pieces> piecesList = piecesRepository.findAll();
        assertThat(piecesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePieces() throws Exception {
        // Initialize the database
        piecesService.save(pieces);

        int databaseSizeBeforeDelete = piecesRepository.findAll().size();

        // Delete the pieces
        restPiecesMockMvc.perform(delete("/api/pieces/{id}", pieces.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pieces> piecesList = piecesRepository.findAll();
        assertThat(piecesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
