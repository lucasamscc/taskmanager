package me.lucasamscc.taskmanager.modules.task.subtask;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubtaskController.class)
class SubtaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SubtaskService subtaskService;

    @Test
    @DisplayName("Should correctly access the subtask status endpoint.")
    void shouldAccessPatchStatusRoute() throws Exception {
        UUID id = UUID.randomUUID();
        String json = "{\"status\": \"COMPLETED\"}";

        mockMvc.perform(patch("/subtasks/" + id + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }
}