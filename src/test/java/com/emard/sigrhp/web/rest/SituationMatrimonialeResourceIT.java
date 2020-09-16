package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.SigrhpApp;
import com.emard.sigrhp.domain.SituationMatrimoniale;
import com.emard.sigrhp.repository.SituationMatrimonialeRepository;
import com.emard.sigrhp.service.SituationMatrimonialeService;

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
 * Integration tests for the {@link SituationMatrimonialeResource} REST controller.
 */
@SpringBootTest(classes = SigrhpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SituationMatrimonialeResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Double DEFAULT_NB_PARTS = 1D;
    private static final Double UPDATED_NB_PARTS = 2D;

    @Autowired
    private SituationMatrimonialeRepository situationMatrimonialeRepository;

    @Autowired
    private SituationMatrimonialeService situationMatrimonialeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSituationMatrimonialeMockMvc;

    private SituationMatrimoniale situationMatrimoniale;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SituationMatrimoniale createEntity(EntityManager em) {
        SituationMatrimoniale situationMatrimoniale = new SituationMatrimoniale()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .nbParts(DEFAULT_NB_PARTS);
        return situationMatrimoniale;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SituationMatrimoniale createUpdatedEntity(EntityManager em) {
        SituationMatrimoniale situationMatrimoniale = new SituationMatrimoniale()
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .nbParts(UPDATED_NB_PARTS);
        return situationMatrimoniale;
    }

    @BeforeEach
    public void initTest() {
        situationMatrimoniale = createEntity(em);
    }

    @Test
    @Transactional
    public void createSituationMatrimoniale() throws Exception {
        int databaseSizeBeforeCreate = situationMatrimonialeRepository.findAll().size();
        // Create the SituationMatrimoniale
        restSituationMatrimonialeMockMvc.perform(post("/api/situation-matrimoniales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(situationMatrimoniale)))
            .andExpect(status().isCreated());

        // Validate the SituationMatrimoniale in the database
        List<SituationMatrimoniale> situationMatrimonialeList = situationMatrimonialeRepository.findAll();
        assertThat(situationMatrimonialeList).hasSize(databaseSizeBeforeCreate + 1);
        SituationMatrimoniale testSituationMatrimoniale = situationMatrimonialeList.get(situationMatrimonialeList.size() - 1);
        assertThat(testSituationMatrimoniale.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testSituationMatrimoniale.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSituationMatrimoniale.getNbParts()).isEqualTo(DEFAULT_NB_PARTS);
    }

    @Test
    @Transactional
    public void createSituationMatrimonialeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = situationMatrimonialeRepository.findAll().size();

        // Create the SituationMatrimoniale with an existing ID
        situationMatrimoniale.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSituationMatrimonialeMockMvc.perform(post("/api/situation-matrimoniales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(situationMatrimoniale)))
            .andExpect(status().isBadRequest());

        // Validate the SituationMatrimoniale in the database
        List<SituationMatrimoniale> situationMatrimonialeList = situationMatrimonialeRepository.findAll();
        assertThat(situationMatrimonialeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = situationMatrimonialeRepository.findAll().size();
        // set the field null
        situationMatrimoniale.setLibelle(null);

        // Create the SituationMatrimoniale, which fails.


        restSituationMatrimonialeMockMvc.perform(post("/api/situation-matrimoniales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(situationMatrimoniale)))
            .andExpect(status().isBadRequest());

        List<SituationMatrimoniale> situationMatrimonialeList = situationMatrimonialeRepository.findAll();
        assertThat(situationMatrimonialeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = situationMatrimonialeRepository.findAll().size();
        // set the field null
        situationMatrimoniale.setCode(null);

        // Create the SituationMatrimoniale, which fails.


        restSituationMatrimonialeMockMvc.perform(post("/api/situation-matrimoniales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(situationMatrimoniale)))
            .andExpect(status().isBadRequest());

        List<SituationMatrimoniale> situationMatrimonialeList = situationMatrimonialeRepository.findAll();
        assertThat(situationMatrimonialeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNbPartsIsRequired() throws Exception {
        int databaseSizeBeforeTest = situationMatrimonialeRepository.findAll().size();
        // set the field null
        situationMatrimoniale.setNbParts(null);

        // Create the SituationMatrimoniale, which fails.


        restSituationMatrimonialeMockMvc.perform(post("/api/situation-matrimoniales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(situationMatrimoniale)))
            .andExpect(status().isBadRequest());

        List<SituationMatrimoniale> situationMatrimonialeList = situationMatrimonialeRepository.findAll();
        assertThat(situationMatrimonialeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSituationMatrimoniales() throws Exception {
        // Initialize the database
        situationMatrimonialeRepository.saveAndFlush(situationMatrimoniale);

        // Get all the situationMatrimonialeList
        restSituationMatrimonialeMockMvc.perform(get("/api/situation-matrimoniales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(situationMatrimoniale.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nbParts").value(hasItem(DEFAULT_NB_PARTS.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getSituationMatrimoniale() throws Exception {
        // Initialize the database
        situationMatrimonialeRepository.saveAndFlush(situationMatrimoniale);

        // Get the situationMatrimoniale
        restSituationMatrimonialeMockMvc.perform(get("/api/situation-matrimoniales/{id}", situationMatrimoniale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(situationMatrimoniale.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.nbParts").value(DEFAULT_NB_PARTS.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSituationMatrimoniale() throws Exception {
        // Get the situationMatrimoniale
        restSituationMatrimonialeMockMvc.perform(get("/api/situation-matrimoniales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSituationMatrimoniale() throws Exception {
        // Initialize the database
        situationMatrimonialeService.save(situationMatrimoniale);

        int databaseSizeBeforeUpdate = situationMatrimonialeRepository.findAll().size();

        // Update the situationMatrimoniale
        SituationMatrimoniale updatedSituationMatrimoniale = situationMatrimonialeRepository.findById(situationMatrimoniale.getId()).get();
        // Disconnect from session so that the updates on updatedSituationMatrimoniale are not directly saved in db
        em.detach(updatedSituationMatrimoniale);
        updatedSituationMatrimoniale
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .nbParts(UPDATED_NB_PARTS);

        restSituationMatrimonialeMockMvc.perform(put("/api/situation-matrimoniales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSituationMatrimoniale)))
            .andExpect(status().isOk());

        // Validate the SituationMatrimoniale in the database
        List<SituationMatrimoniale> situationMatrimonialeList = situationMatrimonialeRepository.findAll();
        assertThat(situationMatrimonialeList).hasSize(databaseSizeBeforeUpdate);
        SituationMatrimoniale testSituationMatrimoniale = situationMatrimonialeList.get(situationMatrimonialeList.size() - 1);
        assertThat(testSituationMatrimoniale.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testSituationMatrimoniale.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSituationMatrimoniale.getNbParts()).isEqualTo(UPDATED_NB_PARTS);
    }

    @Test
    @Transactional
    public void updateNonExistingSituationMatrimoniale() throws Exception {
        int databaseSizeBeforeUpdate = situationMatrimonialeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSituationMatrimonialeMockMvc.perform(put("/api/situation-matrimoniales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(situationMatrimoniale)))
            .andExpect(status().isBadRequest());

        // Validate the SituationMatrimoniale in the database
        List<SituationMatrimoniale> situationMatrimonialeList = situationMatrimonialeRepository.findAll();
        assertThat(situationMatrimonialeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSituationMatrimoniale() throws Exception {
        // Initialize the database
        situationMatrimonialeService.save(situationMatrimoniale);

        int databaseSizeBeforeDelete = situationMatrimonialeRepository.findAll().size();

        // Delete the situationMatrimoniale
        restSituationMatrimonialeMockMvc.perform(delete("/api/situation-matrimoniales/{id}", situationMatrimoniale.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SituationMatrimoniale> situationMatrimonialeList = situationMatrimonialeRepository.findAll();
        assertThat(situationMatrimonialeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
