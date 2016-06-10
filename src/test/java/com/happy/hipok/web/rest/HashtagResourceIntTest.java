package com.happy.hipok.web.rest;

import com.happy.hipok.Application;
import com.happy.hipok.domain.Hashtag;
import com.happy.hipok.repository.HashtagRepository;
import com.happy.hipok.web.rest.dto.HashtagDTO;
import com.happy.hipok.web.rest.mapper.HashtagMapper;

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
 * Test class for the HashtagResource REST controller.
 *
 * @see HashtagResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HashtagResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAA";
    private static final String UPDATED_LABEL = "BBBBB";

    @Inject
    private HashtagRepository hashtagRepository;

    @Inject
    private HashtagMapper hashtagMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHashtagMockMvc;

    private Hashtag hashtag;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HashtagResource hashtagResource = new HashtagResource();
        ReflectionTestUtils.setField(hashtagResource, "hashtagRepository", hashtagRepository);
        ReflectionTestUtils.setField(hashtagResource, "hashtagMapper", hashtagMapper);
        this.restHashtagMockMvc = MockMvcBuilders.standaloneSetup(hashtagResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hashtag = new Hashtag();
        hashtag.setLabel(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createHashtag() throws Exception {
        int databaseSizeBeforeCreate = hashtagRepository.findAll().size();

        // Create the Hashtag
        HashtagDTO hashtagDTO = hashtagMapper.hashtagToHashtagDTO(hashtag);

        restHashtagMockMvc.perform(post("/api/hashtags")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hashtagDTO)))
                .andExpect(status().isCreated());

        // Validate the Hashtag in the database
        List<Hashtag> hashtags = hashtagRepository.findAll();
        assertThat(hashtags).hasSize(databaseSizeBeforeCreate + 1);
        Hashtag testHashtag = hashtags.get(hashtags.size() - 1);
        assertThat(testHashtag.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void getAllHashtags() throws Exception {
        // Initialize the database
        hashtagRepository.saveAndFlush(hashtag);

        // Get all the hashtags
        restHashtagMockMvc.perform(get("/api/hashtags"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hashtag.getId().intValue())))
                .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }

    @Test
    @Transactional
    public void getHashtag() throws Exception {
        // Initialize the database
        hashtagRepository.saveAndFlush(hashtag);

        // Get the hashtag
        restHashtagMockMvc.perform(get("/api/hashtags/{id}", hashtag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hashtag.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHashtag() throws Exception {
        // Get the hashtag
        restHashtagMockMvc.perform(get("/api/hashtags/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHashtag() throws Exception {
        // Initialize the database
        hashtagRepository.saveAndFlush(hashtag);

		int databaseSizeBeforeUpdate = hashtagRepository.findAll().size();

        // Update the hashtag
        hashtag.setLabel(UPDATED_LABEL);
        HashtagDTO hashtagDTO = hashtagMapper.hashtagToHashtagDTO(hashtag);

        restHashtagMockMvc.perform(put("/api/hashtags")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hashtagDTO)))
                .andExpect(status().isOk());

        // Validate the Hashtag in the database
        List<Hashtag> hashtags = hashtagRepository.findAll();
        assertThat(hashtags).hasSize(databaseSizeBeforeUpdate);
        Hashtag testHashtag = hashtags.get(hashtags.size() - 1);
        assertThat(testHashtag.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void deleteHashtag() throws Exception {
        // Initialize the database
        hashtagRepository.saveAndFlush(hashtag);

		int databaseSizeBeforeDelete = hashtagRepository.findAll().size();

        // Get the hashtag
        restHashtagMockMvc.perform(delete("/api/hashtags/{id}", hashtag.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Hashtag> hashtags = hashtagRepository.findAll();
        assertThat(hashtags).hasSize(databaseSizeBeforeDelete - 1);
    }
}
