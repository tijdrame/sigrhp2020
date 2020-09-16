package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.SigrhpApp;
import com.emard.sigrhp.domain.Recap;
import com.emard.sigrhp.repository.RecapRepository;

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
 * Integration tests for the {@link RecapResource} REST controller.
 */
@SpringBootTest(classes = SigrhpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RecapResourceIT {

    private static final Double DEFAULT_BRUT_FISCAL = 1D;
    private static final Double UPDATED_BRUT_FISCAL = 2D;

    private static final Double DEFAULT_NET_A_PAYER = 1D;
    private static final Double UPDATED_NET_A_PAYER = 2D;

    private static final Double DEFAULT_SALAIRE_BRUT_MENSUEL = 1D;
    private static final Double UPDATED_SALAIRE_BRUT_MENSUEL = 2D;

    private static final Double DEFAULT_IMPOT_SUR_REVENU = 1D;
    private static final Double UPDATED_IMPOT_SUR_REVENU = 2D;

    private static final Double DEFAULT_TRIMF = 1D;
    private static final Double UPDATED_TRIMF = 2D;

    private static final Double DEFAULT_IPRES_PART_SALARIALE = 1D;
    private static final Double UPDATED_IPRES_PART_SALARIALE = 2D;

    private static final Double DEFAULT_TOT_RETENUE = 1D;
    private static final Double UPDATED_TOT_RETENUE = 2D;

    private static final Double DEFAULT_IPRES_PART_PATRONALES = 1D;
    private static final Double UPDATED_IPRES_PART_PATRONALES = 2D;

    private static final Double DEFAULT_CSS_ACCIDENT_DE_TRAVAIL = 1D;
    private static final Double UPDATED_CSS_ACCIDENT_DE_TRAVAIL = 2D;

    private static final Double DEFAULT_CSS_PRESTATION_FAMILIALE = 1D;
    private static final Double UPDATED_CSS_PRESTATION_FAMILIALE = 2D;

    private static final Double DEFAULT_IPM_PATRONALE = 1D;
    private static final Double UPDATED_IPM_PATRONALE = 2D;

    private static final Double DEFAULT_CONTRIBUTION_FORFAITAIRE = 1D;
    private static final Double UPDATED_CONTRIBUTION_FORFAITAIRE = 2D;

    private static final Double DEFAULT_PRIME_IMPOSABLE = 1D;
    private static final Double UPDATED_PRIME_IMPOSABLE = 2D;

    private static final Double DEFAULT_PRIME_NON_IMPOSABLE = 1D;
    private static final Double UPDATED_PRIME_NON_IMPOSABLE = 2D;

    private static final Double DEFAULT_AVANTAGE = 1D;
    private static final Double UPDATED_AVANTAGE = 2D;

    private static final Double DEFAULT_RECAP_LIGNE = 1D;
    private static final Double UPDATED_RECAP_LIGNE = 2D;

    @Autowired
    private RecapRepository recapRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecapMockMvc;

    private Recap recap;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recap createEntity(EntityManager em) {
        Recap recap = new Recap()
            .brutFiscal(DEFAULT_BRUT_FISCAL)
            .netAPayer(DEFAULT_NET_A_PAYER)
            .salaireBrutMensuel(DEFAULT_SALAIRE_BRUT_MENSUEL)
            .impotSurRevenu(DEFAULT_IMPOT_SUR_REVENU)
            .trimf(DEFAULT_TRIMF)
            .ipresPartSalariale(DEFAULT_IPRES_PART_SALARIALE)
            .totRetenue(DEFAULT_TOT_RETENUE)
            .ipresPartPatronales(DEFAULT_IPRES_PART_PATRONALES)
            .cssAccidentDeTravail(DEFAULT_CSS_ACCIDENT_DE_TRAVAIL)
            .cssPrestationFamiliale(DEFAULT_CSS_PRESTATION_FAMILIALE)
            .ipmPatronale(DEFAULT_IPM_PATRONALE)
            .contributionForfaitaire(DEFAULT_CONTRIBUTION_FORFAITAIRE)
            .primeImposable(DEFAULT_PRIME_IMPOSABLE)
            .primeNonImposable(DEFAULT_PRIME_NON_IMPOSABLE)
            .avantage(DEFAULT_AVANTAGE)
            .recapLigne(DEFAULT_RECAP_LIGNE);
        return recap;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recap createUpdatedEntity(EntityManager em) {
        Recap recap = new Recap()
            .brutFiscal(UPDATED_BRUT_FISCAL)
            .netAPayer(UPDATED_NET_A_PAYER)
            .salaireBrutMensuel(UPDATED_SALAIRE_BRUT_MENSUEL)
            .impotSurRevenu(UPDATED_IMPOT_SUR_REVENU)
            .trimf(UPDATED_TRIMF)
            .ipresPartSalariale(UPDATED_IPRES_PART_SALARIALE)
            .totRetenue(UPDATED_TOT_RETENUE)
            .ipresPartPatronales(UPDATED_IPRES_PART_PATRONALES)
            .cssAccidentDeTravail(UPDATED_CSS_ACCIDENT_DE_TRAVAIL)
            .cssPrestationFamiliale(UPDATED_CSS_PRESTATION_FAMILIALE)
            .ipmPatronale(UPDATED_IPM_PATRONALE)
            .contributionForfaitaire(UPDATED_CONTRIBUTION_FORFAITAIRE)
            .primeImposable(UPDATED_PRIME_IMPOSABLE)
            .primeNonImposable(UPDATED_PRIME_NON_IMPOSABLE)
            .avantage(UPDATED_AVANTAGE)
            .recapLigne(UPDATED_RECAP_LIGNE);
        return recap;
    }

    @BeforeEach
    public void initTest() {
        recap = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecap() throws Exception {
        int databaseSizeBeforeCreate = recapRepository.findAll().size();
        // Create the Recap
        restRecapMockMvc.perform(post("/api/recaps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recap)))
            .andExpect(status().isCreated());

        // Validate the Recap in the database
        List<Recap> recapList = recapRepository.findAll();
        assertThat(recapList).hasSize(databaseSizeBeforeCreate + 1);
        Recap testRecap = recapList.get(recapList.size() - 1);
        assertThat(testRecap.getBrutFiscal()).isEqualTo(DEFAULT_BRUT_FISCAL);
        assertThat(testRecap.getNetAPayer()).isEqualTo(DEFAULT_NET_A_PAYER);
        assertThat(testRecap.getSalaireBrutMensuel()).isEqualTo(DEFAULT_SALAIRE_BRUT_MENSUEL);
        assertThat(testRecap.getImpotSurRevenu()).isEqualTo(DEFAULT_IMPOT_SUR_REVENU);
        assertThat(testRecap.getTrimf()).isEqualTo(DEFAULT_TRIMF);
        assertThat(testRecap.getIpresPartSalariale()).isEqualTo(DEFAULT_IPRES_PART_SALARIALE);
        assertThat(testRecap.getTotRetenue()).isEqualTo(DEFAULT_TOT_RETENUE);
        assertThat(testRecap.getIpresPartPatronales()).isEqualTo(DEFAULT_IPRES_PART_PATRONALES);
        assertThat(testRecap.getCssAccidentDeTravail()).isEqualTo(DEFAULT_CSS_ACCIDENT_DE_TRAVAIL);
        assertThat(testRecap.getCssPrestationFamiliale()).isEqualTo(DEFAULT_CSS_PRESTATION_FAMILIALE);
        assertThat(testRecap.getIpmPatronale()).isEqualTo(DEFAULT_IPM_PATRONALE);
        assertThat(testRecap.getContributionForfaitaire()).isEqualTo(DEFAULT_CONTRIBUTION_FORFAITAIRE);
        assertThat(testRecap.getPrimeImposable()).isEqualTo(DEFAULT_PRIME_IMPOSABLE);
        assertThat(testRecap.getPrimeNonImposable()).isEqualTo(DEFAULT_PRIME_NON_IMPOSABLE);
        assertThat(testRecap.getAvantage()).isEqualTo(DEFAULT_AVANTAGE);
        assertThat(testRecap.getRecapLigne()).isEqualTo(DEFAULT_RECAP_LIGNE);
    }

    @Test
    @Transactional
    public void createRecapWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recapRepository.findAll().size();

        // Create the Recap with an existing ID
        recap.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecapMockMvc.perform(post("/api/recaps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recap)))
            .andExpect(status().isBadRequest());

        // Validate the Recap in the database
        List<Recap> recapList = recapRepository.findAll();
        assertThat(recapList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRecaps() throws Exception {
        // Initialize the database
        recapRepository.saveAndFlush(recap);

        // Get all the recapList
        restRecapMockMvc.perform(get("/api/recaps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recap.getId().intValue())))
            .andExpect(jsonPath("$.[*].brutFiscal").value(hasItem(DEFAULT_BRUT_FISCAL.doubleValue())))
            .andExpect(jsonPath("$.[*].netAPayer").value(hasItem(DEFAULT_NET_A_PAYER.doubleValue())))
            .andExpect(jsonPath("$.[*].salaireBrutMensuel").value(hasItem(DEFAULT_SALAIRE_BRUT_MENSUEL.doubleValue())))
            .andExpect(jsonPath("$.[*].impotSurRevenu").value(hasItem(DEFAULT_IMPOT_SUR_REVENU.doubleValue())))
            .andExpect(jsonPath("$.[*].trimf").value(hasItem(DEFAULT_TRIMF.doubleValue())))
            .andExpect(jsonPath("$.[*].ipresPartSalariale").value(hasItem(DEFAULT_IPRES_PART_SALARIALE.doubleValue())))
            .andExpect(jsonPath("$.[*].totRetenue").value(hasItem(DEFAULT_TOT_RETENUE.doubleValue())))
            .andExpect(jsonPath("$.[*].ipresPartPatronales").value(hasItem(DEFAULT_IPRES_PART_PATRONALES.doubleValue())))
            .andExpect(jsonPath("$.[*].cssAccidentDeTravail").value(hasItem(DEFAULT_CSS_ACCIDENT_DE_TRAVAIL.doubleValue())))
            .andExpect(jsonPath("$.[*].cssPrestationFamiliale").value(hasItem(DEFAULT_CSS_PRESTATION_FAMILIALE.doubleValue())))
            .andExpect(jsonPath("$.[*].ipmPatronale").value(hasItem(DEFAULT_IPM_PATRONALE.doubleValue())))
            .andExpect(jsonPath("$.[*].contributionForfaitaire").value(hasItem(DEFAULT_CONTRIBUTION_FORFAITAIRE.doubleValue())))
            .andExpect(jsonPath("$.[*].primeImposable").value(hasItem(DEFAULT_PRIME_IMPOSABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].primeNonImposable").value(hasItem(DEFAULT_PRIME_NON_IMPOSABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].avantage").value(hasItem(DEFAULT_AVANTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].recapLigne").value(hasItem(DEFAULT_RECAP_LIGNE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getRecap() throws Exception {
        // Initialize the database
        recapRepository.saveAndFlush(recap);

        // Get the recap
        restRecapMockMvc.perform(get("/api/recaps/{id}", recap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recap.getId().intValue()))
            .andExpect(jsonPath("$.brutFiscal").value(DEFAULT_BRUT_FISCAL.doubleValue()))
            .andExpect(jsonPath("$.netAPayer").value(DEFAULT_NET_A_PAYER.doubleValue()))
            .andExpect(jsonPath("$.salaireBrutMensuel").value(DEFAULT_SALAIRE_BRUT_MENSUEL.doubleValue()))
            .andExpect(jsonPath("$.impotSurRevenu").value(DEFAULT_IMPOT_SUR_REVENU.doubleValue()))
            .andExpect(jsonPath("$.trimf").value(DEFAULT_TRIMF.doubleValue()))
            .andExpect(jsonPath("$.ipresPartSalariale").value(DEFAULT_IPRES_PART_SALARIALE.doubleValue()))
            .andExpect(jsonPath("$.totRetenue").value(DEFAULT_TOT_RETENUE.doubleValue()))
            .andExpect(jsonPath("$.ipresPartPatronales").value(DEFAULT_IPRES_PART_PATRONALES.doubleValue()))
            .andExpect(jsonPath("$.cssAccidentDeTravail").value(DEFAULT_CSS_ACCIDENT_DE_TRAVAIL.doubleValue()))
            .andExpect(jsonPath("$.cssPrestationFamiliale").value(DEFAULT_CSS_PRESTATION_FAMILIALE.doubleValue()))
            .andExpect(jsonPath("$.ipmPatronale").value(DEFAULT_IPM_PATRONALE.doubleValue()))
            .andExpect(jsonPath("$.contributionForfaitaire").value(DEFAULT_CONTRIBUTION_FORFAITAIRE.doubleValue()))
            .andExpect(jsonPath("$.primeImposable").value(DEFAULT_PRIME_IMPOSABLE.doubleValue()))
            .andExpect(jsonPath("$.primeNonImposable").value(DEFAULT_PRIME_NON_IMPOSABLE.doubleValue()))
            .andExpect(jsonPath("$.avantage").value(DEFAULT_AVANTAGE.doubleValue()))
            .andExpect(jsonPath("$.recapLigne").value(DEFAULT_RECAP_LIGNE.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingRecap() throws Exception {
        // Get the recap
        restRecapMockMvc.perform(get("/api/recaps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecap() throws Exception {
        // Initialize the database
        recapRepository.saveAndFlush(recap);

        int databaseSizeBeforeUpdate = recapRepository.findAll().size();

        // Update the recap
        Recap updatedRecap = recapRepository.findById(recap.getId()).get();
        // Disconnect from session so that the updates on updatedRecap are not directly saved in db
        em.detach(updatedRecap);
        updatedRecap
            .brutFiscal(UPDATED_BRUT_FISCAL)
            .netAPayer(UPDATED_NET_A_PAYER)
            .salaireBrutMensuel(UPDATED_SALAIRE_BRUT_MENSUEL)
            .impotSurRevenu(UPDATED_IMPOT_SUR_REVENU)
            .trimf(UPDATED_TRIMF)
            .ipresPartSalariale(UPDATED_IPRES_PART_SALARIALE)
            .totRetenue(UPDATED_TOT_RETENUE)
            .ipresPartPatronales(UPDATED_IPRES_PART_PATRONALES)
            .cssAccidentDeTravail(UPDATED_CSS_ACCIDENT_DE_TRAVAIL)
            .cssPrestationFamiliale(UPDATED_CSS_PRESTATION_FAMILIALE)
            .ipmPatronale(UPDATED_IPM_PATRONALE)
            .contributionForfaitaire(UPDATED_CONTRIBUTION_FORFAITAIRE)
            .primeImposable(UPDATED_PRIME_IMPOSABLE)
            .primeNonImposable(UPDATED_PRIME_NON_IMPOSABLE)
            .avantage(UPDATED_AVANTAGE)
            .recapLigne(UPDATED_RECAP_LIGNE);

        restRecapMockMvc.perform(put("/api/recaps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecap)))
            .andExpect(status().isOk());

        // Validate the Recap in the database
        List<Recap> recapList = recapRepository.findAll();
        assertThat(recapList).hasSize(databaseSizeBeforeUpdate);
        Recap testRecap = recapList.get(recapList.size() - 1);
        assertThat(testRecap.getBrutFiscal()).isEqualTo(UPDATED_BRUT_FISCAL);
        assertThat(testRecap.getNetAPayer()).isEqualTo(UPDATED_NET_A_PAYER);
        assertThat(testRecap.getSalaireBrutMensuel()).isEqualTo(UPDATED_SALAIRE_BRUT_MENSUEL);
        assertThat(testRecap.getImpotSurRevenu()).isEqualTo(UPDATED_IMPOT_SUR_REVENU);
        assertThat(testRecap.getTrimf()).isEqualTo(UPDATED_TRIMF);
        assertThat(testRecap.getIpresPartSalariale()).isEqualTo(UPDATED_IPRES_PART_SALARIALE);
        assertThat(testRecap.getTotRetenue()).isEqualTo(UPDATED_TOT_RETENUE);
        assertThat(testRecap.getIpresPartPatronales()).isEqualTo(UPDATED_IPRES_PART_PATRONALES);
        assertThat(testRecap.getCssAccidentDeTravail()).isEqualTo(UPDATED_CSS_ACCIDENT_DE_TRAVAIL);
        assertThat(testRecap.getCssPrestationFamiliale()).isEqualTo(UPDATED_CSS_PRESTATION_FAMILIALE);
        assertThat(testRecap.getIpmPatronale()).isEqualTo(UPDATED_IPM_PATRONALE);
        assertThat(testRecap.getContributionForfaitaire()).isEqualTo(UPDATED_CONTRIBUTION_FORFAITAIRE);
        assertThat(testRecap.getPrimeImposable()).isEqualTo(UPDATED_PRIME_IMPOSABLE);
        assertThat(testRecap.getPrimeNonImposable()).isEqualTo(UPDATED_PRIME_NON_IMPOSABLE);
        assertThat(testRecap.getAvantage()).isEqualTo(UPDATED_AVANTAGE);
        assertThat(testRecap.getRecapLigne()).isEqualTo(UPDATED_RECAP_LIGNE);
    }

    @Test
    @Transactional
    public void updateNonExistingRecap() throws Exception {
        int databaseSizeBeforeUpdate = recapRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecapMockMvc.perform(put("/api/recaps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recap)))
            .andExpect(status().isBadRequest());

        // Validate the Recap in the database
        List<Recap> recapList = recapRepository.findAll();
        assertThat(recapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecap() throws Exception {
        // Initialize the database
        recapRepository.saveAndFlush(recap);

        int databaseSizeBeforeDelete = recapRepository.findAll().size();

        // Delete the recap
        restRecapMockMvc.perform(delete("/api/recaps/{id}", recap.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Recap> recapList = recapRepository.findAll();
        assertThat(recapList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
