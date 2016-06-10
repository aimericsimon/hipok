package com.happy.hipok.web.rest;

import com.happy.hipok.Application;
import com.happy.hipok.domain.Follow;
import com.happy.hipok.domain.enumeration.State;
import com.happy.hipok.repository.FollowRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FollowResource REST controller.
 *
 * @see FollowResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FollowResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));



    private static final State DEFAULT_STATE = State.PENDING;
    private static final State UPDATED_STATE = State.ACCEPTED;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_STR = dateTimeFormatter.format(DEFAULT_DATE);

    @Inject
    private FollowRepository followRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFollowMockMvc;

    private Follow follow;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FollowResource followResource = new FollowResource();
        ReflectionTestUtils.setField(followResource, "followRepository", followRepository);
        this.restFollowMockMvc = MockMvcBuilders.standaloneSetup(followResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        follow = new Follow();
        follow.setState(DEFAULT_STATE);
        follow.setDate(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createFollow() throws Exception {
        int databaseSizeBeforeCreate = followRepository.findAll().size();

        // Create the Follow

        restFollowMockMvc.perform(post("/api/follows")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(follow)))
                .andExpect(status().isCreated());

        // Validate the Follow in the database
        List<Follow> follows = followRepository.findAll();
        assertThat(follows).hasSize(databaseSizeBeforeCreate + 1);
        Follow testFollow = follows.get(follows.size() - 1);
        assertThat(testFollow.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testFollow.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void getAllFollows() throws Exception {
        // Initialize the database
        followRepository.saveAndFlush(follow);

        // Get all the follows
        restFollowMockMvc.perform(get("/api/follows?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(follow.getId().intValue())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE_STR)));
    }

    @Test
    @Transactional
    public void getFollow() throws Exception {
        // Initialize the database
        followRepository.saveAndFlush(follow);

        // Get the follow
        restFollowMockMvc.perform(get("/api/follows/{id}", follow.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(follow.getId().intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingFollow() throws Exception {
        // Get the follow
        restFollowMockMvc.perform(get("/api/follows/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFollow() throws Exception {
        // Initialize the database
        followRepository.saveAndFlush(follow);

		int databaseSizeBeforeUpdate = followRepository.findAll().size();

        // Update the follow
        follow.setState(UPDATED_STATE);
        follow.setDate(UPDATED_DATE);

        restFollowMockMvc.perform(put("/api/follows")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(follow)))
                .andExpect(status().isOk());

        // Validate the Follow in the database
        List<Follow> follows = followRepository.findAll();
        assertThat(follows).hasSize(databaseSizeBeforeUpdate);
        Follow testFollow = follows.get(follows.size() - 1);
        assertThat(testFollow.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testFollow.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void deleteFollow() throws Exception {
        // Initialize the database
        followRepository.saveAndFlush(follow);

		int databaseSizeBeforeDelete = followRepository.findAll().size();

        // Get the follow
        restFollowMockMvc.perform(delete("/api/follows/{id}", follow.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Follow> follows = followRepository.findAll();
        assertThat(follows).hasSize(databaseSizeBeforeDelete - 1);
    }
}
