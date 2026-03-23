package com.rupp.tola.dev.scoring_management_system.controller;

import com.rupp.tola.dev.scoring_management_system.data.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.request.SemesterRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.SemesterResponse;
import com.rupp.tola.dev.scoring_management_system.service.SemesterService;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.ok(SingleResponse.success("Successfully to retrieve semesters." , response));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<SingleResponse<SemesterResponse>> getById(@PathVariable UUID id) {
        SemesterResponse response = semesterService.getById(id);
        return ResponseEntity.ok(SingleResponse.success("Successfully to retrieve semesters." , response));
    }

    @PostMapping
    public ResponseEntity<SingleResponse<SemesterResponse>> create(@RequestBody SemesterRequest request){
        SemesterResponse response = semesterService.create(request);
        return ResponseEntity.ok(SingleResponse.success("Successfully to create semesters." , response));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<SingleResponse<SemesterResponse>> update(@PathVariable UUID id, @RequestBody SemesterRequest request){
        SemesterResponse responser = semesterService.update(id, request);
        return ResponseEntity.ok(SingleResponse.success("Successfully to update semesters." , responser));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<SingleResponse<Void>> delete(@PathVariable UUID id){
        semesterService.delete(id);
        return ResponseEntity.ok(SingleResponse.success("Delete semester successfully" , null));
    }

}
