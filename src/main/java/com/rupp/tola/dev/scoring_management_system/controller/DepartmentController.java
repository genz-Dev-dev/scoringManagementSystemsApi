package com.rupp.tola.dev.scoring_management_system.controller;

import com.rupp.tola.dev.scoring_management_system.data.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.request.DepartmentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.DepartmentResponse;
import com.rupp.tola.dev.scoring_management_system.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SingleResponse<DepartmentResponse>> create(@Valid @ModelAttribute DepartmentRequest request) {
        DepartmentResponse response = departmentService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SingleResponse.success("Successfully created department.", response));
    }

    @GetMapping
    public ResponseEntity<SingleResponse<List<DepartmentResponse>>> getAll() {
        List<DepartmentResponse> response = departmentService.getAll();
        return ResponseEntity.ok(SingleResponse.success("Successfully retrieved all departments.", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResponse<DepartmentResponse>> getById(@PathVariable UUID id) {
        DepartmentResponse response = departmentService.getById(id);
        return ResponseEntity.ok(SingleResponse.success("Successfully retrieved department.", response));
    }

    @PutMapping(value = "/{id}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SingleResponse<DepartmentResponse>> update(@PathVariable UUID id,
                                                                     @Valid @ModelAttribute DepartmentRequest request) {
        DepartmentResponse response = departmentService.update(id, request);
        return ResponseEntity.ok(SingleResponse.success("Successfully updated department.", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SingleResponse<Void>> delete(@PathVariable UUID id) {
        departmentService.delete(id);
        return ResponseEntity.ok(SingleResponse.success("Successfully deleted department.", null));
    }
}
