package com.example.ecommerce_app.testserviceimpl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.*;

import com.example.ecommerce_app.dto.ProductDto;
import com.example.ecommerce_app.entity.Product;
import com.example.ecommerce_app.repo.ProductRepo;
import com.example.ecommerce_app.serviceImp.ProductServiceImpl;


@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepo productRepo;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void testSearchProduct() {
    	var productName = "Test";
    	var page = 0;
    	var size = 2;

        var product1 = new Product();
        product1.setProductId(1L);
        product1.setProductName("Test Product 1");

        var product2 = new Product();
        product2.setProductId(2L);
        product2.setProductName("Test Product 2");

        var productList = Arrays.asList(product1, product2);
        var pageable = PageRequest.of(page, size);
        var productPage = new PageImpl<>(productList, pageable, productList.size());

        when(productRepo.findByProductNameContainingIgnoreCase(productName, pageable)).thenReturn(productPage);

        // Act
        Page<ProductDto> result = productService.SearchProduct(productName, size, page);

        // Assert
        assertEquals(2, result.getContent().size());
        assertEquals("Test Product 1", result.getContent().get(0).getProductName());
        assertEquals("Test Product 2", result.getContent().get(1).getProductName());

        verify(productRepo, times(1)).findByProductNameContainingIgnoreCase(productName, pageable);
    }
}
