package com.emard.sigrhp.web.rest;

import com.emard.sigrhp.SigrhpApp;
import com.emard.sigrhp.domain.Bulletin;
import com.emard.sigrhp.domain.TypePaiement;
import com.emard.sigrhp.domain.Exercice;
import com.emard.sigrhp.repository.BulletinRepository;
import com.emard.sigrhp.service.BulletinService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BulletinResource} REST controller.
 */
@SpringBootTest(classes = SigrhpApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class BulletinResourceIT {

    private static final Double DEFAULT_RETENUE_IPM = 1D;
    private static final Double UPDATED_RETENUE_IPM = 2D;

    private static final Double DEFAULT_RETENUE_PHARMACIE = 1D;
    private static final Double UPDATED_RETENUE_PHARMACIE = 2D;

    private static final Double DEFAULT_AUTRE_RETENUE = 1D;
    private static final Double UPDATED_AUTRE_RETENUE = 2D;

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

    private static final Float DEFAULT_NB_PART = 1F;
    private static final Float UPDATED_NB_PART = 2F;

    private static final Integer DEFAULT_NB_FEMMES = 1;
    private static final Integer UPDATED_NB_FEMMES = 2;

    private static final Integer DEFAULT_NB_ENFANTS = 1;
    private static final Integer UPDATED_NB_ENFANTS = 2;

    private static final Double DEFAULT_PRIME_IMPOSABLE = 1D;
    private static final Double UPDATED_PRIME_IMPOSABLE = 2D;

    private static final Double DEFAULT_PRIME_NON_IMPOSABLE = 1D;
    private static final Double UPDATED_PRIME_NON_IMPOSABLE = 2D;

    private static final Double DEFAULT_AVANTAGE = 1D;
    private static final Double UPDATED_AVANTAGE = 2D;

    @Autowired
    private BulletinRepository bulletinRepository;

    @Mock
    private BulletinRepository bulletinRepositoryMock;

    @Mock
    private BulletinService bulletinServiceMock;

    @Autowired
    private BulletinService bulletinService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBulletinMockMvc;

    private Bulletin bulletin;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bulletin createEntity(EntityManager em) {
        Bulletin bulletin = new Bulletin()
            .retenueIpm(DEFAULT_RETENUE_IPM)
            .retenuePharmacie(DEFAULT_RETENUE_PHARMACIE)
            .autreRetenue(DEFAULT_AUTRE_RETENUE)
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
            .nbPart(DEFAULT_NB_PART)
            .nbFemmes(DEFAULT_NB_FEMMES)
            .nbEnfants(DEFAULT_NB_ENFANTS)
            .primeImposable(DEFAULT_PRIME_IMPOSABLE)
            .primeNonImposable(DEFAULT_PRIME_NON_IMPOSABLE)
            .avantage(DEFAULT_AVANTAGE);
        // Add required entity
        TypePaiement typePaiement;
        if (TestUtil.findAll(em, TypePaiement.class).isEmpty()) {
            typePaiement = TypePaiementResourceIT.createEntity(em);
            em.persist(typePaiement);
            em.flush();
        } else {
            typePaiement = TestUtil.findAll(em, TypePaiement.class).get(0);
        }
        bulletin.setTypePaiement(typePaiement);
        // Add required entity
        Exercice exercice;
        if (TestUtil.findAll(em, Exercice.class).isEmpty()) {
            exercice = ExerciceResourceIT.createEntity(em);
            em.persist(exercice);
            em.flush();
        } else {
            exercice = TestUtil.findAll(em, Exercice.class).get(0);
        }
        bulletin.setExercice(exercice);
        return bulletin;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bulletin createUpdatedEntity(EntityManager em) {
        Bulletin bulletin = new Bulletin()
            .retenueIpm(UPDATED_RETENUE_IPM)
            .retenuePharmacie(UPDATED_RETENUE_PHARMACIE)
            .autreRetenue(UPDATED_AUTRE_RETENUE)
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
            .nbPart(UPDATED_NB_PART)
            .nbFemmes(UPDATED_NB_FEMMES)
            .nbEnfants(UPDATED_NB_ENFANTS)
            .primeImposable(UPDATED_PRIME_IMPOSABLE)
            .primeNonImposable(UPDATED_PRIME_NON_IMPOSABLE)
            .avantage(UPDATED_AVANTAGE);
        // Add required entity
        TypePaiement typePaiement;
        if (TestUtil.findAll(em, TypePaiement.class).isEmpty()) {
            typePaiement = TypePaiementResourceIT.createUpdatedEntity(em);
            em.persist(typePaiement);
            em.flush();
        } else {
            typePaiement = TestUtil.findAll(em, TypePaiement.class).get(0);
        }
        bulletin.setTypePaiement(typePaiement);
        // Add required entity
        Exercice exercice;
        if (TestUtil.findAll(em, Exercice.class).isEmpty()) {
            exercice = ExerciceResourceIT.createUpdatedEntity(em);
            em.persist(exercice);
            em.flush();
        } else {
            exercice = TestUtil.findAll(em, Exercice.class).get(0);
        }
        bulletin.setExercice(exercice);
        return bulletin;
    }

    @BeforeEach
    public void initTest() {
        bulletin = createEntity(em);
    }

    @Test
    @Transactional
    public void createBulletin() throws Exception {
        int databaseSizeBeforeCreate = bulletinRepository.findAll().size();
        // Create the Bulletin
        restBulletinMockMvc.perform(post("/api/bulletins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bulletin)))
            .andExpect(status().isCreated());

        // Validate the Bulletin in the database
        List<Bulletin> bulletinList = bulletinRepository.findAll();
        assertThat(bulletinList).hasSize(databaseSizeBeforeCreate + 1);
        Bulletin testBulletin = bulletinList.get(bulletinList.size() - 1);
        assertThat(testBulletin.getRetenueIpm()).isEqualTo(DEFAULT_RETENUE_IPM);
        assertThat(testBulletin.getRetenuePharmacie()).isEqualTo(DEFAULT_RETENUE_PHARMACIE);
        assertThat(testBulletin.getAutreRetenue()).isEqualTo(DEFAULT_AUTRE_RETENUE);
        assertThat(testBulletin.getBrutFiscal()).isEqualTo(DEFAULT_BRUT_FISCAL);
        assertThat(testBulletin.getNetAPayer()).isEqualTo(DEFAULT_NET_A_PAYER);
        assertThat(testBulletin.getSalaireBrutMensuel()).isEqualTo(DEFAULT_SALAIRE_BRUT_MENSUEL);
        assertThat(testBulletin.getImpotSurRevenu()).isEqualTo(DEFAULT_IMPOT_SUR_REVENU);
        assertThat(testBulletin.getTrimf()).isEqualTo(DEFAULT_TRIMF);
        assertThat(testBulletin.getIpresPartSalariale()).isEqualTo(DEFAULT_IPRES_PART_SALARIALE);
        assertThat(testBulletin.getTotRetenue()).isEqualTo(DEFAULT_TOT_RETENUE);
        assertThat(testBulletin.getIpresPartPatronales()).isEqualTo(DEFAULT_IPRES_PART_PATRONALES);
        assertThat(testBulletin.getCssAccidentDeTravail()).isEqualTo(DEFAULT_CSS_ACCIDENT_DE_TRAVAIL);
        assertThat(testBulletin.getCssPrestationFamiliale()).isEqualTo(DEFAULT_CSS_PRESTATION_FAMILIALE);
        assertThat(testBulletin.getIpmPatronale()).isEqualTo(DEFAULT_IPM_PATRONALE);
        assertThat(testBulletin.getContributionForfaitaire()).isEqualTo(DEFAULT_CONTRIBUTION_FORFAITAIRE);
        assertThat(testBulletin.getNbPart()).isEqualTo(DEFAULT_NB_PART);
        assertThat(testBulletin.getNbFemmes()).isEqualTo(DEFAULT_NB_FEMMES);
        assertThat(testBulletin.getNbEnfants()).isEqualTo(DEFAULT_NB_ENFANTS);
        assertThat(testBulletin.getPrimeImposable()).isEqualTo(DEFAULT_PRIME_IMPOSABLE);
        assertThat(testBulletin.getPrimeNonImposable()).isEqualTo(DEFAULT_PRIME_NON_IMPOSABLE);
        assertThat(testBulletin.getAvantage()).isEqualTo(DEFAULT_AVANTAGE);
    }

    @Test
    @Transactional
    public void createBulletinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bulletinRepository.findAll().size();

        // Create the Bulletin with an existing ID
        bulletin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBulletinMockMvc.perform(post("/api/bulletins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bulletin)))
            .andExpect(status().isBadRequest());

        // Validate the Bulletin in the database
        List<Bulletin> bulletinList = bulletinRepository.findAll();
        assertThat(bulletinList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBulletins() throws Exception {
        // Initialize the database
        bulletinRepository.saveAndFlush(bulletin);

        // Get all the bulletinList
        restBulletinMockMvc.perform(get("/api/bulletins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bulletin.getId().intValue())))
            .andExpect(jsonPath("$.[*].retenueIpm").value(hasItem(DEFAULT_RETENUE_IPM.doubleValue())))
            .andExpect(jsonPath("$.[*].retenuePharmacie").value(hasItem(DEFAULT_RETENUE_PHARMACIE.doubleValue())))
            .andExpect(jsonPath("$.[*].autreRetenue").value(hasItem(DEFAULT_AUTRE_RETENUE.doubleValue())))
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
            .andExpect(jsonPath("$.[*].nbPart").value(hasItem(DEFAULT_NB_PART.doubleValue())))
            .andExpect(jsonPath("$.[*].nbFemmes").value(hasItem(DEFAULT_NB_FEMMES)))
            .andExpect(jsonPath("$.[*].nbEnfants").value(hasItem(DEFAULT_NB_ENFANTS)))
            .andExpect(jsonPath("$.[*].primeImposable").value(hasItem(DEFAULT_PRIME_IMPOSABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].primeNonImposable").value(hasItem(DEFAULT_PRIME_NON_IMPOSABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].avantage").value(hasItem(DEFAULT_AVANTAGE.doubleValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllBulletinsWithEagerRelationshipsIsEnabled() throws Exception {
        when(bulletinServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBulletinMockMvc.perform(get("/api/bulletins?eagerload=true"))
            .andExpect(status().isOk());

        verify(bulletinServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllBulletinsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(bulletinServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBulletinMockMvc.perform(get("/api/bulletins?eagerload=true"))
            .andExpect(status().isOk());

        verify(bulletinServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getBulletin() throws Exception {
        // Initialize the database
        bulletinRepository.saveAndFlush(bulletin);

        // Get the bulletin
        restBulletinMockMvc.perform(get("/api/bulletins/{id}", bulletin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bulletin.getId().intValue()))
            .andExpect(jsonPath("$.retenueIpm").value(DEFAULT_RETENUE_IPM.doubleValue()))
            .andExpect(jsonPath("$.retenuePharmacie").value(DEFAULT_RETENUE_PHARMACIE.doubleValue()))
            .andExpect(jsonPath("$.autreRetenue").value(DEFAULT_AUTRE_RETENUE.doubleValue()))
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
            .andExpect(jsonPath("$.nbPart").value(DEFAULT_NB_PART.doubleValue()))
            .andExpect(jsonPath("$.nbFemmes").value(DEFAULT_NB_FEMMES))
            .andExpect(jsonPath("$.nbEnfants").value(DEFAULT_NB_ENFANTS))
            .andExpect(jsonPath("$.primeImposable").value(DEFAULT_PRIME_IMPOSABLE.doubleValue()))
            .andExpect(jsonPath("$.primeNonImposable").value(DEFAULT_PRIME_NON_IMPOSABLE.doubleValue()))
            .andExpect(jsonPath("$.avantage").value(DEFAULT_AVANTAGE.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingBulletin() throws Exception {
        // Get the bulletin
        restBulletinMockMvc.perform(get("/api/bulletins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBulletin() throws Exception {
        // Initialize the database
        bulletinService.save(bulletin);

        int databaseSizeBeforeUpdate = bulletinRepository.findAll().size();

        // Update the bulletin
        Bulletin updatedBulletin = bulletinRepository.findById(bulletin.getId()).get();
        // Disconnect from session so that the updates on updatedBulletin are not directly saved in db
        em.detach(updatedBulletin);
        updatedBulletin
            .retenueIpm(UPDATED_RETENUE_IPM)
            .retenuePharmacie(UPDATED_RETENUE_PHARMACIE)
            .autreRetenue(UPDATED_AUTRE_RETENUE)
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
            .nbPart(UPDATED_NB_PART)
            .nbFemmes(UPDATED_NB_FEMMES)
            .nbEnfants(UPDATED_NB_ENFANTS)
            .primeImposable(UPDATED_PRIME_IMPOSABLE)
            .primeNonImposable(UPDATED_PRIME_NON_IMPOSABLE)
            .avantage(UPDATED_AVANTAGE);

        restBulletinMockMvc.perform(put("/api/bulletins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBulletin)))
            .andExpect(status().isOk());

        // Validate the Bulletin in the database
        List<Bulletin> bulletinList = bulletinRepository.findAll();
        assertThat(bulletinList).hasSize(databaseSizeBeforeUpdate);
        Bulletin testBulletin = bulletinList.get(bulletinList.size() - 1);
        assertThat(testBulletin.getRetenueIpm()).isEqualTo(UPDATED_RETENUE_IPM);
        assertThat(testBulletin.getRetenuePharmacie()).isEqualTo(UPDATED_RETENUE_PHARMACIE);
        assertThat(testBulletin.getAutreRetenue()).isEqualTo(UPDATED_AUTRE_RETENUE);
        assertThat(testBulletin.getBrutFiscal()).isEqualTo(UPDATED_BRUT_FISCAL);
        assertThat(testBulletin.getNetAPayer()).isEqualTo(UPDATED_NET_A_PAYER);
        assertThat(testBulletin.getSalaireBrutMensuel()).isEqualTo(UPDATED_SALAIRE_BRUT_MENSUEL);
        assertThat(testBulletin.getImpotSurRevenu()).isEqualTo(UPDATED_IMPOT_SUR_REVENU);
        assertThat(testBulletin.getTrimf()).isEqualTo(UPDATED_TRIMF);
        assertThat(testBulletin.getIpresPartSalariale()).isEqualTo(UPDATED_IPRES_PART_SALARIALE);
        assertThat(testBulletin.getTotRetenue()).isEqualTo(UPDATED_TOT_RETENUE);
        assertThat(testBulletin.getIpresPartPatronales()).isEqualTo(UPDATED_IPRES_PART_PATRONALES);
        assertThat(testBulletin.getCssAccidentDeTravail()).isEqualTo(UPDATED_CSS_ACCIDENT_DE_TRAVAIL);
        assertThat(testBulletin.getCssPrestationFamiliale()).isEqualTo(UPDATED_CSS_PRESTATION_FAMILIALE);
        assertThat(testBulletin.getIpmPatronale()).isEqualTo(UPDATED_IPM_PATRONALE);
        assertThat(testBulletin.getContributionForfaitaire()).isEqualTo(UPDATED_CONTRIBUTION_FORFAITAIRE);
        assertThat(testBulletin.getNbPart()).isEqualTo(UPDATED_NB_PART);
        assertThat(testBulletin.getNbFemmes()).isEqualTo(UPDATED_NB_FEMMES);
        assertThat(testBulletin.getNbEnfants()).isEqualTo(UPDATED_NB_ENFANTS);
        assertThat(testBulletin.getPrimeImposable()).isEqualTo(UPDATED_PRIME_IMPOSABLE);
        assertThat(testBulletin.getPrimeNonImposable()).isEqualTo(UPDATED_PRIME_NON_IMPOSABLE);
        assertThat(testBulletin.getAvantage()).isEqualTo(UPDATED_AVANTAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingBulletin() throws Exception {
        int databaseSizeBeforeUpdate = bulletinRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBulletinMockMvc.perform(put("/api/bulletins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bulletin)))
            .andExpect(status().isBadRequest());

        // Validate the Bulletin in the database
        List<Bulletin> bulletinList = bulletinRepository.findAll();
        assertThat(bulletinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBulletin() throws Exception {
        // Initialize the database
        bulletinService.save(bulletin);

        int databaseSizeBeforeDelete = bulletinRepository.findAll().size();

        // Delete the bulletin
        restBulletinMockMvc.perform(delete("/api/bulletins/{id}", bulletin.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bulletin> bulletinList = bulletinRepository.findAll();
        assertThat(bulletinList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
