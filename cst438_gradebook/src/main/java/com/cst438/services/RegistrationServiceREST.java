package com.cst438.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cst438.domain.FinalGradeDTO;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.EnrollmentDTO;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.Enrollment;

@Service
@ConditionalOnProperty(prefix = "registration", name = "service", havingValue = "rest")
@RestController
public class RegistrationServiceREST implements RegistrationService {
	RestTemplate restTemplate = new RestTemplate();
	
	@Value("${registration.url}") 
	String registration_url;
	
	public RegistrationServiceREST() {
		System.out.println("REST registration service ");
	}
	
	@Override
	public void sendFinalGrades(int course_id , FinalGradeDTO[] grades) {
		String url = registration_url + course_id + "/finalgrades";

		try {
			restTemplate.put(url, grades);
			System.out.println("Final grades sent successfully.");

		} catch (Exception e) {
			System.err.println("Error sending final grades: " + e.getMessage());
		}
	}
	
	@Autowired
	CourseRepository courseRepository;

	@Autowired
	EnrollmentRepository enrollmentRepository;

	
	/*
	 * endpoint used by registration service to add an enrollment to an existing
	 * course.
	 */
	@PostMapping("/enrollment")
	@Transactional
	public EnrollmentDTO addEnrollment(@RequestBody EnrollmentDTO enrollmentDTO) {
		// Create a new enrollment entity from the received DTO
		Enrollment enrollment = new Enrollment();
		enrollment.setStudentName(enrollmentDTO.studentName());
		enrollment.setStudentEmail(enrollmentDTO.studentEmail());

		// Retrieve the course entity from the course repository based on the courseId
		Course course = courseRepository.findById(enrollmentDTO.courseId()).orElse(null);
		if (course != null) {
			enrollment.setCourse(course);
		}

		// Save the enrollment entity using the EnrollmentRepository
		enrollmentRepository.save(enrollment);

		// Return the received DTO as the response
		return enrollmentDTO;
	}

}
