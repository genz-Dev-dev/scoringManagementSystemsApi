package com.rupp.tola.dev.scoring_management_system.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.rupp.tola.dev.scoring_management_system.constant.UploadBatchesStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_uplaod_batches")
@Getter
@Setter
@NoArgsConstructor
public class UploadBatches {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "upload_batch_id")
	private UUID id;

	@Column(name = "file_name", updatable = false, nullable = false)
	private String fileName;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UploadBatchesStatus status;

	@Column(name = "total_rows", updatable = false, nullable = false)
	private Integer totalRow;

	@Column(name = "success_rows", updatable = false, nullable = false)
	private Integer successRow;

	@Column(name = "fail_row",updatable = false, nullable = false)
	private Integer failRow;

	@Column(name = "create_at", updatable = false, nullable = false)
	@CreationTimestamp
	private LocalDateTime createAt;
	
	@Column(name = "completed_at", updatable = false, nullable = false)
	private LocalDateTime completedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id" , referencedColumnName = "user_id")
	private User user;
}
