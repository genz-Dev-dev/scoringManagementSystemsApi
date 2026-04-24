package com.kh.rupp_dev.boukryuniversity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreResponse {

    private UUID id;

    private UUID studentId;

    private UUID semesterId;

    private UUID subjectId;

    private BigDecimal score;

    private String grade;

    private boolean status;

}
