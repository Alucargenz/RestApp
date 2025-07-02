package org.controller;

import org.example.Main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPublicEndpoint() throws Exception {
        mockMvc.perform(get("/api/public"))
                .andExpect(status().isOk())
                .andExpect(content().string("This is a public endpoint."));
    }

    @Test
    public void testUserEndpointWithUserCredentials() throws Exception {
        mockMvc.perform(get("/api/user").with(httpBasic("user", "password")))
                .andExpect(status().isOk());
    }

    @Test
    public void testAdminEndpointWithAdminCredentials() throws Exception {
        mockMvc.perform(post("/api/admin")
                        .with(httpBasic("admin", "admin"))
                        .contentType("application/json")
                        .content("{\"content\":\"Test\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAdminEndpointUnauthorized() throws Exception {
        mockMvc.perform(post("/api/admin")
                        .with(httpBasic("user", "password"))
                        .contentType("application/json")
                        .content("{\"content\":\"Test\"}"))
                .andExpect(status().isForbidden());
    }
}

