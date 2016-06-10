package com.happy.hipok.web.rest;

import com.happy.hipok.Application;
import com.happy.hipok.domain.Edito;
import com.happy.hipok.repository.EditoRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the AppEditoResource REST controller.
 *
 * @see EditoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EditoResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAA";
    private static final String UPDATED_LABEL = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private EditoRepository editoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEditoMockMvc;

    private Edito edito;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EditoResource editoResource = new EditoResource();
        ReflectionTestUtils.setField(editoResource, "editoRepository", editoRepository);
        this.restEditoMockMvc = MockMvcBuilders.standaloneSetup(editoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        edito = new Edito();
        edito.setLabel(DEFAULT_LABEL);
        edito.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createEdito() throws Exception {
        int databaseSizeBeforeCreate = editoRepository.findAll().size();

        // Create the Edito

        restEditoMockMvc.perform(post("/api/editos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(edito)))
                .andExpect(status().isCreated());

        // Validate the Edito in the database
        List<Edito> editos = editoRepository.findAll();
        assertThat(editos).hasSize(databaseSizeBeforeCreate + 1);
        Edito testEdito = editos.get(editos.size() - 1);
        assertThat(testEdito.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testEdito.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllEditos() throws Exception {
        // Initialize the database
        editoRepository.saveAndFlush(edito);

        // Get all the editos
        restEditoMockMvc.perform(get("/api/editos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(edito.getId().intValue())))
                .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getEdito() throws Exception {
        // Initialize the database
        editoRepository.saveAndFlush(edito);

        // Get the edito
        restEditoMockMvc.perform(get("/api/editos/{id}", edito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(edito.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEdito() throws Exception {
        // Get the edito
        restEditoMockMvc.perform(get("/api/editos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEdito() throws Exception {
        // Initialize the database
        editoRepository.saveAndFlush(edito);

		int databaseSizeBeforeUpdate = editoRepository.findAll().size();

        // Update the edito
        edito.setLabel(UPDATED_LABEL);
        edito.setDescription(UPDATED_DESCRIPTION);

        restEditoMockMvc.perform(put("/api/editos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(edito)))
                .andExpect(status().isOk());

        // Validate the Edito in the database
        List<Edito> editos = editoRepository.findAll();
        assertThat(editos).hasSize(databaseSizeBeforeUpdate);
        Edito testEdito = editos.get(editos.size() - 1);
        assertThat(testEdito.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testEdito.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteEdito() throws Exception {
        // Initialize the database
        editoRepository.saveAndFlush(edito);

		int databaseSizeBeforeDelete = editoRepository.findAll().size();

        // Get the edito
        restEditoMockMvc.perform(delete("/api/editos/{id}", edito.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Edito> editos = editoRepository.findAll();
        assertThat(editos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
