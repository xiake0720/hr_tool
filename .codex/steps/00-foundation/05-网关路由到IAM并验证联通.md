# Step 05：网关路由到 IAM 并验证联通

## CODEX 执行指令（必须严格照做）
确保通过网关访问 iam-service 的 `/api/health` 成功，验证 Nacos + Gateway 路由链路可用。UTF-8（无 BOM）。

### 任务清单
1）确认 gateway-service 路由规则正确（/api/** -> iam-service）
2）确认 iam-service 注册在 Nacos 且健康
3）若请求头携带 X-Request-Id，确保最终响应里的 requestId 与之匹配

### 交付物
-（如需要）调整 gateway/iam 配置
- 更新 README.md：后端链路启动顺序与验证 curl

### 验收（你必须给出）
- 启动顺序：
    1. Nacos（你无需写安装，只写“确保已启动”）
    2. iam-service
    3. gateway-service
- curl（必须）：
    - `curl http://localhost:8080/api/health`
    - `curl -H "X-Request-Id: test-123" http://localhost:8080/api/health`（验证 requestId）

### 禁止扩展
- 不引入任何 auth 逻辑
