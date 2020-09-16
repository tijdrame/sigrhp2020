package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.SigrhpApp;
import com.emard.sigrhp.domain.PrimeCollab;
import com.emard.sigrhp.domain.Collaborateur;
import com.emard.sigrhp.domain.Prime;
import com.emard.sigrhp.repository.PrimeCollabRepository;
import com.emard.sigrhp.service.PrimeCollabService;

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
 * Integration tests for the {@link PrimeCollabResource} REST controller.
 */
@SpringBootTest(classes = SigrhpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PrimeCollabResourceIT {

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    @Autowired
    private PrimeCollabRepository primeCollabRepository;

    @Autowired
    private PrimeCollabService primeCollabService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrimeCollabMockMvc;

    private PrimeCollab primeCollab;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrimeCollab createEntity(EntityManager em) {
        PrimeCollab primeCollab = new PrimeCollab()
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
        primeCollab.setCollaborateur(collaborateur);
        // Add required entity
        Prime prime;
        if (TestUtil.findAll(em, Prime.class).isEmpty()) {
            prime = PrimeResourceIT.createEntity(em);
            em.persist(prime);
            em.flush();
        } else {
            prime = TestUtil.findAll(em, Prime.class).get(0);
        }
        primeCollab.setPrime(prime);
        return primeCollab;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrimeCollab createUpdatedEntity(EntityManager em) {
        PrimeCollab primeCollab = new PrimeCollab()
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
        primeCollab.setCollaborateur(collaborateur);
        // Add required entity
        Prime prime;
        if (TestUtil.findAll(em, Prime.class).isEmpty()) {
            prime = PrimeResourceIT.createUpdatedEntity(em);
            em.persist(prime);
            em.flush();
        } else {
            prime = TestUtil.findAll(em, Prime.class).get(0);
        }
        primeCollab.setPrime(prime);
        return primeCollab;
    }

    @BeforeEach
    public void initTest() {
        primeCollab = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrimeCollab() throws Exception {
        int databaseSizeBeforeCreate = primeCollabRepository.findAll().size();
        // Create the PrimeCollab
        restPrimeCollabMockMvc.perform(post("/api/prime-collabs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(primeCollab)))
            .andExpect(status().isCreated());

        // Validate the PrimeCollab in the database
        List<PrimeCollab> primeCollabList = primeCollabRepository.findAll();
        assertThat(primeCollabList).hasSize(databaseSizeBeforeCreate + 1);
        PrimeCollab testPrimeCollab = primeCollabList.get(primeCollabList.size() - 1);
        assertThat(testPrimeCollab.getMontant()).isEqualTo(DEFAULT_MONTANT);
    }

    @Test
    @Transactional
    public void createPrimeCollabWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = primeCollabRepository.findAll().size();

        // Create the PrimeCollab with an existing ID
        primeCollab.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrimeCollabMockMvc.perform(post("/api/prime-collabs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(primeCollab)))
            .andExpect(status().isBadRequest());

        // Validate the PrimeCollab in the database
        List<PrimeCollab> primeCollabList = primeCollabRepository.findAll();
        assertThat(primeCollabList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = primeCollabRepository.findAll().size();
        // set the field null
        primeCollab.setMontant(null);

        // Create the PrimeCollab, which fails.


        restPrimeCollabMockMvc.perform(post("/api/prime-collabs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(primeCollab)))
            .andExpect(status().isBadRequest());

        List<PrimeCollab> primeCollabList = primeCollabRepository.findAll();
        assertThat(primeCollabList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrimeCollabs() throws Exception {
        // Initialize the database
        primeCollabRepository.saveAndFlush(primeCollab);

        // Get all the primeCollabList
        restPrimeCollabMockMvc.perform(get("/api/prime-collabs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(primeCollab.getId().intValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getPrimeCollab() throws Exception {
        // Initialize the database
        primeCollabRepository.saveAndFlush(primeCollab);

        // Get the primeCollab
        restPrimeCollabMockMvc.perform(get("/api/prime-collabs/{id}", primeCollab.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(primeCollab.getId().intValue()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPrimeCollab() throws Exception {
        // Get the primeCollab
        restPrimeCollabMockMvc.perform(get("/api/prime-collabs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrimeCollab() throws Exception {
        // Initialize the database
        primeCollabService.save(primeCollab);

        int databaseSizeBeforeUpdate = primeCollabRepository.findAll().size();

        // Update the primeCollab
        PrimeCollab updatedPrimeCollab = primeCollabRepository.findById(primeCollab.getId()).get();
        // Disconnect from session so that the updates on updatedPrimeCollab are not directly saved in db
        em.detach(updatedPrimeCollab);
        updatedPrimeCollab
            .montant(UPDATED_MONTANT);

        restPrimeCollabMockMvc.perform(put("/api/prime-collabs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrimeCollab)))
            .andExpect(status().isOk());

        // Validate the PrimeCollab in the database
        List<PrimeCollab> primeCollabList = primeCollabRepository.findAll();
        assertThat(primeCollabList).hasSize(databaseSizeBeforeUpdate);
        PrimeCollab testPrimeCollab = primeCollabList.get(primeCollabList.size() - 1);
        assertThat(testPrimeCollab.getMontant()).isEqualTo(UPDATED_MONTANT);
    }

    @Test
    @Transactional
    public void updateNonExistingPrimeCollab() throws Exception {
        int databaseSizeBeforeUpdate = primeCollabRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrimeCollabMockMvc.perform(put("/api/prime-collabs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(primeCollab)))
            .andExpect(status().isBadRequest());

        // Validate the PrimeCollab in the database
        List<PrimeCollab> primeCollabList = primeCollabRepository.findAll();
        assertThat(primeCollabList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrimeCollab() throws Exception {
        // Initialize the database
        primeCollabService.save(primeCollab);

        int databaseSizeBeforeDelete = primeCollabRepository.findAll().size();

        // Delete the primeCollab
        restPrimeCollabMockMvc.perform(delete("/api/prime-collabs/{id}", primeCollab.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrimeCollab> primeCollabList = primeCollabRepository.findAll();
        assertThat(primeCollabList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
