package com.kh.rupp_dev.boukryuniversity.service;

import com.kh.rupp_dev.boukryuniversity.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    RefreshToken create();

    Optional<RefreshToken> findByToken(String refresh);

    boolean verify(RefreshToken refreshToken);

}
