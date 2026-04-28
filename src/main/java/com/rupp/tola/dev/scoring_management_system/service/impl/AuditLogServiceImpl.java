package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.dto.response.AuditLogResponse;
import com.rupp.tola.dev.scoring_management_system.entity.AuditLog;
import com.rupp.tola.dev.scoring_management_system.mapper.AuditLogMapper;
import com.rupp.tola.dev.scoring_management_system.repository.AuditLogRepository;
import com.rupp.tola.dev.scoring_management_system.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class AuditLogServiceImpl implements AuditLogService {
    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    @Override
    public List<AuditLogResponse> getAllLogs() {
        return auditLogRepository.findAll()
                .stream()
                .map(auditLogMapper::toResponse)
                .toList();
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(propagation = org.springframework.transaction.annotation.Propagation.REQUIRES_NEW)
    public void saveAuditLog(AuditLog auditLog) {
        auditLogRepository.save(auditLog);
    }
}

