# Step 14：用户管理 CRUD（创建/修改/查询/禁用）

## CODEX 执行指令（必须严格照做）
实现用户管理基础接口（不做前端），使用 MyBatis（推荐 MyBatis-Plus），返回统一 ApiResponse。UTF-8（无 BOM）。

### 任务清单
1）建立用户 DO/Mapper/Service：
- UserDO（对应 user 表）
- UserMapper（含 tenant-aware 查询）
- UserService（事务边界、校验）
  2）DTO：
- UserCreateReq（username/email/phone/externalSub 可选）
- UserUpdateReq（id + 可更新字段）
- UserResp
  3）接口：
- POST `/api/users` 创建
- PUT `/api/users/{id}` 修改
- GET `/api/users/{id}` 查询详情
- GET `/api/users` 分页查询（page/pageSize）
- PUT `/api/users/{id}/disable` 禁用
  4）tenant 规则（Phase 1 简化）：
- tenantId 从 CurrentUserContext 取；若暂时为空，允许通过一个固定 dev tenant（例如 1）占位，但必须写 TODO，且不得从客户端传 tenantId

### 交付物
- UserDO/Mapper/Service/Controller
- DTOs
- 分页响应结构

### 验收（你必须给出）
- curl 示例（至少 3 个）：
    - 创建用户
    - 分页查询
    - 禁用用户
- 通过网关同样可访问（8080/api/users）

### 禁止扩展
- 不做复杂搜索条件
- 不做导入导出
