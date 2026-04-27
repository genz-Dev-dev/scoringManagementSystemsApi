package com.rupp.tola.dev.scoring_management_system.dto.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreRequest {


	private UUID studentId;
	private UUID subjectId;
	private UUID semesterId;
	private UUID userId;

	private double score;
	private Integer version;
	private boolean status;

}