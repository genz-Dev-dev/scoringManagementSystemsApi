package com.rupp.tola.dev.scoring_management_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {


    @Column(name = "No")
    private String houseNumber;

    @Size(max = 50)
    @Column(name = "address_street", nullable = false, length = 50)
    private String street;

    @Size(max = 50)
    @Column(name = "address_sangkat", nullable = false, length = 50)
    private String sangkat;

    @Size(max = 50)
    @Column(name = "address_khan", nullable = false, length = 50)
    private String khan;

    @NotBlank(message = "Province is required")
    @Size(max = 50)
    @Column(name = "address_province", nullable = false, length = 50)
    private String province;

    @NotBlank(message = "Province is required")
    @Size(max = 50)
    @Column(name = "country", nullable = false, length = 50)
    private String country;

    public String toDisplayString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(houseNumber);
        stringBuilder.append(", ").append(street);
        stringBuilder.append(", Sangkat ").append(sangkat);
        stringBuilder.append(", Khan ").append(khan);
        stringBuilder.append(", ").append(province);
        stringBuilder.append(", ").append(country);
        return stringBuilder.toString();
    }
}
