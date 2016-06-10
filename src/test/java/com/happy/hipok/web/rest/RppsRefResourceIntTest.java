package com.happy.hipok.web.rest;

import com.happy.hipok.Application;
import com.happy.hipok.domain.RppsRef;
import com.happy.hipok.repository.RppsRefRepository;

import org.junit.Before;
import org.junit.Ignore;
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
 * Test class for the RppsRefResource REST controller.
 *
 * @see RppsRefResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RppsRefResourceIntTest {

    private static final String DEFAULT_NUMBER = "AAAAA";
    private static final String UPDATED_NUMBER = "BBBBB";

    @Inject
    private RppsRefRepository rppsRefRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRppsRefMockMvc;

    private RppsRef rppsRef;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RppsRefResource rppsRefResource = new RppsRefResource();
        ReflectionTestUtils.setField(rppsRefResource, "rppsRefRepository", rppsRefRepository);
        this.restRppsRefMockMvc = MockMvcBuilders.standaloneSetup(rppsRefResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        rppsRef = new RppsRef();
        rppsRef.setNumber(DEFAULT_NUMBER);
    }

    @Test
    @Ignore
    @Transactional
    public void createRppsRef() throws Exception {
        int databaseSizeBeforeCreate = rppsRefRepository.findAll().size();

        // Create the RppsRef

        restRppsRefMockMvc.perform(post("/api/rppsRefs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rppsRef)))
                .andExpect(status().isCreated());

        // Validate the RppsRef in the database
        List<RppsRef> rppsRefs = rppsRefRepository.findAll();
        assertThat(rppsRefs).hasSize(databaseSizeBeforeCreate + 1);
        RppsRef testRppsRef = rppsRefs.get(rppsRefs.size() - 1);
        assertThat(testRppsRef.getNumber()).isEqualTo(DEFAULT_NUMBER);
    }

    @Test
    @Ignore
    @Transactional
    public void getAllRppsRefs() throws Exception {
        // Initialize the database
        rppsRefRepository.saveAndFlush(rppsRef);

        // Get all the rppsRefs
        restRppsRefMockMvc.perform(get("/api/rppsRefs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rppsRef.getId().intValue())))
                .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getRppsRef() throws Exception {
        // Initialize the database
        rppsRefRepository.saveAndFlush(rppsRef);

        // Get the rppsRef
        restRppsRefMockMvc.perform(get("/api/rppsRefs/{id}", rppsRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(rppsRef.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRppsRef() throws Exception {
        // Get the rppsRef
        restRppsRefMockMvc.perform(get("/api/rppsRefs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Ignore
    @Transactional
    public void updateRppsRef() throws Exception {
        // Initialize the database
        rppsRefRepository.saveAndFlush(rppsRef);

		int databaseSizeBeforeUpdate = rppsRefRepository.findAll().size();

        // Update the rppsRef
        rppsRef.setNumber(UPDATED_NUMBER);

        restRppsRefMockMvc.perform(put("/api/rppsRefs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rppsRef)))
                .andExpect(status().isOk());

        // Validate the RppsRef in the database
        List<RppsRef> rppsRefs = rppsRefRepository.findAll();
        assertThat(rppsRefs).hasSize(databaseSizeBeforeUpdate);
        RppsRef testRppsRef = rppsRefs.get(rppsRefs.size() - 1);
        assertThat(testRppsRef.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Ignore
    @Transactional
    public void deleteRppsRef() throws Exception {
        // Initialize the database
        rppsRefRepository.saveAndFlush(rppsRef);

		int databaseSizeBeforeDelete = rppsRefRepository.findAll().size();

        // Get the rppsRef
        restRppsRefMockMvc.perform(delete("/api/rppsRefs/{id}", rppsRef.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RppsRef> rppsRefs = rppsRefRepository.findAll();
        assertThat(rppsRefs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
