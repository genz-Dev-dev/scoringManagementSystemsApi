package com.rupp.tola.dev.scoring_management_system.controller;

import java.io.ByteArrayInputStream;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.dto.request.ImportStudentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.PaginationRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.StudentRequest;
import com.rupp.tola.dev.scoring_management_system.data.MultipleResponse;
import com.rupp.tola.dev.scoring_management_system.data.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.response.ClassResponse;
import com.rupp.tola.dev.scoring_management_system.dto.response.StudentResponse;
import com.rupp.tola.dev.scoring_management_system.dto.response.StudentStatisticsResponse;
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

	@GetMapping
	public ResponseEntity<MultipleResponse<StudentResponse>> findAll(PaginationRequest request) {
		Page<StudentResponse> responses = studentService.getAll(request.toPageable());
		return ResponseEntity.ok()
				.body(MultipleResponse.success("Successfully retrieved all students with pagination.", responses));
	}

	@GetMapping(path = "/{uuid}")
	public ResponseEntity<SingleResponse<StudentResponse>> getById(@PathVariable UUID uuid) {
		StudentResponse studentsResponse = studentService.getById(uuid);
		log.info("getByUuid: {}", uuid);
		return ResponseEntity.ok(SingleResponse.success("Successfully retrieved student.", studentsResponse));
	}

	@PostMapping
	public ResponseEntity<SingleResponse<StudentResponse>> create(@Valid @RequestBody StudentRequest request) {
		StudentResponse response = studentService.create(request);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(SingleResponse.success("Successfully created student.", response));
	}

	@PutMapping(path = "/{uuid}")
	public ResponseEntity<SingleResponse<StudentResponse>> update(@PathVariable UUID uuid,
			@Valid @RequestBody StudentRequest request) {
		StudentResponse response = studentService.update(uuid, request);
		return ResponseEntity.ok(SingleResponse.success("Successfully updated student.", response));
	}

	@DeleteMapping(path = "/{uuid}")
	public ResponseEntity<SingleResponse<Void>> delete(@PathVariable UUID uuid) {
		studentService.delete(uuid);
		return ResponseEntity.ok(SingleResponse.success("Successfully deleted student.", null));
	}

	@GetMapping(path = "/{uuid}/classes")
	public ResponseEntity<SingleResponse<ClassResponse>> getClassByStudentId(@PathVariable UUID uuid) {
		ClassResponse response = studentService.getClassByStudentId(uuid);
		return ResponseEntity.ok(SingleResponse.success("Successfully retrieved student class.", response));
	}

	@PostMapping(path = "/import-student", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<?>> importStudent(@ModelAttribute ImportStudentRequest request) {
		return ResponseEntity
				.ok(SingleResponse.success("Import student successfully.", studentService.importStudents(request)));

	}

	@GetMapping(path = "/export-student")
	public ResponseEntity<Resource> exportStudent(@RequestParam UUID classId) {
		ByteArrayInputStream stream = excelService.exportStudent(classId);
		ByteArrayResource recourse = new ByteArrayResource(stream.readAllBytes());
		return ResponseEntity.status(HttpStatus.OK)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=students-" + classId + ".xlsx")
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(recourse);
	}

	@GetMapping("/statistics")
	public ResponseEntity<SingleResponse<StudentStatisticsResponse>> getStatistics() {
		StudentStatisticsResponse response = studentService.statistics();
		return ResponseEntity
				.ok(SingleResponse.success("Successfully retrieved student statistics response.", response));
	}

}
