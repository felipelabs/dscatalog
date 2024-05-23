package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.services.ProductService;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProductResources.class)
public class ProductResourcesTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ProductDTO productDTO;
    private PageImpl<ProductDTO> page;
    private long existingId;
    private long nonExistingId;

    @BeforeEach
    void setUp() throws Exception {

        productDTO = Factory.createProductDTO();
        page = new PageImpl<>(List.of(productDTO));
        existingId = 1L;
        nonExistingId = 100L;

        when(productService.findAllPaged(any())).thenReturn(page);
        when(productService.findById(existingId)).thenReturn(productDTO);
        when(productService.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
    }

    @Test
    public void findAllShouldReturnPage() throws Exception{
        mockMvc.perform(get("/products")).andExpect(status().isOk());
    }

    @Test
    public void findByIdShouldReturnProductWhenIdExists() throws Exception{
        mockMvc.perform(get("/products/{id}", existingId)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExists() throws Exception{
        mockMvc.perform(get("/products/{id}", nonExistingId)).andExpect(status().isNotFound());
    }
}
