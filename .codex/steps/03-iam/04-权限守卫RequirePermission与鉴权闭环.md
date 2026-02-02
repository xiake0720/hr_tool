# Step 17：@RequirePermission 权限守卫 + 鉴权闭环

## CODEX 执行指令（必须严格照做）
实现权限注解 `@RequirePermission`，并让某些接口必须具备指定 permission 才能访问。UTF-8（无 BOM）。

### 任务清单
1）定义注解：
- `@RequirePermission("ORG_UNIT:MANAGE")`
  2）实现 AOP 或 HandlerInterceptor：
- 在进入 Controller/Service 前检查 CurrentUserContext.permissions 是否包含
- 若不包含返回 403 + AUTH_4003（或更细 IAM_5403，但 code 仍需稳定）
  3）实现“权限载入”机制（Phase 1 简化）：
- 从 DB 查用户角色->权限集合，填充到 CurrentUserContext
- 可加 Redis 缓存（短 TTL），key 命名按规范

4）选择至少 2 个接口加权限守卫：
- 例如 POST /api/org-units 需要 ORG_UNIT:MANAGE
- 例如 POST /api/roles 需要 ROLE:MANAGE

### 交付物
- @RequirePermission
- 权限检查切面/拦截器
- 权限加载服务（UserPermissionService）+（可选）Redis 缓存

### 验收（你必须给出）
- 场景1：无权限 token 调用受保护接口 -> 403 + AUTH_4003
- 场景2：绑定权限后再调用 -> 成功
- 给出至少 2 条 curl 示例（携带 token）

### 禁止扩展
- 不做复杂 scope（tenant 外更细颗粒，后续再加）
