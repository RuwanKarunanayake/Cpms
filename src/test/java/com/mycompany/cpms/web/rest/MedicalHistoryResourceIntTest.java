package com.mycompany.cpms.web.rest;

import com.mycompany.cpms.CpmsApp;

import com.mycompany.cpms.domain.MedicalHistory;
import com.mycompany.cpms.domain.User;
import com.mycompany.cpms.repository.MedicalHistoryRepository;
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
 * Test class for the MedicalHistoryResource REST controller.
 *
 * @see MedicalHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CpmsApp.class)
public class MedicalHistoryResourceIntTest {

    private static final Boolean DEFAULT_DIABATES = false;
    private static final Boolean UPDATED_DIABATES = true;

    private static final Boolean DEFAULT_HYPERTENSION = false;
    private static final Boolean UPDATED_HYPERTENSION = true;

    private static final Boolean DEFAULT_IHD = false;
    private static final Boolean UPDATED_IHD = true;

    private static final Boolean DEFAULT_BA = false;
    private static final Boolean UPDATED_BA = true;

    private static final Boolean DEFAULT_CKD = false;
    private static final Boolean UPDATED_CKD = true;

    private static final Boolean DEFAULT_EPILEPSY = false;
    private static final Boolean UPDATED_EPILEPSY = true;

    private static final String DEFAULT_OTHER_PAST = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_PAST = "BBBBBBBBBB";

    private static final String DEFAULT_PAST_SURGICAL = "AAAAAAAAAA";
    private static final String UPDATED_PAST_SURGICAL = "BBBBBBBBBB";

    private static final String DEFAULT_PAST_ALLERGY = "AAAAAAAAAA";
    private static final String UPDATED_PAST_ALLERGY = "BBBBBBBBBB";

    private static final String DEFAULT_PAST_DRUG = "AAAAAAAAAA";
    private static final String UPDATED_PAST_DRUG = "BBBBBBBBBB";

    private static final String DEFAULT_PAST_FAMILY = "AAAAAAAAAA";
    private static final String UPDATED_PAST_FAMILY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SMOKE = false;
    private static final Boolean UPDATED_SMOKE = true;

    private static final Boolean DEFAULT_ALCHOHOL = false;
    private static final Boolean UPDATED_ALCHOHOL = true;

    private static final String DEFAULT_OTHER_HISTORY = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_HISTORY = "BBBBBBBBBB";

    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedicalHistoryMockMvc;

    private MedicalHistory medicalHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MedicalHistoryResource medicalHistoryResource = new MedicalHistoryResource(medicalHistoryRepository);
        this.restMedicalHistoryMockMvc = MockMvcBuilders.standaloneSetup(medicalHistoryResource)
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
    public static MedicalHistory createEntity(EntityManager em) {
        MedicalHistory medicalHistory = new MedicalHistory()
            .diabates(DEFAULT_DIABATES)
            .hypertension(DEFAULT_HYPERTENSION)
            .ihd(DEFAULT_IHD)
            .ba(DEFAULT_BA)
            .ckd(DEFAULT_CKD)
            .epilepsy(DEFAULT_EPILEPSY)
            .otherPast(DEFAULT_OTHER_PAST)
            .pastSurgical(DEFAULT_PAST_SURGICAL)
            .pastAllergy(DEFAULT_PAST_ALLERGY)
            .pastDrug(DEFAULT_PAST_DRUG)
            .pastFamily(DEFAULT_PAST_FAMILY)
            .smoke(DEFAULT_SMOKE)
            .alchohol(DEFAULT_ALCHOHOL)
            .otherHistory(DEFAULT_OTHER_HISTORY);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        medicalHistory.setUser(user);
        return medicalHistory;
    }

    @Before
    public void initTest() {
        medicalHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalHistory() throws Exception {
        int databaseSizeBeforeCreate = medicalHistoryRepository.findAll().size();

        // Create the MedicalHistory
        restMedicalHistoryMockMvc.perform(post("/api/medical-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistory)))
            .andExpect(status().isCreated());

        // Validate the MedicalHistory in the database
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalHistory testMedicalHistory = medicalHistoryList.get(medicalHistoryList.size() - 1);
        assertThat(testMedicalHistory.isDiabates()).isEqualTo(DEFAULT_DIABATES);
        assertThat(testMedicalHistory.isHypertension()).isEqualTo(DEFAULT_HYPERTENSION);
        assertThat(testMedicalHistory.isIhd()).isEqualTo(DEFAULT_IHD);
        assertThat(testMedicalHistory.isBa()).isEqualTo(DEFAULT_BA);
        assertThat(testMedicalHistory.isCkd()).isEqualTo(DEFAULT_CKD);
        assertThat(testMedicalHistory.isEpilepsy()).isEqualTo(DEFAULT_EPILEPSY);
        assertThat(testMedicalHistory.getOtherPast()).isEqualTo(DEFAULT_OTHER_PAST);
        assertThat(testMedicalHistory.getPastSurgical()).isEqualTo(DEFAULT_PAST_SURGICAL);
        assertThat(testMedicalHistory.getPastAllergy()).isEqualTo(DEFAULT_PAST_ALLERGY);
        assertThat(testMedicalHistory.getPastDrug()).isEqualTo(DEFAULT_PAST_DRUG);
        assertThat(testMedicalHistory.getPastFamily()).isEqualTo(DEFAULT_PAST_FAMILY);
        assertThat(testMedicalHistory.isSmoke()).isEqualTo(DEFAULT_SMOKE);
        assertThat(testMedicalHistory.isAlchohol()).isEqualTo(DEFAULT_ALCHOHOL);
        assertThat(testMedicalHistory.getOtherHistory()).isEqualTo(DEFAULT_OTHER_HISTORY);
    }

    @Test
    @Transactional
    public void createMedicalHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalHistoryRepository.findAll().size();

        // Create the MedicalHistory with an existing ID
        medicalHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalHistoryMockMvc.perform(post("/api/medical-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistory)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDiabatesIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicalHistoryRepository.findAll().size();
        // set the field null
        medicalHistory.setDiabates(null);

        // Create the MedicalHistory, which fails.

        restMedicalHistoryMockMvc.perform(post("/api/medical-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistory)))
            .andExpect(status().isBadRequest());

        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHypertensionIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicalHistoryRepository.findAll().size();
        // set the field null
        medicalHistory.setHypertension(null);

        // Create the MedicalHistory, which fails.

        restMedicalHistoryMockMvc.perform(post("/api/medical-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistory)))
            .andExpect(status().isBadRequest());

        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIhdIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicalHistoryRepository.findAll().size();
        // set the field null
        medicalHistory.setIhd(null);

        // Create the MedicalHistory, which fails.

        restMedicalHistoryMockMvc.perform(post("/api/medical-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistory)))
            .andExpect(status().isBadRequest());

        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBaIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicalHistoryRepository.findAll().size();
        // set the field null
        medicalHistory.setBa(null);

        // Create the MedicalHistory, which fails.

        restMedicalHistoryMockMvc.perform(post("/api/medical-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistory)))
            .andExpect(status().isBadRequest());

        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCkdIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicalHistoryRepository.findAll().size();
        // set the field null
        medicalHistory.setCkd(null);

        // Create the MedicalHistory, which fails.

        restMedicalHistoryMockMvc.perform(post("/api/medical-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistory)))
            .andExpect(status().isBadRequest());

        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEpilepsyIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicalHistoryRepository.findAll().size();
        // set the field null
        medicalHistory.setEpilepsy(null);

        // Create the MedicalHistory, which fails.

        restMedicalHistoryMockMvc.perform(post("/api/medical-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistory)))
            .andExpect(status().isBadRequest());

        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSmokeIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicalHistoryRepository.findAll().size();
        // set the field null
        medicalHistory.setSmoke(null);

        // Create the MedicalHistory, which fails.

        restMedicalHistoryMockMvc.perform(post("/api/medical-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistory)))
            .andExpect(status().isBadRequest());

        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAlchoholIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicalHistoryRepository.findAll().size();
        // set the field null
        medicalHistory.setAlchohol(null);

        // Create the MedicalHistory, which fails.

        restMedicalHistoryMockMvc.perform(post("/api/medical-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistory)))
            .andExpect(status().isBadRequest());

        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMedicalHistories() throws Exception {
        // Initialize the database
        medicalHistoryRepository.saveAndFlush(medicalHistory);

        // Get all the medicalHistoryList
        restMedicalHistoryMockMvc.perform(get("/api/medical-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].diabates").value(hasItem(DEFAULT_DIABATES.booleanValue())))
            .andExpect(jsonPath("$.[*].hypertension").value(hasItem(DEFAULT_HYPERTENSION.booleanValue())))
            .andExpect(jsonPath("$.[*].ihd").value(hasItem(DEFAULT_IHD.booleanValue())))
            .andExpect(jsonPath("$.[*].ba").value(hasItem(DEFAULT_BA.booleanValue())))
            .andExpect(jsonPath("$.[*].ckd").value(hasItem(DEFAULT_CKD.booleanValue())))
            .andExpect(jsonPath("$.[*].epilepsy").value(hasItem(DEFAULT_EPILEPSY.booleanValue())))
            .andExpect(jsonPath("$.[*].otherPast").value(hasItem(DEFAULT_OTHER_PAST.toString())))
            .andExpect(jsonPath("$.[*].pastSurgical").value(hasItem(DEFAULT_PAST_SURGICAL.toString())))
            .andExpect(jsonPath("$.[*].pastAllergy").value(hasItem(DEFAULT_PAST_ALLERGY.toString())))
            .andExpect(jsonPath("$.[*].pastDrug").value(hasItem(DEFAULT_PAST_DRUG.toString())))
            .andExpect(jsonPath("$.[*].pastFamily").value(hasItem(DEFAULT_PAST_FAMILY.toString())))
            .andExpect(jsonPath("$.[*].smoke").value(hasItem(DEFAULT_SMOKE.booleanValue())))
            .andExpect(jsonPath("$.[*].alchohol").value(hasItem(DEFAULT_ALCHOHOL.booleanValue())))
            .andExpect(jsonPath("$.[*].otherHistory").value(hasItem(DEFAULT_OTHER_HISTORY.toString())));
    }

    @Test
    @Transactional
    public void getMedicalHistory() throws Exception {
        // Initialize the database
        medicalHistoryRepository.saveAndFlush(medicalHistory);

        // Get the medicalHistory
        restMedicalHistoryMockMvc.perform(get("/api/medical-histories/{id}", medicalHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicalHistory.getId().intValue()))
            .andExpect(jsonPath("$.diabates").value(DEFAULT_DIABATES.booleanValue()))
            .andExpect(jsonPath("$.hypertension").value(DEFAULT_HYPERTENSION.booleanValue()))
            .andExpect(jsonPath("$.ihd").value(DEFAULT_IHD.booleanValue()))
            .andExpect(jsonPath("$.ba").value(DEFAULT_BA.booleanValue()))
            .andExpect(jsonPath("$.ckd").value(DEFAULT_CKD.booleanValue()))
            .andExpect(jsonPath("$.epilepsy").value(DEFAULT_EPILEPSY.booleanValue()))
            .andExpect(jsonPath("$.otherPast").value(DEFAULT_OTHER_PAST.toString()))
            .andExpect(jsonPath("$.pastSurgical").value(DEFAULT_PAST_SURGICAL.toString()))
            .andExpect(jsonPath("$.pastAllergy").value(DEFAULT_PAST_ALLERGY.toString()))
            .andExpect(jsonPath("$.pastDrug").value(DEFAULT_PAST_DRUG.toString()))
            .andExpect(jsonPath("$.pastFamily").value(DEFAULT_PAST_FAMILY.toString()))
            .andExpect(jsonPath("$.smoke").value(DEFAULT_SMOKE.booleanValue()))
            .andExpect(jsonPath("$.alchohol").value(DEFAULT_ALCHOHOL.booleanValue()))
            .andExpect(jsonPath("$.otherHistory").value(DEFAULT_OTHER_HISTORY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMedicalHistory() throws Exception {
        // Get the medicalHistory
        restMedicalHistoryMockMvc.perform(get("/api/medical-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalHistory() throws Exception {
        // Initialize the database
        medicalHistoryRepository.saveAndFlush(medicalHistory);
        int databaseSizeBeforeUpdate = medicalHistoryRepository.findAll().size();

        // Update the medicalHistory
        MedicalHistory updatedMedicalHistory = medicalHistoryRepository.findOne(medicalHistory.getId());
        updatedMedicalHistory
            .diabates(UPDATED_DIABATES)
            .hypertension(UPDATED_HYPERTENSION)
            .ihd(UPDATED_IHD)
            .ba(UPDATED_BA)
            .ckd(UPDATED_CKD)
            .epilepsy(UPDATED_EPILEPSY)
            .otherPast(UPDATED_OTHER_PAST)
            .pastSurgical(UPDATED_PAST_SURGICAL)
            .pastAllergy(UPDATED_PAST_ALLERGY)
            .pastDrug(UPDATED_PAST_DRUG)
            .pastFamily(UPDATED_PAST_FAMILY)
            .smoke(UPDATED_SMOKE)
            .alchohol(UPDATED_ALCHOHOL)
            .otherHistory(UPDATED_OTHER_HISTORY);

        restMedicalHistoryMockMvc.perform(put("/api/medical-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicalHistory)))
            .andExpect(status().isOk());

        // Validate the MedicalHistory in the database
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeUpdate);
        MedicalHistory testMedicalHistory = medicalHistoryList.get(medicalHistoryList.size() - 1);
        assertThat(testMedicalHistory.isDiabates()).isEqualTo(UPDATED_DIABATES);
        assertThat(testMedicalHistory.isHypertension()).isEqualTo(UPDATED_HYPERTENSION);
        assertThat(testMedicalHistory.isIhd()).isEqualTo(UPDATED_IHD);
        assertThat(testMedicalHistory.isBa()).isEqualTo(UPDATED_BA);
        assertThat(testMedicalHistory.isCkd()).isEqualTo(UPDATED_CKD);
        assertThat(testMedicalHistory.isEpilepsy()).isEqualTo(UPDATED_EPILEPSY);
        assertThat(testMedicalHistory.getOtherPast()).isEqualTo(UPDATED_OTHER_PAST);
        assertThat(testMedicalHistory.getPastSurgical()).isEqualTo(UPDATED_PAST_SURGICAL);
        assertThat(testMedicalHistory.getPastAllergy()).isEqualTo(UPDATED_PAST_ALLERGY);
        assertThat(testMedicalHistory.getPastDrug()).isEqualTo(UPDATED_PAST_DRUG);
        assertThat(testMedicalHistory.getPastFamily()).isEqualTo(UPDATED_PAST_FAMILY);
        assertThat(testMedicalHistory.isSmoke()).isEqualTo(UPDATED_SMOKE);
        assertThat(testMedicalHistory.isAlchohol()).isEqualTo(UPDATED_ALCHOHOL);
        assertThat(testMedicalHistory.getOtherHistory()).isEqualTo(UPDATED_OTHER_HISTORY);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalHistory() throws Exception {
        int databaseSizeBeforeUpdate = medicalHistoryRepository.findAll().size();

        // Create the MedicalHistory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMedicalHistoryMockMvc.perform(put("/api/medical-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistory)))
            .andExpect(status().isCreated());

        // Validate the MedicalHistory in the database
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMedicalHistory() throws Exception {
        // Initialize the database
        medicalHistoryRepository.saveAndFlush(medicalHistory);
        int databaseSizeBeforeDelete = medicalHistoryRepository.findAll().size();

        // Get the medicalHistory
        restMedicalHistoryMockMvc.perform(delete("/api/medical-histories/{id}", medicalHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalHistory.class);
    }
}
