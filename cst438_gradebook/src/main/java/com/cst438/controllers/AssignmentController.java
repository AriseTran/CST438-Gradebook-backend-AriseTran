package com.cst438.controllers;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentDTO;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
		String instructorEmail = "dwisneski@csumb.edu";  // username (should be instructor's email)
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
	public int addAssignment(@RequestBody AssignmentDTO adto){
		String instructorEmail = "dwisneski@csumb.edu";

		Course c = courseRepository.findById(adto.courseId()).orElse(null);
		if(c == null || !c.getInstructor().equals(instructorEmail)){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "course id not found");
		}

		Assignment assignment = new Assignment();
		assignment.setCourse(c);
		assignment.setDueDate(java.sql.Date.valueOf(adto.dueDate()));
		assignment.setName(adto.assignmentName());

		// Add assignment
		assignmentRepository.save(assignment);

		return assignment.getId();
	}

	@PutMapping("/assignment/{assignmentId}")
	public void updateAssignment(@PathVariable int assignmentId, @RequestBody AssignmentDTO updatedAssignment) {
		// Check if the assignment exists
		Assignment assignment = assignmentRepository.findById(assignmentId).orElse(null);
		if (assignment == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found");
		}

		// Update assignment
		assignment.setDueDate(java.sql.Date.valueOf(updatedAssignment.dueDate()));
		assignment.setName(updatedAssignment.assignmentName());

		assignmentRepository.save(assignment);
	}

	@DeleteMapping("/assignment/{assignmentId}")
	public void deleteAssignment(@PathVariable int assignmentId) {
		// Check if the assignment exists
		Assignment assignment = assignmentRepository.findById(assignmentId).orElse(null);
		if (assignment == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found");
		}

		// Delete assignment
		assignmentRepository.delete(assignment);
	}

	// TODO create CRUD methods for Assignment
}
