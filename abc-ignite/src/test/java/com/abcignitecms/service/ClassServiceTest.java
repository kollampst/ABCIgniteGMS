package com.abcignitecms.service;

import com.abcignitecms.dto.ClassDTO;
import com.abcignitecms.exceptions.CapacityExceededException;
import com.abcignitecms.exceptions.ClassAlreadyExistsException;
import com.abcignitecms.exceptions.InvalidDateException;
import com.abcignitecms.model.GymClass;
import com.abcignitecms.repository.ClassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClassServiceTest {

    @Mock
    private ClassRepository classRepository;

    @InjectMocks
    private ClassService classService;

    private ClassDTO validClassDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        validClassDTO = new ClassDTO(
                "Yoga",
                LocalDate.of(2025, 2, 15),
                LocalDate.of(2025, 3, 15),
                LocalTime.of(10, 0),
                60,
                30
        );
    }

    @Test
    void testCreateClass_Success() {
        // Arrange
        when(classRepository.findByName(validClassDTO.getName())).thenReturn(null); // No class with the same name
        doNothing().when(classRepository).save(any(GymClass.class));
        GymClass expectedGymClass = new GymClass(
                validClassDTO.getName(),
                validClassDTO.getStartDate(),
                validClassDTO.getEndDate(),
                validClassDTO.getStartTime(),
                validClassDTO.getDuration(),
                validClassDTO.getCapacity()
        );

        // Act
        GymClass actualGymClass = classService.createClass(validClassDTO);

        // Assert
        assertNotNull(actualGymClass);
        assertEquals(expectedGymClass.getName(), actualGymClass.getName());
        assertEquals(expectedGymClass.getStartDate(), actualGymClass.getStartDate());
        assertEquals(expectedGymClass.getEndDate(), actualGymClass.getEndDate());
        assertEquals(expectedGymClass.getStartTime(), actualGymClass.getStartTime());
        assertEquals(expectedGymClass.getDuration(), actualGymClass.getDuration());
        assertEquals(expectedGymClass.getCapacity(), actualGymClass.getCapacity());

        verify(classRepository, times(1)).save(any(GymClass.class));
    }

    @Test
    void testCreateClass_StartDateAfterEndDate() {
        // Arrange
        ClassDTO invalidClassDTO = new ClassDTO(
                "Yoga",
                LocalDate.of(2025, 3, 1), // Start date
                LocalDate.of(2025, 2, 15), // End date (before start date)
                LocalTime.of(10, 0),
                60,
                30
        );

        // Act & Assert
        InvalidDateException exception = assertThrows(InvalidDateException.class, () -> {
            classService.createClass(invalidClassDTO);
        });
        assertEquals("Start date cannot be after the end date", exception.getMessage());
    }

    @Test
    void testCreateClass_ClassAlreadyExists() {
        // Arrange
        when(classRepository.findByName(validClassDTO.getName())).thenReturn(new GymClass(
                validClassDTO.getName(),
                validClassDTO.getStartDate(),
                validClassDTO.getEndDate(),
                validClassDTO.getStartTime(),
                validClassDTO.getDuration(),
                validClassDTO.getCapacity()
        ));

        // Act & Assert
        ClassAlreadyExistsException exception = assertThrows(ClassAlreadyExistsException.class, () -> {
            classService.createClass(validClassDTO);
        });
        assertEquals("Class with this name already exists", exception.getMessage());
    }

    @Test
    void testCreateClass_EmptyClassName() {
        // Arrange
        ClassDTO invalidClassDTO = new ClassDTO(
                "", // Empty class name
                LocalDate.of(2025, 2, 15),
                LocalDate.of(2025, 3, 15),
                LocalTime.of(10, 0),
                60,
                30
        );

        // Act & Assert
        ClassAlreadyExistsException exception = assertThrows(ClassAlreadyExistsException.class, () -> {
            classService.createClass(invalidClassDTO);
        });
        assertEquals("Class with this name already exists", exception.getMessage());
    }

    @Test
    void testCreateClass_withZeroCapacity_shouldThrowException() {
        // Arrange
    	ClassDTO classDTO = new ClassDTO(
                "Yoga Class", 
                LocalDate.now().plusDays(1), // future start date
                LocalDate.now().plusDays(5), // future end date
                LocalTime.of(10, 0), // valid start time
                60, // valid duration
                0 // invalid capacity, less than 1
        );

        // Act & Assert
        assertThrows(CapacityExceededException.class, () -> classService.createClass(classDTO));
    }

    @Test
    void testCreateClass_EmptyDuration() {
        // Arrange
        ClassDTO invalidClassDTO = new ClassDTO(
                "Yoga",
                LocalDate.of(2025, 2, 15),
                LocalDate.of(2025, 3, 15),
                LocalTime.of(10, 0),
                0, // Zero duration
                30
        );

        // Act
        GymClass createdClass = classService.createClass(invalidClassDTO);

        // Assert
        assertEquals(0, createdClass.getDuration());
    }

    @Test
    void testGetAllClasses() {
        // Arrange
        GymClass gymClass1 = new GymClass("Yoga", LocalDate.of(2025, 2, 15), LocalDate.of(2025, 3, 15), LocalTime.of(10, 0), 60, 30);
        GymClass gymClass2 = new GymClass("Zumba", LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 10), LocalTime.of(11, 0), 45, 25);

        when(classRepository.findAll()).thenReturn(List.of(gymClass1, gymClass2));

        // Act
        Iterable<GymClass> allClasses = classService.getAllClasses();

        // Assert
        assertNotNull(allClasses);
        assertTrue(allClasses.iterator().hasNext());
        assertEquals(2, ((List<?>) allClasses).size());
    }
}
