package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private long existingId;
    private long nonExistingId;
    private long countTotalProducts;
    private String jsonBody;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 100L;
        countTotalProducts = 25;
        jsonBody = objectMapper.writeValueAsString(Factory.createProductDTO());
    }

    @Test
    public void findAllShouldReturnSortedPageWhenSortByName() throws Exception{
        mockMvc.perform(get("/products?page=0&size=12&sort=name,asc").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(countTotalProducts));
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() throws Exception{
        String expectedName = Factory.createProductDTO().getName();
        String expectedDescription = Factory.createProductDTO().getDescription();

        mockMvc.perform(put("/products/{id}", existingId).content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value(expectedName))
                .andExpect(jsonPath("$.description").value(expectedDescription));
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception{
        String expectedName = Factory.createProductDTO().getName();
        String expectedDescription = Factory.createProductDTO().getDescription();

        mockMvc.perform(put("/products/{id}", nonExistingId).content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
