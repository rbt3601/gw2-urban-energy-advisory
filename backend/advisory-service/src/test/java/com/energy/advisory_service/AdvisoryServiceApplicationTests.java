package com.energy.advisory_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdvisoryServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void submitAssessmentReturnsCompletedWhenAllDataAvailable() throws Exception {
        String body = """
                {
                  "requestId": "REQ-1001",
                  "createdAt": "2026-03-15T10:00:00Z",
                  "property": {
                    "propertyId": "PROP-001",
                    "location": "Leicester green cold",
                    "propertyType": "home"
                  },
                  "timeWindow": {
                    "startDateTime": "2026-03-01T00:00:00Z",
                    "endDateTime": "2026-03-02T00:00:00Z",
                    "granularity": "hourly"
                  }
                }
                """;

        mockMvc.perform(post("/submitEnergyAssessment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.assessmentStatus").value("Completed"))
                .andExpect(jsonPath("$.energyConsumptionData.sourceName").exists())
                .andExpect(jsonPath("$.weatherContextData.sourceName").exists())
                .andExpect(jsonPath("$.carbonIntensityData.sourceName").exists());
    }

    @Test
    void submitAssessmentReturnsPartialWhenCarbonUnavailable() throws Exception {
        String body = """
                {
                  "requestId": "REQ-1002",
                  "createdAt": "2026-03-15T10:00:00Z",
                  "property": {
                    "propertyId": "PROP-002",
                    "location": "Leicester no_carbon",
                    "propertyType": "home"
                  },
                  "timeWindow": {
                    "startDateTime": "2026-03-01T00:00:00Z",
                    "endDateTime": "2026-03-02T00:00:00Z",
                    "granularity": "hourly"
                  }
                }
                """;

        mockMvc.perform(post("/submitEnergyAssessment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.assessmentStatus").value("Partial"))
                .andExpect(jsonPath("$.energyConsumptionData.sourceName").exists())
                .andExpect(jsonPath("$.carbonIntensityData").doesNotExist());
    }

    @Test
    void submitAssessmentReturnsFailedWhenEnergyUnavailable() throws Exception {
        String body = """
                {
                  "requestId": "REQ-1003",
                  "createdAt": "2026-03-15T10:00:00Z",
                  "property": {
                    "propertyId": "FAIL-ENERGY-001",
                    "location": "Leicester",
                    "propertyType": "home"
                  },
                  "timeWindow": {
                    "startDateTime": "2026-03-01T00:00:00Z",
                    "endDateTime": "2026-03-02T00:00:00Z",
                    "granularity": "hourly"
                  }
                }
                """;

        mockMvc.perform(post("/submitEnergyAssessment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.assessmentStatus").value("Failed"))
                .andExpect(jsonPath("$.errorMessage").exists())
                .andExpect(jsonPath("$.completedAt").doesNotExist());
    }

    @Test
    void getAssessmentReturnsNotFoundForUnknownId() throws Exception {
        mockMvc.perform(get("/assessments/UNKNOWN"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Assessment not found with id: UNKNOWN"));
    }
}
