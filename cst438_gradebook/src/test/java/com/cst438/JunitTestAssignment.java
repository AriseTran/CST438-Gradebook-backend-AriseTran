package com.cst438;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cst438.domain.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Date;
import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class JunitTestAssignment {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AssignmentRepository assignmentRepository;


    @Test
    void testAddAssignment() throws Exception {
        Course testCourse = new Course();
        testCourse.setTitle("Test Course");
        testCourse.setCourse_id(1);
        testCourse.setYear(2023);
        testCourse.setInstructor("John Doe");
        testCourse.setSemester("Fall");

        // Create a sample AssignmentDTO for testing
        Assignment assignment = new Assignment();
        assignment.setName("Sample Assignment");
        assignment.setDueDate(Date.valueOf("2023-09-30"));
        assignment.setCourse(testCourse);


//        String assignmentJson = asJsonString(assignment);
//
//        // Perform an HTTP POST request to add the assignment
//        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders
//                        .post("/assignment")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(assignmentJson))
//                .andExpect(status().isOk())
//                .andReturn().getResponse();
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
