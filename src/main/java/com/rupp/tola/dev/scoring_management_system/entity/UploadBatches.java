package com.rupp.tola.dev.scoring_management_system.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.enums.UploadBatchesStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "upload_batches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

	@Column(name = "total_rows", updatable = false, nullable = false, columnDefinition = "INT DEFAULT 0)")
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
	private Users users;
}
