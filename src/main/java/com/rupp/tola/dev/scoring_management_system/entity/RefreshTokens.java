package com.rupp.tola.dev.scoring_management_system.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokens extends BaseEntity {

    @Column(name = "token" , nullable = false)
    private String token;

    @Column(name = "expiry_at" , nullable = false)
    private Instant expiryAt;

    @OneToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "user_id" , referencedColumnName = "user_id")
    private Users users;
}
