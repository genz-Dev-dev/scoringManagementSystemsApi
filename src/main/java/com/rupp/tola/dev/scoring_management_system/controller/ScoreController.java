package com.rupp.tola.dev.scoring_management_system.controller;

import com.rupp.tola.dev.scoring_management_system.data.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.request.ScoreRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ScoreResponse;
import com.rupp.tola.dev.scoring_management_system.service.ScoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/scores")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;

    @PostMapping
    public ResponseEntity<SingleResponse<ScoreResponse>> createNew(
            @Valid @RequestBody ScoreRequest request
    ) {
        log.info("REST request to create new score : {}", request);
        ScoreResponse response = scoreService.create(request);
        log.info("Score created successfully : {}", response);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SingleResponse.success("Successfully created score.", response));
    }

    @PostMapping("/bulk")
    public ResponseEntity<SingleResponse<List<ScoreResponse>>> createBulk(
            @RequestBody List<ScoreRequest> requests
    ) {
        log.info("REST request to bulk create scores, total records : {}", requests.size());
        List<ScoreResponse> responses = scoreService.createBulk(requests);
        log.info("Bulk score created successfully, total saved : {}", responses.size());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SingleResponse.success("Successfully created scores in bulk.", responses));
    }

    @GetMapping
    public ResponseEntity<SingleResponse<Page<ScoreResponse>>> getAll(Pageable pageable) {
        log.info("REST request to get all scores, page : {}, size : {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<ScoreResponse> page = scoreService.getAll(pageable);
        log.info("Fetched {} scores on page {}", page.getNumberOfElements(), page.getNumber());
        return ResponseEntity.ok(SingleResponse.success("Scores retrieved successfully.", page)) ;
    }

    @GetMapping("/course")
    public ResponseEntity<SingleResponse<List<ScoreResponse>>> getByCourse(
            @RequestParam UUID semesterId,
            @RequestParam UUID subjectId
    ) {
        log.info("REST request to get scores by course, semesterId : {}, subjectId : {}", semesterId, subjectId);
        List<ScoreResponse> responses = scoreService.findByCourse(semesterId, subjectId);
        log.info("Fetched {} scores for semesterId : {}, subjectId : {}", responses.size(), semesterId, subjectId);
        return ResponseEntity.ok(SingleResponse.success("Scores retrieved successfully.", responses));
    }

    @GetMapping("/student-course")
    public ResponseEntity<SingleResponse<ScoreResponse>> getByStudentAndCourse(
            @RequestParam UUID studentId,
            @RequestParam UUID semesterId,
            @RequestParam UUID subjectId
    ) {
        log.info("REST request to get score by student and course, studentId : {}, semesterId : {}, subjectId : {}", studentId, semesterId, subjectId);
        ScoreResponse response = scoreService.findByStudentAndCourse(studentId, semesterId, subjectId);
        log.info("Score retrieved successfully for studentId : {}", studentId);
        return ResponseEntity.ok(SingleResponse.success("Score retrieved successfully.", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResponse<ScoreResponse>> findById(@PathVariable UUID id) {
        log.info("REST request to get score by id : {}", id);
        ScoreResponse response = scoreService.getById(id);
        log.info("Score retrieved successfully for id : {}", id);
        return ResponseEntity.ok(SingleResponse.success("Score retrieved successfully.", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SingleResponse<ScoreResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody ScoreRequest request
    ) {
        log.info("REST request to update score, id : {}, payload : {}", id, request);
        ScoreResponse response = scoreService.updateScoreValue(id, request);
        log.info("Score updated successfully for id : {}", id);
        return ResponseEntity.ok(SingleResponse.success("Score updated successfully.", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SingleResponse<Void>> delete(@PathVariable UUID id) {
        log.info("REST request to delete score, id : {}", id);
        scoreService.delete(id);
        log.info("Score deleted successfully for id : {}", id);
        return ResponseEntity.ok(SingleResponse.success("Score deleted successfully.", null));
    }
}
