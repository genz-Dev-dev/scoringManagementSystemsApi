package com.kh.rupp_dev.boukryuniversity.security.handler;

import com.kh.rupp_dev.boukryuniversity.entity.User;
import com.kh.rupp_dev.boukryuniversity.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String userEmail = request.getParameter("email");
        if(userEmail != null) {
            Optional<User> user = userRepository.findByEmail(userEmail);
            if(user.isPresent()) {
                int attempt = user.get().getAttempt();
                user.get().setAttempt(attempt + 1);
                if(attempt >= 3) {
                    user.get().setStatus(false);
                    user.get().setLockTime(LocalDate.now());
                }
                userRepository.save(user.get());
            }
        }
        super.onAuthenticationFailure(request, response, exception);
    }
}
