package com.ht.tools.iam.api.dto;

/**
 * 用途：infra 冒烟接口响应 DTO。
 * 职责/边界：仅用于 API 输出，不用于持久化或业务内部传输。
 * 调用时机：Controller 返回 ApiResponse 时使用。
 * 线程模型：不可变 DTO，线程安全。
 * 关系说明：由 InfraPingService 构造并返回。
 */
public class InfraPingResponse {

    /**
     * 固定响应消息。
     */
    private final String msg;

    /**
     * 记录数量。
     */
    private final long count;

    /**
     * 行为：构造 infra 冒烟响应 DTO。
     * 入参：msg 响应消息，count 记录数量。
     * 出参：InfraPingResponse。
     * 异常：无。
     *
     * @param msg 响应消息
     * @param count 记录数量
     */
    public InfraPingResponse(String msg, long count) {
        this.msg = msg;
        this.count = count;
    }

    /**
     * @return 响应消息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @return 记录数量
     */
    public long getCount() {
        return count;
    }
}
