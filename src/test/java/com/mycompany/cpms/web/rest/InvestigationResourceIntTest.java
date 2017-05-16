package com.mycompany.cpms.web.rest;

import com.mycompany.cpms.CpmsApp;

import com.mycompany.cpms.domain.Investigation;
import com.mycompany.cpms.domain.Recording;
import com.mycompany.cpms.repository.InvestigationRepository;
import com.mycompany.cpms.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InvestigationResource REST controller.
 *
 * @see InvestigationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CpmsApp.class)
public class InvestigationResourceIntTest {

    private static final Boolean DEFAULT_F_BC = false;
    private static final Boolean UPDATED_F_BC = true;

    private static final Boolean DEFAULT_B_V = false;
    private static final Boolean UPDATED_B_V = true;

    private static final Boolean DEFAULT_S_E = false;
    private static final Boolean UPDATED_S_E = true;

    private static final Boolean DEFAULT_S_CV = false;
    private static final Boolean UPDATED_S_CV = true;

    private static final Boolean DEFAULT_L_FT = false;
    private static final Boolean UPDATED_L_FT = true;

    private static final Boolean DEFAULT_LIPID_PROFILE = false;
    private static final Boolean UPDATED_LIPID_PROFILE = true;

    private static final Boolean DEFAULT_F_BS = false;
    private static final Boolean UPDATED_F_BS = true;

    private static final Boolean DEFAULT_P_PBS = false;
    private static final Boolean UPDATED_P_PBS = true;

    private static final Boolean DEFAULT_BLOOD_TEST_COMPLETED = false;
    private static final Boolean UPDATED_BLOOD_TEST_COMPLETED = true;

    private static final Boolean DEFAULT_U_FR = false;
    private static final Boolean UPDATED_U_FR = true;

    private static final Boolean DEFAULT_URINE_TEST_COMPLETED = false;
    private static final Boolean UPDATED_URINE_TEST_COMPLETED = true;

    private static final Boolean DEFAULT_CULTURE = false;
    private static final Boolean UPDATED_CULTURE = true;

    private static final Boolean DEFAULT_CULTURE_TEST_COMPLETED = false;
    private static final Boolean UPDATED_CULTURE_TEST_COMPLETED = true;

    private static final String DEFAULT_OTHER = "AAAAAAAAAA";
    private static final String UPDATED_OTHER = "BBBBBBBBBB";

    @Autowired
    private InvestigationRepository investigationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInvestigationMockMvc;

    private Investigation investigation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InvestigationResource investigationResource = new InvestigationResource(investigationRepository);
        this.restInvestigationMockMvc = MockMvcBuilders.standaloneSetup(investigationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Investigation createEntity(EntityManager em) {
        Investigation investigation = new Investigation()
            .fBC(DEFAULT_F_BC)
            .bV(DEFAULT_B_V)
            .sE(DEFAULT_S_E)
            .sCV(DEFAULT_S_CV)
            .lFT(DEFAULT_L_FT)
            .lipidProfile(DEFAULT_LIPID_PROFILE)
            .fBS(DEFAULT_F_BS)
            .pPBS(DEFAULT_P_PBS)
            .bloodTestCompleted(DEFAULT_BLOOD_TEST_COMPLETED)
            .uFR(DEFAULT_U_FR)
            .urineTestCompleted(DEFAULT_URINE_TEST_COMPLETED)
            .culture(DEFAULT_CULTURE)
            .cultureTestCompleted(DEFAULT_CULTURE_TEST_COMPLETED)
            .other(DEFAULT_OTHER);
        // Add required entity
        Recording recording = RecordingResourceIntTest.createEntity(em);
        em.persist(recording);
        em.flush();
        investigation.setRecording(recording);
        return investigation;
    }

    @Before
    public void initTest() {
        investigation = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvestigation() throws Exception {
        int databaseSizeBeforeCreate = investigationRepository.findAll().size();

        // Create the Investigation
        restInvestigationMockMvc.perform(post("/api/investigations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investigation)))
            .andExpect(status().isCreated());

        // Validate the Investigation in the database
        List<Investigation> investigationList = investigationRepository.findAll();
        assertThat(investigationList).hasSize(databaseSizeBeforeCreate + 1);
        Investigation testInvestigation = investigationList.get(investigationList.size() - 1);
        assertThat(testInvestigation.isfBC()).isEqualTo(DEFAULT_F_BC);
        assertThat(testInvestigation.isbV()).isEqualTo(DEFAULT_B_V);
        assertThat(testInvestigation.issE()).isEqualTo(DEFAULT_S_E);
        assertThat(testInvestigation.issCV()).isEqualTo(DEFAULT_S_CV);
        assertThat(testInvestigation.islFT()).isEqualTo(DEFAULT_L_FT);
        assertThat(testInvestigation.isLipidProfile()).isEqualTo(DEFAULT_LIPID_PROFILE);
        assertThat(testInvestigation.isfBS()).isEqualTo(DEFAULT_F_BS);
        assertThat(testInvestigation.ispPBS()).isEqualTo(DEFAULT_P_PBS);
        assertThat(testInvestigation.isBloodTestCompleted()).isEqualTo(DEFAULT_BLOOD_TEST_COMPLETED);
        assertThat(testInvestigation.isuFR()).isEqualTo(DEFAULT_U_FR);
        assertThat(testInvestigation.isUrineTestCompleted()).isEqualTo(DEFAULT_URINE_TEST_COMPLETED);
        assertThat(testInvestigation.isCulture()).isEqualTo(DEFAULT_CULTURE);
        assertThat(testInvestigation.isCultureTestCompleted()).isEqualTo(DEFAULT_CULTURE_TEST_COMPLETED);
        assertThat(testInvestigation.getOther()).isEqualTo(DEFAULT_OTHER);
    }

    @Test
    @Transactional
    public void createInvestigationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = investigationRepository.findAll().size();

        // Create the Investigation with an existing ID
        investigation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvestigationMockMvc.perform(post("/api/investigations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investigation)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Investigation> investigationList = investigationRepository.findAll();
        assertThat(investigationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkfBCIsRequired() throws Exception {
        int databaseSizeBeforeTest = investigationRepository.findAll().size();
        // set the field null
        investigation.setfBC(null);

        // Create the Investigation, which fails.

        restInvestigationMockMvc.perform(post("/api/investigations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investigation)))
            .andExpect(status().isBadRequest());

        List<Investigation> investigationList = investigationRepository.findAll();
        assertThat(investigationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkbVIsRequired() throws Exception {
        int databaseSizeBeforeTest = investigationRepository.findAll().size();
        // set the field null
        investigation.setbV(null);

        // Create the Investigation, which fails.

        restInvestigationMockMvc.perform(post("/api/investigations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investigation)))
            .andExpect(status().isBadRequest());

        List<Investigation> investigationList = investigationRepository.findAll();
        assertThat(investigationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checksEIsRequired() throws Exception {
        int databaseSizeBeforeTest = investigationRepository.findAll().size();
        // set the field null
        investigation.setsE(null);

        // Create the Investigation, which fails.

        restInvestigationMockMvc.perform(post("/api/investigations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investigation)))
            .andExpect(status().isBadRequest());

        List<Investigation> investigationList = investigationRepository.findAll();
        assertThat(investigationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checksCVIsRequired() throws Exception {
        int databaseSizeBeforeTest = investigationRepository.findAll().size();
        // set the field null
        investigation.setsCV(null);

        // Create the Investigation, which fails.

        restInvestigationMockMvc.perform(post("/api/investigations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investigation)))
            .andExpect(status().isBadRequest());

        List<Investigation> investigationList = investigationRepository.findAll();
        assertThat(investigationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checklFTIsRequired() throws Exception {
        int databaseSizeBeforeTest = investigationRepository.findAll().size();
        // set the field null
        investigation.setlFT(null);

        // Create the Investigation, which fails.

        restInvestigationMockMvc.perform(post("/api/investigations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investigation)))
            .andExpect(status().isBadRequest());

        List<Investigation> investigationList = investigationRepository.findAll();
        assertThat(investigationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLipidProfileIsRequired() throws Exception {
        int databaseSizeBeforeTest = investigationRepository.findAll().size();
        // set the field null
        investigation.setLipidProfile(null);

        // Create the Investigation, which fails.

        restInvestigationMockMvc.perform(post("/api/investigations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investigation)))
            .andExpect(status().isBadRequest());

        List<Investigation> investigationList = investigationRepository.findAll();
        assertThat(investigationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkfBSIsRequired() throws Exception {
        int databaseSizeBeforeTest = investigationRepository.findAll().size();
        // set the field null
        investigation.setfBS(null);

        // Create the Investigation, which fails.

        restInvestigationMockMvc.perform(post("/api/investigations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investigation)))
            .andExpect(status().isBadRequest());

        List<Investigation> investigationList = investigationRepository.findAll();
        assertThat(investigationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkpPBSIsRequired() throws Exception {
        int databaseSizeBeforeTest = investigationRepository.findAll().size();
        // set the field null
        investigation.setpPBS(null);

        // Create the Investigation, which fails.

        restInvestigationMockMvc.perform(post("/api/investigations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investigation)))
            .andExpect(status().isBadRequest());

        List<Investigation> investigationList = investigationRepository.findAll();
        assertThat(investigationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBloodTestCompletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = investigationRepository.findAll().size();
        // set the field null
        investigation.setBloodTestCompleted(null);

        // Create the Investigation, which fails.

        restInvestigationMockMvc.perform(post("/api/investigations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investigation)))
            .andExpect(status().isBadRequest());

        List<Investigation> investigationList = investigationRepository.findAll();
        assertThat(investigationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkuFRIsRequired() throws Exception {
        int databaseSizeBeforeTest = investigationRepository.findAll().size();
        // set the field null
        investigation.setuFR(null);

        // Create the Investigation, which fails.

        restInvestigationMockMvc.perform(post("/api/investigations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investigation)))
            .andExpect(status().isBadRequest());

        List<Investigation> investigationList = investigationRepository.findAll();
        assertThat(investigationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrineTestCompletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = investigationRepository.findAll().size();
        // set the field null
        investigation.setUrineTestCompleted(null);

        // Create the Investigation, which fails.

        restInvestigationMockMvc.perform(post("/api/investigations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investigation)))
            .andExpect(status().isBadRequest());

        List<Investigation> investigationList = investigationRepository.findAll();
        assertThat(investigationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCultureIsRequired() throws Exception {
        int databaseSizeBeforeTest = investigationRepository.findAll().size();
        // set the field null
        investigation.setCulture(null);

        // Create the Investigation, which fails.

        restInvestigationMockMvc.perform(post("/api/investigations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investigation)))
            .andExpect(status().isBadRequest());

        List<Investigation> investigationList = investigationRepository.findAll();
        assertThat(investigationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCultureTestCompletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = investigationRepository.findAll().size();
        // set the field null
        investigation.setCultureTestCompleted(null);

        // Create the Investigation, which fails.

        restInvestigationMockMvc.perform(post("/api/investigations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investigation)))
            .andExpect(status().isBadRequest());

        List<Investigation> investigationList = investigationRepository.findAll();
        assertThat(investigationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvestigations() throws Exception {
        // Initialize the database
        investigationRepository.saveAndFlush(investigation);

        // Get all the investigationList
        restInvestigationMockMvc.perform(get("/api/investigations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(investigation.getId().intValue())))
            .andExpect(jsonPath("$.[*].fBC").value(hasItem(DEFAULT_F_BC.booleanValue())))
            .andExpect(jsonPath("$.[*].bV").value(hasItem(DEFAULT_B_V.booleanValue())))
            .andExpect(jsonPath("$.[*].sE").value(hasItem(DEFAULT_S_E.booleanValue())))
            .andExpect(jsonPath("$.[*].sCV").value(hasItem(DEFAULT_S_CV.booleanValue())))
            .andExpect(jsonPath("$.[*].lFT").value(hasItem(DEFAULT_L_FT.booleanValue())))
            .andExpect(jsonPath("$.[*].lipidProfile").value(hasItem(DEFAULT_LIPID_PROFILE.booleanValue())))
            .andExpect(jsonPath("$.[*].fBS").value(hasItem(DEFAULT_F_BS.booleanValue())))
            .andExpect(jsonPath("$.[*].pPBS").value(hasItem(DEFAULT_P_PBS.booleanValue())))
            .andExpect(jsonPath("$.[*].bloodTestCompleted").value(hasItem(DEFAULT_BLOOD_TEST_COMPLETED.booleanValue())))
            .andExpect(jsonPath("$.[*].uFR").value(hasItem(DEFAULT_U_FR.booleanValue())))
            .andExpect(jsonPath("$.[*].urineTestCompleted").value(hasItem(DEFAULT_URINE_TEST_COMPLETED.booleanValue())))
            .andExpect(jsonPath("$.[*].culture").value(hasItem(DEFAULT_CULTURE.booleanValue())))
            .andExpect(jsonPath("$.[*].cultureTestCompleted").value(hasItem(DEFAULT_CULTURE_TEST_COMPLETED.booleanValue())))
            .andExpect(jsonPath("$.[*].other").value(hasItem(DEFAULT_OTHER.toString())));
    }

    @Test
    @Transactional
    public void getInvestigation() throws Exception {
        // Initialize the database
        investigationRepository.saveAndFlush(investigation);

        // Get the investigation
        restInvestigationMockMvc.perform(get("/api/investigations/{id}", investigation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(investigation.getId().intValue()))
            .andExpect(jsonPath("$.fBC").value(DEFAULT_F_BC.booleanValue()))
            .andExpect(jsonPath("$.bV").value(DEFAULT_B_V.booleanValue()))
            .andExpect(jsonPath("$.sE").value(DEFAULT_S_E.booleanValue()))
            .andExpect(jsonPath("$.sCV").value(DEFAULT_S_CV.booleanValue()))
            .andExpect(jsonPath("$.lFT").value(DEFAULT_L_FT.booleanValue()))
            .andExpect(jsonPath("$.lipidProfile").value(DEFAULT_LIPID_PROFILE.booleanValue()))
            .andExpect(jsonPath("$.fBS").value(DEFAULT_F_BS.booleanValue()))
            .andExpect(jsonPath("$.pPBS").value(DEFAULT_P_PBS.booleanValue()))
            .andExpect(jsonPath("$.bloodTestCompleted").value(DEFAULT_BLOOD_TEST_COMPLETED.booleanValue()))
            .andExpect(jsonPath("$.uFR").value(DEFAULT_U_FR.booleanValue()))
            .andExpect(jsonPath("$.urineTestCompleted").value(DEFAULT_URINE_TEST_COMPLETED.booleanValue()))
            .andExpect(jsonPath("$.culture").value(DEFAULT_CULTURE.booleanValue()))
            .andExpect(jsonPath("$.cultureTestCompleted").value(DEFAULT_CULTURE_TEST_COMPLETED.booleanValue()))
            .andExpect(jsonPath("$.other").value(DEFAULT_OTHER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvestigation() throws Exception {
        // Get the investigation
        restInvestigationMockMvc.perform(get("/api/investigations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvestigation() throws Exception {
        // Initialize the database
        investigationRepository.saveAndFlush(investigation);
        int databaseSizeBeforeUpdate = investigationRepository.findAll().size();

        // Update the investigation
        Investigation updatedInvestigation = investigationRepository.findOne(investigation.getId());
        updatedInvestigation
            .fBC(UPDATED_F_BC)
            .bV(UPDATED_B_V)
            .sE(UPDATED_S_E)
            .sCV(UPDATED_S_CV)
            .lFT(UPDATED_L_FT)
            .lipidProfile(UPDATED_LIPID_PROFILE)
            .fBS(UPDATED_F_BS)
            .pPBS(UPDATED_P_PBS)
            .bloodTestCompleted(UPDATED_BLOOD_TEST_COMPLETED)
            .uFR(UPDATED_U_FR)
            .urineTestCompleted(UPDATED_URINE_TEST_COMPLETED)
            .culture(UPDATED_CULTURE)
            .cultureTestCompleted(UPDATED_CULTURE_TEST_COMPLETED)
            .other(UPDATED_OTHER);

        restInvestigationMockMvc.perform(put("/api/investigations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvestigation)))
            .andExpect(status().isOk());

        // Validate the Investigation in the database
        List<Investigation> investigationList = investigationRepository.findAll();
        assertThat(investigationList).hasSize(databaseSizeBeforeUpdate);
        Investigation testInvestigation = investigationList.get(investigationList.size() - 1);
        assertThat(testInvestigation.isfBC()).isEqualTo(UPDATED_F_BC);
        assertThat(testInvestigation.isbV()).isEqualTo(UPDATED_B_V);
        assertThat(testInvestigation.issE()).isEqualTo(UPDATED_S_E);
        assertThat(testInvestigation.issCV()).isEqualTo(UPDATED_S_CV);
        assertThat(testInvestigation.islFT()).isEqualTo(UPDATED_L_FT);
        assertThat(testInvestigation.isLipidProfile()).isEqualTo(UPDATED_LIPID_PROFILE);
        assertThat(testInvestigation.isfBS()).isEqualTo(UPDATED_F_BS);
        assertThat(testInvestigation.ispPBS()).isEqualTo(UPDATED_P_PBS);
        assertThat(testInvestigation.isBloodTestCompleted()).isEqualTo(UPDATED_BLOOD_TEST_COMPLETED);
        assertThat(testInvestigation.isuFR()).isEqualTo(UPDATED_U_FR);
        assertThat(testInvestigation.isUrineTestCompleted()).isEqualTo(UPDATED_URINE_TEST_COMPLETED);
        assertThat(testInvestigation.isCulture()).isEqualTo(UPDATED_CULTURE);
        assertThat(testInvestigation.isCultureTestCompleted()).isEqualTo(UPDATED_CULTURE_TEST_COMPLETED);
        assertThat(testInvestigation.getOther()).isEqualTo(UPDATED_OTHER);
    }

    @Test
    @Transactional
    public void updateNonExistingInvestigation() throws Exception {
        int databaseSizeBeforeUpdate = investigationRepository.findAll().size();

        // Create the Investigation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInvestigationMockMvc.perform(put("/api/investigations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investigation)))
            .andExpect(status().isCreated());

        // Validate the Investigation in the database
        List<Investigation> investigationList = investigationRepository.findAll();
        assertThat(investigationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInvestigation() throws Exception {
        // Initialize the database
        investigationRepository.saveAndFlush(investigation);
        int databaseSizeBeforeDelete = investigationRepository.findAll().size();

        // Get the investigation
        restInvestigationMockMvc.perform(delete("/api/investigations/{id}", investigation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Investigation> investigationList = investigationRepository.findAll();
        assertThat(investigationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Investigation.class);
    }
}
