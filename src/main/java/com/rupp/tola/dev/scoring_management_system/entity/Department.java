package com.rupp.tola.dev.scoring_management_system.entity;

import com.rupp.tola.dev.scoring_management_system.audit.AuditListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tbl_department")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditListener.class)
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "department_id")
    private UUID id;

    @Column(name = "department_name", nullable = false, unique = true)
    private String name;

    @Column(name = "department_thumbnail" , nullable = false)
    private String thumbnail;

    @Column(name = "department_code", nullable = false, unique = true)
    private String code;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "department")
    private List<Subject> subjects;
}
