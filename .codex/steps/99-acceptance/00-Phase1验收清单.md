# Phase 1 验收清单（Codex 执行后必须满足）

## 后端链路
- [ ] Nacos 可见 gateway-service 与 iam-service
- [ ] 通过网关访问 iam-service：
    - GET http://localhost:8080/api/health 成功
    - requestId 可透传（X-Request-Id）
- [ ] MySQL 连接正常，Flyway 执行成功（V1__init.sql）
- [ ] MyBatis CRUD 正常（至少 ping 示例或 user CRUD）
- [ ] Redis 接入正常（cache test 成功）
- [ ] Kafka 测试事件闭环成功（producer + consumer + 幂等占位）

## 认证与上下文
- [ ] OAuth2 Resource Server JWT 校验生效
- [ ] 401 返回 AUTH_4001；403 返回 AUTH_4003（ApiResponse 结构一致）
- [ ] CurrentUserContext 存在并在响应中返回 requestId
- [ ] GET /api/me 可通过网关访问并返回基本上下文

## IAM 功能
- [ ] 用户 CRUD 完整（创建/修改/分页/禁用）
- [ ] 组织树：创建/树查询/移动 + 防环校验
- [ ] 角色/权限：创建 + 绑定关系
- [ ] @RequirePermission 生效，受保护接口权限不足返回 403

## 前端（Vue3）
- [ ] web-frontend 可启动
- [ ] /login 可保存 token，apiClient 自动带 token
- [ ] /me 可展示 /api/me 数据
- [ ] /users /org /roles 页面骨架完成，且具备 loading/empty/error 状态
