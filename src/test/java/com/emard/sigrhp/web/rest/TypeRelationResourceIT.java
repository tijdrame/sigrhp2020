package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.SigrhpApp;
import com.emard.sigrhp.domain.TypeRelation;
import com.emard.sigrhp.repository.TypeRelationRepository;
import com.emard.sigrhp.service.TypeRelationService;

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
 * Integration tests for the {@link TypeRelationResource} REST controller.
 */
@SpringBootTest(classes = SigrhpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TypeRelationResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Double DEFAULT_NB_PARTS = 1D;
    private static final Double UPDATED_NB_PARTS = 2D;

    @Autowired
    private TypeRelationRepository typeRelationRepository;

    @Autowired
    private TypeRelationService typeRelationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeRelationMockMvc;

    private TypeRelation typeRelation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeRelation createEntity(EntityManager em) {
        TypeRelation typeRelation = new TypeRelation()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .nbParts(DEFAULT_NB_PARTS);
        return typeRelation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeRelation createUpdatedEntity(EntityManager em) {
        TypeRelation typeRelation = new TypeRelation()
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .nbParts(UPDATED_NB_PARTS);
        return typeRelation;
    }

    @BeforeEach
    public void initTest() {
        typeRelation = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeRelation() throws Exception {
        int databaseSizeBeforeCreate = typeRelationRepository.findAll().size();
        // Create the TypeRelation
        restTypeRelationMockMvc.perform(post("/api/type-relations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeRelation)))
            .andExpect(status().isCreated());

        // Validate the TypeRelation in the database
        List<TypeRelation> typeRelationList = typeRelationRepository.findAll();
        assertThat(typeRelationList).hasSize(databaseSizeBeforeCreate + 1);
        TypeRelation testTypeRelation = typeRelationList.get(typeRelationList.size() - 1);
        assertThat(testTypeRelation.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypeRelation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeRelation.getNbParts()).isEqualTo(DEFAULT_NB_PARTS);
    }

    @Test
    @Transactional
    public void createTypeRelationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeRelationRepository.findAll().size();

        // Create the TypeRelation with an existing ID
        typeRelation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeRelationMockMvc.perform(post("/api/type-relations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeRelation)))
            .andExpect(status().isBadRequest());

        // Validate the TypeRelation in the database
        List<TypeRelation> typeRelationList = typeRelationRepository.findAll();
        assertThat(typeRelationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeRelationRepository.findAll().size();
        // set the field null
        typeRelation.setLibelle(null);

        // Create the TypeRelation, which fails.


        restTypeRelationMockMvc.perform(post("/api/type-relations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeRelation)))
            .andExpect(status().isBadRequest());

        List<TypeRelation> typeRelationList = typeRelationRepository.findAll();
        assertThat(typeRelationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeRelationRepository.findAll().size();
        // set the field null
        typeRelation.setCode(null);

        // Create the TypeRelation, which fails.


        restTypeRelationMockMvc.perform(post("/api/type-relations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeRelation)))
            .andExpect(status().isBadRequest());

        List<TypeRelation> typeRelationList = typeRelationRepository.findAll();
        assertThat(typeRelationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNbPartsIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeRelationRepository.findAll().size();
        // set the field null
        typeRelation.setNbParts(null);

        // Create the TypeRelation, which fails.


        restTypeRelationMockMvc.perform(post("/api/type-relations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeRelation)))
            .andExpect(status().isBadRequest());

        List<TypeRelation> typeRelationList = typeRelationRepository.findAll();
        assertThat(typeRelationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeRelations() throws Exception {
        // Initialize the database
        typeRelationRepository.saveAndFlush(typeRelation);

        // Get all the typeRelationList
        restTypeRelationMockMvc.perform(get("/api/type-relations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nbParts").value(hasItem(DEFAULT_NB_PARTS.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTypeRelation() throws Exception {
        // Initialize the database
        typeRelationRepository.saveAndFlush(typeRelation);

        // Get the typeRelation
        restTypeRelationMockMvc.perform(get("/api/type-relations/{id}", typeRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeRelation.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.nbParts").value(DEFAULT_NB_PARTS.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTypeRelation() throws Exception {
        // Get the typeRelation
        restTypeRelationMockMvc.perform(get("/api/type-relations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeRelation() throws Exception {
        // Initialize the database
        typeRelationService.save(typeRelation);

        int databaseSizeBeforeUpdate = typeRelationRepository.findAll().size();

        // Update the typeRelation
        TypeRelation updatedTypeRelation = typeRelationRepository.findById(typeRelation.getId()).get();
        // Disconnect from session so that the updates on updatedTypeRelation are not directly saved in db
        em.detach(updatedTypeRelation);
        updatedTypeRelation
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .nbParts(UPDATED_NB_PARTS);

        restTypeRelationMockMvc.perform(put("/api/type-relations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeRelation)))
            .andExpect(status().isOk());

        // Validate the TypeRelation in the database
        List<TypeRelation> typeRelationList = typeRelationRepository.findAll();
        assertThat(typeRelationList).hasSize(databaseSizeBeforeUpdate);
        TypeRelation testTypeRelation = typeRelationList.get(typeRelationList.size() - 1);
        assertThat(testTypeRelation.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypeRelation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeRelation.getNbParts()).isEqualTo(UPDATED_NB_PARTS);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeRelation() throws Exception {
        int databaseSizeBeforeUpdate = typeRelationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeRelationMockMvc.perform(put("/api/type-relations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeRelation)))
            .andExpect(status().isBadRequest());

        // Validate the TypeRelation in the database
        List<TypeRelation> typeRelationList = typeRelationRepository.findAll();
        assertThat(typeRelationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeRelation() throws Exception {
        // Initialize the database
        typeRelationService.save(typeRelation);

        int databaseSizeBeforeDelete = typeRelationRepository.findAll().size();

        // Delete the typeRelation
        restTypeRelationMockMvc.perform(delete("/api/type-relations/{id}", typeRelation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeRelation> typeRelationList = typeRelationRepository.findAll();
        assertThat(typeRelationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
