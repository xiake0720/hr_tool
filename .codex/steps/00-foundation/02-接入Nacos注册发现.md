# Step 02：接入 Nacos 注册发现（本地）

## CODEX 执行指令（必须严格照做）
让 gateway-service 与 iam-service 启动后能注册到本地 Nacos，并在 Nacos 控制台可见。UTF-8（无 BOM）。

### 任务清单
1）为两个服务补齐 Nacos 注册配置（application.yml）：
- nacos server-addr: 127.0.0.1:8848
- spring.application.name：
    - gateway-service
    - iam-service
      -（可选）namespace=dev、group=DEFAULT_GROUP

2）在 `infra/LOCAL_ENV.md` 补充一段：
- 如何在 Nacos 控制台查看服务是否上线（服务列表）

3）为两个服务打开基础健康检查：
- 推荐引入 actuator，并开放 health endpoint（仅本地 dev）

### 交付物
- 两个服务的 application.yml 更新
  -（可选）actuator 依赖与配置

### 验收（你必须给出）
- 启动两个服务后，在 Nacos 控制台能看到：
    - gateway-service 实例 1
    - iam-service 实例 1

### 禁止扩展
- 不启用 Nacos 配置中心（Phase 1 先只做注册中心）
- 不做服务鉴权/加密配置（本地优先）
