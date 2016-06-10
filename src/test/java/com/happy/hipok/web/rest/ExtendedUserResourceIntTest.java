package com.happy.hipok.web.rest;

import com.happy.hipok.Application;
import com.happy.hipok.domain.ExtendedUser;
import com.happy.hipok.domain.TitleRef;
import com.happy.hipok.repository.ExtendedUserRepository;
import com.happy.hipok.web.rest.dto.ExtendedUserDTO;
import com.happy.hipok.web.rest.mapper.ExtendedUserMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.happy.hipok.domain.enumeration.Sex;

/**
 * Test class for the ExtendedUserResource REST controller.
 *
 * @see ExtendedUserResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ExtendedUserResourceIntTest {


    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());


private static final Sex DEFAULT_SEX = Sex.M;
    private static final Sex UPDATED_SEX = Sex.F;
    private static final String DEFAULT_PRACTICE_LOCATION = "AAAAA";
    private static final String UPDATED_PRACTICE_LOCATION = "BBBBB";
    private static final String DEFAULT_ADELI_NUMBER = "AAAAA";
    private static final String UPDATED_ADELI_NUMBER = "BBBBB";

    @Inject
    private ExtendedUserRepository extendedUserRepository;

    @Inject
    private ExtendedUserMapper extendedUserMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restExtendedUserMockMvc;

    private ExtendedUser extendedUser;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExtendedUserResource extendedUserResource = new ExtendedUserResource();
        ReflectionTestUtils.setField(extendedUserResource, "extendedUserRepository", extendedUserRepository);
        ReflectionTestUtils.setField(extendedUserResource, "extendedUserMapper", extendedUserMapper);
        this.restExtendedUserMockMvc = MockMvcBuilders.standaloneSetup(extendedUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        extendedUser = new ExtendedUser();
        extendedUser.setBirthDate(DEFAULT_BIRTH_DATE);
        extendedUser.setSex(DEFAULT_SEX);
        extendedUser.setPracticeLocation(DEFAULT_PRACTICE_LOCATION);
        extendedUser.setAdeliNumber(DEFAULT_ADELI_NUMBER);
        TitleRef titleRef = new TitleRef();
        titleRef.setId(1L);
        titleRef.setCode("M");
        extendedUser.setTitleRef(titleRef);
    }

    @Test
    @Transactional
    public void createExtendedUser() throws Exception {
        int databaseSizeBeforeCreate = extendedUserRepository.findAll().size();

        // Create the ExtendedUser
        ExtendedUserDTO extendedUserDTO = extendedUserMapper.extendedUserToExtendedUserDTO(extendedUser);

        restExtendedUserMockMvc.perform(post("/api/extendedUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(extendedUserDTO)))
                .andExpect(status().isCreated());

        // Validate the ExtendedUser in the database
        List<ExtendedUser> extendedUsers = extendedUserRepository.findAll();
        assertThat(extendedUsers).hasSize(databaseSizeBeforeCreate + 1);
        ExtendedUser testExtendedUser = extendedUsers.get(extendedUsers.size() - 1);
        assertThat(testExtendedUser.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testExtendedUser.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testExtendedUser.getPracticeLocation()).isEqualTo(DEFAULT_PRACTICE_LOCATION);
        assertThat(testExtendedUser.getAdeliNumber()).isEqualTo(DEFAULT_ADELI_NUMBER);
    }

    @Test
    @Transactional
    public void getAllExtendedUsers() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUsers
        restExtendedUserMockMvc.perform(get("/api/extendedUsers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(extendedUser.getId().intValue())))
                .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
                .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
                .andExpect(jsonPath("$.[*].practiceLocation").value(hasItem(DEFAULT_PRACTICE_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].adeliNumber").value(hasItem(DEFAULT_ADELI_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getExtendedUser() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get the extendedUser
        restExtendedUserMockMvc.perform(get("/api/extendedUsers/{id}", extendedUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(extendedUser.getId().intValue()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.practiceLocation").value(DEFAULT_PRACTICE_LOCATION.toString()))
            .andExpect(jsonPath("$.adeliNumber").value(DEFAULT_ADELI_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExtendedUser() throws Exception {
        // Get the extendedUser
        restExtendedUserMockMvc.perform(get("/api/extendedUsers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExtendedUser() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

		int databaseSizeBeforeUpdate = extendedUserRepository.findAll().size();

        // Update the extendedUser
        extendedUser.setBirthDate(UPDATED_BIRTH_DATE);
        extendedUser.setSex(UPDATED_SEX);
        extendedUser.setPracticeLocation(UPDATED_PRACTICE_LOCATION);
        extendedUser.setAdeliNumber(UPDATED_ADELI_NUMBER);
        ExtendedUserDTO extendedUserDTO = extendedUserMapper.extendedUserToExtendedUserDTO(extendedUser);

        restExtendedUserMockMvc.perform(put("/api/extendedUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(extendedUserDTO)))
                .andExpect(status().isOk());

        // Validate the ExtendedUser in the database
        List<ExtendedUser> extendedUsers = extendedUserRepository.findAll();
        assertThat(extendedUsers).hasSize(databaseSizeBeforeUpdate);
        ExtendedUser testExtendedUser = extendedUsers.get(extendedUsers.size() - 1);
        assertThat(testExtendedUser.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testExtendedUser.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testExtendedUser.getPracticeLocation()).isEqualTo(UPDATED_PRACTICE_LOCATION);
        assertThat(testExtendedUser.getAdeliNumber()).isEqualTo(UPDATED_ADELI_NUMBER);
    }

    @Test
    @Transactional
    public void deleteExtendedUser() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

		int databaseSizeBeforeDelete = extendedUserRepository.findAll().size();

        // Get the extendedUser
        restExtendedUserMockMvc.perform(delete("/api/extendedUsers/{id}", extendedUser.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ExtendedUser> extendedUsers = extendedUserRepository.findAll();
        assertThat(extendedUsers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
