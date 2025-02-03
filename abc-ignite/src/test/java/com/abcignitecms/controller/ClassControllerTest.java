package com.abcignitecms.controller;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.abcignitecms.dto.ClassDTO;
import com.abcignitecms.model.GymClass;
import com.abcignitecms.service.ClassService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@WebMvcTest(ClassController.class)  // WebMvcTest will only scan for controllers
public class ClassControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean  // This will provide a mocked instance of ClassService to the Spring context
    private ClassService classService;

    private ClassDTO validClassDTO;

    @BeforeEach
    public void setUp() {
        validClassDTO = new ClassDTO(
                "Yoga",
                LocalDate.now().plusDays(1), // Start date in the future
                LocalDate.now().plusMonths(1), // End date in the future
                LocalTime.of(10, 0), // Start time 10:00 AM
                60, // Duration in minutes
                20 // Capacity
        );
    }

    @Test
    public void createClass_ValidData_Returns200() throws Exception {
        GymClass gymClass = new GymClass(
                validClassDTO.getName(),
                validClassDTO.getStartDate(),
                validClassDTO.getEndDate(),
                validClassDTO.getStartTime(),
                validClassDTO.getDuration(),
                validClassDTO.getCapacity()
        );

        // Mock the classService to return the GymClass
        when(classService.createClass(validClassDTO)).thenReturn(gymClass);

        // Perform the request and assert the response
        mockMvc.perform(post("/classes")
                .contentType("application/json")
                .content("{\n" +
                        "  \"name\": \"Yoga\",\n" +
                        "  \"startDate\": \"2025-03-01\",\n" +
                        "  \"endDate\": \"2025-04-01\",\n" +
                        "  \"startTime\": \"10:00\",\n" +
                        "  \"duration\": 60,\n" +
                        "  \"capacity\": 20\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Class created successfully")));
    }
    
    @Test
    public void createClass_InvalidDate_Returns400() throws Exception {
    	String invalidJson = "{\n" +
                "  \"name\": \"Yoga\",\n" +
                "  \"startDate\": \"2020-01-01\",\n" +  // Invalid past date
                "  \"endDate\": \"2025-04-01\",\n" +
                "  \"startTime\": \"10:00\",\n" +
                "  \"duration\": 60,\n" +
                "  \"capacity\": 20\n" +
                "}";

        mockMvc.perform(post("/classes")
                .contentType("application/json")
                .content(invalidJson))
                .andExpect(status().isBadRequest())  // Expecting 400 Bad Request
                .andExpect(jsonPath("$.startDate").value("Start date must be in the present or future"));
    }

    @Test
    public void createClass_InvalidCapacity_Returns400() throws Exception {
        // Invalid capacity: less than 1
        ClassDTO invalidClassDTO = new ClassDTO(
                "Yoga",
                LocalDate.now().plusDays(1),
                LocalDate.now().plusMonths(1),
                LocalTime.of(10, 0),
                60,
                0 // Invalid capacity
        );

        mockMvc.perform(post("/classes")
                .contentType("application/json")
                .content("{\n" +
                        "  \"name\": \"Yoga\",\n" +
                        "  \"startDate\": \"2025-03-01\",\n" +
                        "  \"endDate\": \"2025-04-01\",\n" +
                        "  \"startTime\": \"10:00\",\n" +
                        "  \"duration\": 60,\n" +
                        "  \"capacity\": 0\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"capacity\":\"Capacity must be at least 1\"}"));
    }

    @Test
    public void getAllClasses_Returns200() throws Exception {
        GymClass gymClass1 = new GymClass("Yoga", LocalDate.now().plusDays(1), LocalDate.now().plusMonths(1), LocalTime.of(10, 0), 60, 20);
        GymClass gymClass2 = new GymClass("Pilates", LocalDate.now().plusDays(2), LocalDate.now().plusMonths(1), LocalTime.of(11, 0), 45, 15);

        when(classService.getAllClasses()).thenReturn(List.of(gymClass1, gymClass2));

        mockMvc.perform(get("/classes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Yoga"))
                .andExpect(jsonPath("$[1].name").value("Pilates"));
    }

}
