package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.entity.RefreshTokens;
import com.rupp.tola.dev.scoring_management_system.repository.RefreshTokenRepository;
import com.rupp.tola.dev.scoring_management_system.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.expiration-refresh}")
    private Long expiration;

    @Override
    public RefreshTokens create() {
        RefreshTokens refresh = new RefreshTokens();
        String randomRefreshToken = UUID.randomUUID().toString();
        refresh.setToken(randomRefreshToken);
        refresh.setExpiryAt(Instant.now().plusMillis(expiration));
        log.info("Created refresh token [{}]", refresh);
        return refresh;
    }

    @Override
    public Optional<RefreshTokens> findByToken(String refresh) {
        return refreshTokenRepository.findByToken(refresh);
    }

    @Override
    public boolean verify(RefreshTokens refreshTokens) {
        if(refreshTokens.getExpiryAt().isBefore(Instant.now())) {
            log.info("Refresh token is expiration.");
            refreshTokenRepository.delete(refreshTokens);
            return false;
        }
        return true;
    }
}
