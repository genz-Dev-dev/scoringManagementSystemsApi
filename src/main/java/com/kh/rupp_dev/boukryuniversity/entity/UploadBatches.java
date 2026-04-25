package com.kh.rupp_dev.boukryuniversity.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import com.kh.rupp_dev.boukryuniversity.constant.UploadBatchesStatus;

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

@Entity
@Table(name = "tbl_uplaod_batches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadBatches {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "upload_batch_id")
	private UUID id;

	@Column(name = "file_name", nullable = false)
	private String fileName;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UploadBatchesStatus status;

	@Column(name = "total_rows")
	private Integer totalRow;

	@Column(name = "success_rows")
	private Integer successRow;

	@Column(name = "fail_row")
	private Integer failRow;

	@Column(name = "created_at", updatable = false, nullable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(name = "completed_at", nullable = false)
	private LocalDateTime completedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id" , referencedColumnName = "user_id")
	private User user;
}
