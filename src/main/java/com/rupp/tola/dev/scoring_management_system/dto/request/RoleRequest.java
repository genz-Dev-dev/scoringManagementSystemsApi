package com.rupp.tola.dev.scoring_management_system.dto.request;

import com.rupp.tola.dev.scoring_management_system.enums.RoleName;
import com.rupp.tola.dev.scoring_management_system.enums.RoleStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRequest {

    @NotBlank(message = "Name is required.")
    @Size(min = 6 , max = 25 , message = "Name must be between 6 and 25 characters.")
    private RoleName name;

    @NotBlank(message = "Status is required.")
    private RoleStatus status;

    private UUID userId;
}
