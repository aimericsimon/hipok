package com.happy.hipok.web.rest;

import com.happy.hipok.Application;
import com.happy.hipok.domain.AnatomicZoneRef;
import com.happy.hipok.repository.AnatomicZoneRefRepository;

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
 * Test class for the AnatomicZoneRefResource REST controller.
 *
 * @see AnatomicZoneRefResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AnatomicZoneRefResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAA";
    private static final String UPDATED_LABEL = "BBBBB";

    @Inject
    private AnatomicZoneRefRepository anatomicZoneRefRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAnatomicZoneRefMockMvc;

    private AnatomicZoneRef anatomicZoneRef;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AnatomicZoneRefResource anatomicZoneRefResource = new AnatomicZoneRefResource();
        ReflectionTestUtils.setField(anatomicZoneRefResource, "anatomicZoneRefRepository", anatomicZoneRefRepository);
        this.restAnatomicZoneRefMockMvc = MockMvcBuilders.standaloneSetup(anatomicZoneRefResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        anatomicZoneRef = new AnatomicZoneRef();
        anatomicZoneRef.setLabel(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createAnatomicZoneRef() throws Exception {
        int databaseSizeBeforeCreate = anatomicZoneRefRepository.findAll().size();

        // Create the AnatomicZoneRef

        restAnatomicZoneRefMockMvc.perform(post("/api/anatomicZoneRefs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(anatomicZoneRef)))
                .andExpect(status().isCreated());

        // Validate the AnatomicZoneRef in the database
        List<AnatomicZoneRef> anatomicZoneRefs = anatomicZoneRefRepository.findAll();
        assertThat(anatomicZoneRefs).hasSize(databaseSizeBeforeCreate + 1);
        AnatomicZoneRef testAnatomicZoneRef = anatomicZoneRefs.get(anatomicZoneRefs.size() - 1);
        assertThat(testAnatomicZoneRef.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void getAllAnatomicZoneRefs() throws Exception {
        // Initialize the database
        anatomicZoneRefRepository.saveAndFlush(anatomicZoneRef);

        // Get all the anatomicZoneRefs
        restAnatomicZoneRefMockMvc.perform(get("/api/anatomicZoneRefs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(anatomicZoneRef.getId().intValue())))
                .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }

    @Test
    @Transactional
    public void getAnatomicZoneRef() throws Exception {
        // Initialize the database
        anatomicZoneRefRepository.saveAndFlush(anatomicZoneRef);

        // Get the anatomicZoneRef
        restAnatomicZoneRefMockMvc.perform(get("/api/anatomicZoneRefs/{id}", anatomicZoneRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(anatomicZoneRef.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnatomicZoneRef() throws Exception {
        // Get the anatomicZoneRef
        restAnatomicZoneRefMockMvc.perform(get("/api/anatomicZoneRefs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnatomicZoneRef() throws Exception {
        // Initialize the database
        anatomicZoneRefRepository.saveAndFlush(anatomicZoneRef);

		int databaseSizeBeforeUpdate = anatomicZoneRefRepository.findAll().size();

        // Update the anatomicZoneRef
        anatomicZoneRef.setLabel(UPDATED_LABEL);

        restAnatomicZoneRefMockMvc.perform(put("/api/anatomicZoneRefs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(anatomicZoneRef)))
                .andExpect(status().isOk());

        // Validate the AnatomicZoneRef in the database
        List<AnatomicZoneRef> anatomicZoneRefs = anatomicZoneRefRepository.findAll();
        assertThat(anatomicZoneRefs).hasSize(databaseSizeBeforeUpdate);
        AnatomicZoneRef testAnatomicZoneRef = anatomicZoneRefs.get(anatomicZoneRefs.size() - 1);
        assertThat(testAnatomicZoneRef.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void deleteAnatomicZoneRef() throws Exception {
        // Initialize the database
        anatomicZoneRefRepository.saveAndFlush(anatomicZoneRef);

		int databaseSizeBeforeDelete = anatomicZoneRefRepository.findAll().size();

        // Get the anatomicZoneRef
        restAnatomicZoneRefMockMvc.perform(delete("/api/anatomicZoneRefs/{id}", anatomicZoneRef.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AnatomicZoneRef> anatomicZoneRefs = anatomicZoneRefRepository.findAll();
        assertThat(anatomicZoneRefs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
