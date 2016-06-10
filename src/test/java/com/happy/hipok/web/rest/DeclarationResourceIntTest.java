package com.happy.hipok.web.rest;

import com.happy.hipok.Application;
import com.happy.hipok.domain.Declaration;
import com.happy.hipok.repository.DeclarationRepository;

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
 * Test class for the DeclarationResource REST controller.
 *
 * @see DeclarationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DeclarationResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_DECLARATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DECLARATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DECLARATION_DATE_STR = dateTimeFormatter.format(DEFAULT_DECLARATION_DATE);

    @Inject
    private DeclarationRepository declarationRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDeclarationMockMvc;

    private Declaration declaration;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DeclarationResource declarationResource = new DeclarationResource();
        ReflectionTestUtils.setField(declarationResource, "declarationRepository", declarationRepository);
        this.restDeclarationMockMvc = MockMvcBuilders.standaloneSetup(declarationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        declaration = new Declaration();
        declaration.setDescription(DEFAULT_DESCRIPTION);
        declaration.setDeclarationDate(DEFAULT_DECLARATION_DATE);
    }

    @Test
    @Transactional
    public void createDeclaration() throws Exception {
        int databaseSizeBeforeCreate = declarationRepository.findAll().size();

        // Create the Declaration

        restDeclarationMockMvc.perform(post("/api/declarations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(declaration)))
                .andExpect(status().isCreated());

        // Validate the Declaration in the database
        List<Declaration> declarations = declarationRepository.findAll();
        assertThat(declarations).hasSize(databaseSizeBeforeCreate + 1);
        Declaration testDeclaration = declarations.get(declarations.size() - 1);
        assertThat(testDeclaration.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDeclaration.getDeclarationDate()).isEqualTo(DEFAULT_DECLARATION_DATE);
    }

    @Test
    @Transactional
    public void getAllDeclarations() throws Exception {
        // Initialize the database
        declarationRepository.saveAndFlush(declaration);

        // Get all the declarations
        restDeclarationMockMvc.perform(get("/api/declarations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(declaration.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].declarationDate").value(hasItem(DEFAULT_DECLARATION_DATE_STR)));
    }

    @Test
    @Transactional
    public void getDeclaration() throws Exception {
        // Initialize the database
        declarationRepository.saveAndFlush(declaration);

        // Get the declaration
        restDeclarationMockMvc.perform(get("/api/declarations/{id}", declaration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(declaration.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.declarationDate").value(DEFAULT_DECLARATION_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingDeclaration() throws Exception {
        // Get the declaration
        restDeclarationMockMvc.perform(get("/api/declarations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeclaration() throws Exception {
        // Initialize the database
        declarationRepository.saveAndFlush(declaration);

		int databaseSizeBeforeUpdate = declarationRepository.findAll().size();

        // Update the declaration
        declaration.setDescription(UPDATED_DESCRIPTION);
        declaration.setDeclarationDate(UPDATED_DECLARATION_DATE);

        restDeclarationMockMvc.perform(put("/api/declarations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(declaration)))
                .andExpect(status().isOk());

        // Validate the Declaration in the database
        List<Declaration> declarations = declarationRepository.findAll();
        assertThat(declarations).hasSize(databaseSizeBeforeUpdate);
        Declaration testDeclaration = declarations.get(declarations.size() - 1);
        assertThat(testDeclaration.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDeclaration.getDeclarationDate()).isEqualTo(UPDATED_DECLARATION_DATE);
    }

    @Test
    @Transactional
    public void deleteDeclaration() throws Exception {
        // Initialize the database
        declarationRepository.saveAndFlush(declaration);

		int databaseSizeBeforeDelete = declarationRepository.findAll().size();

        // Get the declaration
        restDeclarationMockMvc.perform(delete("/api/declarations/{id}", declaration.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Declaration> declarations = declarationRepository.findAll();
        assertThat(declarations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
