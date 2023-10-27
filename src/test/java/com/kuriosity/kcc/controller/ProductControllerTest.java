package com.kuriosity.kcc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuriosity.kcc.model.Product;
import com.kuriosity.kcc.model.User;
import com.kuriosity.kcc.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration
@WithMockUser(username = "tester")
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    ObjectMapper mapper;

    Product prod1;
    Product prod2;
    Product prod3;

    User user = User.builder()
            .username("tester")
            .email("test@email.com")
            .password("password")
            .build();

    @BeforeEach
    void setUp() {
        prod1 = new Product(1L, "New Example Shirt", 40.00, "Test Description", 100, "image");
        prod2 = new Product(2L, "New Example Shirt 2", 45.00, "Test Description 2", 100, "image");
        prod3 = new Product(3L, "New Example Shirt 3", 50.00, "Test Description 3", 100, "image");
    }


    @Test
    void getProducts_success() throws Exception {
        when(productService.getProducts()).thenReturn(List.of(prod1, prod2, prod3));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getProduct_success() throws Exception {
        when(productService.getProduct(prod1.getId())).thenReturn(Optional.of(prod1));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/" + prod1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createProduct_success() throws Exception {
        when(productService.createProduct(any(Product.class))).thenReturn(prod1);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(prod1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void updateProduct_success() throws Exception {
        Product update = new Product(prod1.getId(), "New Example 1", 40.00, "Test Description", 100, "image");

        when(productService.updateProduct(prod1.getId(), update)).thenReturn(update);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/api/products/{productId}", prod1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(update));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    @Test
    void deleteProduct_success() throws Exception {

        when(productService.deleteProduct(prod1.getId()))
                .thenReturn(Optional.of(prod1));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/api/products/{productId}", prod1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct(prod1.getId());
    }

}

