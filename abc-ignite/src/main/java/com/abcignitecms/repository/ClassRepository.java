package com.abcignitecms.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.abcignitecms.model.GymClass;
@Repository
public class ClassRepository {
	 private final List<GymClass> gymClasses = new ArrayList<>();

	    public void save(GymClass gymClass) {
	        gymClasses.add(gymClass);
	    }

	    public Iterable<GymClass> findAll() {
	        return gymClasses;
	    }

	    public GymClass findByName(String name) {
	        return gymClasses.stream()
	            .filter(c -> c.getName().equals(name))
	            .findFirst()
	            .orElse(null);
	    }
}