package com.kh.rupp_dev.boukryuniversity.controller;

import com.kh.rupp_dev.boukryuniversity.payload.SingleResponse;
import com.kh.rupp_dev.boukryuniversity.dto.request.SubjectRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.SubjectResponse;
import com.kh.rupp_dev.boukryuniversity.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SingleResponse<SubjectResponse>> create(@Valid @ModelAttribute SubjectRequest request) {
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

    @PutMapping(value = "/{id}" , consumes =  MediaType.MULTIPART_FORM_DATA_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SingleResponse<SubjectResponse>> update(@Valid @ModelAttribute SubjectRequest request, @PathVariable UUID id){
        SubjectResponse response = subjectService.update(id, request);
        return ResponseEntity.ok(SingleResponse.success("Successfully updated subject.", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SingleResponse<Void>> delete(@PathVariable UUID id) {
        subjectService.delete(id);
        return ResponseEntity.ok(SingleResponse.success("Successfully deleted subject.", null));
    }
}
