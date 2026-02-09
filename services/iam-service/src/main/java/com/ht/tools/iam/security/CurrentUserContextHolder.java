package com.ht.tools.iam.security;

/**
 * 用途：CurrentUserContext 的 ThreadLocal 持有器。
 * 职责/边界：仅提供设置、获取与清理能力，不负责上下文构建。
 * 调用时机：过滤器写入，业务层读取，响应结束后清理。
 * 线程模型：基于 ThreadLocal，线程隔离。
 */
public final class CurrentUserContextHolder {

    private static final ThreadLocal<CurrentUserContext> HOLDER = new ThreadLocal<>();

    private CurrentUserContextHolder() {
    }

    /**
     * 行为：设置当前线程的用户上下文。
     * 入参：context 当前用户上下文。
     * 出参：无。
     *
     * @param context 当前用户上下文
     */
    public static void set(CurrentUserContext context) {
        HOLDER.set(context);
    }

    /**
     * 行为：获取当前线程的用户上下文。
     * 入参：无。
     * 出参：CurrentUserContext，可能为 null。
     *
     * @return 当前用户上下文
     */
    public static CurrentUserContext get() {
        return HOLDER.get();
    }

    /**
     * 行为：清理 ThreadLocal 中的上下文。
     * 入参：无。
     * 出参：无。
     */
    public static void clear() {
        HOLDER.remove();
    }
}
