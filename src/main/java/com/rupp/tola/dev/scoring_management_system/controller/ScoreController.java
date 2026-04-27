package com.rupp.tola.dev.scoring_management_system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rupp.tola.dev.scoring_management_system.data.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.request.ScoreRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ScoreResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Score;
import com.rupp.tola.dev.scoring_management_system.mapper.ScoreMapper;
import com.rupp.tola.dev.scoring_management_system.service.ScoreService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping(path = "/scores")
@RequiredArgsConstructor
public class ScoreController {

	private final ScoreService scoreService;
	private final ScoreMapper scoreMapper;

//	@PostMapping
//	public ResponseEntity<MultipleResponse<ScoreResponse>> createNew(@RequestBody ScoreRequest request) {
//
//		// 1. Save entity
//		Score score = scoreService.create(request);
//
//		ScoreResponse response = scoreMapper.toResponse(score);
//
//		MultipleResponse<ScoreResponse> result = MultipleResponse.<ScoreResponse>builder()
//				.message("Score created successfully")
//				.build();
//
//		return ResponseEntity.ok(result);
//	}

//	@PostMapping
//	public ResponseEntity<SingleResponse<ScoreResponse>> createNew(@RequestBody ScoreRequest request) {
//
//		Score score = scoreService.create(request);
//
//		ScoreResponse response = scoreMapper.toResponse(score);
//
//		return ResponseEntity.ok(SingleResponse.success("Score created successfully", response));
//	}

	@PostMapping
	public ResponseEntity<SingleResponse<List<ScoreResponse>>> create(
			@RequestBody List<ScoreRequest> requests) {

		return ResponseEntity.ok(
				SingleResponse.success("Created successfully",
						scoreService.create(requests))
		);
	}
//	@GetMapping("/{id}")
//	public ResponseEntity<SingleResponse<ScoreResponse>> findById(@PathVariable UUID id) {
//		ScoreResponse response = scoreService.getById(id);
//		return ResponseEntity.ok(SingleResponse.success("Score retrieved successfully for ID: " + id, response));
//	}

}
