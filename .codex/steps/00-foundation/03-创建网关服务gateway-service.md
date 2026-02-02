# Step 03：完善网关服务 gateway-service（最小可路由）

## CODEX 执行指令（必须严格照做）
让 gateway-service 成为统一入口，具备基础路由、基础返回规范（至少能代理到 iam-service）。UTF-8（无 BOM）。

### 任务清单
1）在 gateway-service 增加路由配置：
- 将 `/api/**` 转发到 iam-service（通过 Nacos 服务名负载均衡）
- 保留 requestId 透传（X-Request-Id）

2）实现一个最小网关自检接口（仅网关本地）：
- GET `/gateway/health` 返回简单文本或 JSON

3）确保网关启动后能正常监听 8080，且 Nacos 注册正常

### 交付物
- gateway-service application.yml（路由配置）
  -（可选）网关 health controller

### 验收（你必须给出）
- 网关启动命令
- curl：
    - `curl http://localhost:8080/gateway/health`

### 禁止扩展
- 不做鉴权策略下沉到网关（Phase 1 先让 IAM 服务自己做资源服务器校验）
- 不做复杂熔断限流（后续再加）
