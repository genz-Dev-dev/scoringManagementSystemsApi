package com.rupp.tola.dev.scoring_management_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity<I , T> {

    @Id
    private I id;

    @CreationTimestamp
    @Column(name = "created_at" , nullable = false , updatable = false)
    private T createdAt;

    @UpdateTimestamp
    @Column(name = "update_at" , nullable = false)
    private T updateAt;
}
