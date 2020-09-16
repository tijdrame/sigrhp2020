package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.SigrhpApp;
import com.emard.sigrhp.domain.DemandeConge;
import com.emard.sigrhp.domain.TypeAbsence;
import com.emard.sigrhp.repository.DemandeCongeRepository;
import com.emard.sigrhp.service.DemandeCongeService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DemandeCongeResource} REST controller.
 */
@SpringBootTest(classes = SigrhpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DemandeCongeResourceIT {

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MOTIF_REJET = "AAAAAAAAAA";
    private static final String UPDATED_MOTIF_REJET = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private DemandeCongeRepository demandeCongeRepository;

    @Autowired
    private DemandeCongeService demandeCongeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandeCongeMockMvc;

    private DemandeConge demandeConge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeConge createEntity(EntityManager em) {
        DemandeConge demandeConge = new DemandeConge()
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .motifRejet(DEFAULT_MOTIF_REJET)
            .libelle(DEFAULT_LIBELLE);
        // Add required entity
        TypeAbsence typeAbsence;
        if (TestUtil.findAll(em, TypeAbsence.class).isEmpty()) {
            typeAbsence = TypeAbsenceResourceIT.createEntity(em);
            em.persist(typeAbsence);
            em.flush();
        } else {
            typeAbsence = TestUtil.findAll(em, TypeAbsence.class).get(0);
        }
        demandeConge.setTypeAbsence(typeAbsence);
        return demandeConge;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeConge createUpdatedEntity(EntityManager em) {
        DemandeConge demandeConge = new DemandeConge()
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .motifRejet(UPDATED_MOTIF_REJET)
            .libelle(UPDATED_LIBELLE);
        // Add required entity
        TypeAbsence typeAbsence;
        if (TestUtil.findAll(em, TypeAbsence.class).isEmpty()) {
            typeAbsence = TypeAbsenceResourceIT.createUpdatedEntity(em);
            em.persist(typeAbsence);
            em.flush();
        } else {
            typeAbsence = TestUtil.findAll(em, TypeAbsence.class).get(0);
        }
        demandeConge.setTypeAbsence(typeAbsence);
        return demandeConge;
    }

    @BeforeEach
    public void initTest() {
        demandeConge = createEntity(em);
    }

    @Test
    @Transactional
    public void createDemandeConge() throws Exception {
        int databaseSizeBeforeCreate = demandeCongeRepository.findAll().size();
        // Create the DemandeConge
        restDemandeCongeMockMvc.perform(post("/api/demande-conges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(demandeConge)))
            .andExpect(status().isCreated());

        // Validate the DemandeConge in the database
        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeCreate + 1);
        DemandeConge testDemandeConge = demandeCongeList.get(demandeCongeList.size() - 1);
        assertThat(testDemandeConge.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testDemandeConge.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testDemandeConge.getMotifRejet()).isEqualTo(DEFAULT_MOTIF_REJET);
        assertThat(testDemandeConge.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createDemandeCongeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = demandeCongeRepository.findAll().size();

        // Create the DemandeConge with an existing ID
        demandeConge.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandeCongeMockMvc.perform(post("/api/demande-conges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(demandeConge)))
            .andExpect(status().isBadRequest());

        // Validate the DemandeConge in the database
        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeCongeRepository.findAll().size();
        // set the field null
        demandeConge.setDateDebut(null);

        // Create the DemandeConge, which fails.


        restDemandeCongeMockMvc.perform(post("/api/demande-conges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(demandeConge)))
            .andExpect(status().isBadRequest());

        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeCongeRepository.findAll().size();
        // set the field null
        demandeConge.setDateFin(null);

        // Create the DemandeConge, which fails.


        restDemandeCongeMockMvc.perform(post("/api/demande-conges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(demandeConge)))
            .andExpect(status().isBadRequest());

        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeCongeRepository.findAll().size();
        // set the field null
        demandeConge.setLibelle(null);

        // Create the DemandeConge, which fails.


        restDemandeCongeMockMvc.perform(post("/api/demande-conges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(demandeConge)))
            .andExpect(status().isBadRequest());

        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDemandeConges() throws Exception {
        // Initialize the database
        demandeCongeRepository.saveAndFlush(demandeConge);

        // Get all the demandeCongeList
        restDemandeCongeMockMvc.perform(get("/api/demande-conges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandeConge.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].motifRejet").value(hasItem(DEFAULT_MOTIF_REJET)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getDemandeConge() throws Exception {
        // Initialize the database
        demandeCongeRepository.saveAndFlush(demandeConge);

        // Get the demandeConge
        restDemandeCongeMockMvc.perform(get("/api/demande-conges/{id}", demandeConge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demandeConge.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.motifRejet").value(DEFAULT_MOTIF_REJET))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }
    @Test
    @Transactional
    public void getNonExistingDemandeConge() throws Exception {
        // Get the demandeConge
        restDemandeCongeMockMvc.perform(get("/api/demande-conges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDemandeConge() throws Exception {
        // Initialize the database
        demandeCongeService.save(demandeConge);

        int databaseSizeBeforeUpdate = demandeCongeRepository.findAll().size();

        // Update the demandeConge
        DemandeConge updatedDemandeConge = demandeCongeRepository.findById(demandeConge.getId()).get();
        // Disconnect from session so that the updates on updatedDemandeConge are not directly saved in db
        em.detach(updatedDemandeConge);
        updatedDemandeConge
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .motifRejet(UPDATED_MOTIF_REJET)
            .libelle(UPDATED_LIBELLE);

        restDemandeCongeMockMvc.perform(put("/api/demande-conges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDemandeConge)))
            .andExpect(status().isOk());

        // Validate the DemandeConge in the database
        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeUpdate);
        DemandeConge testDemandeConge = demandeCongeList.get(demandeCongeList.size() - 1);
        assertThat(testDemandeConge.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testDemandeConge.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testDemandeConge.getMotifRejet()).isEqualTo(UPDATED_MOTIF_REJET);
        assertThat(testDemandeConge.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingDemandeConge() throws Exception {
        int databaseSizeBeforeUpdate = demandeCongeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeCongeMockMvc.perform(put("/api/demande-conges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(demandeConge)))
            .andExpect(status().isBadRequest());

        // Validate the DemandeConge in the database
        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDemandeConge() throws Exception {
        // Initialize the database
        demandeCongeService.save(demandeConge);

        int databaseSizeBeforeDelete = demandeCongeRepository.findAll().size();

        // Delete the demandeConge
        restDemandeCongeMockMvc.perform(delete("/api/demande-conges/{id}", demandeConge.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
