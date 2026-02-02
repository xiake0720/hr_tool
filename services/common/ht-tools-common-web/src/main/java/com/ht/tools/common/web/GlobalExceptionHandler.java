package com.ht.tools.common.web;

import com.ht.tools.common.core.api.ApiResponse;
import com.ht.tools.common.core.error.BizException;
import com.ht.tools.common.core.error.ErrorCode;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 用途：全局异常转换器，将常见异常映射为统一 ApiResponse。
 * 边界：只处理 Controller 层向外抛出的异常，不替代底层日志与告警。
 * 线程模型：无状态 Bean，线程安全。
 * 调用时机：Spring MVC 捕获异常后触发。
 * 清理责任：无。
 * 误用风险：过度吞掉异常会隐藏真实错误原因，需结合日志记录。
 * 与其它组件关系：与 ApiResponse、ErrorCode 协同输出统一结构。
 * 为什么这样设计：避免每个控制器重复 try-catch，统一错误语义。
 * 踩坑点：认证/鉴权异常优先由 Spring Security 处理，确保配置不冲突。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 入参：ex - 校验相关异常。
     * 返回：参数校验失败的统一响应。
     * 副作用：无（不记录日志）。
     * 是否可空：不可空。
     * 异常：无。
     * 线程安全：线程安全（无共享状态）。
     * 使用建议：如需精细错误信息可扩展字段，但保持错误码稳定。
     *
     * @param ex 校验异常
     * @return ApiResponse<Void>
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class, ConstraintViolationException.class})
    public ApiResponse<Void> handleValidation(Exception ex) {
        return ApiResponse.fail(ErrorCode.SYS_1000.getCode(), ErrorCode.SYS_1000.getMessage());
    }

    /**
     * 入参：ex - 请求体解析异常。
     * 返回：参数校验失败的统一响应。
     * 副作用：无。
     * 是否可空：不可空。
     * 异常：无。
     * 线程安全：线程安全。
     * 使用建议：用于 JSON 解析失败等场景。
     *
     * @param ex 解析异常
     * @return ApiResponse<Void>
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse<Void> handleBadRequest(HttpMessageNotReadableException ex) {
        return ApiResponse.fail(ErrorCode.SYS_1000.getCode(), ErrorCode.SYS_1000.getMessage());
    }

    /**
     * 入参：ex - 认证异常。
     * 返回：未认证响应。
     * 副作用：无。
     * 是否可空：不可空。
     * 异常：无。
     * 线程安全：线程安全。
     * 使用建议：确保与 Spring Security 的入口点配置一致。
     *
     * @param ex 认证异常
     * @return ApiResponse<Void>
     */
    @ExceptionHandler(AuthenticationException.class)
    public ApiResponse<Void> handleAuthentication(AuthenticationException ex) {
        return ApiResponse.fail(ErrorCode.AUTH_4001.getCode(), ErrorCode.AUTH_4001.getMessage());
    }

    /**
     * 入参：ex - 鉴权异常。
     * 返回：无权限响应。
     * 副作用：无。
     * 是否可空：不可空。
     * 异常：无。
     * 线程安全：线程安全。
     * 使用建议：结合权限守卫使用，避免直接暴露权限细节。
     *
     * @param ex 鉴权异常
     * @return ApiResponse<Void>
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ApiResponse<Void> handleAccessDenied(AccessDeniedException ex) {
        return ApiResponse.fail(ErrorCode.AUTH_4003.getCode(), ErrorCode.AUTH_4003.getMessage());
    }

    /**
     * 入参：ex - 业务异常。
     * 返回：业务错误响应。
     * 副作用：无。
     * 是否可空：不可空。
     * 异常：无。
     * 线程安全：线程安全。
     * 使用建议：BizException 中的 message 需避免敏感信息。
     *
     * @param ex 业务异常
     * @return ApiResponse<Void>
     */
    @ExceptionHandler(BizException.class)
    public ApiResponse<Void> handleBiz(BizException ex) {
        return ApiResponse.fail(ex.getErrorCode().getCode(), ex.getMessage());
    }

    /**
     * 入参：ex - 未分类异常。
     * 返回：系统异常响应。
     * 副作用：无。
     * 是否可空：不可空。
     * 异常：无。
     * 线程安全：线程安全。
     * 使用建议：实际项目中应配合日志告警输出堆栈。
     *
     * @param ex 未分类异常
     * @return ApiResponse<Void>
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleFallback(Exception ex) {
        return ApiResponse.fail(ErrorCode.SYS_2000.getCode(), ErrorCode.SYS_2000.getMessage());
    }
}