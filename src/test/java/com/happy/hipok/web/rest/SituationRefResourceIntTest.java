package com.happy.hipok.web.rest;

import com.happy.hipok.Application;
import com.happy.hipok.domain.SituationRef;
import com.happy.hipok.repository.SituationRefRepository;

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
 * Test class for the SituationRefResource REST controller.
 *
 * @see SituationRefResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SituationRefResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAA";
    private static final String UPDATED_LABEL = "BBBBB";
    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    @Inject
    private SituationRefRepository situationRefRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSituationRefMockMvc;

    private SituationRef situationRef;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SituationRefResource situationRefResource = new SituationRefResource();
        ReflectionTestUtils.setField(situationRefResource, "situationRefRepository", situationRefRepository);
        this.restSituationRefMockMvc = MockMvcBuilders.standaloneSetup(situationRefResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        situationRef = new SituationRef();
        situationRef.setLabel(DEFAULT_LABEL);
        situationRef.setCode(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createSituationRef() throws Exception {
        int databaseSizeBeforeCreate = situationRefRepository.findAll().size();

        // Create the SituationRef

        restSituationRefMockMvc.perform(post("/api/situationRefs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(situationRef)))
                .andExpect(status().isCreated());

        // Validate the SituationRef in the database
        List<SituationRef> situationRefs = situationRefRepository.findAll();
        assertThat(situationRefs).hasSize(databaseSizeBeforeCreate + 1);
        SituationRef testSituationRef = situationRefs.get(situationRefs.size() - 1);
        assertThat(testSituationRef.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testSituationRef.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void getAllSituationRefs() throws Exception {
        // Initialize the database
        situationRefRepository.saveAndFlush(situationRef);

        // Get all the situationRefs
        restSituationRefMockMvc.perform(get("/api/situationRefs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(situationRef.getId().intValue())))
                .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getSituationRef() throws Exception {
        // Initialize the database
        situationRefRepository.saveAndFlush(situationRef);

        // Get the situationRef
        restSituationRefMockMvc.perform(get("/api/situationRefs/{id}", situationRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(situationRef.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSituationRef() throws Exception {
        // Get the situationRef
        restSituationRefMockMvc.perform(get("/api/situationRefs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSituationRef() throws Exception {
        // Initialize the database
        situationRefRepository.saveAndFlush(situationRef);

		int databaseSizeBeforeUpdate = situationRefRepository.findAll().size();

        // Update the situationRef
        situationRef.setLabel(UPDATED_LABEL);
        situationRef.setCode(UPDATED_CODE);

        restSituationRefMockMvc.perform(put("/api/situationRefs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(situationRef)))
                .andExpect(status().isOk());

        // Validate the SituationRef in the database
        List<SituationRef> situationRefs = situationRefRepository.findAll();
        assertThat(situationRefs).hasSize(databaseSizeBeforeUpdate);
        SituationRef testSituationRef = situationRefs.get(situationRefs.size() - 1);
        assertThat(testSituationRef.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testSituationRef.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void deleteSituationRef() throws Exception {
        // Initialize the database
        situationRefRepository.saveAndFlush(situationRef);

		int databaseSizeBeforeDelete = situationRefRepository.findAll().size();

        // Get the situationRef
        restSituationRefMockMvc.perform(delete("/api/situationRefs/{id}", situationRef.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SituationRef> situationRefs = situationRefRepository.findAll();
        assertThat(situationRefs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
