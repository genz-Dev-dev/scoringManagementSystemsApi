package com.kh.rupp_dev.boukryuniversity.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_audit_log")
@Getter
@Setter
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
