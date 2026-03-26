package com.rupp.tola.dev.scoring_management_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentStatisticsResponse {
    private int totalStudent;
    private int totalMale;
    private int totalFemale;
}
