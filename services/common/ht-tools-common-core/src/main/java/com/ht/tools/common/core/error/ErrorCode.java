package com.ht.tools.common.core.error;

/**
 * 用途：统一错误码枚举，保持错误语义与 HTTP 映射一致。
 * 职责/边界：仅用于应用层错误表达，不直接承担异常抛出或日志格式化。
 * 调用时机：异常处理、业务校验或权限拦截失败时引用。
 * 线程模型：枚举常量天然线程安全。
 * 关系说明：与 GlobalExceptionHandler、ApiResponse 协同输出统一结构。
 * 误用风险：随意新增或复用错误码会导致前端处理混乱。
 */
public enum ErrorCode {
    SYS_1000("SYS_1000", "参数校验失败"),
    SYS_1001("SYS_1001", "资源不存在"),
    SYS_1002("SYS_1002", "资源冲突"),
    SYS_2000("SYS_2000", "系统异常"),
    AUTH_4001("AUTH_4001", "未认证"),
    AUTH_4003("AUTH_4003", "无权限"),
    ORG_5201("ORG_5201", "组织结构成环"),
    IAM_5301("IAM_5301", "数据已存在");

    /**
     * 错误码字符串。
     */
    private final String code;

    /**
     * 错误提示信息。
     */
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @return 错误码字符串
     */
    public String getCode() {
        return code;
    }

    /**
     * @return 错误提示信息
     */
    public String getMessage() {
        return message;
    }
}
