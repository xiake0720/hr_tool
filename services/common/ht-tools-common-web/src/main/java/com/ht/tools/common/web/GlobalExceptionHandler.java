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
 * 职责/边界：仅处理 Controller 层异常，不替代日志与告警。
 * 调用时机：Spring MVC 捕获异常后触发。
 * 线程模型：无状态 Bean，线程安全。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 参数校验异常。
     *
     * @param ex 校验异常
     * @return ApiResponse<Void>
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class, ConstraintViolationException.class})
    public ApiResponse<Void> handleValidation(Exception ex) {
        return ApiResponse.fail(ErrorCode.SYS_1000.getCode(), ErrorCode.SYS_1000.getMessage());
    }

    /**
     * 请求体解析异常。
     *
     * @param ex 解析异常
     * @return ApiResponse<Void>
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse<Void> handleBadRequest(HttpMessageNotReadableException ex) {
        return ApiResponse.fail(ErrorCode.SYS_1000.getCode(), ErrorCode.SYS_1000.getMessage());
    }

    /**
     * 未认证异常。
     *
     * @param ex 认证异常
     * @return ApiResponse<Void>
     */
    @ExceptionHandler(AuthenticationException.class)
    public ApiResponse<Void> handleAuthentication(AuthenticationException ex) {
        return ApiResponse.fail(ErrorCode.AUTH_4001.getCode(), ErrorCode.AUTH_4001.getMessage());
    }

    /**
     * 无权限异常。
     *
     * @param ex 权限异常
     * @return ApiResponse<Void>
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ApiResponse<Void> handleAccessDenied(AccessDeniedException ex) {
        return ApiResponse.fail(ErrorCode.AUTH_4003.getCode(), ErrorCode.AUTH_4003.getMessage());
    }

    /**
     * 业务异常。
     *
     * @param ex 业务异常
     * @return ApiResponse<Void>
     */
    @ExceptionHandler(BizException.class)
    public ApiResponse<Void> handleBiz(BizException ex) {
        return ApiResponse.fail(ex.getErrorCode().getCode(), ex.getMessage());
    }

    /**
     * 未分类异常兜底。
     *
     * @param ex 未分类异常
     * @return ApiResponse<Void>
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleFallback(Exception ex) {
        return ApiResponse.fail(ErrorCode.SYS_2000.getCode(), ErrorCode.SYS_2000.getMessage());
    }
}
