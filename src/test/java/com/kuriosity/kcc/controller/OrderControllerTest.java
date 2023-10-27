package com.kuriosity.kcc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuriosity.kcc.model.Order;
import com.kuriosity.kcc.model.User;
import com.kuriosity.kcc.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration
@WithMockUser(username = "tester")
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    ObjectMapper mapper;

    Order order1;

    User user = User.builder()
            .username("tester")
            .email("test@email.com")
            .password("password")
            .build();

    @BeforeEach
    void setUp() {
        order1 = new Order(1L, Date.from(TemporalType.DATE), 50.00, "Pending", user);
    }


    @Test
    void getOrders_success() throws Exception {
        when(orderService.getOrder()).thenReturn(Optional.of(order1));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getOrder_success() throws Exception {
        when(orderService.getOrder(order1.getId())).thenReturn(Optional.of(order1));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders" + order1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createOrder_success() throws Exception {
        when(orderService.createOrder(any(Order.class))).thenReturn(order1);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(order1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void updateOrder_success() throws Exception {
        Order update = new Order(order1.getId(), Date.from(TemporalType.DATE), 50.00, "Pending", user);

        when(orderService.updateOrder(order1.getId(), update)).thenReturn(update);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/api/orders/{orderId}", order1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(update));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    @Test
    void deleteOrder_success() throws Exception {

        when(orderService.deleteOrder(order1.getId()))
                .thenReturn(Optional.of(order1));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/api/orders/{orderId}", order1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent());

        verify(orderService).deleteOrder(order1.getId());
    }

}
