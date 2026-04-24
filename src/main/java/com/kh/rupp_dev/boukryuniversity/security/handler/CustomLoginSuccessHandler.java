package com.kh.rupp_dev.boukryuniversity.security.handler;

import com.kh.rupp_dev.boukryuniversity.entity.User;
import com.kh.rupp_dev.boukryuniversity.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User email not found with Email: " + email));
        user.setAttempt(0);
        user.setLockTime(null);
        userRepository.save(user);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
