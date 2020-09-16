package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.SigrhpApp;
import com.emard.sigrhp.domain.TypeContrat;
import com.emard.sigrhp.repository.TypeContratRepository;
import com.emard.sigrhp.service.TypeContratService;

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
 * Integration tests for the {@link TypeContratResource} REST controller.
 */
@SpringBootTest(classes = SigrhpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TypeContratResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private TypeContratRepository typeContratRepository;

    @Autowired
    private TypeContratService typeContratService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeContratMockMvc;

    private TypeContrat typeContrat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeContrat createEntity(EntityManager em) {
        TypeContrat typeContrat = new TypeContrat()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE);
        return typeContrat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeContrat createUpdatedEntity(EntityManager em) {
        TypeContrat typeContrat = new TypeContrat()
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE);
        return typeContrat;
    }

    @BeforeEach
    public void initTest() {
        typeContrat = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeContrat() throws Exception {
        int databaseSizeBeforeCreate = typeContratRepository.findAll().size();
        // Create the TypeContrat
        restTypeContratMockMvc.perform(post("/api/type-contrats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeContrat)))
            .andExpect(status().isCreated());

        // Validate the TypeContrat in the database
        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeCreate + 1);
        TypeContrat testTypeContrat = typeContratList.get(typeContratList.size() - 1);
        assertThat(testTypeContrat.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypeContrat.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createTypeContratWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeContratRepository.findAll().size();

        // Create the TypeContrat with an existing ID
        typeContrat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeContratMockMvc.perform(post("/api/type-contrats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeContrat)))
            .andExpect(status().isBadRequest());

        // Validate the TypeContrat in the database
        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeContratRepository.findAll().size();
        // set the field null
        typeContrat.setLibelle(null);

        // Create the TypeContrat, which fails.


        restTypeContratMockMvc.perform(post("/api/type-contrats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeContrat)))
            .andExpect(status().isBadRequest());

        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeContratRepository.findAll().size();
        // set the field null
        typeContrat.setCode(null);

        // Create the TypeContrat, which fails.


        restTypeContratMockMvc.perform(post("/api/type-contrats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeContrat)))
            .andExpect(status().isBadRequest());

        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeContrats() throws Exception {
        // Initialize the database
        typeContratRepository.saveAndFlush(typeContrat);

        // Get all the typeContratList
        restTypeContratMockMvc.perform(get("/api/type-contrats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeContrat.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @Test
    @Transactional
    public void getTypeContrat() throws Exception {
        // Initialize the database
        typeContratRepository.saveAndFlush(typeContrat);

        // Get the typeContrat
        restTypeContratMockMvc.perform(get("/api/type-contrats/{id}", typeContrat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeContrat.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }
    @Test
    @Transactional
    public void getNonExistingTypeContrat() throws Exception {
        // Get the typeContrat
        restTypeContratMockMvc.perform(get("/api/type-contrats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeContrat() throws Exception {
        // Initialize the database
        typeContratService.save(typeContrat);

        int databaseSizeBeforeUpdate = typeContratRepository.findAll().size();

        // Update the typeContrat
        TypeContrat updatedTypeContrat = typeContratRepository.findById(typeContrat.getId()).get();
        // Disconnect from session so that the updates on updatedTypeContrat are not directly saved in db
        em.detach(updatedTypeContrat);
        updatedTypeContrat
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE);

        restTypeContratMockMvc.perform(put("/api/type-contrats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeContrat)))
            .andExpect(status().isOk());

        // Validate the TypeContrat in the database
        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeUpdate);
        TypeContrat testTypeContrat = typeContratList.get(typeContratList.size() - 1);
        assertThat(testTypeContrat.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypeContrat.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeContrat() throws Exception {
        int databaseSizeBeforeUpdate = typeContratRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeContratMockMvc.perform(put("/api/type-contrats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeContrat)))
            .andExpect(status().isBadRequest());

        // Validate the TypeContrat in the database
        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeContrat() throws Exception {
        // Initialize the database
        typeContratService.save(typeContrat);

        int databaseSizeBeforeDelete = typeContratRepository.findAll().size();

        // Delete the typeContrat
        restTypeContratMockMvc.perform(delete("/api/type-contrats/{id}", typeContrat.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
