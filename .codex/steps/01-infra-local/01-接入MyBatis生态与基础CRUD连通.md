# Step 07：接入 MyBatis 生态（推荐 MyBatis-Plus）并做最小CRUD连通

## CODEX 执行指令（必须严格照做）
在 iam-service 中完成 MyBatis（推荐 MyBatis-Plus）基础配置，并做一个最小示例表的 CRUD（非业务域，仅用于连通性验证）。UTF-8（无 BOM）。

### 任务清单
1）配置 MyBatis-Plus：
- mapper 扫描
- 基础配置（日志、驼峰映射等）
  2）新增一个“连通性示例”表与迁移（例如 t_ping）：
- 用 Flyway `V0__ping.sql` 或 `V1__init.sql` 里先包含 ping（若你准备下一步才写 V1，那这里用 V0）
- 表字段：id、value、created_at、updated_at
  3）实现最小 API：
- POST `/api/ping` 写入一条记录
- GET `/api/ping/latest` 读取最新一条
- 返回统一 ApiResponse

### 交付物
- db/migration/ V0__ping.sql（或临时迁移）
- PingDO + PingMapper + PingService + PingController

### 验收（你必须给出）
- 启动 iam-service，让 Flyway 自动建 ping 表
- curl：
    - `curl -X POST http://localhost:8081/api/ping`
    - `curl http://localhost:8081/api/ping/latest`
- 再通过网关验证：
    - `curl http://localhost:8080/api/ping/latest`

### 禁止扩展
- 不要开始写用户/组织/角色表（下一组 steps 写 V1__init.sql 才开始）
