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
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "tbl_audit_log")
@Getter
@Setter
public class AuditLog {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "username" , nullable = false)
	private String username;

	@Column(name = "action" , nullable = false)
	private String action;

	@Column(name = "entity_name")
	private String entityName;

	@Column(name = "entity_id")
	private String entityId;

	@Column(name = "old_value")
	private String oldValue;

	@Column(name = "new_value")
	private String newValue;

	@CreationTimestamp
	@Column(name = "timestamp")
	private LocalDateTime timestamp;

}
