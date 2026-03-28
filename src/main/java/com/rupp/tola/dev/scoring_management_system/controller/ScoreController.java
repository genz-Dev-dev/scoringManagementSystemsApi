package com.rupp.tola.dev.scoring_management_system.controller;

import com.rupp.tola.dev.scoring_management_system.data.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.request.ScoreRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ScoreResponse;
import com.rupp.tola.dev.scoring_management_system.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/scores")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;

    @PostMapping
    public ResponseEntity<SingleResponse<ScoreResponse>> createNew(@RequestBody ScoreRequest request) {
        ScoreResponse response = scoreService.create(request);
        return ResponseEntity.ok(SingleResponse.success("A new score has been successfully created.", response));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<SingleResponse<ScoreResponse>> update(
            @PathVariable UUID id,
            @RequestBody ScoreRequest request
    ) {
        ScoreResponse response = scoreService.update(id, request);
      return ResponseEntity.ok(SingleResponse.success("The score has been successfully updated.", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResponse<ScoreResponse>> findById(@PathVariable UUID id) {
        ScoreResponse response = scoreService.getById(id);
        return ResponseEntity.ok(SingleResponse.success("Score retrieved successfully for ID: " + id, response));
    }

}
