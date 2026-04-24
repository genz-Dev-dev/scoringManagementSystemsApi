package com.kh.rupp_dev.boukryuniversity.interceptor;

import com.kh.rupp_dev.boukryuniversity.entity.AuditLog;
import com.kh.rupp_dev.boukryuniversity.entity.User;
import com.kh.rupp_dev.boukryuniversity.repository.AuditLogRepository;
import com.kh.rupp_dev.boukryuniversity.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
@RequiredArgsConstructor
public class ApiAuditInterceptor implements HandlerInterceptor {

    private final AuditLogRepository auditLogRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        String username = (user != null && user.isAuthenticated()) ? user.getName() : "Anonymous";
        String method = request.getMethod();
        String uri = request.getRequestURI();

        AuditLog auditLog = new AuditLog();
        auditLog.setUsername(username);
        auditLog.setAction(method);
        auditLog.setEntityName("API");
        auditLog.setEntityId(uri);

        auditLogRepository.save(auditLog);
        return true;
    }
}
