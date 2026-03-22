package com.rupp.tola.dev.scoring_management_system.service;

import com.rupp.tola.dev.scoring_management_system.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    RefreshToken create();

    Optional<RefreshToken> findByToken(String refresh);

    boolean verify(RefreshToken refreshToken);

}
