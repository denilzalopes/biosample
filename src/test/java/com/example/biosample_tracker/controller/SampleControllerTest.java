package com.example.biosample_tracker.controller;

import com.example.biosample_tracker.model.Sample;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateSample_shouldReturnCreatedSample() throws Exception {
        Sample sample = new Sample();
        sample.setCode("CODE123");
        sample.setDescription("Description de test");

        mockMvc.perform(post("/api/samples")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("CODE123"));
    }

    @Test
    void testGetSampleById_shouldReturnSample() throws Exception {
        Sample sample = new Sample();
        sample.setCode("CODE1");
        sample.setDescription("desc");
        String response = mockMvc.perform(post("/api/samples")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Number id = JsonPath.read(response, "$.id");

        mockMvc.perform(get("/api/samples/" + id.longValue()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.longValue()));
    }

    @Test
    void testUpdateSample_shouldReturnUpdatedSample() throws Exception {
        Sample sample = new Sample();
        sample.setCode("CODE2");
        sample.setDescription("desc2");
        String response = mockMvc.perform(post("/api/samples")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Number id = JsonPath.read(response, "$.id");

        Sample updated = new Sample();
        updated.setCode("UPDATED");
        updated.setDescription("desc modifiée");

        mockMvc.perform(put("/api/samples/" + id.longValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("UPDATED"));
    }

    @Test
    void testCreateSample_shouldReturnValidationError() throws Exception {
        Sample sample = new Sample(); // champs vides ou invalides

        mockMvc.perform(post("/api/samples")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").exists()); // adapte selon le champ requis
    }

    @Test
    void testDeleteSample_shouldReturnNoContent() throws Exception {
        Sample sample = new Sample();
        sample.setCode("CODE3");
        sample.setDescription("desc3");
        String response = mockMvc.perform(post("/api/samples")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Number id = JsonPath.read(response, "$.id");

        mockMvc.perform(delete("/api/samples/" + id.longValue()))
                .andExpect(status().isNoContent());
    }
}