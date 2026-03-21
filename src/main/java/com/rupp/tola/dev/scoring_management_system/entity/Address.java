package com.rupp.tola.dev.scoring_management_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Column(name = "No")
    private String houseNumber;

    @Column(name = "address_street", nullable = false, length = 50)
    private String street;

    @Column(name = "address_sangkat", nullable = false, length = 50)
    private String sangkat;

    @Column(name = "address_khan", nullable = false, length = 50)
    private String khan;

    @Column(name = "address_province", nullable = false, length = 50)
    private String province;

    @Column(name = "country", nullable = false, length = 50)
    private String country;
}
