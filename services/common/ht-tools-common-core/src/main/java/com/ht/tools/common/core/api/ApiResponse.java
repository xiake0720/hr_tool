package com.ht.tools.common.core.api;

import com.ht.tools.common.core.request.RequestIdHolder;

/**
 * 用途：统一 API 响应包装，保证返回结构稳定且包含 requestId 便于链路追踪。
 * 边界：仅用于 API 出参，不作为领域对象或持久化对象使用。
 * 线程模型：非线程安全；通常在单线程请求上下文中构建并返回。
 * 调用时机：Controller 返回响应时构建；异常处理器也会构建该对象。
 * 清理责任：无；由 JVM 垃圾回收。
 * 误用风险：跨线程复用同一实例会导致数据污染；未设置 requestId 会影响排查。
 * 与其它组件关系：依赖 {@link RequestIdHolder} 提供 requestId，与 {@code ErrorCode} 约定错误码。
 * 为什么这样设计：统一前后端协议，减少前端分支判断并保证日志可关联。
 * 踩坑点：不要在业务层返回原始对象；必须保持 code/message/data/requestId 四段完整。
 *
 * @param <T> 数据类型
 */
public class ApiResponse<T> {

    /**
     * 错误码：稳定字符串标识，示例：SYS_1000。
     * 是否必填：是。
     * 示例值：SYS_0000。
     */
    private String code;

    /**
     * 错误信息或成功提示。
     * 是否必填：是。
     * 示例值：OK。
     */
    private String message;

    /**
     * 业务数据载体。
     * 是否必填：否（例如纯成功响应）。
     * 示例值：{"id":1}。
     */
    private T data;

    /**
     * 请求追踪 ID，用于日志与客户端联动排障。
     * 是否必填：是。
     * 示例值：f47ac10b-58cc-4372-a567-0e02b2c3d479。
     */
    private String requestId;

    /**
     * 入参：无。
     * 返回：无。
     * 副作用：无。
     * 是否可空：不适用。
     * 异常：无。
     * 线程安全：非线程安全（默认构造后需在单线程内填充字段）。
     * 使用建议：仅供框架反序列化或手动构建时使用。
     */
    public ApiResponse() {
    }

    /**
     * 入参：code/message/data/requestId 组成完整响应。
     * 返回：无。
     * 副作用：初始化字段。
     * 是否可空：data 可空；code/message/requestId 不建议为空。
     * 异常：无。
     * 线程安全：非线程安全。
     * 使用建议：优先使用静态工厂方法 {@link #ok(Object)} 或 {@link #fail(String, String)}。
     *
     * @param code 错误码
     * @param message 文本信息
     * @param data 数据负载
     * @param requestId 请求追踪 ID
     */
    public ApiResponse(String code, String message, T data, String requestId) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.requestId = requestId;
    }

    /**
     * 入参：data - 业务数据。
     * 返回：成功响应体。
     * 副作用：会读取或生成 {@link RequestIdHolder} 中的 requestId。
     * 是否可空：data 可空。
     * 异常：无。
     * 线程安全：线程安全（每次调用创建新对象）。
     * 使用建议：Controller 成功返回时调用。
     *
     * @param data 业务数据
     * @return 成功响应
     */
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>("SYS_0000", "OK", data, RequestIdHolder.getOrCreate());
    }

    /**
     * 入参：无。
     * 返回：空数据成功响应体。
     * 副作用：会读取或生成 {@link RequestIdHolder} 中的 requestId。
     * 是否可空：不适用。
     * 异常：无。
     * 线程安全：线程安全（每次调用创建新对象）。
     * 使用建议：无需返回数据的成功场景使用。
     *
     * @return 成功响应
     */
    public static ApiResponse<Void> ok() {
        return new ApiResponse<>("SYS_0000", "OK", null, RequestIdHolder.getOrCreate());
    }

    /**
     * 入参：code/message 代表失败原因。
     * 返回：失败响应体（data 为 null）。
     * 副作用：会读取或生成 {@link RequestIdHolder} 中的 requestId。
     * 是否可空：code/message 不建议为空。
     * 异常：无。
     * 线程安全：线程安全（每次调用创建新对象）。
     * 使用建议：异常处理器统一返回失败响应时使用。
     *
     * @param code 错误码
     * @param message 错误信息
     * @return 失败响应
     */
    public static <T> ApiResponse<T> fail(String code, String message) {
        return new ApiResponse<>(code, message, null, RequestIdHolder.getOrCreate());
    }

    /**
     * 入参：无。
     * 返回：错误码。
     * 副作用：无。
     * 是否可空：可能为空（不建议）。
     * 异常：无。
     * 线程安全：线程安全（只读访问）。
     * 使用建议：仅用于序列化或日志输出，不用于业务判断。
     *
     * @return 错误码
     */
    public String getCode() {
        return code;
    }

    /**
     * 入参：code - 错误码。
     * 返回：无。
     * 副作用：更新当前对象的错误码。
     * 是否可空：不建议为空。
     * 异常：无。
     * 线程安全：非线程安全（修改状态）。
     * 使用建议：优先通过构造器或工厂方法设置。
     *
     * @param code 错误码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 入参：无。
     * 返回：错误信息。
     * 副作用：无。
     * 是否可空：可能为空（不建议）。
     * 异常：无。
     * 线程安全：线程安全（只读访问）。
     * 使用建议：仅用于序列化或日志输出。
     *
     * @return 错误信息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 入参：message - 错误或提示信息。
     * 返回：无。
     * 副作用：更新当前对象的 message。
     * 是否可空：不建议为空。
     * 异常：无。
     * 线程安全：非线程安全（修改状态）。
     * 使用建议：优先通过构造器或工厂方法设置。
     *
     * @param message 文本信息
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 入参：无。
     * 返回：业务数据。
     * 副作用：无。
     * 是否可空：可能为空。
     * 异常：无。
     * 线程安全：线程安全（只读访问）。
     * 使用建议：仅用于序列化或日志输出。
     *
     * @return 业务数据
     */
    public T getData() {
        return data;
    }

    /**
     * 入参：data - 业务数据。
     * 返回：无。
     * 副作用：更新当前对象的 data。
     * 是否可空：可空。
     * 异常：无。
     * 线程安全：非线程安全（修改状态）。
     * 使用建议：优先通过构造器或工厂方法设置。
     *
     * @param data 业务数据
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * 入参：无。
     * 返回：请求追踪 ID。
     * 副作用：无。
     * 是否可空：可能为空（不建议）。
     * 异常：无。
     * 线程安全：线程安全（只读访问）。
     * 使用建议：用于日志与问题排查，不参与业务判断。
     *
     * @return requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * 入参：requestId - 请求追踪 ID。
     * 返回：无。
     * 副作用：更新当前对象的 requestId。
     * 是否可空：不建议为空。
     * 异常：无。
     * 线程安全：非线程安全（修改状态）。
     * 使用建议：优先由 {@link RequestIdHolder} 提供，避免手动拼装。
     *
     * @param requestId 请求追踪 ID
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}