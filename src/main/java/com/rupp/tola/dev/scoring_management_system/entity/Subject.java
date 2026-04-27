package com.rupp.tola.dev.scoring_management_system.entity;

import com.rupp.tola.dev.scoring_management_system.audit.AuditListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tbl_subject")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditListener.class)
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "subject_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "subject_thumbnail" , nullable = false)
    private String thumbnail;

    @Column(name = "subject_name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "subject_code", nullable = false, unique = true)
    private String code;
}
