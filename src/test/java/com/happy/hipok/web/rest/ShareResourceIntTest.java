package com.happy.hipok.web.rest;

import com.happy.hipok.Application;
import com.happy.hipok.domain.Share;
import com.happy.hipok.repository.ShareRepository;

import com.happy.hipok.service.ShareService;
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
 * Test class for the ShareResource REST controller.
 *
 * @see ShareResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ShareResourceIntTest {


    @Inject
    private ShareRepository shareRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private ShareService shareService;

    private MockMvc restShareMockMvc;

    private Share share;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShareResource shareResource = new ShareResource();
        ReflectionTestUtils.setField(shareResource, "shareRepository", shareRepository);
        ReflectionTestUtils.setField(shareResource, "shareService", shareService);
        this.restShareMockMvc = MockMvcBuilders.standaloneSetup(shareResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        share = new Share();
    }

    @Test
    @Transactional
    public void createShare() throws Exception {
        int databaseSizeBeforeCreate = shareRepository.findAll().size();

        // Create the Share

        restShareMockMvc.perform(post("/api/shares")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(share)))
                .andExpect(status().isCreated());

        // Validate the Share in the database
        List<Share> shares = shareRepository.findAll();
        assertThat(shares).hasSize(databaseSizeBeforeCreate + 1);
        Share testShare = shares.get(shares.size() - 1);
    }

    @Test
    @Transactional
    public void getAllShares() throws Exception {
        // Initialize the database
        shareRepository.saveAndFlush(share);

        // Get all the shares
        restShareMockMvc.perform(get("/api/shares"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(share.getId().intValue())));
    }

    @Test
    @Transactional
    public void getShare() throws Exception {
        // Initialize the database
        shareRepository.saveAndFlush(share);

        // Get the share
        restShareMockMvc.perform(get("/api/shares/{id}", share.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(share.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingShare() throws Exception {
        // Get the share
        restShareMockMvc.perform(get("/api/shares/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShare() throws Exception {
        // Initialize the database
        shareRepository.saveAndFlush(share);

		int databaseSizeBeforeUpdate = shareRepository.findAll().size();

        // Update the share

        restShareMockMvc.perform(put("/api/shares")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(share)))
                .andExpect(status().isOk());

        // Validate the Share in the database
        List<Share> shares = shareRepository.findAll();
        assertThat(shares).hasSize(databaseSizeBeforeUpdate);
        Share testShare = shares.get(shares.size() - 1);
    }

    @Test
    @Transactional
    public void deleteShare() throws Exception {
        // Initialize the database
        shareRepository.saveAndFlush(share);

		int databaseSizeBeforeDelete = shareRepository.findAll().size();

        // Get the share
        restShareMockMvc.perform(delete("/api/shares/{id}", share.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Share> shares = shareRepository.findAll();
        assertThat(shares).hasSize(databaseSizeBeforeDelete - 1);
    }
}
