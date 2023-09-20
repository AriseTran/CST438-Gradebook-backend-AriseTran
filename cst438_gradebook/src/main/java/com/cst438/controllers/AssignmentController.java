package com.cst438.controllers;

import com.cst438.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.sql.Date;
import java.util.List;

@RestController
@CrossOrigin 
public class AssignmentController {
	
	@Autowired
	AssignmentRepository assignmentRepository;
	
	@Autowired
	CourseRepository courseRepository;
	
	@GetMapping("/assignment")
	public AssignmentDTO[] getAllAssignmentsForInstructor() {
		// get all assignments for this instructor
		String instructorEmail = "dwisneski@csumb.edu";  // user name (should be instructor's email) 
		List<Assignment> assignments = assignmentRepository.findByEmail(instructorEmail);
		AssignmentDTO[] result = new AssignmentDTO[assignments.size()];
		for (int i=0; i<assignments.size(); i++) {
			Assignment as = assignments.get(i);
			AssignmentDTO dto = new AssignmentDTO(
					as.getId(), 
					as.getName(), 
					as.getDueDate().toString(), 
					as.getCourse().getTitle(), 
					as.getCourse().getCourse_id());
			result[i]=dto;
		}
		return result;
	}

	@PostMapping("/assignment")
	public ResponseEntity<String> addAssignment() {
		Assignment assignment = new Assignment();
		assignment.setName(assignment.getName());
		assignment.setDueDate(Date.valueOf(assignment.getDueDate().toLocalDate()));

		Course course = courseRepository.findById(assignment.getCourse().getCourse_id())
				.orElseThrow(() -> new EntityNotFoundException("Course not found"));

		assignment.setCourse(course);

		assignmentRepository.save(assignment);

		return ResponseEntity.ok("Assignment added successfully");
	}

	@PutMapping("/assignment/{assignmentId}")
	public ResponseEntity<String> modifyAssignment(@PathVariable int assignmentId) {
		Assignment assignment = assignmentRepository.findById(assignmentId)
				.orElseThrow(() -> new EntityNotFoundException("Assignment not found"));

		assignment.setName(assignment.getName());
		assignment.setDueDate(Date.valueOf(assignment.getDueDate().toLocalDate()));

		Course course = courseRepository.findById(assignment.getCourse().getCourse_id())
				.orElseThrow(() -> new EntityNotFoundException("Course not found"));

		assignment.setCourse(course);

		assignmentRepository.save(assignment);

		return ResponseEntity.ok("Assignment modified successfully");
	}

	@DeleteMapping("/assignment/{assignmentId}")
	public ResponseEntity<String> deleteAssignment(@PathVariable int assignmentId) {
		Assignment assignment = assignmentRepository.findById(assignmentId)
				.orElseThrow(() -> new EntityNotFoundException("Assignment not found"));

		assignmentRepository.delete(assignment);

		return ResponseEntity.ok("Assignment deleted successfully");
	}

	// TODO create CRUD methods for Assignment
}
