package com.rupp.tola.dev.scoring_management_system.controller;

import com.rupp.tola.dev.scoring_management_system.data.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.request.DepartmentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.DepartmentResponse;
import com.rupp.tola.dev.scoring_management_system.service.DepartmentService;
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
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<SingleResponse<DepartmentResponse>> create(@Valid @RequestBody DepartmentRequest request) {
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

    @PutMapping("/{id}")
    public ResponseEntity<SingleResponse<DepartmentResponse>> update(@PathVariable UUID id,
                                                                     @Valid @RequestBody DepartmentRequest request) {
        DepartmentResponse response = departmentService.update(id, request);
        return ResponseEntity.ok(SingleResponse.success("Successfully updated department.", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SingleResponse<Void>> delete(@PathVariable UUID id) {
        departmentService.delete(id);
        return ResponseEntity.ok(SingleResponse.success("Successfully deleted department.", null));
    }
}
