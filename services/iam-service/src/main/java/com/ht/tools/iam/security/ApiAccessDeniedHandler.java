package com.ht.tools.iam.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ht.tools.common.core.api.ApiResponse;
import com.ht.tools.common.core.error.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * 用途：统一 403 无权限响应输出为 ApiResponse。
 * 职责/边界：仅负责输出响应体，不处理授权逻辑。
 * 调用时机：已认证但无权限访问资源时触发。
 * 线程模型：无状态组件，线程安全。
 */
@Component
public class ApiAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public ApiAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ApiResponse<Void> body = ApiResponse.fail(ErrorCode.AUTH_4003.getCode(), ErrorCode.AUTH_4003.getMessage());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), body);
    }
}
