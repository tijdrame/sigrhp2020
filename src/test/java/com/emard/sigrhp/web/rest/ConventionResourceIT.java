package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.SigrhpApp;
import com.emard.sigrhp.domain.Convention;
import com.emard.sigrhp.repository.ConventionRepository;
import com.emard.sigrhp.service.ConventionService;

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
 * Integration tests for the {@link ConventionResource} REST controller.
 */
@SpringBootTest(classes = SigrhpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConventionResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private ConventionRepository conventionRepository;

    @Autowired
    private ConventionService conventionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConventionMockMvc;

    private Convention convention;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Convention createEntity(EntityManager em) {
        Convention convention = new Convention()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE);
        return convention;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Convention createUpdatedEntity(EntityManager em) {
        Convention convention = new Convention()
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE);
        return convention;
    }

    @BeforeEach
    public void initTest() {
        convention = createEntity(em);
    }

    @Test
    @Transactional
    public void createConvention() throws Exception {
        int databaseSizeBeforeCreate = conventionRepository.findAll().size();
        // Create the Convention
        restConventionMockMvc.perform(post("/api/conventions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(convention)))
            .andExpect(status().isCreated());

        // Validate the Convention in the database
        List<Convention> conventionList = conventionRepository.findAll();
        assertThat(conventionList).hasSize(databaseSizeBeforeCreate + 1);
        Convention testConvention = conventionList.get(conventionList.size() - 1);
        assertThat(testConvention.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testConvention.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createConventionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conventionRepository.findAll().size();

        // Create the Convention with an existing ID
        convention.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConventionMockMvc.perform(post("/api/conventions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(convention)))
            .andExpect(status().isBadRequest());

        // Validate the Convention in the database
        List<Convention> conventionList = conventionRepository.findAll();
        assertThat(conventionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = conventionRepository.findAll().size();
        // set the field null
        convention.setLibelle(null);

        // Create the Convention, which fails.


        restConventionMockMvc.perform(post("/api/conventions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(convention)))
            .andExpect(status().isBadRequest());

        List<Convention> conventionList = conventionRepository.findAll();
        assertThat(conventionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = conventionRepository.findAll().size();
        // set the field null
        convention.setCode(null);

        // Create the Convention, which fails.


        restConventionMockMvc.perform(post("/api/conventions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(convention)))
            .andExpect(status().isBadRequest());

        List<Convention> conventionList = conventionRepository.findAll();
        assertThat(conventionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConventions() throws Exception {
        // Initialize the database
        conventionRepository.saveAndFlush(convention);

        // Get all the conventionList
        restConventionMockMvc.perform(get("/api/conventions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(convention.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @Test
    @Transactional
    public void getConvention() throws Exception {
        // Initialize the database
        conventionRepository.saveAndFlush(convention);

        // Get the convention
        restConventionMockMvc.perform(get("/api/conventions/{id}", convention.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(convention.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }
    @Test
    @Transactional
    public void getNonExistingConvention() throws Exception {
        // Get the convention
        restConventionMockMvc.perform(get("/api/conventions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConvention() throws Exception {
        // Initialize the database
        conventionService.save(convention);

        int databaseSizeBeforeUpdate = conventionRepository.findAll().size();

        // Update the convention
        Convention updatedConvention = conventionRepository.findById(convention.getId()).get();
        // Disconnect from session so that the updates on updatedConvention are not directly saved in db
        em.detach(updatedConvention);
        updatedConvention
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE);

        restConventionMockMvc.perform(put("/api/conventions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConvention)))
            .andExpect(status().isOk());

        // Validate the Convention in the database
        List<Convention> conventionList = conventionRepository.findAll();
        assertThat(conventionList).hasSize(databaseSizeBeforeUpdate);
        Convention testConvention = conventionList.get(conventionList.size() - 1);
        assertThat(testConvention.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testConvention.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingConvention() throws Exception {
        int databaseSizeBeforeUpdate = conventionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConventionMockMvc.perform(put("/api/conventions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(convention)))
            .andExpect(status().isBadRequest());

        // Validate the Convention in the database
        List<Convention> conventionList = conventionRepository.findAll();
        assertThat(conventionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConvention() throws Exception {
        // Initialize the database
        conventionService.save(convention);

        int databaseSizeBeforeDelete = conventionRepository.findAll().size();

        // Delete the convention
        restConventionMockMvc.perform(delete("/api/conventions/{id}", convention.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Convention> conventionList = conventionRepository.findAll();
        assertThat(conventionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
