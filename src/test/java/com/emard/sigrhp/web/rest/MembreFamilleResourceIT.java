package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.SigrhpApp;
import com.emard.sigrhp.domain.MembreFamille;
import com.emard.sigrhp.domain.Collaborateur;
import com.emard.sigrhp.domain.Sexe;
import com.emard.sigrhp.domain.TypeRelation;
import com.emard.sigrhp.repository.MembreFamilleRepository;
import com.emard.sigrhp.service.MembreFamilleService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MembreFamilleResource} REST controller.
 */
@SpringBootTest(classes = SigrhpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MembreFamilleResourceIT {

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MARIAGE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MARIAGE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_IS_ACTIF = false;
    private static final Boolean UPDATED_IS_ACTIF = true;

    @Autowired
    private MembreFamilleRepository membreFamilleRepository;

    @Autowired
    private MembreFamilleService membreFamilleService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMembreFamilleMockMvc;

    private MembreFamille membreFamille;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MembreFamille createEntity(EntityManager em) {
        MembreFamille membreFamille = new MembreFamille()
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .adresse(DEFAULT_ADRESSE)
            .telephone(DEFAULT_TELEPHONE)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .dateMariage(DEFAULT_DATE_MARIAGE)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .isActif(DEFAULT_IS_ACTIF);
        // Add required entity
        Collaborateur collaborateur;
        if (TestUtil.findAll(em, Collaborateur.class).isEmpty()) {
            collaborateur = CollaborateurResourceIT.createEntity(em);
            em.persist(collaborateur);
            em.flush();
        } else {
            collaborateur = TestUtil.findAll(em, Collaborateur.class).get(0);
        }
        membreFamille.setCollaborateur(collaborateur);
        // Add required entity
        Sexe sexe;
        if (TestUtil.findAll(em, Sexe.class).isEmpty()) {
            sexe = SexeResourceIT.createEntity(em);
            em.persist(sexe);
            em.flush();
        } else {
            sexe = TestUtil.findAll(em, Sexe.class).get(0);
        }
        membreFamille.setSexe(sexe);
        // Add required entity
        TypeRelation typeRelation;
        if (TestUtil.findAll(em, TypeRelation.class).isEmpty()) {
            typeRelation = TypeRelationResourceIT.createEntity(em);
            em.persist(typeRelation);
            em.flush();
        } else {
            typeRelation = TestUtil.findAll(em, TypeRelation.class).get(0);
        }
        membreFamille.setTypeRelation(typeRelation);
        return membreFamille;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MembreFamille createUpdatedEntity(EntityManager em) {
        MembreFamille membreFamille = new MembreFamille()
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .dateMariage(UPDATED_DATE_MARIAGE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .isActif(UPDATED_IS_ACTIF);
        // Add required entity
        Collaborateur collaborateur;
        if (TestUtil.findAll(em, Collaborateur.class).isEmpty()) {
            collaborateur = CollaborateurResourceIT.createUpdatedEntity(em);
            em.persist(collaborateur);
            em.flush();
        } else {
            collaborateur = TestUtil.findAll(em, Collaborateur.class).get(0);
        }
        membreFamille.setCollaborateur(collaborateur);
        // Add required entity
        Sexe sexe;
        if (TestUtil.findAll(em, Sexe.class).isEmpty()) {
            sexe = SexeResourceIT.createUpdatedEntity(em);
            em.persist(sexe);
            em.flush();
        } else {
            sexe = TestUtil.findAll(em, Sexe.class).get(0);
        }
        membreFamille.setSexe(sexe);
        // Add required entity
        TypeRelation typeRelation;
        if (TestUtil.findAll(em, TypeRelation.class).isEmpty()) {
            typeRelation = TypeRelationResourceIT.createUpdatedEntity(em);
            em.persist(typeRelation);
            em.flush();
        } else {
            typeRelation = TestUtil.findAll(em, TypeRelation.class).get(0);
        }
        membreFamille.setTypeRelation(typeRelation);
        return membreFamille;
    }

    @BeforeEach
    public void initTest() {
        membreFamille = createEntity(em);
    }

    @Test
    @Transactional
    public void createMembreFamille() throws Exception {
        int databaseSizeBeforeCreate = membreFamilleRepository.findAll().size();
        // Create the MembreFamille
        restMembreFamilleMockMvc.perform(post("/api/membre-familles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membreFamille)))
            .andExpect(status().isCreated());

        // Validate the MembreFamille in the database
        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeCreate + 1);
        MembreFamille testMembreFamille = membreFamilleList.get(membreFamilleList.size() - 1);
        assertThat(testMembreFamille.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testMembreFamille.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testMembreFamille.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testMembreFamille.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testMembreFamille.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testMembreFamille.getDateMariage()).isEqualTo(DEFAULT_DATE_MARIAGE);
        assertThat(testMembreFamille.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testMembreFamille.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testMembreFamille.isIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
    }

    @Test
    @Transactional
    public void createMembreFamilleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = membreFamilleRepository.findAll().size();

        // Create the MembreFamille with an existing ID
        membreFamille.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMembreFamilleMockMvc.perform(post("/api/membre-familles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membreFamille)))
            .andExpect(status().isBadRequest());

        // Validate the MembreFamille in the database
        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = membreFamilleRepository.findAll().size();
        // set the field null
        membreFamille.setPrenom(null);

        // Create the MembreFamille, which fails.


        restMembreFamilleMockMvc.perform(post("/api/membre-familles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membreFamille)))
            .andExpect(status().isBadRequest());

        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = membreFamilleRepository.findAll().size();
        // set the field null
        membreFamille.setNom(null);

        // Create the MembreFamille, which fails.


        restMembreFamilleMockMvc.perform(post("/api/membre-familles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membreFamille)))
            .andExpect(status().isBadRequest());

        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdresseIsRequired() throws Exception {
        int databaseSizeBeforeTest = membreFamilleRepository.findAll().size();
        // set the field null
        membreFamille.setAdresse(null);

        // Create the MembreFamille, which fails.


        restMembreFamilleMockMvc.perform(post("/api/membre-familles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membreFamille)))
            .andExpect(status().isBadRequest());

        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = membreFamilleRepository.findAll().size();
        // set the field null
        membreFamille.setTelephone(null);

        // Create the MembreFamille, which fails.


        restMembreFamilleMockMvc.perform(post("/api/membre-familles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membreFamille)))
            .andExpect(status().isBadRequest());

        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = membreFamilleRepository.findAll().size();
        // set the field null
        membreFamille.setDateNaissance(null);

        // Create the MembreFamille, which fails.


        restMembreFamilleMockMvc.perform(post("/api/membre-familles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membreFamille)))
            .andExpect(status().isBadRequest());

        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMembreFamilles() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList
        restMembreFamilleMockMvc.perform(get("/api/membre-familles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membreFamille.getId().intValue())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].dateMariage").value(hasItem(DEFAULT_DATE_MARIAGE.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].isActif").value(hasItem(DEFAULT_IS_ACTIF.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMembreFamille() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get the membreFamille
        restMembreFamilleMockMvc.perform(get("/api/membre-familles/{id}", membreFamille.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(membreFamille.getId().intValue()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.dateMariage").value(DEFAULT_DATE_MARIAGE.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.isActif").value(DEFAULT_IS_ACTIF.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingMembreFamille() throws Exception {
        // Get the membreFamille
        restMembreFamilleMockMvc.perform(get("/api/membre-familles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMembreFamille() throws Exception {
        // Initialize the database
        membreFamilleService.save(membreFamille);

        int databaseSizeBeforeUpdate = membreFamilleRepository.findAll().size();

        // Update the membreFamille
        MembreFamille updatedMembreFamille = membreFamilleRepository.findById(membreFamille.getId()).get();
        // Disconnect from session so that the updates on updatedMembreFamille are not directly saved in db
        em.detach(updatedMembreFamille);
        updatedMembreFamille
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .dateMariage(UPDATED_DATE_MARIAGE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .isActif(UPDATED_IS_ACTIF);

        restMembreFamilleMockMvc.perform(put("/api/membre-familles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMembreFamille)))
            .andExpect(status().isOk());

        // Validate the MembreFamille in the database
        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeUpdate);
        MembreFamille testMembreFamille = membreFamilleList.get(membreFamilleList.size() - 1);
        assertThat(testMembreFamille.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testMembreFamille.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMembreFamille.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testMembreFamille.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testMembreFamille.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testMembreFamille.getDateMariage()).isEqualTo(UPDATED_DATE_MARIAGE);
        assertThat(testMembreFamille.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testMembreFamille.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testMembreFamille.isIsActif()).isEqualTo(UPDATED_IS_ACTIF);
    }

    @Test
    @Transactional
    public void updateNonExistingMembreFamille() throws Exception {
        int databaseSizeBeforeUpdate = membreFamilleRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMembreFamilleMockMvc.perform(put("/api/membre-familles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membreFamille)))
            .andExpect(status().isBadRequest());

        // Validate the MembreFamille in the database
        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMembreFamille() throws Exception {
        // Initialize the database
        membreFamilleService.save(membreFamille);

        int databaseSizeBeforeDelete = membreFamilleRepository.findAll().size();

        // Delete the membreFamille
        restMembreFamilleMockMvc.perform(delete("/api/membre-familles/{id}", membreFamille.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
