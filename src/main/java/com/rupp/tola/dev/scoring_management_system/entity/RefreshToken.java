package com.rupp.tola.dev.scoring_management_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "tbl_refresh_token")
@Getter
@Setter
@NoArgsConstructor
public class RefreshToken extends BaseEntity {

    @Column(name = "token" , nullable = false)
    private String token;

    @Column(name = "expiry_at" , nullable = false)
    private Instant expiryAt;

    @OneToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "user_id" , referencedColumnName = "user_id")
    private User user;
}
