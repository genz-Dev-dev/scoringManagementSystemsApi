package com.kh.rupp_dev.boukryuniversity.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            String username = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName();

            return Optional.ofNullable(username);
        } catch (Exception e) {
            return Optional.of("SYSTEM");
        }
    }
}
//@Component
//public class AuditorAwareImpl implements AuditorAware<String> {
//
//    @Override
//    public Optional<String> getCurrentAuditor() {
//        String userName = SecurityContextHolder.getContext().getAuthentication()
//                .getName();
//        return Optional.ofNullable(userName);
//    }
//}
