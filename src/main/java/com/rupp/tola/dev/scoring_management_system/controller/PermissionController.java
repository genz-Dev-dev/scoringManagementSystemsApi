package com.rupp.tola.dev.scoring_management_system.controller;

import com.rupp.tola.dev.scoring_management_system.dto.request.PermissionRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.PermissionResponse;
import com.rupp.tola.dev.scoring_management_system.payload.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve permission by id.")
    public ResponseEntity<SingleResponse<PermissionResponse>> getById(@PathVariable UUID id) {
        PermissionResponse response = permissionService.getById(id);
        return ResponseEntity.ok().body(SingleResponse.success("Success to retrieve permission by id."
                ,response));
    }

    @GetMapping
    @Operation(summary = "Retrieve all permissions.")
    public ResponseEntity<SingleResponse<List<PermissionResponse>>> getAll() {
        List<PermissionResponse> responses = permissionService.getAll();
        return ResponseEntity.ok()
                .body(SingleResponse.success("Success to retrieve all permissions.", responses));
    }

    @GetMapping("/module/{module}")
    @Operation(summary = "Retrieve all module with module name.")
    public ResponseEntity<SingleResponse<List<PermissionResponse>>> findByModule(@PathVariable String module) {
        List<PermissionResponse> responses = permissionService.findByModule(module);
        return ResponseEntity.ok().body(
                SingleResponse.success("Success to retrieve all permissions.", responses)
        );
    }

    @PostMapping
    @Operation(summary = "Create permission by request.")
    public ResponseEntity<SingleResponse<PermissionResponse>> create(@Valid @RequestBody PermissionRequest request) {
        PermissionResponse response = permissionService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SingleResponse
                        .success("Success to create permission." , response));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update permission with request.")
    public ResponseEntity<SingleResponse<PermissionResponse>> update(@PathVariable UUID id ,@Valid @RequestBody PermissionRequest request) {
        PermissionResponse response = permissionService.update(id, request);
        return ResponseEntity.ok()
                .body(SingleResponse.success("Success to update permission." , response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SingleResponse<Void>> delete(@PathVariable UUID id) {
        permissionService.delete(id);
        return ResponseEntity.ok()
                .body(SingleResponse.success("Success to delete permission." , null));
    }


}