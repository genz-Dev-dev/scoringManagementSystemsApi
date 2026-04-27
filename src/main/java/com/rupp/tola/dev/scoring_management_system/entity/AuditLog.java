package com.rupp.tola.dev.scoring_management_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "tbl_audit_log")
public class AuditLog {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "audit_id", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "table_name", nullable = false)
	private String tableName;

	@Column(name = "record_id")
	private UUID recordId;

	@Column(name = "action")
	private String action;

	@Column(name = "new_data", columnDefinition = "jsonb")
	private String newData;

	@Column(name = "old_data", columnDefinition = "jsonb")
	private String oldData;

	@Column(name = "change_at")
	private LocalDateTime changeAt;
}