# Step 12：实现 GET /api/me 并完成网关链路冒烟

## CODEX 执行指令（必须严格照做）
实现 `/api/me`，先返回“基于 JWT 的最小用户信息 + requestId”，tenantId/userId 先允许为空占位，后续再接入 DB 映射。UTF-8（无 BOM）。

### 任务清单
1）实现 `MeController`：
- GET `/api/me`
- 返回 ApiResponse，data 包含：
    - tenantId（可 null）
    - userId（可 null）
    - externalSub（JWT sub）
    - username/email（若能取到）
    - roles/permissions（可空集合）
    - requestId
      2）确保该接口受保护（需要 JWT），并能通过网关访问：
- `http://localhost:8080/api/me`

### 交付物
- MeController + MeResp DTO

### 验收（你必须给出）
- 通过网关调用（需要你本地有可用 JWT）：
    - `curl -H "Authorization: Bearer <TOKEN>" http://localhost:8080/api/me`
- 返回结构必须是 ApiResponse 且包含 requestId

### 禁止扩展
- 暂不实现真正登录页面与 token 获取流程（前端 step 再做）
