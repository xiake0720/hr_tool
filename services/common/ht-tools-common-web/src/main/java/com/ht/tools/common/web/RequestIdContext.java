package com.ht.tools.common.web;

import com.ht.tools.common.core.request.RequestIdHolder;

/**
 * 用途：在单次 HTTP 请求链路中保存与获取 requestId。
 * 职责/边界：仅提供 ThreadLocal 读写封装，不处理生成策略以外的逻辑。
 * 调用时机：RequestIdFilter 写入，ApiResponse 读取。
 * 线程模型：ThreadLocal 线程隔离。
 */
public final class RequestIdContext {

    private RequestIdContext() {
    }

    /**
     * @param requestId 请求追踪 ID
     */
    public static void set(String requestId) {
        RequestIdHolder.set(requestId);
    }

    /**
     * @return requestId，可能为 null
     */
    public static String get() {
        return RequestIdHolder.get();
    }

    /**
     * @return requestId，若不存在则生成并写入
     */
    public static String getOrCreate() {
        return RequestIdHolder.getOrCreate();
    }

    /**
     * 行为：清理 ThreadLocal 中的 requestId。
     */
    public static void clear() {
        RequestIdHolder.clear();
    }
}
