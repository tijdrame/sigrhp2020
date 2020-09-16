package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.SigrhpApp;
import com.emard.sigrhp.domain.Statut;
import com.emard.sigrhp.repository.StatutRepository;
import com.emard.sigrhp.service.StatutService;

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
 * Integration tests for the {@link StatutResource} REST controller.
 */
@SpringBootTest(classes = SigrhpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StatutResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private StatutRepository statutRepository;

    @Autowired
    private StatutService statutService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatutMockMvc;

    private Statut statut;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Statut createEntity(EntityManager em) {
        Statut statut = new Statut()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE);
        return statut;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Statut createUpdatedEntity(EntityManager em) {
        Statut statut = new Statut()
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE);
        return statut;
    }

    @BeforeEach
    public void initTest() {
        statut = createEntity(em);
    }

    @Test
    @Transactional
    public void createStatut() throws Exception {
        int databaseSizeBeforeCreate = statutRepository.findAll().size();
        // Create the Statut
        restStatutMockMvc.perform(post("/api/statuts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statut)))
            .andExpect(status().isCreated());

        // Validate the Statut in the database
        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeCreate + 1);
        Statut testStatut = statutList.get(statutList.size() - 1);
        assertThat(testStatut.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testStatut.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createStatutWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statutRepository.findAll().size();

        // Create the Statut with an existing ID
        statut.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatutMockMvc.perform(post("/api/statuts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statut)))
            .andExpect(status().isBadRequest());

        // Validate the Statut in the database
        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = statutRepository.findAll().size();
        // set the field null
        statut.setLibelle(null);

        // Create the Statut, which fails.


        restStatutMockMvc.perform(post("/api/statuts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statut)))
            .andExpect(status().isBadRequest());

        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = statutRepository.findAll().size();
        // set the field null
        statut.setCode(null);

        // Create the Statut, which fails.


        restStatutMockMvc.perform(post("/api/statuts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statut)))
            .andExpect(status().isBadRequest());

        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStatuts() throws Exception {
        // Initialize the database
        statutRepository.saveAndFlush(statut);

        // Get all the statutList
        restStatutMockMvc.perform(get("/api/statuts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statut.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @Test
    @Transactional
    public void getStatut() throws Exception {
        // Initialize the database
        statutRepository.saveAndFlush(statut);

        // Get the statut
        restStatutMockMvc.perform(get("/api/statuts/{id}", statut.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statut.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }
    @Test
    @Transactional
    public void getNonExistingStatut() throws Exception {
        // Get the statut
        restStatutMockMvc.perform(get("/api/statuts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatut() throws Exception {
        // Initialize the database
        statutService.save(statut);

        int databaseSizeBeforeUpdate = statutRepository.findAll().size();

        // Update the statut
        Statut updatedStatut = statutRepository.findById(statut.getId()).get();
        // Disconnect from session so that the updates on updatedStatut are not directly saved in db
        em.detach(updatedStatut);
        updatedStatut
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE);

        restStatutMockMvc.perform(put("/api/statuts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStatut)))
            .andExpect(status().isOk());

        // Validate the Statut in the database
        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeUpdate);
        Statut testStatut = statutList.get(statutList.size() - 1);
        assertThat(testStatut.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testStatut.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingStatut() throws Exception {
        int databaseSizeBeforeUpdate = statutRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatutMockMvc.perform(put("/api/statuts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statut)))
            .andExpect(status().isBadRequest());

        // Validate the Statut in the database
        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStatut() throws Exception {
        // Initialize the database
        statutService.save(statut);

        int databaseSizeBeforeDelete = statutRepository.findAll().size();

        // Delete the statut
        restStatutMockMvc.perform(delete("/api/statuts/{id}", statut.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
