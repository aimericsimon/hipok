package com.happy.hipok.web.rest;

import com.happy.hipok.Application;
import com.happy.hipok.domain.TitleRef;
import com.happy.hipok.repository.TitleRefRepository;

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
 * Test class for the TitleRefResource REST controller.
 *
 * @see TitleRefResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TitleRefResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAA";
    private static final String UPDATED_LABEL = "BBBBB";
    private static final String DEFAULT_ABBREVIATION = "AAAAA";
    private static final String UPDATED_ABBREVIATION = "BBBBB";
    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    @Inject
    private TitleRefRepository titleRefRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTitleRefMockMvc;

    private TitleRef titleRef;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TitleRefResource titleRefResource = new TitleRefResource();
        ReflectionTestUtils.setField(titleRefResource, "titleRefRepository", titleRefRepository);
        this.restTitleRefMockMvc = MockMvcBuilders.standaloneSetup(titleRefResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        titleRef = new TitleRef();
        titleRef.setLabel(DEFAULT_LABEL);
        titleRef.setAbbreviation(DEFAULT_ABBREVIATION);
        titleRef.setCode(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createTitleRef() throws Exception {
        int databaseSizeBeforeCreate = titleRefRepository.findAll().size();

        // Create the TitleRef

        restTitleRefMockMvc.perform(post("/api/titleRefs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(titleRef)))
                .andExpect(status().isCreated());

        // Validate the TitleRef in the database
        List<TitleRef> titleRefs = titleRefRepository.findAll();
        assertThat(titleRefs).hasSize(databaseSizeBeforeCreate + 1);
        TitleRef testTitleRef = titleRefs.get(titleRefs.size() - 1);
        assertThat(testTitleRef.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testTitleRef.getAbbreviation()).isEqualTo(DEFAULT_ABBREVIATION);
        assertThat(testTitleRef.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void getAllTitleRefs() throws Exception {
        // Initialize the database
        titleRefRepository.saveAndFlush(titleRef);

        // Get all the titleRefs
        restTitleRefMockMvc.perform(get("/api/titleRefs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(titleRef.getId().intValue())))
                .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
                .andExpect(jsonPath("$.[*].abbreviation").value(hasItem(DEFAULT_ABBREVIATION.toString())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getTitleRef() throws Exception {
        // Initialize the database
        titleRefRepository.saveAndFlush(titleRef);

        // Get the titleRef
        restTitleRefMockMvc.perform(get("/api/titleRefs/{id}", titleRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(titleRef.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.abbreviation").value(DEFAULT_ABBREVIATION.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTitleRef() throws Exception {
        // Get the titleRef
        restTitleRefMockMvc.perform(get("/api/titleRefs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTitleRef() throws Exception {
        // Initialize the database
        titleRefRepository.saveAndFlush(titleRef);

		int databaseSizeBeforeUpdate = titleRefRepository.findAll().size();

        // Update the titleRef
        titleRef.setLabel(UPDATED_LABEL);
        titleRef.setAbbreviation(UPDATED_ABBREVIATION);
        titleRef.setCode(UPDATED_CODE);

        restTitleRefMockMvc.perform(put("/api/titleRefs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(titleRef)))
                .andExpect(status().isOk());

        // Validate the TitleRef in the database
        List<TitleRef> titleRefs = titleRefRepository.findAll();
        assertThat(titleRefs).hasSize(databaseSizeBeforeUpdate);
        TitleRef testTitleRef = titleRefs.get(titleRefs.size() - 1);
        assertThat(testTitleRef.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testTitleRef.getAbbreviation()).isEqualTo(UPDATED_ABBREVIATION);
        assertThat(testTitleRef.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void deleteTitleRef() throws Exception {
        // Initialize the database
        titleRefRepository.saveAndFlush(titleRef);

		int databaseSizeBeforeDelete = titleRefRepository.findAll().size();

        // Get the titleRef
        restTitleRefMockMvc.perform(delete("/api/titleRefs/{id}", titleRef.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TitleRef> titleRefs = titleRefRepository.findAll();
        assertThat(titleRefs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
