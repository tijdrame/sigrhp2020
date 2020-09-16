package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.SigrhpApp;
import com.emard.sigrhp.domain.Prime;
import com.emard.sigrhp.repository.PrimeRepository;
import com.emard.sigrhp.service.PrimeService;

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
 * Integration tests for the {@link PrimeResource} REST controller.
 */
@SpringBootTest(classes = SigrhpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PrimeResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IMPOSABLE = false;
    private static final Boolean UPDATED_IMPOSABLE = true;

    @Autowired
    private PrimeRepository primeRepository;

    @Autowired
    private PrimeService primeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrimeMockMvc;

    private Prime prime;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prime createEntity(EntityManager em) {
        Prime prime = new Prime()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .imposable(DEFAULT_IMPOSABLE);
        return prime;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prime createUpdatedEntity(EntityManager em) {
        Prime prime = new Prime()
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .imposable(UPDATED_IMPOSABLE);
        return prime;
    }

    @BeforeEach
    public void initTest() {
        prime = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrime() throws Exception {
        int databaseSizeBeforeCreate = primeRepository.findAll().size();
        // Create the Prime
        restPrimeMockMvc.perform(post("/api/primes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prime)))
            .andExpect(status().isCreated());

        // Validate the Prime in the database
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeCreate + 1);
        Prime testPrime = primeList.get(primeList.size() - 1);
        assertThat(testPrime.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testPrime.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPrime.isImposable()).isEqualTo(DEFAULT_IMPOSABLE);
    }

    @Test
    @Transactional
    public void createPrimeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = primeRepository.findAll().size();

        // Create the Prime with an existing ID
        prime.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrimeMockMvc.perform(post("/api/primes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prime)))
            .andExpect(status().isBadRequest());

        // Validate the Prime in the database
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = primeRepository.findAll().size();
        // set the field null
        prime.setLibelle(null);

        // Create the Prime, which fails.


        restPrimeMockMvc.perform(post("/api/primes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prime)))
            .andExpect(status().isBadRequest());

        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = primeRepository.findAll().size();
        // set the field null
        prime.setCode(null);

        // Create the Prime, which fails.


        restPrimeMockMvc.perform(post("/api/primes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prime)))
            .andExpect(status().isBadRequest());

        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrimes() throws Exception {
        // Initialize the database
        primeRepository.saveAndFlush(prime);

        // Get all the primeList
        restPrimeMockMvc.perform(get("/api/primes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prime.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].imposable").value(hasItem(DEFAULT_IMPOSABLE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPrime() throws Exception {
        // Initialize the database
        primeRepository.saveAndFlush(prime);

        // Get the prime
        restPrimeMockMvc.perform(get("/api/primes/{id}", prime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prime.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.imposable").value(DEFAULT_IMPOSABLE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPrime() throws Exception {
        // Get the prime
        restPrimeMockMvc.perform(get("/api/primes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrime() throws Exception {
        // Initialize the database
        primeService.save(prime);

        int databaseSizeBeforeUpdate = primeRepository.findAll().size();

        // Update the prime
        Prime updatedPrime = primeRepository.findById(prime.getId()).get();
        // Disconnect from session so that the updates on updatedPrime are not directly saved in db
        em.detach(updatedPrime);
        updatedPrime
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .imposable(UPDATED_IMPOSABLE);

        restPrimeMockMvc.perform(put("/api/primes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrime)))
            .andExpect(status().isOk());

        // Validate the Prime in the database
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeUpdate);
        Prime testPrime = primeList.get(primeList.size() - 1);
        assertThat(testPrime.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testPrime.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPrime.isImposable()).isEqualTo(UPDATED_IMPOSABLE);
    }

    @Test
    @Transactional
    public void updateNonExistingPrime() throws Exception {
        int databaseSizeBeforeUpdate = primeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrimeMockMvc.perform(put("/api/primes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prime)))
            .andExpect(status().isBadRequest());

        // Validate the Prime in the database
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrime() throws Exception {
        // Initialize the database
        primeService.save(prime);

        int databaseSizeBeforeDelete = primeRepository.findAll().size();

        // Delete the prime
        restPrimeMockMvc.perform(delete("/api/primes/{id}", prime.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
