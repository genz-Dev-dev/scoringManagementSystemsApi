package com.rupp.tola.dev.scoring_management_system.service;

import com.rupp.tola.dev.scoring_management_system.entity.RefreshTokens;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshTokens create();
    Optional<RefreshTokens> findByToken(String refresh);
    boolean verify(RefreshTokens refreshTokens);
}
