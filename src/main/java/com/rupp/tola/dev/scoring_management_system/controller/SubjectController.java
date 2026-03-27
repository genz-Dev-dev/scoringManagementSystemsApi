package com.rupp.tola.dev.scoring_management_system.controller;

import com.rupp.tola.dev.scoring_management_system.data.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.request.SubjectRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.SubjectResponse;
import com.rupp.tola.dev.scoring_management_system.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<SingleResponse<SubjectResponse>> createNew(@Valid @RequestBody SubjectRequest request) {
        SubjectResponse subjectResponse = subjectService.create(request);
        return ResponseEntity.ok(SingleResponse.success("Successfully to create subject.", subjectResponse));
    }

    @GetMapping
    public ResponseEntity<SingleResponse<List<SubjectResponse>>> getAll() {
        List<SubjectResponse> responses = subjectService.getAll();
        return ResponseEntity.ok(SingleResponse.success("Successfully to get all subjects.", responses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResponse<SubjectResponse>> getById(@PathVariable UUID id) {
        SubjectResponse response = subjectService.getByUuid(id);
        return ResponseEntity.ok(SingleResponse.success("Successfully to get subject with id: " + id, response));
    }

    @DeleteMapping
    public ResponseEntity<SingleResponse<SubjectResponse>> delete(@PathVariable UUID id) {
        subjectService.delete(id);
        return ResponseEntity.ok(SingleResponse.success("Successfully to delete subject with id: " + id, null));
    }

    @PutMapping
    public ResponseEntity<SingleResponse<SubjectResponse>> update(
            @PathVariable UUID id,
            @RequestBody SubjectRequest request)
    {
        SubjectResponse response = subjectService.update(id, request);
        return ResponseEntity.ok(SingleResponse.success("Successfully to update subject with id:" + id, response));
    }
}
