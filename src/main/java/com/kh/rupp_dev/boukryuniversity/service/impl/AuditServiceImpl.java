package com.kh.rupp_dev.boukryuniversity.service.impl;

import com.kh.rupp_dev.boukryuniversity.dto.response.AuditResponse;
import com.kh.rupp_dev.boukryuniversity.entity.AuditLog;
import com.kh.rupp_dev.boukryuniversity.repository.AuditLogRepository;
import com.kh.rupp_dev.boukryuniversity.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;

    @Override
    public List<AuditResponse> getAll() {
        return auditLogRepository
                .findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private AuditResponse toResponse(AuditLog auditLog) {
        return AuditResponse
                .builder()
                .id(auditLog.getId())
                .username(auditLog.getUsername())
                .action(auditLog.getAction())
                .entityName(auditLog.getEntityName())
                .entityId(auditLog.getEntityId())
                .oldValue(auditLog.getOldValue())
                .newValue(auditLog.getNewValue())
                .timestamp(auditLog.getTimestamp())
                .build();
    }
}
