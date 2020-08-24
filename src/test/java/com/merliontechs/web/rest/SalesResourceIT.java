package com.merliontechs.web.rest;

import com.merliontechs.TestApp;
import com.merliontechs.domain.Sales;
import com.merliontechs.repository.SalesRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.merliontechs.domain.enumeration.State;
/**
 * Integration tests for the {@link SalesResource} REST controller.
 */
@SpringBootTest(classes = TestApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SalesResourceIT {

    private static final State DEFAULT_STATE = State.IN_CHARGE;
    private static final State UPDATED_STATE = State.SHIPPED;

    private static final String DEFAULT_COMPRADOR = "AAAAAAAAAA";
    private static final String UPDATED_COMPRADOR = "BBBBBBBBBB";

    private static final String DEFAULT_FECHA = "AAAAAAAAAA";
    private static final String UPDATED_FECHA = "BBBBBBBBBB";

    private static final String DEFAULT_PAGADO = "AAAAAAAAAA";
    private static final String UPDATED_PAGADO = "BBBBBBBBBB";

    private static final String DEFAULT_PRECIO = "AAAAAAAAAA";
    private static final String UPDATED_PRECIO = "BBBBBBBBBB";

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalesMockMvc;

    private Sales sales;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sales createEntity(EntityManager em) {
        Sales sales = new Sales()
            .state(DEFAULT_STATE)
            .comprador(DEFAULT_COMPRADOR)
            .fecha(DEFAULT_FECHA)
            .pagado(DEFAULT_PAGADO)
            .precio(DEFAULT_PRECIO);
        return sales;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sales createUpdatedEntity(EntityManager em) {
        Sales sales = new Sales()
            .state(UPDATED_STATE)
            .comprador(UPDATED_COMPRADOR)
            .fecha(UPDATED_FECHA)
            .pagado(UPDATED_PAGADO)
            .precio(UPDATED_PRECIO);
        return sales;
    }

    @BeforeEach
    public void initTest() {
        sales = createEntity(em);
    }

    @Test
    @Transactional
    public void createSales() throws Exception {
        int databaseSizeBeforeCreate = salesRepository.findAll().size();
        // Create the Sales
        restSalesMockMvc.perform(post("/api/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sales)))
            .andExpect(status().isCreated());

        // Validate the Sales in the database
        List<Sales> salesList = salesRepository.findAll();
        assertThat(salesList).hasSize(databaseSizeBeforeCreate + 1);
        Sales testSales = salesList.get(salesList.size() - 1);
        assertThat(testSales.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testSales.getComprador()).isEqualTo(DEFAULT_COMPRADOR);
        assertThat(testSales.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testSales.getPagado()).isEqualTo(DEFAULT_PAGADO);
        assertThat(testSales.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    public void createSalesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salesRepository.findAll().size();

        // Create the Sales with an existing ID
        sales.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesMockMvc.perform(post("/api/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sales)))
            .andExpect(status().isBadRequest());

        // Validate the Sales in the database
        List<Sales> salesList = salesRepository.findAll();
        assertThat(salesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSales() throws Exception {
        // Initialize the database
        salesRepository.saveAndFlush(sales);

        // Get all the salesList
        restSalesMockMvc.perform(get("/api/sales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sales.getId().intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].comprador").value(hasItem(DEFAULT_COMPRADOR)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA)))
            .andExpect(jsonPath("$.[*].pagado").value(hasItem(DEFAULT_PAGADO)))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO)));
    }
    
    @Test
    @Transactional
    public void getSales() throws Exception {
        // Initialize the database
        salesRepository.saveAndFlush(sales);

        // Get the sales
        restSalesMockMvc.perform(get("/api/sales/{id}", sales.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sales.getId().intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.comprador").value(DEFAULT_COMPRADOR))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA))
            .andExpect(jsonPath("$.pagado").value(DEFAULT_PAGADO))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO));
    }
    @Test
    @Transactional
    public void getNonExistingSales() throws Exception {
        // Get the sales
        restSalesMockMvc.perform(get("/api/sales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSales() throws Exception {
        // Initialize the database
        salesRepository.saveAndFlush(sales);

        int databaseSizeBeforeUpdate = salesRepository.findAll().size();

        // Update the sales
        Sales updatedSales = salesRepository.findById(sales.getId()).get();
        // Disconnect from session so that the updates on updatedSales are not directly saved in db
        em.detach(updatedSales);
        updatedSales
            .state(UPDATED_STATE)
            .comprador(UPDATED_COMPRADOR)
            .fecha(UPDATED_FECHA)
            .pagado(UPDATED_PAGADO)
            .precio(UPDATED_PRECIO);

        restSalesMockMvc.perform(put("/api/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSales)))
            .andExpect(status().isOk());

        // Validate the Sales in the database
        List<Sales> salesList = salesRepository.findAll();
        assertThat(salesList).hasSize(databaseSizeBeforeUpdate);
        Sales testSales = salesList.get(salesList.size() - 1);
        assertThat(testSales.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testSales.getComprador()).isEqualTo(UPDATED_COMPRADOR);
        assertThat(testSales.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testSales.getPagado()).isEqualTo(UPDATED_PAGADO);
        assertThat(testSales.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void updateNonExistingSales() throws Exception {
        int databaseSizeBeforeUpdate = salesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesMockMvc.perform(put("/api/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sales)))
            .andExpect(status().isBadRequest());

        // Validate the Sales in the database
        List<Sales> salesList = salesRepository.findAll();
        assertThat(salesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSales() throws Exception {
        // Initialize the database
        salesRepository.saveAndFlush(sales);

        int databaseSizeBeforeDelete = salesRepository.findAll().size();

        // Delete the sales
        restSalesMockMvc.perform(delete("/api/sales/{id}", sales.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sales> salesList = salesRepository.findAll();
        assertThat(salesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
