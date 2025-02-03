package com.abcignitecms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abcignitecms.dto.ClassDTO;
import com.abcignitecms.exceptions.ClassAlreadyExistsException;
import com.abcignitecms.exceptions.InvalidDateException;
import com.abcignitecms.model.GymClass;
import com.abcignitecms.repository.ClassRepository;

@Service
public class ClassService {

    private final ClassRepository classRepository;

    @Autowired
    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public GymClass createClass(ClassDTO classDTO) {
    	// Check if the start date is after the end date
        if (classDTO.getStartDate().isAfter(classDTO.getEndDate())) {
            throw new InvalidDateException("Start date cannot be after the end date");
        }

        // Check if class with the same name already exists
        if (classRepository.findByName(classDTO.getName()) != null) {
            throw new ClassAlreadyExistsException("Class with this name already exists");
        }
        
        GymClass gymClass = new GymClass(
            classDTO.getName(),
            classDTO.getStartDate(),
            classDTO.getEndDate(),
            classDTO.getStartTime(),
            classDTO.getDuration(),
            classDTO.getCapacity()
        );
        classRepository.save(gymClass);
        return gymClass;
    }

    public Iterable<GymClass> getAllClasses() {
        return classRepository.findAll();
    }
}
