# Step 11：建立 CurrentUserContext（用户上下文）+ requestId 链路

## CODEX 执行指令（必须严格照做）
在 iam-service 建立统一的 CurrentUserContext，后续所有租户隔离、权限判断都从这里取值。UTF-8（无 BOM）。

### 任务清单
1）定义 CurrentUserContext（接口或类）字段至少：
- tenantId（Long，可先为空）
- userId（Long，可先为空）
- externalSub（String，来自 JWT sub）
- roles（Set<String>，可先为空）
- permissions（Set<String>，可先为空）
- requestId（String）
  2）实现一个拦截器/过滤器：
- 从 SecurityContext 的 JWT 读取 `sub`、username/email（可选）
- 取 requestId（从 header 或生成）
- 将 CurrentUserContext 放入 ThreadLocal（并提供 clear）
  3）在 ApiResponse 中自动注入 requestId

### 交付物
- CurrentUserContext + Holder
- Filter/Interceptor
  -（如需要）对 GlobalExceptionHandler 做适配，保证任何异常都带 requestId

### 验收（你必须给出）
- 任意接口响应都包含 requestId
- requestId 透传测试：
    - `curl -H "X-Request-Id: test-ctx-1" http://localhost:8080/api/health` 返回 requestId=test-ctx-1

### 禁止扩展
- 这一 step 不做 DB 用户映射（下一步 /api/me 与用户表再做）
