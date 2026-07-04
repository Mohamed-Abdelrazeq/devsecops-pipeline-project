package com.devsecops;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@AutoConfigureMockMvc
class NumericApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RestTemplate restTemplate;

    @Test
    void smallerThanOrEqualToFiftyMessage() throws Exception {
        mockMvc.perform(get("/compare/50"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Smaller than or equal to 50"));
    }

    @Test
    void greaterThanFiftyMessage() throws Exception {
        mockMvc.perform(get("/compare/51"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Greater than 50"));
    }

    @Test
    void welcomeMessage() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Kubernetes DevSecOps"));
    }

    @Test
    void incrementReturnsIncrementedValue() throws Exception {

        when(restTemplate.getForEntity(
                "http://node-service:5000/plusone/5",
                String.class))
                .thenReturn(ResponseEntity.ok("6"));

        mockMvc.perform(get("/increment/5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("6"));
    }
}