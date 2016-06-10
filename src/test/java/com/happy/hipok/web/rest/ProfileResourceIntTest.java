package com.happy.hipok.web.rest;

import com.happy.hipok.Application;
import com.happy.hipok.domain.Profile;
import com.happy.hipok.repository.ProfileRepository;
import com.happy.hipok.web.rest.dto.ProfileDTO;
import com.happy.hipok.web.rest.mapper.ProfileMapper;

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
 * Test class for the ProfileResource REST controller.
 *
 * @see ProfileResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProfileResourceIntTest {

    private static final String DEFAULT_AVATAR_URL = "AAAAA";
    private static final String UPDATED_AVATAR_URL = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_AVATAR_THUMBNAIL_URL = "AAAAA";
    private static final String UPDATED_AVATAR_THUMBNAIL_URL = "BBBBB";

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    private ProfileMapper profileMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProfileMockMvc;

    private Profile profile;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProfileResource profileResource = new ProfileResource();
        ReflectionTestUtils.setField(profileResource, "profileRepository", profileRepository);
        ReflectionTestUtils.setField(profileResource, "profileMapper", profileMapper);
        this.restProfileMockMvc = MockMvcBuilders.standaloneSetup(profileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        profile = new Profile();
        profile.setAvatarUrl(DEFAULT_AVATAR_URL);
        profile.setDescription(DEFAULT_DESCRIPTION);
        profile.setAvatarThumbnailUrl(DEFAULT_AVATAR_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void createProfile() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.profileToProfileDTO(profile);

        restProfileMockMvc.perform(post("/api/profiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
                .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profiles = profileRepository.findAll();
        assertThat(profiles).hasSize(databaseSizeBeforeCreate + 1);
        Profile testProfile = profiles.get(profiles.size() - 1);
        assertThat(testProfile.getAvatarUrl()).isEqualTo(DEFAULT_AVATAR_URL);
        assertThat(testProfile.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProfile.getAvatarThumbnailUrl()).isEqualTo(DEFAULT_AVATAR_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllProfiles() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profiles
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
                .andExpect(jsonPath("$.[*].avatarUrl").value(hasItem(DEFAULT_AVATAR_URL.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].avatarThumbnailUrl").value(hasItem(DEFAULT_AVATAR_THUMBNAIL_URL.toString())));
    }

    @Test
    @Transactional
    public void getProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(profile.getId().intValue()))
            .andExpect(jsonPath("$.avatarUrl").value(DEFAULT_AVATAR_URL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.avatarThumbnailUrl").value(DEFAULT_AVATAR_THUMBNAIL_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

		int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile
        profile.setAvatarUrl(UPDATED_AVATAR_URL);
        profile.setDescription(UPDATED_DESCRIPTION);
        profile.setAvatarThumbnailUrl(UPDATED_AVATAR_THUMBNAIL_URL);
        ProfileDTO profileDTO = profileMapper.profileToProfileDTO(profile);

        restProfileMockMvc.perform(put("/api/profiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
                .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profiles = profileRepository.findAll();
        assertThat(profiles).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profiles.get(profiles.size() - 1);
        assertThat(testProfile.getAvatarUrl()).isEqualTo(UPDATED_AVATAR_URL);
        assertThat(testProfile.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProfile.getAvatarThumbnailUrl()).isEqualTo(UPDATED_AVATAR_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void deleteProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

		int databaseSizeBeforeDelete = profileRepository.findAll().size();

        // Get the profile
        restProfileMockMvc.perform(delete("/api/profiles/{id}", profile.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Profile> profiles = profileRepository.findAll();
        assertThat(profiles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
