package com.rupp.tola.dev.scoring_management_system.controller;

import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.dto.StudentsDTO;
import com.rupp.tola.dev.scoring_management_system.dto.request.ImportStudentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.StudentRequest;
import com.rupp.tola.dev.scoring_management_system.data.MultipleResponse;
import com.rupp.tola.dev.scoring_management_system.data.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.response.StudentResponse;
import com.rupp.tola.dev.scoring_management_system.service.ExcelService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.rupp.tola.dev.scoring_management_system.service.StudentService;

import lombok.RequiredArgsConstructor;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentsController {

	private final StudentService studentService;
	private final ExcelService excelService;

	@PostMapping
	public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentRequest request) {
		StudentResponse response = studentService.create(request);
		log.info("create: {}", request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<MultipleResponse<StudentResponse>> findAll(
			@RequestParam Map<String, String> param
	) {
		Page<StudentResponse> responses = studentService.getAll(param);
		return ResponseEntity.ok().body(MultipleResponse.success("Retrieve all students with pagination.", responses));
	}

	@GetMapping(path = "/{uuid}")
	public ResponseEntity<StudentResponse> getByUuid(@PathVariable UUID uuid) {
		StudentResponse studentsResponse = studentService.getById(uuid);
		log.info("getByUuid: {}", uuid);
		return ResponseEntity.ok(studentsResponse);
	}

	@PutMapping(path = "/{uuid}")
	public ResponseEntity<StudentResponse> updateByUuid(
			@PathVariable UUID uuid,
			@RequestBody StudentsDTO studentsDTO
			) {
		log.info("UpdateByUuid: uuid{}, studentsDto: {}",uuid, studentsDTO);
		return null;
	}

	@DeleteMapping(path = "/{uuid}")
	public ResponseEntity<SingleResponse<Void>> deleteByUuid(@PathVariable UUID uuid) {
		studentService.delete(uuid);
		log.info("DeleteByUuid: {}", uuid);
		return ResponseEntity.ok().body(SingleResponse.success("Delete student successfully.", null));
	}

	@GetMapping("/getByStatus")
	public ResponseEntity<MultipleResponse<StudentResponse>> getByStatus(
			@RequestParam(defaultValue = "false") Boolean status,
			@RequestParam Map<String, String> param
	) {
		Page<StudentResponse> responses = studentService.getByStatusPagination(param, status);
		return ResponseEntity.ok().body(MultipleResponse.success("Retrieve students by status.", responses));
	}

	@PostMapping(path = "/import-student", consumes = MediaType.MULTIPART_FORM_DATA_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<?>> importStudent(@ModelAttribute ImportStudentRequest request) {
		return ResponseEntity.ok(SingleResponse.success("Import student successfully.", studentService.importStudents(request)));

	}

	@GetMapping(path = "/export-student")
	public ResponseEntity<Resource> exportStudent() {
		ByteArrayInputStream stream = excelService.exportStudent();
		ByteArrayResource recourse = new ByteArrayResource(stream.readAllBytes());
		return ResponseEntity
				.status(HttpStatus.OK)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=students.xlsx")
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(recourse);
	}

}