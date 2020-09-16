package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.SigrhpApp;
import com.emard.sigrhp.domain.AvantageCollab;
import com.emard.sigrhp.domain.Collaborateur;
import com.emard.sigrhp.domain.Avantage;
import com.emard.sigrhp.repository.AvantageCollabRepository;
import com.emard.sigrhp.service.AvantageCollabService;

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
 * Integration tests for the {@link AvantageCollabResource} REST controller.
 */
@SpringBootTest(classes = SigrhpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AvantageCollabResourceIT {

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    @Autowired
    private AvantageCollabRepository avantageCollabRepository;

    @Autowired
    private AvantageCollabService avantageCollabService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvantageCollabMockMvc;

    private AvantageCollab avantageCollab;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvantageCollab createEntity(EntityManager em) {
        AvantageCollab avantageCollab = new AvantageCollab()
            .montant(DEFAULT_MONTANT);
        // Add required entity
        Collaborateur collaborateur;
        if (TestUtil.findAll(em, Collaborateur.class).isEmpty()) {
            collaborateur = CollaborateurResourceIT.createEntity(em);
            em.persist(collaborateur);
            em.flush();
        } else {
            collaborateur = TestUtil.findAll(em, Collaborateur.class).get(0);
        }
        avantageCollab.setCollaborateur(collaborateur);
        // Add required entity
        Avantage avantage;
        if (TestUtil.findAll(em, Avantage.class).isEmpty()) {
            avantage = AvantageResourceIT.createEntity(em);
            em.persist(avantage);
            em.flush();
        } else {
            avantage = TestUtil.findAll(em, Avantage.class).get(0);
        }
        avantageCollab.setAvantage(avantage);
        return avantageCollab;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvantageCollab createUpdatedEntity(EntityManager em) {
        AvantageCollab avantageCollab = new AvantageCollab()
            .montant(UPDATED_MONTANT);
        // Add required entity
        Collaborateur collaborateur;
        if (TestUtil.findAll(em, Collaborateur.class).isEmpty()) {
            collaborateur = CollaborateurResourceIT.createUpdatedEntity(em);
            em.persist(collaborateur);
            em.flush();
        } else {
            collaborateur = TestUtil.findAll(em, Collaborateur.class).get(0);
        }
        avantageCollab.setCollaborateur(collaborateur);
        // Add required entity
        Avantage avantage;
        if (TestUtil.findAll(em, Avantage.class).isEmpty()) {
            avantage = AvantageResourceIT.createUpdatedEntity(em);
            em.persist(avantage);
            em.flush();
        } else {
            avantage = TestUtil.findAll(em, Avantage.class).get(0);
        }
        avantageCollab.setAvantage(avantage);
        return avantageCollab;
    }

    @BeforeEach
    public void initTest() {
        avantageCollab = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvantageCollab() throws Exception {
        int databaseSizeBeforeCreate = avantageCollabRepository.findAll().size();
        // Create the AvantageCollab
        restAvantageCollabMockMvc.perform(post("/api/avantage-collabs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avantageCollab)))
            .andExpect(status().isCreated());

        // Validate the AvantageCollab in the database
        List<AvantageCollab> avantageCollabList = avantageCollabRepository.findAll();
        assertThat(avantageCollabList).hasSize(databaseSizeBeforeCreate + 1);
        AvantageCollab testAvantageCollab = avantageCollabList.get(avantageCollabList.size() - 1);
        assertThat(testAvantageCollab.getMontant()).isEqualTo(DEFAULT_MONTANT);
    }

    @Test
    @Transactional
    public void createAvantageCollabWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avantageCollabRepository.findAll().size();

        // Create the AvantageCollab with an existing ID
        avantageCollab.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvantageCollabMockMvc.perform(post("/api/avantage-collabs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avantageCollab)))
            .andExpect(status().isBadRequest());

        // Validate the AvantageCollab in the database
        List<AvantageCollab> avantageCollabList = avantageCollabRepository.findAll();
        assertThat(avantageCollabList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = avantageCollabRepository.findAll().size();
        // set the field null
        avantageCollab.setMontant(null);

        // Create the AvantageCollab, which fails.


        restAvantageCollabMockMvc.perform(post("/api/avantage-collabs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avantageCollab)))
            .andExpect(status().isBadRequest());

        List<AvantageCollab> avantageCollabList = avantageCollabRepository.findAll();
        assertThat(avantageCollabList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAvantageCollabs() throws Exception {
        // Initialize the database
        avantageCollabRepository.saveAndFlush(avantageCollab);

        // Get all the avantageCollabList
        restAvantageCollabMockMvc.perform(get("/api/avantage-collabs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avantageCollab.getId().intValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getAvantageCollab() throws Exception {
        // Initialize the database
        avantageCollabRepository.saveAndFlush(avantageCollab);

        // Get the avantageCollab
        restAvantageCollabMockMvc.perform(get("/api/avantage-collabs/{id}", avantageCollab.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avantageCollab.getId().intValue()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAvantageCollab() throws Exception {
        // Get the avantageCollab
        restAvantageCollabMockMvc.perform(get("/api/avantage-collabs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvantageCollab() throws Exception {
        // Initialize the database
        avantageCollabService.save(avantageCollab);

        int databaseSizeBeforeUpdate = avantageCollabRepository.findAll().size();

        // Update the avantageCollab
        AvantageCollab updatedAvantageCollab = avantageCollabRepository.findById(avantageCollab.getId()).get();
        // Disconnect from session so that the updates on updatedAvantageCollab are not directly saved in db
        em.detach(updatedAvantageCollab);
        updatedAvantageCollab
            .montant(UPDATED_MONTANT);

        restAvantageCollabMockMvc.perform(put("/api/avantage-collabs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAvantageCollab)))
            .andExpect(status().isOk());

        // Validate the AvantageCollab in the database
        List<AvantageCollab> avantageCollabList = avantageCollabRepository.findAll();
        assertThat(avantageCollabList).hasSize(databaseSizeBeforeUpdate);
        AvantageCollab testAvantageCollab = avantageCollabList.get(avantageCollabList.size() - 1);
        assertThat(testAvantageCollab.getMontant()).isEqualTo(UPDATED_MONTANT);
    }

    @Test
    @Transactional
    public void updateNonExistingAvantageCollab() throws Exception {
        int databaseSizeBeforeUpdate = avantageCollabRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvantageCollabMockMvc.perform(put("/api/avantage-collabs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avantageCollab)))
            .andExpect(status().isBadRequest());

        // Validate the AvantageCollab in the database
        List<AvantageCollab> avantageCollabList = avantageCollabRepository.findAll();
        assertThat(avantageCollabList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAvantageCollab() throws Exception {
        // Initialize the database
        avantageCollabService.save(avantageCollab);

        int databaseSizeBeforeDelete = avantageCollabRepository.findAll().size();

        // Delete the avantageCollab
        restAvantageCollabMockMvc.perform(delete("/api/avantage-collabs/{id}", avantageCollab.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AvantageCollab> avantageCollabList = avantageCollabRepository.findAll();
        assertThat(avantageCollabList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
