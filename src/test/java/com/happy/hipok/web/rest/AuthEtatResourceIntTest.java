package com.happy.hipok.web.rest;

import com.happy.hipok.Application;
import com.happy.hipok.domain.AuthEtat;
import com.happy.hipok.repository.AuthEtatRepository;
import com.happy.hipok.web.rest.dto.AuthEtatDTO;
import com.happy.hipok.web.rest.mapper.AuthEtatMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the AuthEtatResource REST controller.
 *
 * @see AuthEtatResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AuthEtatResourceIntTest {


    private static final Long DEFAULT_ID_PROFILE = 1L;
    private static final Long UPDATED_ID_PROFILE = 2L;

    private static final Long DEFAULT_ID_IMAGE_AUTH = 1L;
    private static final Long UPDATED_ID_IMAGE_AUTH = 2L;

    private static final Boolean DEFAULT_ETAT = false;
    private static final Boolean UPDATED_ETAT = true;

    @Inject
    private AuthEtatRepository authEtatRepository;

    @Inject
    private AuthEtatMapper authEtatMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAuthEtatMockMvc;

    private AuthEtat authEtat;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AuthEtatResource authEtatResource = new AuthEtatResource();
        ReflectionTestUtils.setField(authEtatResource, "authEtatRepository", authEtatRepository);
        ReflectionTestUtils.setField(authEtatResource, "authEtatMapper", authEtatMapper);
        this.restAuthEtatMockMvc = MockMvcBuilders.standaloneSetup(authEtatResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        authEtat = new AuthEtat();
        authEtat.setIdProfile(DEFAULT_ID_PROFILE);
        authEtat.setIdImageAuth(DEFAULT_ID_IMAGE_AUTH);
        authEtat.setEtat(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    public void createAuthEtat() throws Exception {
        int databaseSizeBeforeCreate = authEtatRepository.findAll().size();

        // Create the AuthEtat
        AuthEtatDTO authEtatDTO = authEtatMapper.authEtatToAuthEtatDTO(authEtat);

        restAuthEtatMockMvc.perform(post("/api/auth-etats")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(authEtatDTO)))
                .andExpect(status().isCreated());

        // Validate the AuthEtat in the database
        List<AuthEtat> authEtats = authEtatRepository.findAll();
        assertThat(authEtats).hasSize(databaseSizeBeforeCreate + 1);
        AuthEtat testAuthEtat = authEtats.get(authEtats.size() - 1);
        assertThat(testAuthEtat.getIdProfile()).isEqualTo(DEFAULT_ID_PROFILE);
        assertThat(testAuthEtat.getIdImageAuth()).isEqualTo(DEFAULT_ID_IMAGE_AUTH);
        assertThat(testAuthEtat.isEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    public void checkIdProfileIsRequired() throws Exception {
        int databaseSizeBeforeTest = authEtatRepository.findAll().size();
        // set the field null
        authEtat.setIdProfile(null);

        // Create the AuthEtat, which fails.
        AuthEtatDTO authEtatDTO = authEtatMapper.authEtatToAuthEtatDTO(authEtat);

        restAuthEtatMockMvc.perform(post("/api/auth-etats")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(authEtatDTO)))
                .andExpect(status().isBadRequest());

        List<AuthEtat> authEtats = authEtatRepository.findAll();
        assertThat(authEtats).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdImageAuthIsRequired() throws Exception {
        int databaseSizeBeforeTest = authEtatRepository.findAll().size();
        // set the field null
        authEtat.setIdImageAuth(null);

        // Create the AuthEtat, which fails.
        AuthEtatDTO authEtatDTO = authEtatMapper.authEtatToAuthEtatDTO(authEtat);

        restAuthEtatMockMvc.perform(post("/api/auth-etats")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(authEtatDTO)))
                .andExpect(status().isBadRequest());

        List<AuthEtat> authEtats = authEtatRepository.findAll();
        assertThat(authEtats).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAuthEtats() throws Exception {
        // Initialize the database
        authEtatRepository.saveAndFlush(authEtat);

        // Get all the authEtats
        restAuthEtatMockMvc.perform(get("/api/auth-etats?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(authEtat.getId().intValue())))
                .andExpect(jsonPath("$.[*].idProfile").value(hasItem(DEFAULT_ID_PROFILE.intValue())))
                .andExpect(jsonPath("$.[*].idImageAuth").value(hasItem(DEFAULT_ID_IMAGE_AUTH.intValue())))
                .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.booleanValue())));
    }

    @Test
    @Transactional
    public void getAuthEtat() throws Exception {
        // Initialize the database
        authEtatRepository.saveAndFlush(authEtat);

        // Get the authEtat
        restAuthEtatMockMvc.perform(get("/api/auth-etats/{id}", authEtat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(authEtat.getId().intValue()))
            .andExpect(jsonPath("$.idProfile").value(DEFAULT_ID_PROFILE.intValue()))
            .andExpect(jsonPath("$.idImageAuth").value(DEFAULT_ID_IMAGE_AUTH.intValue()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAuthEtat() throws Exception {
        // Get the authEtat
        restAuthEtatMockMvc.perform(get("/api/auth-etats/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuthEtat() throws Exception {
        // Initialize the database
        authEtatRepository.saveAndFlush(authEtat);
        int databaseSizeBeforeUpdate = authEtatRepository.findAll().size();

        // Update the authEtat
        AuthEtat updatedAuthEtat = new AuthEtat();
        updatedAuthEtat.setId(authEtat.getId());
        updatedAuthEtat.setIdProfile(UPDATED_ID_PROFILE);
        updatedAuthEtat.setIdImageAuth(UPDATED_ID_IMAGE_AUTH);
        updatedAuthEtat.setEtat(UPDATED_ETAT);
        AuthEtatDTO authEtatDTO = authEtatMapper.authEtatToAuthEtatDTO(updatedAuthEtat);

        restAuthEtatMockMvc.perform(put("/api/auth-etats")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(authEtatDTO)))
                .andExpect(status().isOk());

        // Validate the AuthEtat in the database
        List<AuthEtat> authEtats = authEtatRepository.findAll();
        assertThat(authEtats).hasSize(databaseSizeBeforeUpdate);
        AuthEtat testAuthEtat = authEtats.get(authEtats.size() - 1);
        assertThat(testAuthEtat.getIdProfile()).isEqualTo(UPDATED_ID_PROFILE);
        assertThat(testAuthEtat.getIdImageAuth()).isEqualTo(UPDATED_ID_IMAGE_AUTH);
        assertThat(testAuthEtat.isEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void deleteAuthEtat() throws Exception {
        // Initialize the database
        authEtatRepository.saveAndFlush(authEtat);
        int databaseSizeBeforeDelete = authEtatRepository.findAll().size();

        // Get the authEtat
        restAuthEtatMockMvc.perform(delete("/api/auth-etats/{id}", authEtat.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AuthEtat> authEtats = authEtatRepository.findAll();
        assertThat(authEtats).hasSize(databaseSizeBeforeDelete - 1);
    }
}
