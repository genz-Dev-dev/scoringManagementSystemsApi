package com.rupp.tola.dev.scoring_management_system.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rupp.tola.dev.scoring_management_system.dto.StudentsDTO;
import com.rupp.tola.dev.scoring_management_system.entity.Students;
import com.rupp.tola.dev.scoring_management_system.mapper.StudentsMapper;
import com.rupp.tola.dev.scoring_management_system.service.StudentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentsController {

	private final StudentService studentService;

	@PostMapping
	public ResponseEntity<StudentsDTO> createStudents(@RequestBody StudentsDTO studentsDTO) {
		Students students = StudentsMapper.iNSTANCE.toStudents(studentsDTO);
		students = studentService.createStudents(students);

		return ResponseEntity.status(HttpStatus.CREATED).body(StudentsMapper.iNSTANCE.toStudentsDTO(students));
	}

// get all students
	@GetMapping
	public ResponseEntity<List<Students>> getStudents() {
		return ResponseEntity.ok(studentService.getStudents());
	}

// get all student 
//	@GetMapping("/getByStatus")
//	public ResponseEntity<List<Students>> getByStatus(@RequestParam(defaultValue = "false") Boolean status,
//			Map<String, String> param) {
//		return ResponseEntity.ok(studentService.getByStatus(status));
//	}
}
