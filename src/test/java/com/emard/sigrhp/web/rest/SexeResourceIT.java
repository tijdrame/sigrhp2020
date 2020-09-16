package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.SigrhpApp;
import com.emard.sigrhp.domain.Sexe;
import com.emard.sigrhp.repository.SexeRepository;
import com.emard.sigrhp.service.SexeService;

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
 * Integration tests for the {@link SexeResource} REST controller.
 */
@SpringBootTest(classes = SigrhpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SexeResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private SexeRepository sexeRepository;

    @Autowired
    private SexeService sexeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSexeMockMvc;

    private Sexe sexe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sexe createEntity(EntityManager em) {
        Sexe sexe = new Sexe()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE);
        return sexe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sexe createUpdatedEntity(EntityManager em) {
        Sexe sexe = new Sexe()
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE);
        return sexe;
    }

    @BeforeEach
    public void initTest() {
        sexe = createEntity(em);
    }

    @Test
    @Transactional
    public void createSexe() throws Exception {
        int databaseSizeBeforeCreate = sexeRepository.findAll().size();
        // Create the Sexe
        restSexeMockMvc.perform(post("/api/sexes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sexe)))
            .andExpect(status().isCreated());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeCreate + 1);
        Sexe testSexe = sexeList.get(sexeList.size() - 1);
        assertThat(testSexe.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testSexe.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createSexeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sexeRepository.findAll().size();

        // Create the Sexe with an existing ID
        sexe.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSexeMockMvc.perform(post("/api/sexes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sexe)))
            .andExpect(status().isBadRequest());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = sexeRepository.findAll().size();
        // set the field null
        sexe.setLibelle(null);

        // Create the Sexe, which fails.


        restSexeMockMvc.perform(post("/api/sexes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sexe)))
            .andExpect(status().isBadRequest());

        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sexeRepository.findAll().size();
        // set the field null
        sexe.setCode(null);

        // Create the Sexe, which fails.


        restSexeMockMvc.perform(post("/api/sexes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sexe)))
            .andExpect(status().isBadRequest());

        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSexes() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList
        restSexeMockMvc.perform(get("/api/sexes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sexe.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @Test
    @Transactional
    public void getSexe() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get the sexe
        restSexeMockMvc.perform(get("/api/sexes/{id}", sexe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sexe.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }
    @Test
    @Transactional
    public void getNonExistingSexe() throws Exception {
        // Get the sexe
        restSexeMockMvc.perform(get("/api/sexes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSexe() throws Exception {
        // Initialize the database
        sexeService.save(sexe);

        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();

        // Update the sexe
        Sexe updatedSexe = sexeRepository.findById(sexe.getId()).get();
        // Disconnect from session so that the updates on updatedSexe are not directly saved in db
        em.detach(updatedSexe);
        updatedSexe
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE);

        restSexeMockMvc.perform(put("/api/sexes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSexe)))
            .andExpect(status().isOk());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
        Sexe testSexe = sexeList.get(sexeList.size() - 1);
        assertThat(testSexe.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testSexe.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingSexe() throws Exception {
        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSexeMockMvc.perform(put("/api/sexes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sexe)))
            .andExpect(status().isBadRequest());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSexe() throws Exception {
        // Initialize the database
        sexeService.save(sexe);

        int databaseSizeBeforeDelete = sexeRepository.findAll().size();

        // Delete the sexe
        restSexeMockMvc.perform(delete("/api/sexes/{id}", sexe.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
