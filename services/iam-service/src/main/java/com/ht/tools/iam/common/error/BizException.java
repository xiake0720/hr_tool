package com.ht.tools.iam.common.error;

/**
 * 用途：业务异常载体，携带 ErrorCode 以便统一输出。
 * 边界：仅在应用层抛出，禁止作为系统异常或第三方异常的替代。
 * 线程模型：异常对象为一次性使用，线程安全问题不适用。
 * 调用时机：业务规则、权限校验或资源状态不满足时抛出。
 * 清理责任：无；由异常链处理与 JVM 回收。
 * 误用风险：滥用 BizException 会掩盖真实系统异常。
 * 与其它组件关系：由 {@code GlobalExceptionHandler} 转为 ApiResponse。
 * 为什么这样设计：保持错误码与业务语义绑定，减少控制层分支判断。
 * 踩坑点：不要在异常中携带敏感数据，避免暴露给客户端。
 */
public class BizException extends RuntimeException {

    /**
     * 业务错误码枚举。
     * 是否必填：是。
     */
    private final ErrorCode errorCode;

    /**
     * 入参：errorCode - 业务错误码。
     * 返回：无。
     * 副作用：初始化异常消息为 errorCode.message。
     * 是否可空：不可空。
     * 异常：无。
     * 线程安全：异常对象为一次性使用，不涉及线程安全。
     * 使用建议：优先传入明确的错误码，便于统一处理。
     *
     * @param errorCode 业务错误码
     */
    public BizException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    /**
     * 入参：errorCode/message - 业务错误码与自定义消息。
     * 返回：无。
     * 副作用：初始化异常消息为指定 message。
     * 是否可空：errorCode 不可空；message 可空但不建议。
     * 异常：无。
     * 线程安全：异常对象为一次性使用，不涉及线程安全。
     * 使用建议：仅在需要替换默认消息时使用。
     *
     * @param errorCode 业务错误码
     * @param message 自定义消息
     */
    public BizException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * 入参：无。
     * 返回：业务错误码枚举。
     * 副作用：无。
     * 是否可空：不为空。
     * 异常：无。
     * 线程安全：异常对象为一次性使用，不涉及线程安全。
     * 使用建议：异常处理器读取错误码时调用。
     *
     * @return 业务错误码
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}