package com.rupp.tola.dev.scoring_management_system.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_uplaod_erorr")
@Getter
@Setter
@NoArgsConstructor
public class UploadError {

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
