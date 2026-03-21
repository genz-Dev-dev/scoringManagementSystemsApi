package com.rupp.tola.dev.scoring_management_system.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportStudent {

    private UUID classId;

    private MultipartFile file;

}
