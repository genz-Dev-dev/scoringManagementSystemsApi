package com.rupp.tola.dev.scoring_management_system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "tbl_student_address")
@Getter
@Setter
@NoArgsConstructor
public class StudentAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "address_id")
    private UUID id;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id" , referencedColumnName = "student_id")
    private Student student;
}
