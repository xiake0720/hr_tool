package com.ht.tools.common.core.request;

import java.util.UUID;

/**
 * 用途：在单次请求处理中保存 requestId（线程级别），供核心响应包装读取。
 * 边界：仅提供线程内访问，不负责跨线程传播。
 * 线程模型：基于 ThreadLocal 绑定当前处理线程。
 * 调用时机：入口层设置与清理；业务层仅读取。
 * 清理责任：请求结束时必须调用 clear。
 * 误用风险：未清理导致线程复用串号或内存泄漏。
 * 与其它组件关系：由 Web 层过滤器设置，供 ApiResponse 读取。
 * 为什么这样设计：保证 core 层无 Spring 依赖，同时提供稳定的 requestId 访问。
 * 踩坑点：异步线程不会自动继承 ThreadLocal，需要显式传递。
 */
public final class RequestIdHolder {

    /**
     * 当前线程的 requestId 存储容器。
     */
    private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();

    private RequestIdHolder() {
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