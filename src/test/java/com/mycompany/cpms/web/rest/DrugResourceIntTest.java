package com.mycompany.cpms.web.rest;

import com.mycompany.cpms.CpmsApp;

import com.mycompany.cpms.domain.Drug;
import com.mycompany.cpms.repository.DrugRepository;
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

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DrugResource REST controller.
 *
 * @see DrugResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CpmsApp.class)
public class DrugResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AVAILABLE = false;
    private static final Boolean UPDATED_AVAILABLE = true;

    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDrugMockMvc;

    private Drug drug;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DrugResource drugResource = new DrugResource(drugRepository);
        this.restDrugMockMvc = MockMvcBuilders.standaloneSetup(drugResource)
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
    public static Drug createEntity(EntityManager em) {
        Drug drug = new Drug()
            .name(DEFAULT_NAME)
            .available(DEFAULT_AVAILABLE);
        return drug;
    }

    @Before
    public void initTest() {
        drug = createEntity(em);
    }

    @Test
    @Transactional
    public void createDrug() throws Exception {
        int databaseSizeBeforeCreate = drugRepository.findAll().size();

        // Create the Drug
        restDrugMockMvc.perform(post("/api/drugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drug)))
            .andExpect(status().isCreated());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeCreate + 1);
        Drug testDrug = drugList.get(drugList.size() - 1);
        assertThat(testDrug.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDrug.isAvailable()).isEqualTo(DEFAULT_AVAILABLE);
    }

    @Test
    @Transactional
    public void createDrugWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = drugRepository.findAll().size();

        // Create the Drug with an existing ID
        drug.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDrugMockMvc.perform(post("/api/drugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drug)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = drugRepository.findAll().size();
        // set the field null
        drug.setName(null);

        // Create the Drug, which fails.

        restDrugMockMvc.perform(post("/api/drugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drug)))
            .andExpect(status().isBadRequest());

        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAvailableIsRequired() throws Exception {
        int databaseSizeBeforeTest = drugRepository.findAll().size();
        // set the field null
        drug.setAvailable(null);

        // Create the Drug, which fails.

        restDrugMockMvc.perform(post("/api/drugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drug)))
            .andExpect(status().isBadRequest());

        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDrugs() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList
        restDrugMockMvc.perform(get("/api/drugs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drug.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE.booleanValue())));
    }

    @Test
    @Transactional
    public void getDrug() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get the drug
        restDrugMockMvc.perform(get("/api/drugs/{id}", drug.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(drug.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.available").value(DEFAULT_AVAILABLE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDrug() throws Exception {
        // Get the drug
        restDrugMockMvc.perform(get("/api/drugs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrug() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);
        int databaseSizeBeforeUpdate = drugRepository.findAll().size();

        // Update the drug
        Drug updatedDrug = drugRepository.findOne(drug.getId());
        updatedDrug
            .name(UPDATED_NAME)
            .available(UPDATED_AVAILABLE);

        restDrugMockMvc.perform(put("/api/drugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDrug)))
            .andExpect(status().isOk());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeUpdate);
        Drug testDrug = drugList.get(drugList.size() - 1);
        assertThat(testDrug.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDrug.isAvailable()).isEqualTo(UPDATED_AVAILABLE);
    }

    @Test
    @Transactional
    public void updateNonExistingDrug() throws Exception {
        int databaseSizeBeforeUpdate = drugRepository.findAll().size();

        // Create the Drug

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDrugMockMvc.perform(put("/api/drugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drug)))
            .andExpect(status().isCreated());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDrug() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);
        int databaseSizeBeforeDelete = drugRepository.findAll().size();

        // Get the drug
        restDrugMockMvc.perform(delete("/api/drugs/{id}", drug.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Drug.class);
    }
}
