package com.ht.tools.iam.common.web;

import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 用途：在请求入口生成或透传 X-Request-Id，并写入 MDC 与响应头。
 * 边界：仅处理 HTTP 请求入口，不负责跨线程传播。
 * 线程模型：单例 Bean，多线程并发执行；内部不保存可变共享状态。
 * 调用时机：每个 HTTP 请求进入时由 Spring MVC 过滤器链触发。
 * 清理责任：finally 中清理 MDC 与 {@link RequestIdContext}。
 * 误用风险：未清理会导致线程复用串号；不应在此做权限或业务判断。
 * 与其它组件关系：与 {@link RequestIdContext}、日志 MDC 配合，实现链路追踪。
 * 为什么这样设计：避免每层手动传递 requestId，保障响应与日志一致。
 * 踩坑点：异步线程不会自动继承 MDC，需要额外传递。
 */
public class RequestIdFilter extends OncePerRequestFilter {

    /**
     * 请求头名称。
     */
    private static final String HEADER_NAME = "X-Request-Id";

    /**
     * MDC 中的 key 名称。
     */
    private static final String MDC_KEY = "requestId";

    /**
     * 入参：request/response/filterChain。
     * 返回：无。
     * 副作用：设置响应头、写入 MDC 与 ThreadLocal。
     * 是否可空：request/response 不可空。
     * 异常：可能抛出 ServletException/IOException。
     * 线程安全：线程安全（无共享可变状态）。
     * 使用建议：仅在 Web 入口层调用，不要在业务代码中直接调用此方法。
     *
     * @param request HTTP 请求
     * @param response HTTP 响应
     * @param filterChain 过滤器链
     * @throws ServletException Servlet 处理异常
     * @throws IOException IO 异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestId = request.getHeader(HEADER_NAME);
        if (!StringUtils.hasText(requestId)) {
            requestId = UUID.randomUUID().toString();
        }
        RequestIdContext.set(requestId);
        MDC.put(MDC_KEY, requestId);
        response.setHeader(HEADER_NAME, requestId);
        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(MDC_KEY);
            RequestIdContext.clear();
        }
    }
}