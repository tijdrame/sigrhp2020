package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.SigrhpApp;
import com.emard.sigrhp.domain.StatutDemande;
import com.emard.sigrhp.repository.StatutDemandeRepository;
import com.emard.sigrhp.service.StatutDemandeService;

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
 * Integration tests for the {@link StatutDemandeResource} REST controller.
 */
@SpringBootTest(classes = SigrhpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StatutDemandeResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private StatutDemandeRepository statutDemandeRepository;

    @Autowired
    private StatutDemandeService statutDemandeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatutDemandeMockMvc;

    private StatutDemande statutDemande;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatutDemande createEntity(EntityManager em) {
        StatutDemande statutDemande = new StatutDemande()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE);
        return statutDemande;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatutDemande createUpdatedEntity(EntityManager em) {
        StatutDemande statutDemande = new StatutDemande()
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE);
        return statutDemande;
    }

    @BeforeEach
    public void initTest() {
        statutDemande = createEntity(em);
    }

    @Test
    @Transactional
    public void createStatutDemande() throws Exception {
        int databaseSizeBeforeCreate = statutDemandeRepository.findAll().size();
        // Create the StatutDemande
        restStatutDemandeMockMvc.perform(post("/api/statut-demandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statutDemande)))
            .andExpect(status().isCreated());

        // Validate the StatutDemande in the database
        List<StatutDemande> statutDemandeList = statutDemandeRepository.findAll();
        assertThat(statutDemandeList).hasSize(databaseSizeBeforeCreate + 1);
        StatutDemande testStatutDemande = statutDemandeList.get(statutDemandeList.size() - 1);
        assertThat(testStatutDemande.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testStatutDemande.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createStatutDemandeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statutDemandeRepository.findAll().size();

        // Create the StatutDemande with an existing ID
        statutDemande.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatutDemandeMockMvc.perform(post("/api/statut-demandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statutDemande)))
            .andExpect(status().isBadRequest());

        // Validate the StatutDemande in the database
        List<StatutDemande> statutDemandeList = statutDemandeRepository.findAll();
        assertThat(statutDemandeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = statutDemandeRepository.findAll().size();
        // set the field null
        statutDemande.setLibelle(null);

        // Create the StatutDemande, which fails.


        restStatutDemandeMockMvc.perform(post("/api/statut-demandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statutDemande)))
            .andExpect(status().isBadRequest());

        List<StatutDemande> statutDemandeList = statutDemandeRepository.findAll();
        assertThat(statutDemandeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = statutDemandeRepository.findAll().size();
        // set the field null
        statutDemande.setCode(null);

        // Create the StatutDemande, which fails.


        restStatutDemandeMockMvc.perform(post("/api/statut-demandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statutDemande)))
            .andExpect(status().isBadRequest());

        List<StatutDemande> statutDemandeList = statutDemandeRepository.findAll();
        assertThat(statutDemandeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStatutDemandes() throws Exception {
        // Initialize the database
        statutDemandeRepository.saveAndFlush(statutDemande);

        // Get all the statutDemandeList
        restStatutDemandeMockMvc.perform(get("/api/statut-demandes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statutDemande.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @Test
    @Transactional
    public void getStatutDemande() throws Exception {
        // Initialize the database
        statutDemandeRepository.saveAndFlush(statutDemande);

        // Get the statutDemande
        restStatutDemandeMockMvc.perform(get("/api/statut-demandes/{id}", statutDemande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statutDemande.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }
    @Test
    @Transactional
    public void getNonExistingStatutDemande() throws Exception {
        // Get the statutDemande
        restStatutDemandeMockMvc.perform(get("/api/statut-demandes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatutDemande() throws Exception {
        // Initialize the database
        statutDemandeService.save(statutDemande);

        int databaseSizeBeforeUpdate = statutDemandeRepository.findAll().size();

        // Update the statutDemande
        StatutDemande updatedStatutDemande = statutDemandeRepository.findById(statutDemande.getId()).get();
        // Disconnect from session so that the updates on updatedStatutDemande are not directly saved in db
        em.detach(updatedStatutDemande);
        updatedStatutDemande
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE);

        restStatutDemandeMockMvc.perform(put("/api/statut-demandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStatutDemande)))
            .andExpect(status().isOk());

        // Validate the StatutDemande in the database
        List<StatutDemande> statutDemandeList = statutDemandeRepository.findAll();
        assertThat(statutDemandeList).hasSize(databaseSizeBeforeUpdate);
        StatutDemande testStatutDemande = statutDemandeList.get(statutDemandeList.size() - 1);
        assertThat(testStatutDemande.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testStatutDemande.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingStatutDemande() throws Exception {
        int databaseSizeBeforeUpdate = statutDemandeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatutDemandeMockMvc.perform(put("/api/statut-demandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statutDemande)))
            .andExpect(status().isBadRequest());

        // Validate the StatutDemande in the database
        List<StatutDemande> statutDemandeList = statutDemandeRepository.findAll();
        assertThat(statutDemandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStatutDemande() throws Exception {
        // Initialize the database
        statutDemandeService.save(statutDemande);

        int databaseSizeBeforeDelete = statutDemandeRepository.findAll().size();

        // Delete the statutDemande
        restStatutDemandeMockMvc.perform(delete("/api/statut-demandes/{id}", statutDemande.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StatutDemande> statutDemandeList = statutDemandeRepository.findAll();
        assertThat(statutDemandeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
