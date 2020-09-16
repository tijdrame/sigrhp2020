package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.SigrhpApp;
import com.emard.sigrhp.domain.Avantage;
import com.emard.sigrhp.repository.AvantageRepository;
import com.emard.sigrhp.service.AvantageService;

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
 * Integration tests for the {@link AvantageResource} REST controller.
 */
@SpringBootTest(classes = SigrhpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AvantageResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private AvantageRepository avantageRepository;

    @Autowired
    private AvantageService avantageService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvantageMockMvc;

    private Avantage avantage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avantage createEntity(EntityManager em) {
        Avantage avantage = new Avantage()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE);
        return avantage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avantage createUpdatedEntity(EntityManager em) {
        Avantage avantage = new Avantage()
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE);
        return avantage;
    }

    @BeforeEach
    public void initTest() {
        avantage = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvantage() throws Exception {
        int databaseSizeBeforeCreate = avantageRepository.findAll().size();
        // Create the Avantage
        restAvantageMockMvc.perform(post("/api/avantages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avantage)))
            .andExpect(status().isCreated());

        // Validate the Avantage in the database
        List<Avantage> avantageList = avantageRepository.findAll();
        assertThat(avantageList).hasSize(databaseSizeBeforeCreate + 1);
        Avantage testAvantage = avantageList.get(avantageList.size() - 1);
        assertThat(testAvantage.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testAvantage.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createAvantageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avantageRepository.findAll().size();

        // Create the Avantage with an existing ID
        avantage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvantageMockMvc.perform(post("/api/avantages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avantage)))
            .andExpect(status().isBadRequest());

        // Validate the Avantage in the database
        List<Avantage> avantageList = avantageRepository.findAll();
        assertThat(avantageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = avantageRepository.findAll().size();
        // set the field null
        avantage.setLibelle(null);

        // Create the Avantage, which fails.


        restAvantageMockMvc.perform(post("/api/avantages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avantage)))
            .andExpect(status().isBadRequest());

        List<Avantage> avantageList = avantageRepository.findAll();
        assertThat(avantageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = avantageRepository.findAll().size();
        // set the field null
        avantage.setCode(null);

        // Create the Avantage, which fails.


        restAvantageMockMvc.perform(post("/api/avantages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avantage)))
            .andExpect(status().isBadRequest());

        List<Avantage> avantageList = avantageRepository.findAll();
        assertThat(avantageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAvantages() throws Exception {
        // Initialize the database
        avantageRepository.saveAndFlush(avantage);

        // Get all the avantageList
        restAvantageMockMvc.perform(get("/api/avantages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avantage.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @Test
    @Transactional
    public void getAvantage() throws Exception {
        // Initialize the database
        avantageRepository.saveAndFlush(avantage);

        // Get the avantage
        restAvantageMockMvc.perform(get("/api/avantages/{id}", avantage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avantage.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }
    @Test
    @Transactional
    public void getNonExistingAvantage() throws Exception {
        // Get the avantage
        restAvantageMockMvc.perform(get("/api/avantages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvantage() throws Exception {
        // Initialize the database
        avantageService.save(avantage);

        int databaseSizeBeforeUpdate = avantageRepository.findAll().size();

        // Update the avantage
        Avantage updatedAvantage = avantageRepository.findById(avantage.getId()).get();
        // Disconnect from session so that the updates on updatedAvantage are not directly saved in db
        em.detach(updatedAvantage);
        updatedAvantage
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE);

        restAvantageMockMvc.perform(put("/api/avantages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAvantage)))
            .andExpect(status().isOk());

        // Validate the Avantage in the database
        List<Avantage> avantageList = avantageRepository.findAll();
        assertThat(avantageList).hasSize(databaseSizeBeforeUpdate);
        Avantage testAvantage = avantageList.get(avantageList.size() - 1);
        assertThat(testAvantage.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testAvantage.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingAvantage() throws Exception {
        int databaseSizeBeforeUpdate = avantageRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvantageMockMvc.perform(put("/api/avantages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avantage)))
            .andExpect(status().isBadRequest());

        // Validate the Avantage in the database
        List<Avantage> avantageList = avantageRepository.findAll();
        assertThat(avantageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAvantage() throws Exception {
        // Initialize the database
        avantageService.save(avantage);

        int databaseSizeBeforeDelete = avantageRepository.findAll().size();

        // Delete the avantage
        restAvantageMockMvc.perform(delete("/api/avantages/{id}", avantage.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Avantage> avantageList = avantageRepository.findAll();
        assertThat(avantageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
