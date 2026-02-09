package com.ht.tools.iam.controller;

import com.ht.tools.common.core.api.ApiResponse;
import com.ht.tools.common.web.RequestIdContext;
import com.ht.tools.iam.api.dto.MeResponse;
import com.ht.tools.iam.security.CurrentUserContext;
import com.ht.tools.iam.security.CurrentUserContextHolder;
import java.util.Collections;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用途：提供当前用户信息接口 /api/me。
 * 职责/边界：仅负责组装 DTO，不直接处理鉴权与业务逻辑。
 * 调用时机：客户端携带 JWT 访问 /api/me 时触发。
 * 线程模型：无状态 Controller，线程安全。
 */
@RestController
public class MeController {

    /**
     * 行为：返回当前用户的最小上下文信息。
     * 入参：无。
     * 出参：ApiResponse<MeResponse>。
     * 异常：认证异常由安全框架处理。
     *
     * @return ApiResponse<MeResponse>
     */
    @GetMapping("/api/me")
    public ApiResponse<MeResponse> me() {
        CurrentUserContext context = CurrentUserContextHolder.get();
        String requestId = RequestIdContext.getOrCreate();
        if (context == null) {
            MeResponse response = new MeResponse(null, null, null, null, null,
                    Collections.emptySet(), Collections.emptySet(), requestId);
            return ApiResponse.ok(response);
        }
        Set<String> roles = context.getRoles() == null ? Collections.emptySet() : context.getRoles();
        Set<String> permissions = context.getPermissions() == null ? Collections.emptySet() : context.getPermissions();
        MeResponse response = new MeResponse(
                context.getTenantId(),
                context.getUserId(),
                context.getExternalSub(),
                context.getUsername(),
                context.getEmail(),
                roles,
                permissions,
                context.getRequestId() == null ? requestId : context.getRequestId());
        return ApiResponse.ok(response);
    }
}
