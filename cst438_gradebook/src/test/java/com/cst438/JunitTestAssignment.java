package com.cst438;

import com.cst438.domain.AssignmentDTO;
import com.cst438.domain.AssignmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JunitTestAssignment {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Test
    void testGetAllAssignment() throws Exception {
        // Get all assignments - assert status 200
        mvc.perform(MockMvcRequestBuilders.get("/assignment"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }

    @Test
    void testAddAssignment() throws Exception {
        // Add assignment Testname - assert status 200
        AssignmentDTO adto = new AssignmentDTO(1, "Testname", "2023-09-09", null, 31045);
        mvc.perform(MockMvcRequestBuilders.post("/assignment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(adto))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void testModifyAssignment() throws Exception {
        // Modify assignment - assert status 200
        AssignmentDTO updatedAssignment = new AssignmentDTO(3, "UpdatedName", "2023-09-09", null, 31046);
        mvc.perform(MockMvcRequestBuilders.put("/assignment/{assignmentId}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedAssignment))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteAssignment() throws Exception {
        // Delete assignment - assert status 200
        mvc.perform(MockMvcRequestBuilders.delete("/assignment/{assignmentId}", 1))
                .andExpect(status().isOk());
        // Delete a second time - assert status 404
        mvc.perform(MockMvcRequestBuilders.delete("/assignment/{assignmentId}", 1))
                .andExpect(status().isNotFound());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
