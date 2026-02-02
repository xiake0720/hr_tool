package com.ht.tools.common.core.api;

import java.util.List;

/**
 * 用途：统一分页响应结构，提供 items/page/pageSize/total 四段信息。
 * 边界：仅作为 API 出参，禁止直接用于持久化或跨线程共享。
 * 线程模型：非线程安全；通常在单线程请求上下文中构建并返回。
 * 调用时机：列表查询接口返回分页数据时使用。
 * 清理责任：无；由 JVM 垃圾回收。
 * 误用风险：page/pageSize/total 未按约定设置会导致前端分页异常。
 * 与其它组件关系：通常与 {@link ApiResponse} 组合使用作为 data。
 * 为什么这样设计：保持前端分页协议稳定，避免每个接口自定义字段。
 * 踩坑点：total 必须为全量记录数而非当前页数量。
 *
 * @param <T> 数据类型
 */
public class PageResponse<T> {

    /**
     * 当前页数据列表。
     * 是否必填：是（可为空列表）。
     * 示例值：[{}, {}]。
     */
    private List<T> items;

    /**
     * 当前页码（从 1 开始）。
     * 是否必填：是。
     * 示例值：1。
     */
    private int page;

    /**
     * 每页大小。
     * 是否必填：是。
     * 示例值：20。
     */
    private int pageSize;

    /**
     * 总记录数。
     * 是否必填：是。
     * 示例值：135。
     */
    private long total;

    /**
     * 入参：无。
     * 返回：无。
     * 副作用：无。
     * 是否可空：不适用。
     * 异常：无。
     * 线程安全：非线程安全。
     * 使用建议：仅供框架反序列化或手动构建时使用。
     */
    public PageResponse() {
    }

    /**
     * 入参：items/page/pageSize/total。
     * 返回：无。
     * 副作用：初始化字段。
     * 是否可空：items 可空但建议传空列表；page/pageSize/total 不应为负。
     * 异常：无。
     * 线程安全：非线程安全。
     * 使用建议：服务层完成分页计算后构建。
     *
     * @param items 当前页数据
     * @param page 当前页码（从 1 开始）
     * @param pageSize 每页大小
     * @param total 总记录数
     */
    public PageResponse(List<T> items, int page, int pageSize, long total) {
        this.items = items;
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
    }

    /**
     * 入参：无。
     * 返回：当前页数据列表。
     * 副作用：无。
     * 是否可空：可能为空列表或 null（不建议为 null）。
     * 异常：无。
     * 线程安全：线程安全（只读访问）。
     * 使用建议：仅用于序列化输出。
     *
     * @return 当前页数据列表
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * 入参：items - 当前页数据列表。
     * 返回：无。
     * 副作用：更新 items。
     * 是否可空：可空，但建议传空列表。
     * 异常：无。
     * 线程安全：非线程安全（修改状态）。
     * 使用建议：构建响应时一次性设置。
     *
     * @param items 当前页数据列表
     */
    public void setItems(List<T> items) {
        this.items = items;
    }

    /**
     * 入参：无。
     * 返回：当前页码。
     * 副作用：无。
     * 是否可空：不适用。
     * 异常：无。
     * 线程安全：线程安全（只读访问）。
     * 使用建议：仅用于序列化输出。
     *
     * @return 当前页码
     */
    public int getPage() {
        return page;
    }

    /**
     * 入参：page - 当前页码（从 1 开始）。
     * 返回：无。
     * 副作用：更新 page。
     * 是否可空：不适用。
     * 异常：无。
     * 线程安全：非线程安全（修改状态）。
     * 使用建议：避免传入 0 或负数。
     *
     * @param page 当前页码
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * 入参：无。
     * 返回：每页大小。
     * 副作用：无。
     * 是否可空：不适用。
     * 异常：无。
     * 线程安全：线程安全（只读访问）。
     * 使用建议：仅用于序列化输出。
     *
     * @return 每页大小
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 入参：pageSize - 每页大小。
     * 返回：无。
     * 副作用：更新 pageSize。
     * 是否可空：不适用。
     * 异常：无。
     * 线程安全：非线程安全（修改状态）。
     * 使用建议：避免传入 0 或负数。
     *
     * @param pageSize 每页大小
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 入参：无。
     * 返回：总记录数。
     * 副作用：无。
     * 是否可空：不适用。
     * 异常：无。
     * 线程安全：线程安全（只读访问）。
     * 使用建议：用于前端计算页数。
     *
     * @return 总记录数
     */
    public long getTotal() {
        return total;
    }

    /**
     * 入参：total - 总记录数。
     * 返回：无。
     * 副作用：更新 total。
     * 是否可空：不适用。
     * 异常：无。
     * 线程安全：非线程安全（修改状态）。
     * 使用建议：应为全量总数而非当前页数量。
     *
     * @param total 总记录数
     */
    public void setTotal(long total) {
        this.total = total;
    }
}