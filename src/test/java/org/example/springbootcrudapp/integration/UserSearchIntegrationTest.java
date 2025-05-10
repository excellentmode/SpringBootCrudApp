package org.example.springbootcrudapp.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springbootcrudapp.config.TestContainersConfig;
import org.example.springbootcrudapp.dto.security.AuthRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestContainersConfig.class)
public class UserSearchIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String jwtToken;

    @BeforeEach
    void setUp() throws Exception {
        AuthRequest request = new AuthRequest();
        request.setEmail("ivan@example.com");
        request.setPassword("password1231231235245");

        String body = objectMapper.writeValueAsString(request);

        String response = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        jwtToken = objectMapper.readTree(response).get("token").asText();
    }

    @Test
    void searchUsersByNameSuccessTest() throws Exception {
        mockMvc.perform(get("/api/v1/user/search")
                        .param("name", "Иван")
                        .param("page", "0")
                        .param("size", "5")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }
}