package com.rupp.tola.dev.scoring_management_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity{

    @CreationTimestamp
    @Column(name = "creation_at", nullable = false, updatable = false)
    private LocalDate creationAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDate updatedAt;
}
