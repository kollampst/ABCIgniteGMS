package com.abcignitecms.repository;

import com.abcignitecms.model.GymClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

class ClassRepositoryTest {

    private ClassRepository classRepository;

    @BeforeEach
    void setUp() {
        // Instantiate the repository before each test
        classRepository = new ClassRepository();
    }

    @Test
    void testSaveGymClass() {
        // Create a GymClass instance with LocalDate and LocalTime
        GymClass gymClass = new GymClass("Yoga", 
                                          LocalDate.of(2023, 3, 1), 
                                          LocalDate.of(2023, 5, 1), 
                                          LocalTime.of(9, 0), 
                                          60, 
                                          20);

        // Save the GymClass to the repository
        classRepository.save(gymClass);

        // Verify that the GymClass is saved and can be found by its name
        assertEquals(gymClass, classRepository.findByName("Yoga"));
    }

    @Test
    void testFindAll() {
        // Create and save two GymClasses with LocalDate and LocalTime
        GymClass gymClass1 = new GymClass("Yoga", 
                                          LocalDate.of(2023, 3, 1), 
                                          LocalDate.of(2023, 5, 1), 
                                          LocalTime.of(9, 0), 
                                          60, 
                                          20);
        GymClass gymClass2 = new GymClass("Pilates", 
                                          LocalDate.of(2023, 3, 1), 
                                          LocalDate.of(2023, 5, 1), 
                                          LocalTime.of(10, 0), 
                                          45, 
                                          15);
        classRepository.save(gymClass1);
        classRepository.save(gymClass2);

        // Verify that findAll returns both GymClasses
        Iterable<GymClass> gymClasses = classRepository.findAll();
        assertNotNull(gymClasses);
        assertTrue(gymClasses.spliterator().getExactSizeIfKnown() == 2, "There should be two GymClasses.");
    }

    @Test
    void testFindByName_Found() {
        // Create and save a GymClass
        GymClass gymClass = new GymClass("Yoga", 
                                          LocalDate.of(2023, 3, 1), 
                                          LocalDate.of(2023, 5, 1), 
                                          LocalTime.of(9, 0), 
                                          60, 
                                          20);
        classRepository.save(gymClass);

        // Verify that the GymClass can be found by its name
        GymClass foundGymClass = classRepository.findByName("Yoga");
        assertNotNull(foundGymClass);
        assertEquals("Yoga", foundGymClass.getName());
    }

    @Test
    void testFindByName_NotFound() {
        // Try to find a GymClass that doesn't exist
        GymClass foundGymClass = classRepository.findByName("NonExistentClass");
        assertNull(foundGymClass, "GymClass should not be found.");
    }
}
