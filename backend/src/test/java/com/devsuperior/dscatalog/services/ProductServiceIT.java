package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductServiceIT {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    private long existingId;
    private long nonExistingId;
    private long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 100L;
        countTotalProducts = 25;
    }

    @Test
    public void deleteShouldDeleteResourceWhenIdExists(){
        productService.delete(existingId);
        Assertions.assertEquals(countTotalProducts -1, productRepository.count());

    }

    @Test
    public void deleteShouldThrowsResourceNotFoundExceptionIdDoesNotExists(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.delete(nonExistingId);
        });

    }
}
