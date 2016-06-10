package com.happy.hipok.web.rest;

import com.happy.hipok.Application;
import com.happy.hipok.domain.Publication;
import com.happy.hipok.repository.PublicationRepository;
import com.happy.hipok.web.rest.dto.PublicationDTO;
import com.happy.hipok.web.rest.mapper.PublicationMapper;

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

import com.happy.hipok.domain.enumeration.Visibility;

/**
 * Test class for the PublicationResource REST controller.
 *
 * @see PublicationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PublicationResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_LOCATION = "AAAAA";
    private static final String UPDATED_LOCATION = "BBBBB";
    
    private static final Visibility DEFAULT_VISIBILITY = Visibility.PUBLIC;
    private static final Visibility UPDATED_VISIBILITY = Visibility.PRIVATE;

    private static final ZonedDateTime DEFAULT_PUBLICATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_PUBLICATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_PUBLICATION_DATE_STR = dateTimeFormatter.format(DEFAULT_PUBLICATION_DATE);

    private static final Integer DEFAULT_NB_VIZUALISATIONS = 1;
    private static final Integer UPDATED_NB_VIZUALISATIONS = 2;
    private static final String DEFAULT_PROCESSED_DESCRIPTION = "AAAAA";
    private static final String UPDATED_PROCESSED_DESCRIPTION = "BBBBB";

    @Inject
    private PublicationRepository publicationRepository;

    @Inject
    private PublicationMapper publicationMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPublicationMockMvc;

    private Publication publication;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PublicationResource publicationResource = new PublicationResource();
        ReflectionTestUtils.setField(publicationResource, "publicationRepository", publicationRepository);
        ReflectionTestUtils.setField(publicationResource, "publicationMapper", publicationMapper);
        this.restPublicationMockMvc = MockMvcBuilders.standaloneSetup(publicationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        publication = new Publication();
        publication.setDescription(DEFAULT_DESCRIPTION);
        publication.setLocation(DEFAULT_LOCATION);
        publication.setVisibility(DEFAULT_VISIBILITY);
        publication.setPublicationDate(DEFAULT_PUBLICATION_DATE);
        publication.setNbVizualisations(DEFAULT_NB_VIZUALISATIONS);
        publication.setProcessedDescription(DEFAULT_PROCESSED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPublication() throws Exception {
        int databaseSizeBeforeCreate = publicationRepository.findAll().size();

        // Create the Publication
        PublicationDTO publicationDTO = publicationMapper.publicationToPublicationDTO(publication);

        restPublicationMockMvc.perform(post("/api/publications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(publicationDTO)))
                .andExpect(status().isCreated());

        // Validate the Publication in the database
        List<Publication> publications = publicationRepository.findAll();
        assertThat(publications).hasSize(databaseSizeBeforeCreate + 1);
        Publication testPublication = publications.get(publications.size() - 1);
        assertThat(testPublication.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPublication.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testPublication.getVisibility()).isEqualTo(DEFAULT_VISIBILITY);
        assertThat(testPublication.getPublicationDate()).isEqualTo(DEFAULT_PUBLICATION_DATE);
        assertThat(testPublication.getNbVizualisations()).isEqualTo(DEFAULT_NB_VIZUALISATIONS);
        assertThat(testPublication.getProcessedDescription()).isEqualTo(DEFAULT_PROCESSED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPublications() throws Exception {
        // Initialize the database
        publicationRepository.saveAndFlush(publication);

        // Get all the publications
        restPublicationMockMvc.perform(get("/api/publications?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(publication.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].visibility").value(hasItem(DEFAULT_VISIBILITY.toString())))
                .andExpect(jsonPath("$.[*].publicationDate").value(hasItem(DEFAULT_PUBLICATION_DATE_STR)))
                .andExpect(jsonPath("$.[*].nbVizualisations").value(hasItem(DEFAULT_NB_VIZUALISATIONS)))
                .andExpect(jsonPath("$.[*].processedDescription").value(hasItem(DEFAULT_PROCESSED_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getPublication() throws Exception {
        // Initialize the database
        publicationRepository.saveAndFlush(publication);

        // Get the publication
        restPublicationMockMvc.perform(get("/api/publications/{id}", publication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(publication.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.visibility").value(DEFAULT_VISIBILITY.toString()))
            .andExpect(jsonPath("$.publicationDate").value(DEFAULT_PUBLICATION_DATE_STR))
            .andExpect(jsonPath("$.nbVizualisations").value(DEFAULT_NB_VIZUALISATIONS))
            .andExpect(jsonPath("$.processedDescription").value(DEFAULT_PROCESSED_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPublication() throws Exception {
        // Get the publication
        restPublicationMockMvc.perform(get("/api/publications/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublication() throws Exception {
        // Initialize the database
        publicationRepository.saveAndFlush(publication);

		int databaseSizeBeforeUpdate = publicationRepository.findAll().size();

        // Update the publication
        publication.setDescription(UPDATED_DESCRIPTION);
        publication.setLocation(UPDATED_LOCATION);
        publication.setVisibility(UPDATED_VISIBILITY);
        publication.setPublicationDate(UPDATED_PUBLICATION_DATE);
        publication.setNbVizualisations(UPDATED_NB_VIZUALISATIONS);
        publication.setProcessedDescription(UPDATED_PROCESSED_DESCRIPTION);
        PublicationDTO publicationDTO = publicationMapper.publicationToPublicationDTO(publication);

        restPublicationMockMvc.perform(put("/api/publications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(publicationDTO)))
                .andExpect(status().isOk());

        // Validate the Publication in the database
        List<Publication> publications = publicationRepository.findAll();
        assertThat(publications).hasSize(databaseSizeBeforeUpdate);
        Publication testPublication = publications.get(publications.size() - 1);
        assertThat(testPublication.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPublication.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testPublication.getVisibility()).isEqualTo(UPDATED_VISIBILITY);
        assertThat(testPublication.getPublicationDate()).isEqualTo(UPDATED_PUBLICATION_DATE);
        assertThat(testPublication.getNbVizualisations()).isEqualTo(UPDATED_NB_VIZUALISATIONS);
        assertThat(testPublication.getProcessedDescription()).isEqualTo(UPDATED_PROCESSED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deletePublication() throws Exception {
        // Initialize the database
        publicationRepository.saveAndFlush(publication);

		int databaseSizeBeforeDelete = publicationRepository.findAll().size();

        // Get the publication
        restPublicationMockMvc.perform(delete("/api/publications/{id}", publication.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Publication> publications = publicationRepository.findAll();
        assertThat(publications).hasSize(databaseSizeBeforeDelete - 1);
    }
}
