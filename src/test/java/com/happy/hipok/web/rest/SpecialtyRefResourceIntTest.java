package com.happy.hipok.web.rest;

import com.happy.hipok.Application;
import com.happy.hipok.domain.SpecialtyRef;
import com.happy.hipok.repository.SpecialtyRefRepository;

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
 * Test class for the SpecialtyRefResource REST controller.
 *
 * @see SpecialtyRefResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SpecialtyRefResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAA";
    private static final String UPDATED_LABEL = "BBBBB";

    @Inject
    private SpecialtyRefRepository specialtyRefRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSpecialtyRefMockMvc;

    private SpecialtyRef specialtyRef;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SpecialtyRefResource specialtyRefResource = new SpecialtyRefResource();
        ReflectionTestUtils.setField(specialtyRefResource, "specialtyRefRepository", specialtyRefRepository);
        this.restSpecialtyRefMockMvc = MockMvcBuilders.standaloneSetup(specialtyRefResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        specialtyRef = new SpecialtyRef();
        specialtyRef.setLabel(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createSpecialtyRef() throws Exception {
        int databaseSizeBeforeCreate = specialtyRefRepository.findAll().size();

        // Create the SpecialtyRef

        restSpecialtyRefMockMvc.perform(post("/api/specialtyRefs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(specialtyRef)))
                .andExpect(status().isCreated());

        // Validate the SpecialtyRef in the database
        List<SpecialtyRef> specialtyRefs = specialtyRefRepository.findAll();
        assertThat(specialtyRefs).hasSize(databaseSizeBeforeCreate + 1);
        SpecialtyRef testSpecialtyRef = specialtyRefs.get(specialtyRefs.size() - 1);
        assertThat(testSpecialtyRef.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void getAllSpecialtyRefs() throws Exception {
        // Initialize the database
        specialtyRefRepository.saveAndFlush(specialtyRef);

        // Get all the specialtyRefs
        restSpecialtyRefMockMvc.perform(get("/api/specialtyRefs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(specialtyRef.getId().intValue())))
                .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }

    @Test
    @Transactional
    public void getSpecialtyRef() throws Exception {
        // Initialize the database
        specialtyRefRepository.saveAndFlush(specialtyRef);

        // Get the specialtyRef
        restSpecialtyRefMockMvc.perform(get("/api/specialtyRefs/{id}", specialtyRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(specialtyRef.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSpecialtyRef() throws Exception {
        // Get the specialtyRef
        restSpecialtyRefMockMvc.perform(get("/api/specialtyRefs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpecialtyRef() throws Exception {
        // Initialize the database
        specialtyRefRepository.saveAndFlush(specialtyRef);

		int databaseSizeBeforeUpdate = specialtyRefRepository.findAll().size();

        // Update the specialtyRef
        specialtyRef.setLabel(UPDATED_LABEL);

        restSpecialtyRefMockMvc.perform(put("/api/specialtyRefs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(specialtyRef)))
                .andExpect(status().isOk());

        // Validate the SpecialtyRef in the database
        List<SpecialtyRef> specialtyRefs = specialtyRefRepository.findAll();
        assertThat(specialtyRefs).hasSize(databaseSizeBeforeUpdate);
        SpecialtyRef testSpecialtyRef = specialtyRefs.get(specialtyRefs.size() - 1);
        assertThat(testSpecialtyRef.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void deleteSpecialtyRef() throws Exception {
        // Initialize the database
        specialtyRefRepository.saveAndFlush(specialtyRef);

		int databaseSizeBeforeDelete = specialtyRefRepository.findAll().size();

        // Get the specialtyRef
        restSpecialtyRefMockMvc.perform(delete("/api/specialtyRefs/{id}", specialtyRef.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SpecialtyRef> specialtyRefs = specialtyRefRepository.findAll();
        assertThat(specialtyRefs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
