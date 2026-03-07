package com.rupp.tola.dev.scoring_management_system.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "audit_logs")
public class AuditLogs {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "audit_id", columnDefinition = "uuid", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "table_name", updatable = false, nullable = false)
	private String tableName;

	@Column(name = "record_id")
	private UUID recordId;

	@Column(name = "action")
	private String action;

	@Column(name = "old_data", columnDefinition = "jsonb")
	private String oldData;

	@Column(name = "new_data", columnDefinition = "jsonb")
	private String newData;

	@Column(name = "change_at")
	private LocalDateTime changeAt;
}
