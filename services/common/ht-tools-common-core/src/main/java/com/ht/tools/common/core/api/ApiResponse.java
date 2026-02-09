package com.ht.tools.common.core.api;

import com.ht.tools.common.core.request.RequestIdHolder;

/**
 * 用途：统一 API 响应包装，包含 requestId 便于链路追踪。
 * 职责/边界：仅用于 API 输出，不作为领域对象或持久化对象使用。
 * 调用时机：Controller 或异常处理器返回响应时构造。
 * 线程模型：非线程安全，按请求构造与使用。
 *
 * @param <T> 数据类型
 */
public class ApiResponse<T> {

    private String code;
    private String message;
    private T data;
    private String requestId;

    /**
     * 行为：默认构造，用于序列化框架。
     */
    public ApiResponse() {
    }

    /**
     * 行为：构造完整响应体。
     *
     * @param code 错误码
     * @param message 提示信息
     * @param data 数据
     * @param requestId 请求追踪 ID
     */
    public ApiResponse(String code, String message, T data, String requestId) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.requestId = requestId;
    }

    /**
     * 行为：成功响应。
     *
     * @param data 数据
     * @param <T> 数据类型
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>("SYS_0000", "OK", data, RequestIdHolder.getOrCreate());
    }

    /**
     * 行为：成功响应（无数据）。
     *
     * @return ApiResponse
     */
    public static ApiResponse<Void> ok() {
        return new ApiResponse<>("SYS_0000", "OK", null, RequestIdHolder.getOrCreate());
    }

    /**
     * 行为：失败响应。
     *
     * @param code 错误码
     * @param message 提示信息
     * @param <T> 数据类型
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> fail(String code, String message) {
        return new ApiResponse<>(code, message, null, RequestIdHolder.getOrCreate());
    }

    /**
     * @return 错误码
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code 错误码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return 提示信息
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message 提示信息
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return 数据
     */
    public T getData() {
        return data;
    }

    /**
     * @param data 数据
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * @return 请求追踪 ID
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * @param requestId 请求追踪 ID
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
