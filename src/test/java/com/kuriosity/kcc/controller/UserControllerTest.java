package com.kuriosity.kcc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuriosity.kcc.model.User;
import com.kuriosity.kcc.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp(){
        user = User.builder()
                .email("test@test.com")
                .password("password")
                .build();
    }

    @Test
    void testSignUp() throws Exception {

        Mockito.when(userService.register(Mockito.any(User.class)));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(user));

        mockMvc.perform(mockRequest)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("sampleJWTToken"));
    }

    @Test
    void testLogin() throws Exception {

        Mockito.when(userService.login(Mockito.any(com.kuriosity.kcc.model.LoginRequest.class))).thenReturn(Optional.of("jwt"));

        // Perform a POST request to the login endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(user.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(user.getPassword()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(user.getAddress()));

    }
}


