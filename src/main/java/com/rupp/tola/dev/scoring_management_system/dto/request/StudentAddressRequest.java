package com.rupp.tola.dev.scoring_management_system.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentAddressRequest {
    private UUID id;
    private String houseNumber;
    private String street;
    private String sangkat;
    private String khan;
    private String province;
    private String country;
}
