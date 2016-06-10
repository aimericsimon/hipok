package com.happy.hipok.web.rest;

import com.happy.hipok.Application;
import com.happy.hipok.domain.DeclarationEmail;
import com.happy.hipok.repository.DeclarationEmailRepository;

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
 * Test class for the DeclarationEmailResource REST controller.
 *
 * @see DeclarationEmailResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DeclarationEmailResourceIntTest {

    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";

    @Inject
    private DeclarationEmailRepository declarationEmailRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDeclarationEmailMockMvc;

    private DeclarationEmail declarationEmail;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DeclarationEmailResource declarationEmailResource = new DeclarationEmailResource();
        ReflectionTestUtils.setField(declarationEmailResource, "declarationEmailRepository", declarationEmailRepository);
        this.restDeclarationEmailMockMvc = MockMvcBuilders.standaloneSetup(declarationEmailResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        declarationEmail = new DeclarationEmail();
        declarationEmail.setEmail(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createDeclarationEmail() throws Exception {
        int databaseSizeBeforeCreate = declarationEmailRepository.findAll().size();

        // Create the DeclarationEmail

        restDeclarationEmailMockMvc.perform(post("/api/declarationEmails")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(declarationEmail)))
                .andExpect(status().isCreated());

        // Validate the DeclarationEmail in the database
        List<DeclarationEmail> declarationEmails = declarationEmailRepository.findAll();
        assertThat(declarationEmails).hasSize(databaseSizeBeforeCreate + 1);
        DeclarationEmail testDeclarationEmail = declarationEmails.get(declarationEmails.size() - 1);
        assertThat(testDeclarationEmail.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllDeclarationEmails() throws Exception {
        // Initialize the database
        declarationEmailRepository.saveAndFlush(declarationEmail);

        // Get all the declarationEmails
        restDeclarationEmailMockMvc.perform(get("/api/declarationEmails?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(declarationEmail.getId().intValue())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getDeclarationEmail() throws Exception {
        // Initialize the database
        declarationEmailRepository.saveAndFlush(declarationEmail);

        // Get the declarationEmail
        restDeclarationEmailMockMvc.perform(get("/api/declarationEmails/{id}", declarationEmail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(declarationEmail.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeclarationEmail() throws Exception {
        // Get the declarationEmail
        restDeclarationEmailMockMvc.perform(get("/api/declarationEmails/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeclarationEmail() throws Exception {
        // Initialize the database
        declarationEmailRepository.saveAndFlush(declarationEmail);

		int databaseSizeBeforeUpdate = declarationEmailRepository.findAll().size();

        // Update the declarationEmail
        declarationEmail.setEmail(UPDATED_EMAIL);

        restDeclarationEmailMockMvc.perform(put("/api/declarationEmails")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(declarationEmail)))
                .andExpect(status().isOk());

        // Validate the DeclarationEmail in the database
        List<DeclarationEmail> declarationEmails = declarationEmailRepository.findAll();
        assertThat(declarationEmails).hasSize(databaseSizeBeforeUpdate);
        DeclarationEmail testDeclarationEmail = declarationEmails.get(declarationEmails.size() - 1);
        assertThat(testDeclarationEmail.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void deleteDeclarationEmail() throws Exception {
        // Initialize the database
        declarationEmailRepository.saveAndFlush(declarationEmail);

		int databaseSizeBeforeDelete = declarationEmailRepository.findAll().size();

        // Get the declarationEmail
        restDeclarationEmailMockMvc.perform(delete("/api/declarationEmails/{id}", declarationEmail.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DeclarationEmail> declarationEmails = declarationEmailRepository.findAll();
        assertThat(declarationEmails).hasSize(databaseSizeBeforeDelete - 1);
    }
}
