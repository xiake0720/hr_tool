package com.ht.tools.common.core.error;

/**
 * 用途：统一错误码枚举，保持错误语义与 HTTP 映射一致。
 * 边界：仅用于应用层错误表达，不直接承担异常抛出或日志格式化。
 * 线程模型：枚举常量天然线程安全。
 * 调用时机：异常处理、业务校验或权限拦截失败时引用。
 * 清理责任：无。
 * 误用风险：随意新增或复用错误码会导致前端处理混乱。
 * 与其它组件关系：与 {@code GlobalExceptionHandler}、{@code ApiResponse} 协同输出。
 * 为什么这样设计：固定错误码集合，降低跨服务沟通成本。
 * 踩坑点：不要将 HTTP 状态码当作错误码；错误码需保持稳定。
 */
public enum ErrorCode {
    SYS_1000("SYS_1000", "参数校验失败"),
    SYS_1001("SYS_1001", "资源不存在"),
    SYS_1002("SYS_1002", "资源冲突"),
    SYS_2000("SYS_2000", "系统异常"),
    AUTH_4001("AUTH_4001", "未认证"),
    AUTH_4003("AUTH_4003", "无权限");

    /**
     * 错误码字符串。
     * 是否必填：是。
     * 示例值：SYS_1000。
     */
    private final String code;

    /**
     * 错误提示信息。
     * 是否必填：是。
     * 示例值：参数校验失败。
     */
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 入参：无。
     * 返回：错误码字符串。
     * 副作用：无。
     * 是否可空：不为空。
     * 异常：无。
     * 线程安全：线程安全（只读访问）。
     * 使用建议：用于 ApiResponse.code 或 BizException 携带。
     *
     * @return 错误码字符串
     */
    public String getCode() {
        return code;
    }

    /**
     * 入参：无。
     * 返回：错误提示信息。
     * 副作用：无。
     * 是否可空：不为空。
     * 异常：无。
     * 线程安全：线程安全（只读访问）。
     * 使用建议：用于 ApiResponse.message 输出。
     *
     * @return 错误提示信息
     */
    public String getMessage() {
        return message;
    }
}