package com.ht.tools.iam.service;

import com.ht.tools.common.core.api.PageResponse;
import com.ht.tools.iam.api.dto.UserCreateReq;
import com.ht.tools.iam.api.dto.UserResp;
import com.ht.tools.iam.api.dto.UserUpdateReq;
import com.ht.tools.iam.infrastructure.entity.UserEntity;
import java.util.Optional;

/**
 * 用途：用户管理服务入口。
 * 职责/边界：封装用户相关业务规则与持久化访问。
 * 调用时机：由 Controller 或安全链路调用。
 * 线程模型：无状态 Service，线程安全。
 */
public interface UserService {

    /**
     * 行为：创建用户。
     *
     * @param req 创建请求
     * @return UserResp
     */
    UserResp create(UserCreateReq req);

    /**
     * 行为：更新用户。
     *
     * @param id 用户 ID
     * @param req 更新请求
     * @return UserResp
     */
    UserResp update(Long id, UserUpdateReq req);

    /**
     * 行为：获取用户详情。
     *
     * @param id 用户 ID
     * @return UserResp
     */
    UserResp get(Long id);

    /**
     * 行为：分页查询用户。
     *
     * @param page 页码
     * @param pageSize 每页大小
     * @return PageResponse<UserResp>
     */
    PageResponse<UserResp> page(int page, int pageSize);

    /**
     * 行为：禁用用户。
     *
     * @param id 用户 ID
     */
    void disable(Long id);

    /**
     * 行为：根据外部标识/登录名/邮箱查找用户。
     *
     * @param externalSub 外部标识
     * @param loginName 登录名
     * @param email 邮箱
     * @param tenantId 租户 ID
     * @return 用户实体
     */
    Optional<UserEntity> findByExternalSubOrLoginOrEmail(String externalSub, String loginName, String email, Long tenantId);
}
