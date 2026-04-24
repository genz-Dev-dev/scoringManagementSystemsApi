package com.kh.rupp_dev.boukryuniversity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SemesterResponse {

    private UUID id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;

}
