package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.SigrhpApp;
import com.emard.sigrhp.domain.Remboursement;
import com.emard.sigrhp.domain.DetailPret;
import com.emard.sigrhp.repository.RemboursementRepository;
import com.emard.sigrhp.service.RemboursementService;

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
 * Integration tests for the {@link RemboursementResource} REST controller.
 */
@SpringBootTest(classes = SigrhpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RemboursementResourceIT {

    private static final LocalDate DEFAULT_DATE_REMBOURSEMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_REMBOURSEMENT = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final Boolean DEFAULT_IS_REMBOURSE = false;
    private static final Boolean UPDATED_IS_REMBOURSE = true;

    @Autowired
    private RemboursementRepository remboursementRepository;

    @Autowired
    private RemboursementService remboursementService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRemboursementMockMvc;

    private Remboursement remboursement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Remboursement createEntity(EntityManager em) {
        Remboursement remboursement = new Remboursement()
            .dateRemboursement(DEFAULT_DATE_REMBOURSEMENT)
            .montant(DEFAULT_MONTANT)
            .isRembourse(DEFAULT_IS_REMBOURSE);
        // Add required entity
        DetailPret detailPret;
        if (TestUtil.findAll(em, DetailPret.class).isEmpty()) {
            detailPret = DetailPretResourceIT.createEntity(em);
            em.persist(detailPret);
            em.flush();
        } else {
            detailPret = TestUtil.findAll(em, DetailPret.class).get(0);
        }
        remboursement.setDetailPret(detailPret);
        return remboursement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Remboursement createUpdatedEntity(EntityManager em) {
        Remboursement remboursement = new Remboursement()
            .dateRemboursement(UPDATED_DATE_REMBOURSEMENT)
            .montant(UPDATED_MONTANT)
            .isRembourse(UPDATED_IS_REMBOURSE);
        // Add required entity
        DetailPret detailPret;
        if (TestUtil.findAll(em, DetailPret.class).isEmpty()) {
            detailPret = DetailPretResourceIT.createUpdatedEntity(em);
            em.persist(detailPret);
            em.flush();
        } else {
            detailPret = TestUtil.findAll(em, DetailPret.class).get(0);
        }
        remboursement.setDetailPret(detailPret);
        return remboursement;
    }

    @BeforeEach
    public void initTest() {
        remboursement = createEntity(em);
    }

    @Test
    @Transactional
    public void createRemboursement() throws Exception {
        int databaseSizeBeforeCreate = remboursementRepository.findAll().size();
        // Create the Remboursement
        restRemboursementMockMvc.perform(post("/api/remboursements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(remboursement)))
            .andExpect(status().isCreated());

        // Validate the Remboursement in the database
        List<Remboursement> remboursementList = remboursementRepository.findAll();
        assertThat(remboursementList).hasSize(databaseSizeBeforeCreate + 1);
        Remboursement testRemboursement = remboursementList.get(remboursementList.size() - 1);
        assertThat(testRemboursement.getDateRemboursement()).isEqualTo(DEFAULT_DATE_REMBOURSEMENT);
        assertThat(testRemboursement.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testRemboursement.isIsRembourse()).isEqualTo(DEFAULT_IS_REMBOURSE);
    }

    @Test
    @Transactional
    public void createRemboursementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = remboursementRepository.findAll().size();

        // Create the Remboursement with an existing ID
        remboursement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRemboursementMockMvc.perform(post("/api/remboursements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(remboursement)))
            .andExpect(status().isBadRequest());

        // Validate the Remboursement in the database
        List<Remboursement> remboursementList = remboursementRepository.findAll();
        assertThat(remboursementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateRemboursementIsRequired() throws Exception {
        int databaseSizeBeforeTest = remboursementRepository.findAll().size();
        // set the field null
        remboursement.setDateRemboursement(null);

        // Create the Remboursement, which fails.


        restRemboursementMockMvc.perform(post("/api/remboursements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(remboursement)))
            .andExpect(status().isBadRequest());

        List<Remboursement> remboursementList = remboursementRepository.findAll();
        assertThat(remboursementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = remboursementRepository.findAll().size();
        // set the field null
        remboursement.setMontant(null);

        // Create the Remboursement, which fails.


        restRemboursementMockMvc.perform(post("/api/remboursements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(remboursement)))
            .andExpect(status().isBadRequest());

        List<Remboursement> remboursementList = remboursementRepository.findAll();
        assertThat(remboursementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRemboursements() throws Exception {
        // Initialize the database
        remboursementRepository.saveAndFlush(remboursement);

        // Get all the remboursementList
        restRemboursementMockMvc.perform(get("/api/remboursements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(remboursement.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateRemboursement").value(hasItem(DEFAULT_DATE_REMBOURSEMENT.toString())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].isRembourse").value(hasItem(DEFAULT_IS_REMBOURSE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getRemboursement() throws Exception {
        // Initialize the database
        remboursementRepository.saveAndFlush(remboursement);

        // Get the remboursement
        restRemboursementMockMvc.perform(get("/api/remboursements/{id}", remboursement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(remboursement.getId().intValue()))
            .andExpect(jsonPath("$.dateRemboursement").value(DEFAULT_DATE_REMBOURSEMENT.toString()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.isRembourse").value(DEFAULT_IS_REMBOURSE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingRemboursement() throws Exception {
        // Get the remboursement
        restRemboursementMockMvc.perform(get("/api/remboursements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRemboursement() throws Exception {
        // Initialize the database
        remboursementService.save(remboursement);

        int databaseSizeBeforeUpdate = remboursementRepository.findAll().size();

        // Update the remboursement
        Remboursement updatedRemboursement = remboursementRepository.findById(remboursement.getId()).get();
        // Disconnect from session so that the updates on updatedRemboursement are not directly saved in db
        em.detach(updatedRemboursement);
        updatedRemboursement
            .dateRemboursement(UPDATED_DATE_REMBOURSEMENT)
            .montant(UPDATED_MONTANT)
            .isRembourse(UPDATED_IS_REMBOURSE);

        restRemboursementMockMvc.perform(put("/api/remboursements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRemboursement)))
            .andExpect(status().isOk());

        // Validate the Remboursement in the database
        List<Remboursement> remboursementList = remboursementRepository.findAll();
        assertThat(remboursementList).hasSize(databaseSizeBeforeUpdate);
        Remboursement testRemboursement = remboursementList.get(remboursementList.size() - 1);
        assertThat(testRemboursement.getDateRemboursement()).isEqualTo(UPDATED_DATE_REMBOURSEMENT);
        assertThat(testRemboursement.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testRemboursement.isIsRembourse()).isEqualTo(UPDATED_IS_REMBOURSE);
    }

    @Test
    @Transactional
    public void updateNonExistingRemboursement() throws Exception {
        int databaseSizeBeforeUpdate = remboursementRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemboursementMockMvc.perform(put("/api/remboursements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(remboursement)))
            .andExpect(status().isBadRequest());

        // Validate the Remboursement in the database
        List<Remboursement> remboursementList = remboursementRepository.findAll();
        assertThat(remboursementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRemboursement() throws Exception {
        // Initialize the database
        remboursementService.save(remboursement);

        int databaseSizeBeforeDelete = remboursementRepository.findAll().size();

        // Delete the remboursement
        restRemboursementMockMvc.perform(delete("/api/remboursements/{id}", remboursement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Remboursement> remboursementList = remboursementRepository.findAll();
        assertThat(remboursementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
