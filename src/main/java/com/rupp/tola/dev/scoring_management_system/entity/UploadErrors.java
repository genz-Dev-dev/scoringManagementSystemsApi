package com.rupp.tola.dev.scoring_management_system.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "upload_errors")
public class UploadErrors {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "upload_error_id")
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "upload_batch_id")
	private UploadBatches batches;

	@Column(name = "row_name", updatable = false, nullable = false)
	private Integer rowNumber;

	@Column(name = "error_message", updatable = false, nullable = false)
	private String errorMessage;

	@Column(name = "raw_data", columnDefinition = "json")
	private String rawData;

	@Column(name = "create_at")
	@CreationTimestamp
	private LocalDateTime createAt;
}
