package com.happy.hipok.web.rest;

import com.happy.hipok.Application;
import com.happy.hipok.domain.DeclarationTypeRef;
import com.happy.hipok.repository.DeclarationTypeRefRepository;

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
 * Test class for the DeclarationTypeRefResource REST controller.
 *
 * @see DeclarationTypeRefResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DeclarationTypeRefResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAA";
    private static final String UPDATED_LABEL = "BBBBB";

    @Inject
    private DeclarationTypeRefRepository declarationTypeRefRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDeclarationTypeRefMockMvc;

    private DeclarationTypeRef declarationTypeRef;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DeclarationTypeRefResource declarationTypeRefResource = new DeclarationTypeRefResource();
        ReflectionTestUtils.setField(declarationTypeRefResource, "declarationTypeRefRepository", declarationTypeRefRepository);
        this.restDeclarationTypeRefMockMvc = MockMvcBuilders.standaloneSetup(declarationTypeRefResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        declarationTypeRef = new DeclarationTypeRef();
        declarationTypeRef.setLabel(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createDeclarationTypeRef() throws Exception {
        int databaseSizeBeforeCreate = declarationTypeRefRepository.findAll().size();

        // Create the DeclarationTypeRef

        restDeclarationTypeRefMockMvc.perform(post("/api/declarationTypeRefs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(declarationTypeRef)))
                .andExpect(status().isCreated());

        // Validate the DeclarationTypeRef in the database
        List<DeclarationTypeRef> declarationTypeRefs = declarationTypeRefRepository.findAll();
        assertThat(declarationTypeRefs).hasSize(databaseSizeBeforeCreate + 1);
        DeclarationTypeRef testDeclarationTypeRef = declarationTypeRefs.get(declarationTypeRefs.size() - 1);
        assertThat(testDeclarationTypeRef.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void getAllDeclarationTypeRefs() throws Exception {
        // Initialize the database
        declarationTypeRefRepository.saveAndFlush(declarationTypeRef);

        // Get all the declarationTypeRefs
        restDeclarationTypeRefMockMvc.perform(get("/api/declarationTypeRefs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(declarationTypeRef.getId().intValue())))
                .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }

    @Test
    @Transactional
    public void getDeclarationTypeRef() throws Exception {
        // Initialize the database
        declarationTypeRefRepository.saveAndFlush(declarationTypeRef);

        // Get the declarationTypeRef
        restDeclarationTypeRefMockMvc.perform(get("/api/declarationTypeRefs/{id}", declarationTypeRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(declarationTypeRef.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeclarationTypeRef() throws Exception {
        // Get the declarationTypeRef
        restDeclarationTypeRefMockMvc.perform(get("/api/declarationTypeRefs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeclarationTypeRef() throws Exception {
        // Initialize the database
        declarationTypeRefRepository.saveAndFlush(declarationTypeRef);

		int databaseSizeBeforeUpdate = declarationTypeRefRepository.findAll().size();

        // Update the declarationTypeRef
        declarationTypeRef.setLabel(UPDATED_LABEL);

        restDeclarationTypeRefMockMvc.perform(put("/api/declarationTypeRefs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(declarationTypeRef)))
                .andExpect(status().isOk());

        // Validate the DeclarationTypeRef in the database
        List<DeclarationTypeRef> declarationTypeRefs = declarationTypeRefRepository.findAll();
        assertThat(declarationTypeRefs).hasSize(databaseSizeBeforeUpdate);
        DeclarationTypeRef testDeclarationTypeRef = declarationTypeRefs.get(declarationTypeRefs.size() - 1);
        assertThat(testDeclarationTypeRef.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void deleteDeclarationTypeRef() throws Exception {
        // Initialize the database
        declarationTypeRefRepository.saveAndFlush(declarationTypeRef);

		int databaseSizeBeforeDelete = declarationTypeRefRepository.findAll().size();

        // Get the declarationTypeRef
        restDeclarationTypeRefMockMvc.perform(delete("/api/declarationTypeRefs/{id}", declarationTypeRef.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DeclarationTypeRef> declarationTypeRefs = declarationTypeRefRepository.findAll();
        assertThat(declarationTypeRefs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
