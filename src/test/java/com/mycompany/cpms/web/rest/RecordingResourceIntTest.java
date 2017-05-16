package com.mycompany.cpms.web.rest;

import com.mycompany.cpms.CpmsApp;

import com.mycompany.cpms.domain.Recording;
import com.mycompany.cpms.domain.User;
import com.mycompany.cpms.domain.Clinic;
import com.mycompany.cpms.repository.RecordingRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RecordingResource REST controller.
 *
 * @see RecordingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CpmsApp.class)
public class RecordingResourceIntTest {

    private static final Boolean DEFAULT_FERBRILE = false;
    private static final Boolean UPDATED_FERBRILE = true;

    private static final Boolean DEFAULT_PILLAR = false;
    private static final Boolean UPDATED_PILLAR = true;

    private static final Boolean DEFAULT_DISPENSYS = false;
    private static final Boolean UPDATED_DISPENSYS = true;

    private static final String DEFAULT_R_R = "AAAAAAAAAA";
    private static final String UPDATED_R_R = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CLEAR_LUNGS = false;
    private static final Boolean UPDATED_CLEAR_LUNGS = true;

    private static final Boolean DEFAULT_RONCHI = false;
    private static final Boolean UPDATED_RONCHI = true;

    private static final Boolean DEFAULT_CRACKLES = false;
    private static final Boolean UPDATED_CRACKLES = true;

    private static final String DEFAULT_OTHER_RS = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_RS = "BBBBBBBBBB";

    private static final String DEFAULT_H_R = "AAAAAAAAAA";
    private static final String UPDATED_H_R = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REGULAR = false;
    private static final Boolean UPDATED_REGULAR = true;

    private static final Boolean DEFAULT_MURMURS = false;
    private static final Boolean UPDATED_MURMURS = true;

    private static final String DEFAULT_OTHER_CVS = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_CVS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SOFT_ABDOMEN = false;
    private static final Boolean UPDATED_SOFT_ABDOMEN = true;

    private static final Boolean DEFAULT_TENSE = false;
    private static final Boolean UPDATED_TENSE = true;

    private static final Boolean DEFAULT_TENDER = false;
    private static final Boolean UPDATED_TENDER = true;

    private static final Boolean DEFAULT_NONE_TENDER = false;
    private static final Boolean UPDATED_NONE_TENDER = true;

    private static final String DEFAULT_OTHER_ABDOMEN = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_ABDOMEN = "BBBBBBBBBB";

    private static final String DEFAULT_NEUROLOGY = "AAAAAAAAAA";
    private static final String UPDATED_NEUROLOGY = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER_SYSTEMS = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_SYSTEMS = "BBBBBBBBBB";

    private static final String DEFAULT_REC_NO = "AAAAAAAAAA";
    private static final String UPDATED_REC_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RecordingRepository recordingRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRecordingMockMvc;

    private Recording recording;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RecordingResource recordingResource = new RecordingResource(recordingRepository);
        this.restRecordingMockMvc = MockMvcBuilders.standaloneSetup(recordingResource)
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
    public static Recording createEntity(EntityManager em) {
        Recording recording = new Recording()
            .ferbrile(DEFAULT_FERBRILE)
            .pillar(DEFAULT_PILLAR)
            .dispensys(DEFAULT_DISPENSYS)
            .rR(DEFAULT_R_R)
            .clearLungs(DEFAULT_CLEAR_LUNGS)
            .ronchi(DEFAULT_RONCHI)
            .crackles(DEFAULT_CRACKLES)
            .otherRS(DEFAULT_OTHER_RS)
            .hR(DEFAULT_H_R)
            .regular(DEFAULT_REGULAR)
            .murmurs(DEFAULT_MURMURS)
            .otherCVS(DEFAULT_OTHER_CVS)
            .softAbdomen(DEFAULT_SOFT_ABDOMEN)
            .tense(DEFAULT_TENSE)
            .tender(DEFAULT_TENDER)
            .noneTender(DEFAULT_NONE_TENDER)
            .otherAbdomen(DEFAULT_OTHER_ABDOMEN)
            .neurology(DEFAULT_NEUROLOGY)
            .otherSystems(DEFAULT_OTHER_SYSTEMS)
            .recNo(DEFAULT_REC_NO)
            .date(DEFAULT_DATE);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        recording.setUser(user);
        // Add required entity
        Clinic clinic = ClinicResourceIntTest.createEntity(em);
        em.persist(clinic);
        em.flush();
        recording.setClinic(clinic);
        return recording;
    }

    @Before
    public void initTest() {
        recording = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecording() throws Exception {
        int databaseSizeBeforeCreate = recordingRepository.findAll().size();

        // Create the Recording
        restRecordingMockMvc.perform(post("/api/recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recording)))
            .andExpect(status().isCreated());

        // Validate the Recording in the database
        List<Recording> recordingList = recordingRepository.findAll();
        assertThat(recordingList).hasSize(databaseSizeBeforeCreate + 1);
        Recording testRecording = recordingList.get(recordingList.size() - 1);
        assertThat(testRecording.isFerbrile()).isEqualTo(DEFAULT_FERBRILE);
        assertThat(testRecording.isPillar()).isEqualTo(DEFAULT_PILLAR);
        assertThat(testRecording.isDispensys()).isEqualTo(DEFAULT_DISPENSYS);
        assertThat(testRecording.getrR()).isEqualTo(DEFAULT_R_R);
        assertThat(testRecording.isClearLungs()).isEqualTo(DEFAULT_CLEAR_LUNGS);
        assertThat(testRecording.isRonchi()).isEqualTo(DEFAULT_RONCHI);
        assertThat(testRecording.isCrackles()).isEqualTo(DEFAULT_CRACKLES);
        assertThat(testRecording.getOtherRS()).isEqualTo(DEFAULT_OTHER_RS);
        assertThat(testRecording.gethR()).isEqualTo(DEFAULT_H_R);
        assertThat(testRecording.isRegular()).isEqualTo(DEFAULT_REGULAR);
        assertThat(testRecording.isMurmurs()).isEqualTo(DEFAULT_MURMURS);
        assertThat(testRecording.getOtherCVS()).isEqualTo(DEFAULT_OTHER_CVS);
        assertThat(testRecording.isSoftAbdomen()).isEqualTo(DEFAULT_SOFT_ABDOMEN);
        assertThat(testRecording.isTense()).isEqualTo(DEFAULT_TENSE);
        assertThat(testRecording.isTender()).isEqualTo(DEFAULT_TENDER);
        assertThat(testRecording.isNoneTender()).isEqualTo(DEFAULT_NONE_TENDER);
        assertThat(testRecording.getOtherAbdomen()).isEqualTo(DEFAULT_OTHER_ABDOMEN);
        assertThat(testRecording.getNeurology()).isEqualTo(DEFAULT_NEUROLOGY);
        assertThat(testRecording.getOtherSystems()).isEqualTo(DEFAULT_OTHER_SYSTEMS);
        assertThat(testRecording.getRecNo()).isEqualTo(DEFAULT_REC_NO);
        assertThat(testRecording.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createRecordingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recordingRepository.findAll().size();

        // Create the Recording with an existing ID
        recording.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecordingMockMvc.perform(post("/api/recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recording)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Recording> recordingList = recordingRepository.findAll();
        assertThat(recordingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFerbrileIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordingRepository.findAll().size();
        // set the field null
        recording.setFerbrile(null);

        // Create the Recording, which fails.

        restRecordingMockMvc.perform(post("/api/recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recording)))
            .andExpect(status().isBadRequest());

        List<Recording> recordingList = recordingRepository.findAll();
        assertThat(recordingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPillarIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordingRepository.findAll().size();
        // set the field null
        recording.setPillar(null);

        // Create the Recording, which fails.

        restRecordingMockMvc.perform(post("/api/recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recording)))
            .andExpect(status().isBadRequest());

        List<Recording> recordingList = recordingRepository.findAll();
        assertThat(recordingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDispensysIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordingRepository.findAll().size();
        // set the field null
        recording.setDispensys(null);

        // Create the Recording, which fails.

        restRecordingMockMvc.perform(post("/api/recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recording)))
            .andExpect(status().isBadRequest());

        List<Recording> recordingList = recordingRepository.findAll();
        assertThat(recordingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClearLungsIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordingRepository.findAll().size();
        // set the field null
        recording.setClearLungs(null);

        // Create the Recording, which fails.

        restRecordingMockMvc.perform(post("/api/recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recording)))
            .andExpect(status().isBadRequest());

        List<Recording> recordingList = recordingRepository.findAll();
        assertThat(recordingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRonchiIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordingRepository.findAll().size();
        // set the field null
        recording.setRonchi(null);

        // Create the Recording, which fails.

        restRecordingMockMvc.perform(post("/api/recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recording)))
            .andExpect(status().isBadRequest());

        List<Recording> recordingList = recordingRepository.findAll();
        assertThat(recordingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCracklesIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordingRepository.findAll().size();
        // set the field null
        recording.setCrackles(null);

        // Create the Recording, which fails.

        restRecordingMockMvc.perform(post("/api/recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recording)))
            .andExpect(status().isBadRequest());

        List<Recording> recordingList = recordingRepository.findAll();
        assertThat(recordingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegularIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordingRepository.findAll().size();
        // set the field null
        recording.setRegular(null);

        // Create the Recording, which fails.

        restRecordingMockMvc.perform(post("/api/recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recording)))
            .andExpect(status().isBadRequest());

        List<Recording> recordingList = recordingRepository.findAll();
        assertThat(recordingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMurmursIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordingRepository.findAll().size();
        // set the field null
        recording.setMurmurs(null);

        // Create the Recording, which fails.

        restRecordingMockMvc.perform(post("/api/recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recording)))
            .andExpect(status().isBadRequest());

        List<Recording> recordingList = recordingRepository.findAll();
        assertThat(recordingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSoftAbdomenIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordingRepository.findAll().size();
        // set the field null
        recording.setSoftAbdomen(null);

        // Create the Recording, which fails.

        restRecordingMockMvc.perform(post("/api/recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recording)))
            .andExpect(status().isBadRequest());

        List<Recording> recordingList = recordingRepository.findAll();
        assertThat(recordingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTenseIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordingRepository.findAll().size();
        // set the field null
        recording.setTense(null);

        // Create the Recording, which fails.

        restRecordingMockMvc.perform(post("/api/recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recording)))
            .andExpect(status().isBadRequest());

        List<Recording> recordingList = recordingRepository.findAll();
        assertThat(recordingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordingRepository.findAll().size();
        // set the field null
        recording.setTender(null);

        // Create the Recording, which fails.

        restRecordingMockMvc.perform(post("/api/recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recording)))
            .andExpect(status().isBadRequest());

        List<Recording> recordingList = recordingRepository.findAll();
        assertThat(recordingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNoneTenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordingRepository.findAll().size();
        // set the field null
        recording.setNoneTender(null);

        // Create the Recording, which fails.

        restRecordingMockMvc.perform(post("/api/recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recording)))
            .andExpect(status().isBadRequest());

        List<Recording> recordingList = recordingRepository.findAll();
        assertThat(recordingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRecNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordingRepository.findAll().size();
        // set the field null
        recording.setRecNo(null);

        // Create the Recording, which fails.

        restRecordingMockMvc.perform(post("/api/recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recording)))
            .andExpect(status().isBadRequest());

        List<Recording> recordingList = recordingRepository.findAll();
        assertThat(recordingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordingRepository.findAll().size();
        // set the field null
        recording.setDate(null);

        // Create the Recording, which fails.

        restRecordingMockMvc.perform(post("/api/recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recording)))
            .andExpect(status().isBadRequest());

        List<Recording> recordingList = recordingRepository.findAll();
        assertThat(recordingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecordings() throws Exception {
        // Initialize the database
        recordingRepository.saveAndFlush(recording);

        // Get all the recordingList
        restRecordingMockMvc.perform(get("/api/recordings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recording.getId().intValue())))
            .andExpect(jsonPath("$.[*].ferbrile").value(hasItem(DEFAULT_FERBRILE.booleanValue())))
            .andExpect(jsonPath("$.[*].pillar").value(hasItem(DEFAULT_PILLAR.booleanValue())))
            .andExpect(jsonPath("$.[*].dispensys").value(hasItem(DEFAULT_DISPENSYS.booleanValue())))
            .andExpect(jsonPath("$.[*].rR").value(hasItem(DEFAULT_R_R.toString())))
            .andExpect(jsonPath("$.[*].clearLungs").value(hasItem(DEFAULT_CLEAR_LUNGS.booleanValue())))
            .andExpect(jsonPath("$.[*].ronchi").value(hasItem(DEFAULT_RONCHI.booleanValue())))
            .andExpect(jsonPath("$.[*].crackles").value(hasItem(DEFAULT_CRACKLES.booleanValue())))
            .andExpect(jsonPath("$.[*].otherRS").value(hasItem(DEFAULT_OTHER_RS.toString())))
            .andExpect(jsonPath("$.[*].hR").value(hasItem(DEFAULT_H_R.toString())))
            .andExpect(jsonPath("$.[*].regular").value(hasItem(DEFAULT_REGULAR.booleanValue())))
            .andExpect(jsonPath("$.[*].murmurs").value(hasItem(DEFAULT_MURMURS.booleanValue())))
            .andExpect(jsonPath("$.[*].otherCVS").value(hasItem(DEFAULT_OTHER_CVS.toString())))
            .andExpect(jsonPath("$.[*].softAbdomen").value(hasItem(DEFAULT_SOFT_ABDOMEN.booleanValue())))
            .andExpect(jsonPath("$.[*].tense").value(hasItem(DEFAULT_TENSE.booleanValue())))
            .andExpect(jsonPath("$.[*].tender").value(hasItem(DEFAULT_TENDER.booleanValue())))
            .andExpect(jsonPath("$.[*].noneTender").value(hasItem(DEFAULT_NONE_TENDER.booleanValue())))
            .andExpect(jsonPath("$.[*].otherAbdomen").value(hasItem(DEFAULT_OTHER_ABDOMEN.toString())))
            .andExpect(jsonPath("$.[*].neurology").value(hasItem(DEFAULT_NEUROLOGY.toString())))
            .andExpect(jsonPath("$.[*].otherSystems").value(hasItem(DEFAULT_OTHER_SYSTEMS.toString())))
            .andExpect(jsonPath("$.[*].recNo").value(hasItem(DEFAULT_REC_NO.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getRecording() throws Exception {
        // Initialize the database
        recordingRepository.saveAndFlush(recording);

        // Get the recording
        restRecordingMockMvc.perform(get("/api/recordings/{id}", recording.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recording.getId().intValue()))
            .andExpect(jsonPath("$.ferbrile").value(DEFAULT_FERBRILE.booleanValue()))
            .andExpect(jsonPath("$.pillar").value(DEFAULT_PILLAR.booleanValue()))
            .andExpect(jsonPath("$.dispensys").value(DEFAULT_DISPENSYS.booleanValue()))
            .andExpect(jsonPath("$.rR").value(DEFAULT_R_R.toString()))
            .andExpect(jsonPath("$.clearLungs").value(DEFAULT_CLEAR_LUNGS.booleanValue()))
            .andExpect(jsonPath("$.ronchi").value(DEFAULT_RONCHI.booleanValue()))
            .andExpect(jsonPath("$.crackles").value(DEFAULT_CRACKLES.booleanValue()))
            .andExpect(jsonPath("$.otherRS").value(DEFAULT_OTHER_RS.toString()))
            .andExpect(jsonPath("$.hR").value(DEFAULT_H_R.toString()))
            .andExpect(jsonPath("$.regular").value(DEFAULT_REGULAR.booleanValue()))
            .andExpect(jsonPath("$.murmurs").value(DEFAULT_MURMURS.booleanValue()))
            .andExpect(jsonPath("$.otherCVS").value(DEFAULT_OTHER_CVS.toString()))
            .andExpect(jsonPath("$.softAbdomen").value(DEFAULT_SOFT_ABDOMEN.booleanValue()))
            .andExpect(jsonPath("$.tense").value(DEFAULT_TENSE.booleanValue()))
            .andExpect(jsonPath("$.tender").value(DEFAULT_TENDER.booleanValue()))
            .andExpect(jsonPath("$.noneTender").value(DEFAULT_NONE_TENDER.booleanValue()))
            .andExpect(jsonPath("$.otherAbdomen").value(DEFAULT_OTHER_ABDOMEN.toString()))
            .andExpect(jsonPath("$.neurology").value(DEFAULT_NEUROLOGY.toString()))
            .andExpect(jsonPath("$.otherSystems").value(DEFAULT_OTHER_SYSTEMS.toString()))
            .andExpect(jsonPath("$.recNo").value(DEFAULT_REC_NO.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRecording() throws Exception {
        // Get the recording
        restRecordingMockMvc.perform(get("/api/recordings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecording() throws Exception {
        // Initialize the database
        recordingRepository.saveAndFlush(recording);
        int databaseSizeBeforeUpdate = recordingRepository.findAll().size();

        // Update the recording
        Recording updatedRecording = recordingRepository.findOne(recording.getId());
        updatedRecording
            .ferbrile(UPDATED_FERBRILE)
            .pillar(UPDATED_PILLAR)
            .dispensys(UPDATED_DISPENSYS)
            .rR(UPDATED_R_R)
            .clearLungs(UPDATED_CLEAR_LUNGS)
            .ronchi(UPDATED_RONCHI)
            .crackles(UPDATED_CRACKLES)
            .otherRS(UPDATED_OTHER_RS)
            .hR(UPDATED_H_R)
            .regular(UPDATED_REGULAR)
            .murmurs(UPDATED_MURMURS)
            .otherCVS(UPDATED_OTHER_CVS)
            .softAbdomen(UPDATED_SOFT_ABDOMEN)
            .tense(UPDATED_TENSE)
            .tender(UPDATED_TENDER)
            .noneTender(UPDATED_NONE_TENDER)
            .otherAbdomen(UPDATED_OTHER_ABDOMEN)
            .neurology(UPDATED_NEUROLOGY)
            .otherSystems(UPDATED_OTHER_SYSTEMS)
            .recNo(UPDATED_REC_NO)
            .date(UPDATED_DATE);

        restRecordingMockMvc.perform(put("/api/recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecording)))
            .andExpect(status().isOk());

        // Validate the Recording in the database
        List<Recording> recordingList = recordingRepository.findAll();
        assertThat(recordingList).hasSize(databaseSizeBeforeUpdate);
        Recording testRecording = recordingList.get(recordingList.size() - 1);
        assertThat(testRecording.isFerbrile()).isEqualTo(UPDATED_FERBRILE);
        assertThat(testRecording.isPillar()).isEqualTo(UPDATED_PILLAR);
        assertThat(testRecording.isDispensys()).isEqualTo(UPDATED_DISPENSYS);
        assertThat(testRecording.getrR()).isEqualTo(UPDATED_R_R);
        assertThat(testRecording.isClearLungs()).isEqualTo(UPDATED_CLEAR_LUNGS);
        assertThat(testRecording.isRonchi()).isEqualTo(UPDATED_RONCHI);
        assertThat(testRecording.isCrackles()).isEqualTo(UPDATED_CRACKLES);
        assertThat(testRecording.getOtherRS()).isEqualTo(UPDATED_OTHER_RS);
        assertThat(testRecording.gethR()).isEqualTo(UPDATED_H_R);
        assertThat(testRecording.isRegular()).isEqualTo(UPDATED_REGULAR);
        assertThat(testRecording.isMurmurs()).isEqualTo(UPDATED_MURMURS);
        assertThat(testRecording.getOtherCVS()).isEqualTo(UPDATED_OTHER_CVS);
        assertThat(testRecording.isSoftAbdomen()).isEqualTo(UPDATED_SOFT_ABDOMEN);
        assertThat(testRecording.isTense()).isEqualTo(UPDATED_TENSE);
        assertThat(testRecording.isTender()).isEqualTo(UPDATED_TENDER);
        assertThat(testRecording.isNoneTender()).isEqualTo(UPDATED_NONE_TENDER);
        assertThat(testRecording.getOtherAbdomen()).isEqualTo(UPDATED_OTHER_ABDOMEN);
        assertThat(testRecording.getNeurology()).isEqualTo(UPDATED_NEUROLOGY);
        assertThat(testRecording.getOtherSystems()).isEqualTo(UPDATED_OTHER_SYSTEMS);
        assertThat(testRecording.getRecNo()).isEqualTo(UPDATED_REC_NO);
        assertThat(testRecording.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingRecording() throws Exception {
        int databaseSizeBeforeUpdate = recordingRepository.findAll().size();

        // Create the Recording

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRecordingMockMvc.perform(put("/api/recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recording)))
            .andExpect(status().isCreated());

        // Validate the Recording in the database
        List<Recording> recordingList = recordingRepository.findAll();
        assertThat(recordingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRecording() throws Exception {
        // Initialize the database
        recordingRepository.saveAndFlush(recording);
        int databaseSizeBeforeDelete = recordingRepository.findAll().size();

        // Get the recording
        restRecordingMockMvc.perform(delete("/api/recordings/{id}", recording.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Recording> recordingList = recordingRepository.findAll();
        assertThat(recordingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recording.class);
    }
}
