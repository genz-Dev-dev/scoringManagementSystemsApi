package com.rupp.tola.dev.scoring_management_system.service;

import com.rupp.tola.dev.scoring_management_system.dto.response.AuditLogResponse;
import com.rupp.tola.dev.scoring_management_system.entity.AuditLog;

import java.util.List;

public interface AuditLogService {
	List<AuditLogResponse> getAllLogs();

	void saveAuditLog(AuditLog auditLog);
}
