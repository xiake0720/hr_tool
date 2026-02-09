package com.ht.tools.iam.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ht.tools.common.core.api.ApiResponse;
import com.ht.tools.common.core.error.ErrorCode;
import com.ht.tools.common.web.RequestIdContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 用途：权限守卫拦截器，检查 @RequirePermission 标注。
 * 职责/边界：仅做权限判断，不替代认证或业务校验。
 * 调用时机：进入 Controller 之前执行。
 * 线程模型：无状态组件，线程安全。
 */
@Component
public class RequirePermissionInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;
    private final Environment environment;

    public RequirePermissionInterceptor(ObjectMapper objectMapper, Environment environment) {
        this.objectMapper = objectMapper;
        this.environment = environment;
    }

    /**
     * 行为：在进入 Controller 前检查所需权限。
     * 入参：request/response/handler。
     * 出参：是否放行。
     * 异常：写响应失败时抛出 IOException。
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param handler 处理器
     * @return 是否放行
     * @throws IOException IO 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        RequirePermission annotation = findPermission(handlerMethod);
        if (annotation == null) {
            return true;
        }
        if (environment.matchesProfiles("local")) {
            return true;
        }
        CurrentUserContext context = CurrentUserContextHolder.get();
        Set<String> permissions = context == null ? Set.of() : context.getPermissions();
        if (permissions == null || !permissions.contains(annotation.value())) {
            writeForbidden(response);
            return false;
        }
        return true;
    }

    /**
     * 行为：解析方法或类上的权限注解。
     *
     * @param handlerMethod 处理器方法
     * @return RequirePermission，可能为 null
     */
    private RequirePermission findPermission(HandlerMethod handlerMethod) {
        RequirePermission methodAnno = handlerMethod.getMethodAnnotation(RequirePermission.class);
        if (methodAnno != null) {
            return methodAnno;
        }
        return handlerMethod.getBeanType().getAnnotation(RequirePermission.class);
    }

    /**
     * 行为：输出 403 响应。
     *
     * @param response HttpServletResponse
     * @throws IOException IO 异常
     */
    private void writeForbidden(HttpServletResponse response) throws IOException {
        ApiResponse<Void> body = ApiResponse.fail(ErrorCode.AUTH_4003.getCode(), ErrorCode.AUTH_4003.getMessage());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("X-Request-Id", RequestIdContext.getOrCreate());
        objectMapper.writeValue(response.getWriter(), body);
    }
}
