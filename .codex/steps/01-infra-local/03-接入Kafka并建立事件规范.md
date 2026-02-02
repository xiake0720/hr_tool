# Step 09：接入 Kafka 并建立事件规范（本地）

## CODEX 执行指令（必须严格照做）
在 iam-service 接入 Kafka producer/consumer，并实现一个“测试事件”闭环（用于后续用户创建事件等）。UTF-8（无 BOM）。

### 任务清单
1）在 iam-service 增加 Kafka 配置（bootstrap servers 支持 env）
2）建立事件规范类（DTO）：
- eventId（UUID）
- eventType
- occurredAt（ISO时间）
- tenantId（Long，可为空仅测试）
- data（map 或 json string）
  3）Topic 命名：
- `hr.iam.test-event.v1`
  4）实现：
- POST `/api/events/test/publish`：发送一条测试事件到 topic
- Consumer：监听该 topic，打印日志（不得打印敏感信息），并做幂等占位（可用 Redis set-if-absent）

### 交付物
- Kafka 配置
- 事件 DTO
- Producer + Consumer
- TestEventController

### 验收（你必须给出）
- 启动 iam-service
- curl：
    - `curl -X POST http://localhost:8081/api/events/test/publish`
- 日志能看到 consumer 收到消息
- 幂等：同 eventId 重复发送应被忽略（至少写出占位逻辑）

### 禁止扩展
- 不做复杂重试/DLQ（后续完善）
