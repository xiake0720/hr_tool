package com.ht.tools.common.core.request;

import java.util.UUID;

/**
 * 用途：在单次请求处理中保存 requestId（线程级别），供核心响应包装读取。
 * 职责/边界：不依赖 Spring，仅提供 ThreadLocal 读写能力。
 * 调用时机：Web 层请求进入时写入，响应返回时读取。
 * 线程模型：基于 ThreadLocal，每个线程独立存储。
 * 误用风险：异步线程需显式传递 requestId，否则无法读取。
 */
public final class RequestIdHolder {

    private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();

    private RequestIdHolder() {
    }

    /**
     * 行为：设置当前线程的 requestId。
     * 入参：requestId 请求追踪 ID。
     * 出参：无。
     *
     * @param requestId 请求追踪 ID
     */
    public static void set(String requestId) {
        HOLDER.set(requestId);
    }

    /**
     * 行为：获取当前线程的 requestId。
     * 入参：无。
     * 出参：requestId，可能为 null。
     *
     * @return requestId
     */
    public static String get() {
        return HOLDER.get();
    }

    /**
     * 行为：获取非空 requestId，若不存在则生成并写入。
     * 入参：无。
     * 出参：requestId。
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
     * 行为：清理 ThreadLocal 中的 requestId。
     * 入参：无。
     * 出参：无。
     */
    public static void clear() {
        HOLDER.remove();
    }
}
