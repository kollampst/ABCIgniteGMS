package com.abcignitecms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abcignitecms.dto.ClassDTO;
import com.abcignitecms.model.GymClass;
import com.abcignitecms.service.ClassService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/classes")
@Validated
public class ClassController {
	private final ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @Operation(summary = "Create a new club/gym class")
    @PostMapping
    public ResponseEntity<String> createClass(@Valid @RequestBody ClassDTO classDTO) {
        GymClass gymClass = classService.createClass(classDTO);
        return ResponseEntity.ok("Class created successfully - " + gymClass);
    }

    @Operation(summary = "Get all club/gym classes")
    @GetMapping
    public ResponseEntity<?> getAllClasses() {
        return ResponseEntity.ok(classService.getAllClasses());
    }
}
