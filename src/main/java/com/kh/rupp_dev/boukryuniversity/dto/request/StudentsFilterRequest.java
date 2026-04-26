package com.kh.rupp_dev.boukryuniversity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentsFilterRequest {
    private UUID classId;
//    private UUID departmentId;
}
