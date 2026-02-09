package com.ht.tools.iam.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ht.tools.common.core.api.ApiResponse;
import com.ht.tools.common.core.error.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * 用途：统一 401 未认证响应输出为 ApiResponse。
 * 职责/边界：仅负责输出响应体，不处理认证逻辑。
 * 调用时机：未认证访问受保护资源时触发。
 * 线程模型：无状态组件，线程安全。
 */
@Component
public class ApiAuthEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public ApiAuthEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        ApiResponse<Void> body = ApiResponse.fail(ErrorCode.AUTH_4001.getCode(), ErrorCode.AUTH_4001.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), body);
    }
}
