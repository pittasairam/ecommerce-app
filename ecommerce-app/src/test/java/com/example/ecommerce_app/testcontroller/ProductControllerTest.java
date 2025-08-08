package com.example.ecommerce_app.testcontroller;

import static org.mockito.Mockito.when;

import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import com.example.ecommerce_app.controller.ProductController;
import com.example.ecommerce_app.dto.ProductDto;
import com.example.ecommerce_app.service.ProductService;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void testSearchProduct() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setProductName("Laptop");

        List<ProductDto> productList = List.of(productDto);
        Page<ProductDto> productPage = new PageImpl(productList);

        when(productService.SearchProduct("Laptop", 5, 0)).thenReturn(productPage);

    }


}
