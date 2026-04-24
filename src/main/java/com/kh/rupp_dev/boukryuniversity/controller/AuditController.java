package com.kh.rupp_dev.boukryuniversity.controller;

import com.kh.rupp_dev.boukryuniversity.dto.response.AuditResponse;
import com.kh.rupp_dev.boukryuniversity.payload.SingleResponse;
import com.kh.rupp_dev.boukryuniversity.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(
        path = "audit-logs",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class AuditController {


    private final AuditService auditService;

    @GetMapping
    public ResponseEntity<SingleResponse<List<AuditResponse>>> getAll() {
        return ResponseEntity.ok().body(SingleResponse.success("Successfully to retrieve all audit logs." , auditService.getAll()));
    }


}
