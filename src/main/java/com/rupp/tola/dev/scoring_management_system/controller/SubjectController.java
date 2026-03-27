package com.rupp.tola.dev.scoring_management_system.controller;

import com.rupp.tola.dev.scoring_management_system.data.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.request.SubjectRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.SubjectResponse;
import com.rupp.tola.dev.scoring_management_system.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<SingleResponse<SubjectResponse>> create(@Valid @RequestBody SubjectRequest request) {
        SubjectResponse response = subjectService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SingleResponse.success("Successfully created subject.", response));
    }

    @GetMapping
    public ResponseEntity<SingleResponse<List<SubjectResponse>>> getAll() {
        List<SubjectResponse> response = subjectService.getAll();
        return ResponseEntity.ok(SingleResponse.success("Successfully retrieved all subjects.", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResponse<SubjectResponse>> getById(@PathVariable UUID id) {
        SubjectResponse response = subjectService.getById(id);
        return ResponseEntity.ok(SingleResponse.success("Successfully retrieved subject.", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SingleResponse<SubjectResponse>> update(@PathVariable UUID id,
                                                                  @Valid @RequestBody SubjectRequest request) {
        SubjectResponse response = subjectService.update(id, request);
        return ResponseEntity.ok(SingleResponse.success("Successfully updated subject.", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SingleResponse<Void>> delete(@PathVariable UUID id) {
        subjectService.delete(id);
        return ResponseEntity.ok(SingleResponse.success("Successfully deleted subject.", null));
    }
}
