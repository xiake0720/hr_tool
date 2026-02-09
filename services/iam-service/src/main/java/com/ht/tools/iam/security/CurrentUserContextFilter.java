package com.ht.tools.iam.security;

import com.ht.tools.common.web.RequestIdContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 用途：从 SecurityContext 的 JWT 构建 CurrentUserContext 并写入 ThreadLocal。
 * 职责/边界：仅负责上下文构建与清理，不做权限判断。
 * 调用时机：认证完成后、业务处理前执行。
 * 线程模型：单次请求内创建并清理上下文。
 */
@Component
public class CurrentUserContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        CurrentUserContext context = new CurrentUserContext();
        context.setRequestId(RequestIdContext.getOrCreate());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();
            context.setExternalSub(jwt.getSubject());
            context.setUsername(getStringClaim(jwt, "preferred_username", "username"));
            context.setEmail(jwt.getClaimAsString("email"));
            context.setRoles(getStringSetClaim(jwt, "roles"));
            context.setPermissions(getStringSetClaim(jwt, "permissions"));
        } else {
            context.setRoles(Collections.emptySet());
            context.setPermissions(Collections.emptySet());
        }

        CurrentUserContextHolder.set(context);
        try {
            filterChain.doFilter(request, response);
        } finally {
            CurrentUserContextHolder.clear();
        }
    }

    private String getStringClaim(Jwt jwt, String... keys) {
        for (String key : keys) {
            String value = jwt.getClaimAsString(key);
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return null;
    }

    private Set<String> getStringSetClaim(Jwt jwt, String key) {
        List<String> values = jwt.getClaimAsStringList(key);
        if (values == null || values.isEmpty()) {
            return Collections.emptySet();
        }
        return new HashSet<>(values);
    }
}
