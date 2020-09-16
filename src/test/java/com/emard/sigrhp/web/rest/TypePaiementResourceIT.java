package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.SigrhpApp;
import com.emard.sigrhp.domain.TypePaiement;
import com.emard.sigrhp.repository.TypePaiementRepository;
import com.emard.sigrhp.service.TypePaiementService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TypePaiementResource} REST controller.
 */
@SpringBootTest(classes = SigrhpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TypePaiementResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private TypePaiementRepository typePaiementRepository;

    @Autowired
    private TypePaiementService typePaiementService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypePaiementMockMvc;

    private TypePaiement typePaiement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypePaiement createEntity(EntityManager em) {
        TypePaiement typePaiement = new TypePaiement()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE);
        return typePaiement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypePaiement createUpdatedEntity(EntityManager em) {
        TypePaiement typePaiement = new TypePaiement()
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE);
        return typePaiement;
    }

    @BeforeEach
    public void initTest() {
        typePaiement = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypePaiement() throws Exception {
        int databaseSizeBeforeCreate = typePaiementRepository.findAll().size();
        // Create the TypePaiement
        restTypePaiementMockMvc.perform(post("/api/type-paiements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePaiement)))
            .andExpect(status().isCreated());

        // Validate the TypePaiement in the database
        List<TypePaiement> typePaiementList = typePaiementRepository.findAll();
        assertThat(typePaiementList).hasSize(databaseSizeBeforeCreate + 1);
        TypePaiement testTypePaiement = typePaiementList.get(typePaiementList.size() - 1);
        assertThat(testTypePaiement.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypePaiement.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createTypePaiementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typePaiementRepository.findAll().size();

        // Create the TypePaiement with an existing ID
        typePaiement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypePaiementMockMvc.perform(post("/api/type-paiements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePaiement)))
            .andExpect(status().isBadRequest());

        // Validate the TypePaiement in the database
        List<TypePaiement> typePaiementList = typePaiementRepository.findAll();
        assertThat(typePaiementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = typePaiementRepository.findAll().size();
        // set the field null
        typePaiement.setLibelle(null);

        // Create the TypePaiement, which fails.


        restTypePaiementMockMvc.perform(post("/api/type-paiements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePaiement)))
            .andExpect(status().isBadRequest());

        List<TypePaiement> typePaiementList = typePaiementRepository.findAll();
        assertThat(typePaiementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typePaiementRepository.findAll().size();
        // set the field null
        typePaiement.setCode(null);

        // Create the TypePaiement, which fails.


        restTypePaiementMockMvc.perform(post("/api/type-paiements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePaiement)))
            .andExpect(status().isBadRequest());

        List<TypePaiement> typePaiementList = typePaiementRepository.findAll();
        assertThat(typePaiementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypePaiements() throws Exception {
        // Initialize the database
        typePaiementRepository.saveAndFlush(typePaiement);

        // Get all the typePaiementList
        restTypePaiementMockMvc.perform(get("/api/type-paiements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typePaiement.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @Test
    @Transactional
    public void getTypePaiement() throws Exception {
        // Initialize the database
        typePaiementRepository.saveAndFlush(typePaiement);

        // Get the typePaiement
        restTypePaiementMockMvc.perform(get("/api/type-paiements/{id}", typePaiement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typePaiement.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }
    @Test
    @Transactional
    public void getNonExistingTypePaiement() throws Exception {
        // Get the typePaiement
        restTypePaiementMockMvc.perform(get("/api/type-paiements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypePaiement() throws Exception {
        // Initialize the database
        typePaiementService.save(typePaiement);

        int databaseSizeBeforeUpdate = typePaiementRepository.findAll().size();

        // Update the typePaiement
        TypePaiement updatedTypePaiement = typePaiementRepository.findById(typePaiement.getId()).get();
        // Disconnect from session so that the updates on updatedTypePaiement are not directly saved in db
        em.detach(updatedTypePaiement);
        updatedTypePaiement
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE);

        restTypePaiementMockMvc.perform(put("/api/type-paiements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypePaiement)))
            .andExpect(status().isOk());

        // Validate the TypePaiement in the database
        List<TypePaiement> typePaiementList = typePaiementRepository.findAll();
        assertThat(typePaiementList).hasSize(databaseSizeBeforeUpdate);
        TypePaiement testTypePaiement = typePaiementList.get(typePaiementList.size() - 1);
        assertThat(testTypePaiement.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypePaiement.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypePaiement() throws Exception {
        int databaseSizeBeforeUpdate = typePaiementRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypePaiementMockMvc.perform(put("/api/type-paiements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePaiement)))
            .andExpect(status().isBadRequest());

        // Validate the TypePaiement in the database
        List<TypePaiement> typePaiementList = typePaiementRepository.findAll();
        assertThat(typePaiementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypePaiement() throws Exception {
        // Initialize the database
        typePaiementService.save(typePaiement);

        int databaseSizeBeforeDelete = typePaiementRepository.findAll().size();

        // Delete the typePaiement
        restTypePaiementMockMvc.perform(delete("/api/type-paiements/{id}", typePaiement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypePaiement> typePaiementList = typePaiementRepository.findAll();
        assertThat(typePaiementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
