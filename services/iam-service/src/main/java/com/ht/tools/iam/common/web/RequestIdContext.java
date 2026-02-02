package com.ht.tools.iam.common.web;

import java.util.UUID;

/**
 * 用途：在单次 HTTP 请求链路中保存与获取 requestId，用于日志追踪与响应回传。
 * 边界：仅面向 Web 请求处理线程使用，不负责跨线程传播。
 * 线程模型：基于 {@link ThreadLocal} 绑定当前处理线程。
 * 调用时机：请求入口 Filter/Interceptor 设置与清理；业务层只读。
 * 清理责任：请求结束时必须调用 {@link #clear()}，避免线程复用污染。
 * 误用风险：异步线程直接调用可能拿不到 requestId 或拿到错误值。
 * 与其它组件关系：与 {@link RequestIdFilter}、{@link com.ht.tools.iam.common.api.ApiResponse} 协作。
 * 为什么这样设计：避免层层传参，统一链路追踪 ID 的来源。
 * 踩坑点：ThreadLocal 默认不跨线程，使用异步需显式传递或封装。
 */
public final class RequestIdContext {

    /**
     * 当前线程的 requestId 存储容器。
     * 是否必填：是（每请求应设置）。
     * 误用风险：未清理会导致线程复用时串号与内存泄漏。
     */
    private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();

    private RequestIdContext() {
        // 工具类禁止实例化
    }

    /**
     * 入参：requestId - 请求追踪 ID。
     * 返回：无。
     * 副作用：写入 ThreadLocal。
     * 是否可空：不建议为空。
     * 异常：无。
     * 线程安全：线程安全（ThreadLocal 线程隔离）。
     * 使用建议：仅在请求入口处调用，业务层避免调用 set。
     *
     * @param requestId 请求追踪 ID
     */
    public static void set(String requestId) {
        HOLDER.set(requestId);
    }

    /**
     * 入参：无。
     * 返回：当前线程的 requestId。
     * 副作用：无。
     * 是否可空：可空，表示尚未设置。
     * 异常：无。
     * 线程安全：线程安全（ThreadLocal 线程隔离）。
     * 使用建议：业务层读取时需考虑可能为 null。
     *
     * @return requestId，可能为 null
     */
    public static String get() {
        return HOLDER.get();
    }

    /**
     * 入参：无。
     * 返回：非空 requestId。
     * 副作用：在未设置时会生成 UUID 并写入 ThreadLocal。
     * 是否可空：不为空。
     * 异常：无。
     * 线程安全：线程安全（ThreadLocal 线程隔离）。
     * 使用建议：用于需要确保 requestId 存在的场景。
     *
     * @return requestId
     */
    public static String getOrCreate() {
        String requestId = HOLDER.get();
        if (requestId == null || requestId.isBlank()) {
            requestId = UUID.randomUUID().toString();
            HOLDER.set(requestId);
        }
        return requestId;
    }

    /**
     * 入参：无。
     * 返回：无。
     * 副作用：清理 ThreadLocal 中的 requestId。
     * 是否可空：不适用。
     * 异常：无。
     * 线程安全：线程安全（ThreadLocal 线程隔离）。
     * 使用建议：必须在请求结束时调用，通常在 Filter 的 finally 块中完成。
     */
    public static void clear() {
        HOLDER.remove();
    }
}