package com.kh.rupp_dev.boukryuniversity.controller;

import com.kh.rupp_dev.boukryuniversity.payload.SingleResponse;
import com.kh.rupp_dev.boukryuniversity.dto.request.SemesterRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.SemesterResponse;
import com.kh.rupp_dev.boukryuniversity.service.SemesterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/semesters")
@RequiredArgsConstructor
public class SemesterController {

    private final SemesterService semesterService;

    @GetMapping
    public ResponseEntity<SingleResponse<List<SemesterResponse>>> getAll() {
        List<SemesterResponse> response = semesterService.getAll();
        return ResponseEntity.ok(SingleResponse.success("Successfully retrieved all semesters.", response));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<SingleResponse<SemesterResponse>> getById(@PathVariable UUID id) {
        SemesterResponse response = semesterService.getById(id);
        return ResponseEntity.ok(SingleResponse.success("Successfully retrieved semester.", response));
    }

    @PostMapping
    public ResponseEntity<SingleResponse<SemesterResponse>> create(@Valid @RequestBody SemesterRequest request){
        SemesterResponse response = semesterService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SingleResponse.success("Successfully created semester.", response));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<SingleResponse<SemesterResponse>> update(@PathVariable UUID id,
                                                                   @Valid @RequestBody SemesterRequest request){
        SemesterResponse response = semesterService.update(id, request);
        return ResponseEntity.ok(SingleResponse.success("Successfully updated semester.", response));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<SingleResponse<Void>> delete(@PathVariable UUID id){
        semesterService.delete(id);
        return ResponseEntity.ok(SingleResponse.success("Successfully deleted semester.", null));
    }
}
