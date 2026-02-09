package com.ht.tools.iam.api.dto;

/**
 * 用途：缓存冒烟接口响应 DTO。
 * 职责/边界：仅用于 API 输出，不用于持久化与业务内部传输。
 * 调用时机：Controller 返回 ApiResponse 时使用。
 * 线程模型：不可变 DTO，线程安全。
 */
public class CacheTestResponse {

    /**
     * 缓存 key。
     */
    private final String key;

    /**
     * 缓存 value。
     */
    private final String value;

    /**
     * 行为：构造缓存冒烟响应 DTO。
     * 入参：key 缓存 key，value 缓存 value。
     * 出参：CacheTestResponse。
     * 异常：无。
     *
     * @param key 缓存 key
     * @param value 缓存 value
     */
    public CacheTestResponse(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * @return 缓存 key
     */
    public String getKey() {
        return key;
    }

    /**
     * @return 缓存 value
     */
    public String getValue() {
        return value;
    }
}
