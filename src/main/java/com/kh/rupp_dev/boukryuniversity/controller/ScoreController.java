package com.kh.rupp_dev.boukryuniversity.controller;

import com.kh.rupp_dev.boukryuniversity.payload.SingleResponse;
import com.kh.rupp_dev.boukryuniversity.dto.request.ScoreRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.ScoreResponse;
import com.kh.rupp_dev.boukryuniversity.service.ScoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/scores")
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
            @RequestBody @Valid ScoreRequest request
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
