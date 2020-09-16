package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.SigrhpApp;
import com.emard.sigrhp.domain.Exercice;
import com.emard.sigrhp.repository.ExerciceRepository;
import com.emard.sigrhp.service.ExerciceService;

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
 * Integration tests for the {@link ExerciceResource} REST controller.
 */
@SpringBootTest(classes = SigrhpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExerciceResourceIT {

    private static final Integer DEFAULT_DEBUT_EXERCICE = 1;
    private static final Integer UPDATED_DEBUT_EXERCICE = 2;

    private static final Integer DEFAULT_FIN_EXERCICE = 1;
    private static final Integer UPDATED_FIN_EXERCICE = 2;

    private static final Boolean DEFAULT_ACTIF = false;
    private static final Boolean UPDATED_ACTIF = true;

    @Autowired
    private ExerciceRepository exerciceRepository;

    @Autowired
    private ExerciceService exerciceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExerciceMockMvc;

    private Exercice exercice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exercice createEntity(EntityManager em) {
        Exercice exercice = new Exercice()
            .debutExercice(DEFAULT_DEBUT_EXERCICE)
            .finExercice(DEFAULT_FIN_EXERCICE)
            .actif(DEFAULT_ACTIF);
        return exercice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exercice createUpdatedEntity(EntityManager em) {
        Exercice exercice = new Exercice()
            .debutExercice(UPDATED_DEBUT_EXERCICE)
            .finExercice(UPDATED_FIN_EXERCICE)
            .actif(UPDATED_ACTIF);
        return exercice;
    }

    @BeforeEach
    public void initTest() {
        exercice = createEntity(em);
    }

    @Test
    @Transactional
    public void createExercice() throws Exception {
        int databaseSizeBeforeCreate = exerciceRepository.findAll().size();
        // Create the Exercice
        restExerciceMockMvc.perform(post("/api/exercices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exercice)))
            .andExpect(status().isCreated());

        // Validate the Exercice in the database
        List<Exercice> exerciceList = exerciceRepository.findAll();
        assertThat(exerciceList).hasSize(databaseSizeBeforeCreate + 1);
        Exercice testExercice = exerciceList.get(exerciceList.size() - 1);
        assertThat(testExercice.getDebutExercice()).isEqualTo(DEFAULT_DEBUT_EXERCICE);
        assertThat(testExercice.getFinExercice()).isEqualTo(DEFAULT_FIN_EXERCICE);
        assertThat(testExercice.isActif()).isEqualTo(DEFAULT_ACTIF);
    }

    @Test
    @Transactional
    public void createExerciceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exerciceRepository.findAll().size();

        // Create the Exercice with an existing ID
        exercice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExerciceMockMvc.perform(post("/api/exercices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exercice)))
            .andExpect(status().isBadRequest());

        // Validate the Exercice in the database
        List<Exercice> exerciceList = exerciceRepository.findAll();
        assertThat(exerciceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDebutExerciceIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciceRepository.findAll().size();
        // set the field null
        exercice.setDebutExercice(null);

        // Create the Exercice, which fails.


        restExerciceMockMvc.perform(post("/api/exercices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exercice)))
            .andExpect(status().isBadRequest());

        List<Exercice> exerciceList = exerciceRepository.findAll();
        assertThat(exerciceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExercices() throws Exception {
        // Initialize the database
        exerciceRepository.saveAndFlush(exercice);

        // Get all the exerciceList
        restExerciceMockMvc.perform(get("/api/exercices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exercice.getId().intValue())))
            .andExpect(jsonPath("$.[*].debutExercice").value(hasItem(DEFAULT_DEBUT_EXERCICE)))
            .andExpect(jsonPath("$.[*].finExercice").value(hasItem(DEFAULT_FIN_EXERCICE)))
            .andExpect(jsonPath("$.[*].actif").value(hasItem(DEFAULT_ACTIF.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getExercice() throws Exception {
        // Initialize the database
        exerciceRepository.saveAndFlush(exercice);

        // Get the exercice
        restExerciceMockMvc.perform(get("/api/exercices/{id}", exercice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(exercice.getId().intValue()))
            .andExpect(jsonPath("$.debutExercice").value(DEFAULT_DEBUT_EXERCICE))
            .andExpect(jsonPath("$.finExercice").value(DEFAULT_FIN_EXERCICE))
            .andExpect(jsonPath("$.actif").value(DEFAULT_ACTIF.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingExercice() throws Exception {
        // Get the exercice
        restExerciceMockMvc.perform(get("/api/exercices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExercice() throws Exception {
        // Initialize the database
        exerciceService.save(exercice);

        int databaseSizeBeforeUpdate = exerciceRepository.findAll().size();

        // Update the exercice
        Exercice updatedExercice = exerciceRepository.findById(exercice.getId()).get();
        // Disconnect from session so that the updates on updatedExercice are not directly saved in db
        em.detach(updatedExercice);
        updatedExercice
            .debutExercice(UPDATED_DEBUT_EXERCICE)
            .finExercice(UPDATED_FIN_EXERCICE)
            .actif(UPDATED_ACTIF);

        restExerciceMockMvc.perform(put("/api/exercices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedExercice)))
            .andExpect(status().isOk());

        // Validate the Exercice in the database
        List<Exercice> exerciceList = exerciceRepository.findAll();
        assertThat(exerciceList).hasSize(databaseSizeBeforeUpdate);
        Exercice testExercice = exerciceList.get(exerciceList.size() - 1);
        assertThat(testExercice.getDebutExercice()).isEqualTo(UPDATED_DEBUT_EXERCICE);
        assertThat(testExercice.getFinExercice()).isEqualTo(UPDATED_FIN_EXERCICE);
        assertThat(testExercice.isActif()).isEqualTo(UPDATED_ACTIF);
    }

    @Test
    @Transactional
    public void updateNonExistingExercice() throws Exception {
        int databaseSizeBeforeUpdate = exerciceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExerciceMockMvc.perform(put("/api/exercices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exercice)))
            .andExpect(status().isBadRequest());

        // Validate the Exercice in the database
        List<Exercice> exerciceList = exerciceRepository.findAll();
        assertThat(exerciceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExercice() throws Exception {
        // Initialize the database
        exerciceService.save(exercice);

        int databaseSizeBeforeDelete = exerciceRepository.findAll().size();

        // Delete the exercice
        restExerciceMockMvc.perform(delete("/api/exercices/{id}", exercice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Exercice> exerciceList = exerciceRepository.findAll();
        assertThat(exerciceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
