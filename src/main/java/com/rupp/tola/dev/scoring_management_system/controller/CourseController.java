package com.rupp.tola.dev.scoring_management_system.controller;

import com.rupp.tola.dev.scoring_management_system.data.MultipleResponse;
import com.rupp.tola.dev.scoring_management_system.data.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.request.CourseRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.PaginationRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.CourseResponse;
import com.rupp.tola.dev.scoring_management_system.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

	private final CourseService courseService;

	@GetMapping
	public ResponseEntity<MultipleResponse<CourseResponse>> getAll(PaginationRequest request) {
		if ("id".equals(request.getSortBy())) {
			request.setSortBy("name");
		}

		Page<CourseResponse> response = courseService.getAll(request.toPageable());
		return ResponseEntity
				.ok(MultipleResponse.success("Successfully retrieved all courses with pagination.", response));
	}

	@GetMapping("/{semesterId}/{subjectId}")
	public ResponseEntity<SingleResponse<CourseResponse>> getById(@PathVariable UUID semesterId,
			@PathVariable UUID subjectId) {
		CourseResponse courseResponse = courseService.getById(semesterId, subjectId);
		return ResponseEntity.ok(SingleResponse.success("Successfully to retrieve course data.", courseResponse));
	}

	@PostMapping
	public ResponseEntity<SingleResponse<CourseResponse>> create(@Valid @RequestBody CourseRequest courseRequest) {
		CourseResponse response = courseService.create(courseRequest);
		return ResponseEntity.ok(SingleResponse.success("Successfully to create course data.", response));
	}

	@PutMapping("/{semesterId}/{subjectId}")
	public ResponseEntity<SingleResponse<CourseResponse>> update(@PathVariable UUID semesterId,
			@PathVariable UUID subjectId, @RequestBody @Valid CourseRequest courseRequest) {
		return ResponseEntity.ok(SingleResponse.success("Successfully to update course data.",
				courseService.update(semesterId, subjectId, courseRequest)));
	}

	@DeleteMapping("/{semesterId}/{subjectId}")
	public ResponseEntity<SingleResponse<Void>> delete(@PathVariable UUID semesterId, @PathVariable UUID subjectId) {
		courseService.delete(semesterId, subjectId);
		return ResponseEntity.ok(SingleResponse.success("Successfully to delete course data.", null));
	}

}
