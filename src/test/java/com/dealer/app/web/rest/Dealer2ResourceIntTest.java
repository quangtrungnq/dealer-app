package com.dealer.app.web.rest;

import com.dealer.app.DealappApp;

import com.dealer.app.domain.Dealer2;
import com.dealer.app.repository.Dealer2Repository;
import com.dealer.app.service.Dealer2Service;
import com.dealer.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dealer.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Dealer2Resource REST controller.
 *
 * @see Dealer2Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DealappApp.class)
public class Dealer2ResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATOR = "AAAAAAAAAA";
    private static final String UPDATED_CREATOR = "BBBBBBBBBB";

    @Autowired
    private Dealer2Repository dealer2Repository;

    @Autowired
    private Dealer2Service dealer2Service;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDealer2MockMvc;

    private Dealer2 dealer2;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Dealer2Resource dealer2Resource = new Dealer2Resource(dealer2Service);
        this.restDealer2MockMvc = MockMvcBuilders.standaloneSetup(dealer2Resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dealer2 createEntity(EntityManager em) {
        Dealer2 dealer2 = new Dealer2()
            .name(DEFAULT_NAME)
            .creator(DEFAULT_CREATOR);
        return dealer2;
    }

    @Before
    public void initTest() {
        dealer2 = createEntity(em);
    }

    @Test
    @Transactional
    public void createDealer2() throws Exception {
        int databaseSizeBeforeCreate = dealer2Repository.findAll().size();

        // Create the Dealer2
        restDealer2MockMvc.perform(post("/api/dealer-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dealer2)))
            .andExpect(status().isCreated());

        // Validate the Dealer2 in the database
        List<Dealer2> dealer2List = dealer2Repository.findAll();
        assertThat(dealer2List).hasSize(databaseSizeBeforeCreate + 1);
        Dealer2 testDealer2 = dealer2List.get(dealer2List.size() - 1);
        assertThat(testDealer2.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDealer2.getCreator()).isEqualTo(DEFAULT_CREATOR);
    }

    @Test
    @Transactional
    public void createDealer2WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dealer2Repository.findAll().size();

        // Create the Dealer2 with an existing ID
        dealer2.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDealer2MockMvc.perform(post("/api/dealer-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dealer2)))
            .andExpect(status().isBadRequest());

        // Validate the Dealer2 in the database
        List<Dealer2> dealer2List = dealer2Repository.findAll();
        assertThat(dealer2List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDealer2S() throws Exception {
        // Initialize the database
        dealer2Repository.saveAndFlush(dealer2);

        // Get all the dealer2List
        restDealer2MockMvc.perform(get("/api/dealer-2-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealer2.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].creator").value(hasItem(DEFAULT_CREATOR.toString())));
    }

    @Test
    @Transactional
    public void getDealer2() throws Exception {
        // Initialize the database
        dealer2Repository.saveAndFlush(dealer2);

        // Get the dealer2
        restDealer2MockMvc.perform(get("/api/dealer-2-s/{id}", dealer2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dealer2.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.creator").value(DEFAULT_CREATOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDealer2() throws Exception {
        // Get the dealer2
        restDealer2MockMvc.perform(get("/api/dealer-2-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDealer2() throws Exception {
        // Initialize the database
        dealer2Service.save(dealer2);

        int databaseSizeBeforeUpdate = dealer2Repository.findAll().size();

        // Update the dealer2
        Dealer2 updatedDealer2 = dealer2Repository.findOne(dealer2.getId());
        updatedDealer2
            .name(UPDATED_NAME)
            .creator(UPDATED_CREATOR);

        restDealer2MockMvc.perform(put("/api/dealer-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDealer2)))
            .andExpect(status().isOk());

        // Validate the Dealer2 in the database
        List<Dealer2> dealer2List = dealer2Repository.findAll();
        assertThat(dealer2List).hasSize(databaseSizeBeforeUpdate);
        Dealer2 testDealer2 = dealer2List.get(dealer2List.size() - 1);
        assertThat(testDealer2.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDealer2.getCreator()).isEqualTo(UPDATED_CREATOR);
    }

    @Test
    @Transactional
    public void updateNonExistingDealer2() throws Exception {
        int databaseSizeBeforeUpdate = dealer2Repository.findAll().size();

        // Create the Dealer2

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDealer2MockMvc.perform(put("/api/dealer-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dealer2)))
            .andExpect(status().isCreated());

        // Validate the Dealer2 in the database
        List<Dealer2> dealer2List = dealer2Repository.findAll();
        assertThat(dealer2List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDealer2() throws Exception {
        // Initialize the database
        dealer2Service.save(dealer2);

        int databaseSizeBeforeDelete = dealer2Repository.findAll().size();

        // Get the dealer2
        restDealer2MockMvc.perform(delete("/api/dealer-2-s/{id}", dealer2.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dealer2> dealer2List = dealer2Repository.findAll();
        assertThat(dealer2List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dealer2.class);
        Dealer2 dealer21 = new Dealer2();
        dealer21.setId(1L);
        Dealer2 dealer22 = new Dealer2();
        dealer22.setId(dealer21.getId());
        assertThat(dealer21).isEqualTo(dealer22);
        dealer22.setId(2L);
        assertThat(dealer21).isNotEqualTo(dealer22);
        dealer21.setId(null);
        assertThat(dealer21).isNotEqualTo(dealer22);
    }
}
