package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.SigrhpApp;
import com.emard.sigrhp.domain.DetailPret;
import com.emard.sigrhp.domain.Collaborateur;
import com.emard.sigrhp.domain.Pret;
import com.emard.sigrhp.repository.DetailPretRepository;
import com.emard.sigrhp.service.DetailPretService;

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
 * Integration tests for the {@link DetailPretResource} REST controller.
 */
@SpringBootTest(classes = SigrhpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DetailPretResourceIT {

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final Boolean DEFAULT_IS_REMBOURSE = false;
    private static final Boolean UPDATED_IS_REMBOURSE = true;

    @Autowired
    private DetailPretRepository detailPretRepository;

    @Autowired
    private DetailPretService detailPretService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDetailPretMockMvc;

    private DetailPret detailPret;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailPret createEntity(EntityManager em) {
        DetailPret detailPret = new DetailPret()
            .montant(DEFAULT_MONTANT)
            .isRembourse(DEFAULT_IS_REMBOURSE);
        // Add required entity
        Collaborateur collaborateur;
        if (TestUtil.findAll(em, Collaborateur.class).isEmpty()) {
            collaborateur = CollaborateurResourceIT.createEntity(em);
            em.persist(collaborateur);
            em.flush();
        } else {
            collaborateur = TestUtil.findAll(em, Collaborateur.class).get(0);
        }
        detailPret.setCollaborateur(collaborateur);
        // Add required entity
        Pret pret;
        if (TestUtil.findAll(em, Pret.class).isEmpty()) {
            pret = PretResourceIT.createEntity(em);
            em.persist(pret);
            em.flush();
        } else {
            pret = TestUtil.findAll(em, Pret.class).get(0);
        }
        detailPret.setPret(pret);
        return detailPret;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailPret createUpdatedEntity(EntityManager em) {
        DetailPret detailPret = new DetailPret()
            .montant(UPDATED_MONTANT)
            .isRembourse(UPDATED_IS_REMBOURSE);
        // Add required entity
        Collaborateur collaborateur;
        if (TestUtil.findAll(em, Collaborateur.class).isEmpty()) {
            collaborateur = CollaborateurResourceIT.createUpdatedEntity(em);
            em.persist(collaborateur);
            em.flush();
        } else {
            collaborateur = TestUtil.findAll(em, Collaborateur.class).get(0);
        }
        detailPret.setCollaborateur(collaborateur);
        // Add required entity
        Pret pret;
        if (TestUtil.findAll(em, Pret.class).isEmpty()) {
            pret = PretResourceIT.createUpdatedEntity(em);
            em.persist(pret);
            em.flush();
        } else {
            pret = TestUtil.findAll(em, Pret.class).get(0);
        }
        detailPret.setPret(pret);
        return detailPret;
    }

    @BeforeEach
    public void initTest() {
        detailPret = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetailPret() throws Exception {
        int databaseSizeBeforeCreate = detailPretRepository.findAll().size();
        // Create the DetailPret
        restDetailPretMockMvc.perform(post("/api/detail-prets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(detailPret)))
            .andExpect(status().isCreated());

        // Validate the DetailPret in the database
        List<DetailPret> detailPretList = detailPretRepository.findAll();
        assertThat(detailPretList).hasSize(databaseSizeBeforeCreate + 1);
        DetailPret testDetailPret = detailPretList.get(detailPretList.size() - 1);
        assertThat(testDetailPret.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testDetailPret.isIsRembourse()).isEqualTo(DEFAULT_IS_REMBOURSE);
    }

    @Test
    @Transactional
    public void createDetailPretWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detailPretRepository.findAll().size();

        // Create the DetailPret with an existing ID
        detailPret.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailPretMockMvc.perform(post("/api/detail-prets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(detailPret)))
            .andExpect(status().isBadRequest());

        // Validate the DetailPret in the database
        List<DetailPret> detailPretList = detailPretRepository.findAll();
        assertThat(detailPretList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailPretRepository.findAll().size();
        // set the field null
        detailPret.setMontant(null);

        // Create the DetailPret, which fails.


        restDetailPretMockMvc.perform(post("/api/detail-prets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(detailPret)))
            .andExpect(status().isBadRequest());

        List<DetailPret> detailPretList = detailPretRepository.findAll();
        assertThat(detailPretList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDetailPrets() throws Exception {
        // Initialize the database
        detailPretRepository.saveAndFlush(detailPret);

        // Get all the detailPretList
        restDetailPretMockMvc.perform(get("/api/detail-prets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailPret.getId().intValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].isRembourse").value(hasItem(DEFAULT_IS_REMBOURSE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDetailPret() throws Exception {
        // Initialize the database
        detailPretRepository.saveAndFlush(detailPret);

        // Get the detailPret
        restDetailPretMockMvc.perform(get("/api/detail-prets/{id}", detailPret.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(detailPret.getId().intValue()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.isRembourse").value(DEFAULT_IS_REMBOURSE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingDetailPret() throws Exception {
        // Get the detailPret
        restDetailPretMockMvc.perform(get("/api/detail-prets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetailPret() throws Exception {
        // Initialize the database
        detailPretService.save(detailPret);

        int databaseSizeBeforeUpdate = detailPretRepository.findAll().size();

        // Update the detailPret
        DetailPret updatedDetailPret = detailPretRepository.findById(detailPret.getId()).get();
        // Disconnect from session so that the updates on updatedDetailPret are not directly saved in db
        em.detach(updatedDetailPret);
        updatedDetailPret
            .montant(UPDATED_MONTANT)
            .isRembourse(UPDATED_IS_REMBOURSE);

        restDetailPretMockMvc.perform(put("/api/detail-prets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDetailPret)))
            .andExpect(status().isOk());

        // Validate the DetailPret in the database
        List<DetailPret> detailPretList = detailPretRepository.findAll();
        assertThat(detailPretList).hasSize(databaseSizeBeforeUpdate);
        DetailPret testDetailPret = detailPretList.get(detailPretList.size() - 1);
        assertThat(testDetailPret.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testDetailPret.isIsRembourse()).isEqualTo(UPDATED_IS_REMBOURSE);
    }

    @Test
    @Transactional
    public void updateNonExistingDetailPret() throws Exception {
        int databaseSizeBeforeUpdate = detailPretRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailPretMockMvc.perform(put("/api/detail-prets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(detailPret)))
            .andExpect(status().isBadRequest());

        // Validate the DetailPret in the database
        List<DetailPret> detailPretList = detailPretRepository.findAll();
        assertThat(detailPretList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDetailPret() throws Exception {
        // Initialize the database
        detailPretService.save(detailPret);

        int databaseSizeBeforeDelete = detailPretRepository.findAll().size();

        // Delete the detailPret
        restDetailPretMockMvc.perform(delete("/api/detail-prets/{id}", detailPret.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetailPret> detailPretList = detailPretRepository.findAll();
        assertThat(detailPretList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
