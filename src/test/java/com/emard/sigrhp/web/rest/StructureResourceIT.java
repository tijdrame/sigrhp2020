package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.SigrhpApp;
import com.emard.sigrhp.domain.Structure;
import com.emard.sigrhp.repository.StructureRepository;
import com.emard.sigrhp.service.StructureService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link StructureResource} REST controller.
 */
@SpringBootTest(classes = SigrhpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StructureResourceIT {

    private static final String DEFAULT_DENOMINATION = "AAAAAAAAAA";
    private static final String UPDATED_DENOMINATION = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_NINEA = "AAAAAAAAAA";
    private static final String UPDATED_NINEA = "BBBBBBBBBB";

    private static final Double DEFAULT_CAPITAL = 1D;
    private static final Double UPDATED_CAPITAL = 2D;

    private static final String DEFAULT_NUMERO_IPRES = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_IPRES = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_CSS = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CSS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_IPM = false;
    private static final Boolean UPDATED_IPM = true;

    private static final byte[] DEFAULT_SIGNATURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SIGNATURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SIGNATURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SIGNATURE_CONTENT_TYPE = "image/png";

    private static final Double DEFAULT_MONTANT_IPM = 1D;
    private static final Double UPDATED_MONTANT_IPM = 2D;

    @Autowired
    private StructureRepository structureRepository;

    @Autowired
    private StructureService structureService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStructureMockMvc;

    private Structure structure;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Structure createEntity(EntityManager em) {
        Structure structure = new Structure()
            .denomination(DEFAULT_DENOMINATION)
            .telephone(DEFAULT_TELEPHONE)
            .adresse(DEFAULT_ADRESSE)
            .ninea(DEFAULT_NINEA)
            .capital(DEFAULT_CAPITAL)
            .numeroIpres(DEFAULT_NUMERO_IPRES)
            .numeroCss(DEFAULT_NUMERO_CSS)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .ipm(DEFAULT_IPM)
            .signature(DEFAULT_SIGNATURE)
            .signatureContentType(DEFAULT_SIGNATURE_CONTENT_TYPE)
            .montantIpm(DEFAULT_MONTANT_IPM);
        return structure;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Structure createUpdatedEntity(EntityManager em) {
        Structure structure = new Structure()
            .denomination(UPDATED_DENOMINATION)
            .telephone(UPDATED_TELEPHONE)
            .adresse(UPDATED_ADRESSE)
            .ninea(UPDATED_NINEA)
            .capital(UPDATED_CAPITAL)
            .numeroIpres(UPDATED_NUMERO_IPRES)
            .numeroCss(UPDATED_NUMERO_CSS)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .ipm(UPDATED_IPM)
            .signature(UPDATED_SIGNATURE)
            .signatureContentType(UPDATED_SIGNATURE_CONTENT_TYPE)
            .montantIpm(UPDATED_MONTANT_IPM);
        return structure;
    }

    @BeforeEach
    public void initTest() {
        structure = createEntity(em);
    }

    @Test
    @Transactional
    public void createStructure() throws Exception {
        int databaseSizeBeforeCreate = structureRepository.findAll().size();
        // Create the Structure
        restStructureMockMvc.perform(post("/api/structures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(structure)))
            .andExpect(status().isCreated());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeCreate + 1);
        Structure testStructure = structureList.get(structureList.size() - 1);
        assertThat(testStructure.getDenomination()).isEqualTo(DEFAULT_DENOMINATION);
        assertThat(testStructure.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testStructure.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testStructure.getNinea()).isEqualTo(DEFAULT_NINEA);
        assertThat(testStructure.getCapital()).isEqualTo(DEFAULT_CAPITAL);
        assertThat(testStructure.getNumeroIpres()).isEqualTo(DEFAULT_NUMERO_IPRES);
        assertThat(testStructure.getNumeroCss()).isEqualTo(DEFAULT_NUMERO_CSS);
        assertThat(testStructure.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testStructure.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testStructure.isIpm()).isEqualTo(DEFAULT_IPM);
        assertThat(testStructure.getSignature()).isEqualTo(DEFAULT_SIGNATURE);
        assertThat(testStructure.getSignatureContentType()).isEqualTo(DEFAULT_SIGNATURE_CONTENT_TYPE);
        assertThat(testStructure.getMontantIpm()).isEqualTo(DEFAULT_MONTANT_IPM);
    }

    @Test
    @Transactional
    public void createStructureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = structureRepository.findAll().size();

        // Create the Structure with an existing ID
        structure.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStructureMockMvc.perform(post("/api/structures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(structure)))
            .andExpect(status().isBadRequest());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDenominationIsRequired() throws Exception {
        int databaseSizeBeforeTest = structureRepository.findAll().size();
        // set the field null
        structure.setDenomination(null);

        // Create the Structure, which fails.


        restStructureMockMvc.perform(post("/api/structures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(structure)))
            .andExpect(status().isBadRequest());

        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = structureRepository.findAll().size();
        // set the field null
        structure.setTelephone(null);

        // Create the Structure, which fails.


        restStructureMockMvc.perform(post("/api/structures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(structure)))
            .andExpect(status().isBadRequest());

        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdresseIsRequired() throws Exception {
        int databaseSizeBeforeTest = structureRepository.findAll().size();
        // set the field null
        structure.setAdresse(null);

        // Create the Structure, which fails.


        restStructureMockMvc.perform(post("/api/structures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(structure)))
            .andExpect(status().isBadRequest());

        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNineaIsRequired() throws Exception {
        int databaseSizeBeforeTest = structureRepository.findAll().size();
        // set the field null
        structure.setNinea(null);

        // Create the Structure, which fails.


        restStructureMockMvc.perform(post("/api/structures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(structure)))
            .andExpect(status().isBadRequest());

        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroIpresIsRequired() throws Exception {
        int databaseSizeBeforeTest = structureRepository.findAll().size();
        // set the field null
        structure.setNumeroIpres(null);

        // Create the Structure, which fails.


        restStructureMockMvc.perform(post("/api/structures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(structure)))
            .andExpect(status().isBadRequest());

        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroCssIsRequired() throws Exception {
        int databaseSizeBeforeTest = structureRepository.findAll().size();
        // set the field null
        structure.setNumeroCss(null);

        // Create the Structure, which fails.


        restStructureMockMvc.perform(post("/api/structures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(structure)))
            .andExpect(status().isBadRequest());

        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStructures() throws Exception {
        // Initialize the database
        structureRepository.saveAndFlush(structure);

        // Get all the structureList
        restStructureMockMvc.perform(get("/api/structures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(structure.getId().intValue())))
            .andExpect(jsonPath("$.[*].denomination").value(hasItem(DEFAULT_DENOMINATION)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].ninea").value(hasItem(DEFAULT_NINEA)))
            .andExpect(jsonPath("$.[*].capital").value(hasItem(DEFAULT_CAPITAL.doubleValue())))
            .andExpect(jsonPath("$.[*].numeroIpres").value(hasItem(DEFAULT_NUMERO_IPRES)))
            .andExpect(jsonPath("$.[*].numeroCss").value(hasItem(DEFAULT_NUMERO_CSS)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].ipm").value(hasItem(DEFAULT_IPM.booleanValue())))
            .andExpect(jsonPath("$.[*].signatureContentType").value(hasItem(DEFAULT_SIGNATURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].signature").value(hasItem(Base64Utils.encodeToString(DEFAULT_SIGNATURE))))
            .andExpect(jsonPath("$.[*].montantIpm").value(hasItem(DEFAULT_MONTANT_IPM.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getStructure() throws Exception {
        // Initialize the database
        structureRepository.saveAndFlush(structure);

        // Get the structure
        restStructureMockMvc.perform(get("/api/structures/{id}", structure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(structure.getId().intValue()))
            .andExpect(jsonPath("$.denomination").value(DEFAULT_DENOMINATION))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.ninea").value(DEFAULT_NINEA))
            .andExpect(jsonPath("$.capital").value(DEFAULT_CAPITAL.doubleValue()))
            .andExpect(jsonPath("$.numeroIpres").value(DEFAULT_NUMERO_IPRES))
            .andExpect(jsonPath("$.numeroCss").value(DEFAULT_NUMERO_CSS))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.ipm").value(DEFAULT_IPM.booleanValue()))
            .andExpect(jsonPath("$.signatureContentType").value(DEFAULT_SIGNATURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.signature").value(Base64Utils.encodeToString(DEFAULT_SIGNATURE)))
            .andExpect(jsonPath("$.montantIpm").value(DEFAULT_MONTANT_IPM.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingStructure() throws Exception {
        // Get the structure
        restStructureMockMvc.perform(get("/api/structures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStructure() throws Exception {
        // Initialize the database
        structureService.save(structure);

        int databaseSizeBeforeUpdate = structureRepository.findAll().size();

        // Update the structure
        Structure updatedStructure = structureRepository.findById(structure.getId()).get();
        // Disconnect from session so that the updates on updatedStructure are not directly saved in db
        em.detach(updatedStructure);
        updatedStructure
            .denomination(UPDATED_DENOMINATION)
            .telephone(UPDATED_TELEPHONE)
            .adresse(UPDATED_ADRESSE)
            .ninea(UPDATED_NINEA)
            .capital(UPDATED_CAPITAL)
            .numeroIpres(UPDATED_NUMERO_IPRES)
            .numeroCss(UPDATED_NUMERO_CSS)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .ipm(UPDATED_IPM)
            .signature(UPDATED_SIGNATURE)
            .signatureContentType(UPDATED_SIGNATURE_CONTENT_TYPE)
            .montantIpm(UPDATED_MONTANT_IPM);

        restStructureMockMvc.perform(put("/api/structures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStructure)))
            .andExpect(status().isOk());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeUpdate);
        Structure testStructure = structureList.get(structureList.size() - 1);
        assertThat(testStructure.getDenomination()).isEqualTo(UPDATED_DENOMINATION);
        assertThat(testStructure.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testStructure.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testStructure.getNinea()).isEqualTo(UPDATED_NINEA);
        assertThat(testStructure.getCapital()).isEqualTo(UPDATED_CAPITAL);
        assertThat(testStructure.getNumeroIpres()).isEqualTo(UPDATED_NUMERO_IPRES);
        assertThat(testStructure.getNumeroCss()).isEqualTo(UPDATED_NUMERO_CSS);
        assertThat(testStructure.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testStructure.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testStructure.isIpm()).isEqualTo(UPDATED_IPM);
        assertThat(testStructure.getSignature()).isEqualTo(UPDATED_SIGNATURE);
        assertThat(testStructure.getSignatureContentType()).isEqualTo(UPDATED_SIGNATURE_CONTENT_TYPE);
        assertThat(testStructure.getMontantIpm()).isEqualTo(UPDATED_MONTANT_IPM);
    }

    @Test
    @Transactional
    public void updateNonExistingStructure() throws Exception {
        int databaseSizeBeforeUpdate = structureRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStructureMockMvc.perform(put("/api/structures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(structure)))
            .andExpect(status().isBadRequest());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStructure() throws Exception {
        // Initialize the database
        structureService.save(structure);

        int databaseSizeBeforeDelete = structureRepository.findAll().size();

        // Delete the structure
        restStructureMockMvc.perform(delete("/api/structures/{id}", structure.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
