package com.mycompany.cpms.web.rest;

import com.mycompany.cpms.CpmsApp;

import com.mycompany.cpms.domain.Userinfo;
import com.mycompany.cpms.domain.User;
import com.mycompany.cpms.repository.UserinfoRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.cpms.domain.enumeration.GenderEnum;
/**
 * Test class for the UserinfoResource REST controller.
 *
 * @see UserinfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CpmsApp.class)
public class UserinfoResourceIntTest {

    private static final String DEFAULT_REG_NO = "AAAAAAAAAA";
    private static final String UPDATED_REG_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final GenderEnum DEFAULT_GENDER = GenderEnum.Male;
    private static final GenderEnum UPDATED_GENDER = GenderEnum.Female;

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    @Autowired
    private UserinfoRepository userinfoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserinfoMockMvc;

    private Userinfo userinfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserinfoResource userinfoResource = new UserinfoResource(userinfoRepository);
        this.restUserinfoMockMvc = MockMvcBuilders.standaloneSetup(userinfoResource)
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
    public static Userinfo createEntity(EntityManager em) {
        Userinfo userinfo = new Userinfo()
            .regNo(DEFAULT_REG_NO)
            .dob(DEFAULT_DOB)
            .address(DEFAULT_ADDRESS)
            .gender(DEFAULT_GENDER)
            .telephone(DEFAULT_TELEPHONE)
            .mobile(DEFAULT_MOBILE);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        userinfo.setUser(user);
        return userinfo;
    }

    @Before
    public void initTest() {
        userinfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserinfo() throws Exception {
        int databaseSizeBeforeCreate = userinfoRepository.findAll().size();

        // Create the Userinfo
        restUserinfoMockMvc.perform(post("/api/userinfos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userinfo)))
            .andExpect(status().isCreated());

        // Validate the Userinfo in the database
        List<Userinfo> userinfoList = userinfoRepository.findAll();
        assertThat(userinfoList).hasSize(databaseSizeBeforeCreate + 1);
        Userinfo testUserinfo = userinfoList.get(userinfoList.size() - 1);
        assertThat(testUserinfo.getRegNo()).isEqualTo(DEFAULT_REG_NO);
        assertThat(testUserinfo.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testUserinfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testUserinfo.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testUserinfo.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testUserinfo.getMobile()).isEqualTo(DEFAULT_MOBILE);
    }

    @Test
    @Transactional
    public void createUserinfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userinfoRepository.findAll().size();

        // Create the Userinfo with an existing ID
        userinfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserinfoMockMvc.perform(post("/api/userinfos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userinfo)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Userinfo> userinfoList = userinfoRepository.findAll();
        assertThat(userinfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRegNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = userinfoRepository.findAll().size();
        // set the field null
        userinfo.setRegNo(null);

        // Create the Userinfo, which fails.

        restUserinfoMockMvc.perform(post("/api/userinfos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userinfo)))
            .andExpect(status().isBadRequest());

        List<Userinfo> userinfoList = userinfoRepository.findAll();
        assertThat(userinfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = userinfoRepository.findAll().size();
        // set the field null
        userinfo.setAddress(null);

        // Create the Userinfo, which fails.

        restUserinfoMockMvc.perform(post("/api/userinfos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userinfo)))
            .andExpect(status().isBadRequest());

        List<Userinfo> userinfoList = userinfoRepository.findAll();
        assertThat(userinfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = userinfoRepository.findAll().size();
        // set the field null
        userinfo.setGender(null);

        // Create the Userinfo, which fails.

        restUserinfoMockMvc.perform(post("/api/userinfos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userinfo)))
            .andExpect(status().isBadRequest());

        List<Userinfo> userinfoList = userinfoRepository.findAll();
        assertThat(userinfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = userinfoRepository.findAll().size();
        // set the field null
        userinfo.setTelephone(null);

        // Create the Userinfo, which fails.

        restUserinfoMockMvc.perform(post("/api/userinfos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userinfo)))
            .andExpect(status().isBadRequest());

        List<Userinfo> userinfoList = userinfoRepository.findAll();
        assertThat(userinfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserinfos() throws Exception {
        // Initialize the database
        userinfoRepository.saveAndFlush(userinfo);

        // Get all the userinfoList
        restUserinfoMockMvc.perform(get("/api/userinfos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userinfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].regNo").value(hasItem(DEFAULT_REG_NO.toString())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.toString())));
    }

    @Test
    @Transactional
    public void getUserinfo() throws Exception {
        // Initialize the database
        userinfoRepository.saveAndFlush(userinfo);

        // Get the userinfo
        restUserinfoMockMvc.perform(get("/api/userinfos/{id}", userinfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userinfo.getId().intValue()))
            .andExpect(jsonPath("$.regNo").value(DEFAULT_REG_NO.toString()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserinfo() throws Exception {
        // Get the userinfo
        restUserinfoMockMvc.perform(get("/api/userinfos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserinfo() throws Exception {
        // Initialize the database
        userinfoRepository.saveAndFlush(userinfo);
        int databaseSizeBeforeUpdate = userinfoRepository.findAll().size();

        // Update the userinfo
        Userinfo updatedUserinfo = userinfoRepository.findOne(userinfo.getId());
        updatedUserinfo
            .regNo(UPDATED_REG_NO)
            .dob(UPDATED_DOB)
            .address(UPDATED_ADDRESS)
            .gender(UPDATED_GENDER)
            .telephone(UPDATED_TELEPHONE)
            .mobile(UPDATED_MOBILE);

        restUserinfoMockMvc.perform(put("/api/userinfos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserinfo)))
            .andExpect(status().isOk());

        // Validate the Userinfo in the database
        List<Userinfo> userinfoList = userinfoRepository.findAll();
        assertThat(userinfoList).hasSize(databaseSizeBeforeUpdate);
        Userinfo testUserinfo = userinfoList.get(userinfoList.size() - 1);
        assertThat(testUserinfo.getRegNo()).isEqualTo(UPDATED_REG_NO);
        assertThat(testUserinfo.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testUserinfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testUserinfo.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testUserinfo.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testUserinfo.getMobile()).isEqualTo(UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserinfo() throws Exception {
        int databaseSizeBeforeUpdate = userinfoRepository.findAll().size();

        // Create the Userinfo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserinfoMockMvc.perform(put("/api/userinfos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userinfo)))
            .andExpect(status().isCreated());

        // Validate the Userinfo in the database
        List<Userinfo> userinfoList = userinfoRepository.findAll();
        assertThat(userinfoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserinfo() throws Exception {
        // Initialize the database
        userinfoRepository.saveAndFlush(userinfo);
        int databaseSizeBeforeDelete = userinfoRepository.findAll().size();

        // Get the userinfo
        restUserinfoMockMvc.perform(delete("/api/userinfos/{id}", userinfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Userinfo> userinfoList = userinfoRepository.findAll();
        assertThat(userinfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Userinfo.class);
    }
}
