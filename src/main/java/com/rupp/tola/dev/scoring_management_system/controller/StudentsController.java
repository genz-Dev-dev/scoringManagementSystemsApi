package com.rupp.tola.dev.scoring_management_system.controller;

import java.util.List;
import java.util.Map;

import com.rupp.tola.dev.scoring_management_system.dto.request.StudentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.StudentResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rupp.tola.dev.scoring_management_system.dto.StudentsDTO;
import com.rupp.tola.dev.scoring_management_system.entity.Students;
import com.rupp.tola.dev.scoring_management_system.mapper.StudentsMapper;
import com.rupp.tola.dev.scoring_management_system.service.StudentService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentsController {

	private final StudentService studentService;

	@PostMapping
	public ResponseEntity<StudentResponse> createStudents(@Valid  @RequestBody StudentRequest request) {
		StudentResponse response = studentService.createStudents(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// get all students
	@GetMapping
	public ResponseEntity<List<StudentResponse>> getAllStudents() {
		List<StudentResponse> students = studentService.getStudents();
		return ResponseEntity.ok(students);
	}

	// get all student 
	//	@GetMapping("/getByStatus")
	//	public ResponseEntity<List<Students>> getByStatus(@RequestParam(defaultValue = "false") Boolean status,
	//			Map<String, String> param) {
	//		return ResponseEntity.ok(studentService.getByStatus(status));
	//	}
	
}