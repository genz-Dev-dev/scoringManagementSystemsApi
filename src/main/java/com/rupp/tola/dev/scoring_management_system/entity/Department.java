package com.rupp.tola.dev.scoring_management_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tbl_department")
@Getter
@Setter
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "department_id")
    private UUID id;

    @Column(name = "department_name", nullable = false, unique = true)
    private String name;

    @Column(name = "department_code", nullable = false, unique = true)
    private String code;

    @Column(name = "description", nullable = false)
    private String description;
}
