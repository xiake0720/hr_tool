package com.ht.tools.iam.security;

import com.ht.tools.common.web.RequestIdContext;
import com.ht.tools.iam.infrastructure.entity.UserEntity;
import com.ht.tools.iam.service.UserPermissionService;
import com.ht.tools.iam.service.UserService;
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

    private static final Long FALLBACK_TENANT_ID = 1L;

    private final UserService userService;
    private final UserPermissionService userPermissionService;

    public CurrentUserContextFilter(UserService userService, UserPermissionService userPermissionService) {
        this.userService = userService;
        this.userPermissionService = userPermissionService;
    }

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
            context.setTenantId(getLongClaim(jwt, "tenant_id"));

            Long tenantId = context.getTenantId() != null ? context.getTenantId() : FALLBACK_TENANT_ID;
            userService.findByExternalSubOrLoginOrEmail(
                            context.getExternalSub(),
                            context.getUsername(),
                            context.getEmail(),
                            tenantId)
                    .ifPresent(user -> fillFromUser(context, tenantId, user));

            if (context.getRoles() == null) {
                context.setRoles(Collections.emptySet());
            }
            if (context.getPermissions() == null) {
                context.setPermissions(Collections.emptySet());
            }
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

    private void fillFromUser(CurrentUserContext context, Long tenantId, UserEntity user) {
        context.setUserId(user.getId());
        if (context.getTenantId() == null) {
            context.setTenantId(tenantId);
        }
        context.setRoles(userPermissionService.loadRoles(tenantId, user.getId()));
        context.setPermissions(userPermissionService.loadPermissions(tenantId, user.getId()));
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

    private Long getLongClaim(Jwt jwt, String key) {
        Object value = jwt.getClaims().get(key);
        if (value instanceof Number number) {
            return number.longValue();
        }
        if (value instanceof String str && !str.isBlank()) {
            try {
                return Long.parseLong(str);
            } catch (NumberFormatException ignored) {
                return null;
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
