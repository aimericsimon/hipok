package com.happy.hipok.web.rest;

import com.happy.hipok.Application;
import com.happy.hipok.domain.Reporting;
import com.happy.hipok.repository.ReportingRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ReportingResource REST controller.
 *
 * @see ReportingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ReportingResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_REPORTING_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_REPORTING_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_REPORTING_DATE_STR = dateTimeFormatter.format(DEFAULT_REPORTING_DATE);

    @Inject
    private ReportingRepository reportingRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restReportingMockMvc;

    private Reporting reporting;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReportingResource reportingResource = new ReportingResource();
        ReflectionTestUtils.setField(reportingResource, "reportingRepository", reportingRepository);
        this.restReportingMockMvc = MockMvcBuilders.standaloneSetup(reportingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        reporting = new Reporting();
        reporting.setReportingDate(DEFAULT_REPORTING_DATE);
    }

    @Test
    @Transactional
    public void createReporting() throws Exception {
        int databaseSizeBeforeCreate = reportingRepository.findAll().size();

        // Create the Reporting

        restReportingMockMvc.perform(post("/api/reportings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(reporting)))
                .andExpect(status().isCreated());

        // Validate the Reporting in the database
        List<Reporting> reportings = reportingRepository.findAll();
        assertThat(reportings).hasSize(databaseSizeBeforeCreate + 1);
        Reporting testReporting = reportings.get(reportings.size() - 1);
        assertThat(testReporting.getReportingDate()).isEqualTo(DEFAULT_REPORTING_DATE);
    }

    @Test
    @Transactional
    public void getAllReportings() throws Exception {
        // Initialize the database
        reportingRepository.saveAndFlush(reporting);

        // Get all the reportings
        restReportingMockMvc.perform(get("/api/reportings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(reporting.getId().intValue())))
                .andExpect(jsonPath("$.[*].reportingDate").value(hasItem(DEFAULT_REPORTING_DATE_STR)));
    }

    @Test
    @Transactional
    public void getReporting() throws Exception {
        // Initialize the database
        reportingRepository.saveAndFlush(reporting);

        // Get the reporting
        restReportingMockMvc.perform(get("/api/reportings/{id}", reporting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(reporting.getId().intValue()))
            .andExpect(jsonPath("$.reportingDate").value(DEFAULT_REPORTING_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingReporting() throws Exception {
        // Get the reporting
        restReportingMockMvc.perform(get("/api/reportings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReporting() throws Exception {
        // Initialize the database
        reportingRepository.saveAndFlush(reporting);

		int databaseSizeBeforeUpdate = reportingRepository.findAll().size();

        // Update the reporting
        reporting.setReportingDate(UPDATED_REPORTING_DATE);

        restReportingMockMvc.perform(put("/api/reportings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(reporting)))
                .andExpect(status().isOk());

        // Validate the Reporting in the database
        List<Reporting> reportings = reportingRepository.findAll();
        assertThat(reportings).hasSize(databaseSizeBeforeUpdate);
        Reporting testReporting = reportings.get(reportings.size() - 1);
        assertThat(testReporting.getReportingDate()).isEqualTo(UPDATED_REPORTING_DATE);
    }

    @Test
    @Transactional
    public void deleteReporting() throws Exception {
        // Initialize the database
        reportingRepository.saveAndFlush(reporting);

		int databaseSizeBeforeDelete = reportingRepository.findAll().size();

        // Get the reporting
        restReportingMockMvc.perform(delete("/api/reportings/{id}", reporting.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Reporting> reportings = reportingRepository.findAll();
        assertThat(reportings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
