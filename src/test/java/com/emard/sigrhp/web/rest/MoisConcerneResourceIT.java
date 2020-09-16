package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.SigrhpApp;
import com.emard.sigrhp.domain.MoisConcerne;
import com.emard.sigrhp.repository.MoisConcerneRepository;
import com.emard.sigrhp.service.MoisConcerneService;

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
 * Integration tests for the {@link MoisConcerneResource} REST controller.
 */
@SpringBootTest(classes = SigrhpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MoisConcerneResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private MoisConcerneRepository moisConcerneRepository;

    @Autowired
    private MoisConcerneService moisConcerneService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMoisConcerneMockMvc;

    private MoisConcerne moisConcerne;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MoisConcerne createEntity(EntityManager em) {
        MoisConcerne moisConcerne = new MoisConcerne()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE);
        return moisConcerne;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MoisConcerne createUpdatedEntity(EntityManager em) {
        MoisConcerne moisConcerne = new MoisConcerne()
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE);
        return moisConcerne;
    }

    @BeforeEach
    public void initTest() {
        moisConcerne = createEntity(em);
    }

    @Test
    @Transactional
    public void createMoisConcerne() throws Exception {
        int databaseSizeBeforeCreate = moisConcerneRepository.findAll().size();
        // Create the MoisConcerne
        restMoisConcerneMockMvc.perform(post("/api/mois-concernes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(moisConcerne)))
            .andExpect(status().isCreated());

        // Validate the MoisConcerne in the database
        List<MoisConcerne> moisConcerneList = moisConcerneRepository.findAll();
        assertThat(moisConcerneList).hasSize(databaseSizeBeforeCreate + 1);
        MoisConcerne testMoisConcerne = moisConcerneList.get(moisConcerneList.size() - 1);
        assertThat(testMoisConcerne.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testMoisConcerne.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createMoisConcerneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moisConcerneRepository.findAll().size();

        // Create the MoisConcerne with an existing ID
        moisConcerne.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMoisConcerneMockMvc.perform(post("/api/mois-concernes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(moisConcerne)))
            .andExpect(status().isBadRequest());

        // Validate the MoisConcerne in the database
        List<MoisConcerne> moisConcerneList = moisConcerneRepository.findAll();
        assertThat(moisConcerneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = moisConcerneRepository.findAll().size();
        // set the field null
        moisConcerne.setLibelle(null);

        // Create the MoisConcerne, which fails.


        restMoisConcerneMockMvc.perform(post("/api/mois-concernes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(moisConcerne)))
            .andExpect(status().isBadRequest());

        List<MoisConcerne> moisConcerneList = moisConcerneRepository.findAll();
        assertThat(moisConcerneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = moisConcerneRepository.findAll().size();
        // set the field null
        moisConcerne.setCode(null);

        // Create the MoisConcerne, which fails.


        restMoisConcerneMockMvc.perform(post("/api/mois-concernes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(moisConcerne)))
            .andExpect(status().isBadRequest());

        List<MoisConcerne> moisConcerneList = moisConcerneRepository.findAll();
        assertThat(moisConcerneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMoisConcernes() throws Exception {
        // Initialize the database
        moisConcerneRepository.saveAndFlush(moisConcerne);

        // Get all the moisConcerneList
        restMoisConcerneMockMvc.perform(get("/api/mois-concernes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moisConcerne.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @Test
    @Transactional
    public void getMoisConcerne() throws Exception {
        // Initialize the database
        moisConcerneRepository.saveAndFlush(moisConcerne);

        // Get the moisConcerne
        restMoisConcerneMockMvc.perform(get("/api/mois-concernes/{id}", moisConcerne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(moisConcerne.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }
    @Test
    @Transactional
    public void getNonExistingMoisConcerne() throws Exception {
        // Get the moisConcerne
        restMoisConcerneMockMvc.perform(get("/api/mois-concernes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMoisConcerne() throws Exception {
        // Initialize the database
        moisConcerneService.save(moisConcerne);

        int databaseSizeBeforeUpdate = moisConcerneRepository.findAll().size();

        // Update the moisConcerne
        MoisConcerne updatedMoisConcerne = moisConcerneRepository.findById(moisConcerne.getId()).get();
        // Disconnect from session so that the updates on updatedMoisConcerne are not directly saved in db
        em.detach(updatedMoisConcerne);
        updatedMoisConcerne
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE);

        restMoisConcerneMockMvc.perform(put("/api/mois-concernes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMoisConcerne)))
            .andExpect(status().isOk());

        // Validate the MoisConcerne in the database
        List<MoisConcerne> moisConcerneList = moisConcerneRepository.findAll();
        assertThat(moisConcerneList).hasSize(databaseSizeBeforeUpdate);
        MoisConcerne testMoisConcerne = moisConcerneList.get(moisConcerneList.size() - 1);
        assertThat(testMoisConcerne.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testMoisConcerne.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingMoisConcerne() throws Exception {
        int databaseSizeBeforeUpdate = moisConcerneRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoisConcerneMockMvc.perform(put("/api/mois-concernes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(moisConcerne)))
            .andExpect(status().isBadRequest());

        // Validate the MoisConcerne in the database
        List<MoisConcerne> moisConcerneList = moisConcerneRepository.findAll();
        assertThat(moisConcerneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMoisConcerne() throws Exception {
        // Initialize the database
        moisConcerneService.save(moisConcerne);

        int databaseSizeBeforeDelete = moisConcerneRepository.findAll().size();

        // Delete the moisConcerne
        restMoisConcerneMockMvc.perform(delete("/api/mois-concernes/{id}", moisConcerne.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MoisConcerne> moisConcerneList = moisConcerneRepository.findAll();
        assertThat(moisConcerneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
