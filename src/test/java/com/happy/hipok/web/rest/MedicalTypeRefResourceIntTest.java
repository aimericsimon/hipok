package com.happy.hipok.web.rest;

import com.happy.hipok.Application;
import com.happy.hipok.domain.MedicalTypeRef;
import com.happy.hipok.repository.MedicalTypeRefRepository;

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

import com.happy.hipok.domain.enumeration.MedicalSubType;

/**
 * Test class for the MedicalTypeRefResource REST controller.
 *
 * @see MedicalTypeRefResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MedicalTypeRefResourceIntTest {



private static final MedicalSubType DEFAULT_SUBTYPE = MedicalSubType.MEDICAL;
    private static final MedicalSubType UPDATED_SUBTYPE = MedicalSubType.PARAMEDICAL;
    private static final String DEFAULT_LABEL = "AAAAA";
    private static final String UPDATED_LABEL = "BBBBB";

    @Inject
    private MedicalTypeRefRepository medicalTypeRefRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMedicalTypeRefMockMvc;

    private MedicalTypeRef medicalTypeRef;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MedicalTypeRefResource medicalTypeRefResource = new MedicalTypeRefResource();
        ReflectionTestUtils.setField(medicalTypeRefResource, "medicalTypeRefRepository", medicalTypeRefRepository);
        this.restMedicalTypeRefMockMvc = MockMvcBuilders.standaloneSetup(medicalTypeRefResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        medicalTypeRef = new MedicalTypeRef();
        medicalTypeRef.setSubtype(DEFAULT_SUBTYPE);
        medicalTypeRef.setLabel(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createMedicalTypeRef() throws Exception {
        int databaseSizeBeforeCreate = medicalTypeRefRepository.findAll().size();

        // Create the MedicalTypeRef

        restMedicalTypeRefMockMvc.perform(post("/api/medicalTypeRefs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(medicalTypeRef)))
                .andExpect(status().isCreated());

        // Validate the MedicalTypeRef in the database
        List<MedicalTypeRef> medicalTypeRefs = medicalTypeRefRepository.findAll();
        assertThat(medicalTypeRefs).hasSize(databaseSizeBeforeCreate + 1);
        MedicalTypeRef testMedicalTypeRef = medicalTypeRefs.get(medicalTypeRefs.size() - 1);
        assertThat(testMedicalTypeRef.getSubtype()).isEqualTo(DEFAULT_SUBTYPE);
        assertThat(testMedicalTypeRef.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void getAllMedicalTypeRefs() throws Exception {
        // Initialize the database
        medicalTypeRefRepository.saveAndFlush(medicalTypeRef);

        // Get all the medicalTypeRefs
        restMedicalTypeRefMockMvc.perform(get("/api/medicalTypeRefs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(medicalTypeRef.getId().intValue())))
                .andExpect(jsonPath("$.[*].subtype").value(hasItem(DEFAULT_SUBTYPE.toString())))
                .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }

    @Test
    @Transactional
    public void getMedicalTypeRef() throws Exception {
        // Initialize the database
        medicalTypeRefRepository.saveAndFlush(medicalTypeRef);

        // Get the medicalTypeRef
        restMedicalTypeRefMockMvc.perform(get("/api/medicalTypeRefs/{id}", medicalTypeRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(medicalTypeRef.getId().intValue()))
            .andExpect(jsonPath("$.subtype").value(DEFAULT_SUBTYPE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMedicalTypeRef() throws Exception {
        // Get the medicalTypeRef
        restMedicalTypeRefMockMvc.perform(get("/api/medicalTypeRefs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalTypeRef() throws Exception {
        // Initialize the database
        medicalTypeRefRepository.saveAndFlush(medicalTypeRef);

		int databaseSizeBeforeUpdate = medicalTypeRefRepository.findAll().size();

        // Update the medicalTypeRef
        medicalTypeRef.setSubtype(UPDATED_SUBTYPE);
        medicalTypeRef.setLabel(UPDATED_LABEL);

        restMedicalTypeRefMockMvc.perform(put("/api/medicalTypeRefs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(medicalTypeRef)))
                .andExpect(status().isOk());

        // Validate the MedicalTypeRef in the database
        List<MedicalTypeRef> medicalTypeRefs = medicalTypeRefRepository.findAll();
        assertThat(medicalTypeRefs).hasSize(databaseSizeBeforeUpdate);
        MedicalTypeRef testMedicalTypeRef = medicalTypeRefs.get(medicalTypeRefs.size() - 1);
        assertThat(testMedicalTypeRef.getSubtype()).isEqualTo(UPDATED_SUBTYPE);
        assertThat(testMedicalTypeRef.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void deleteMedicalTypeRef() throws Exception {
        // Initialize the database
        medicalTypeRefRepository.saveAndFlush(medicalTypeRef);

		int databaseSizeBeforeDelete = medicalTypeRefRepository.findAll().size();

        // Get the medicalTypeRef
        restMedicalTypeRefMockMvc.perform(delete("/api/medicalTypeRefs/{id}", medicalTypeRef.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MedicalTypeRef> medicalTypeRefs = medicalTypeRefRepository.findAll();
        assertThat(medicalTypeRefs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
