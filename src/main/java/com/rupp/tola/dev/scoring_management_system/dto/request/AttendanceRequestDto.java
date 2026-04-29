package com.rupp.tola.dev.scoring_management_system.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRequestDto {

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	@NotNull(message = "user is required!")
	private UUID userId;

}
