# Step 08：接入 Redis 并建立缓存规范（本地）

## CODEX 执行指令（必须严格照做）
在 iam-service 接入 Redis，提供一个最小缓存示例（用于后续权限缓存/幂等等）。UTF-8（无 BOM）。

### 任务清单
1）在 iam-service 增加 Redis 配置（host/port，支持 env 覆盖）
2）新增一个 CacheKey 规范类：
- 统一 key 前缀：`hr:iam:<purpose>:...`
  3）实现最小接口：
- GET `/api/cache/test`：写入一个 key（TTL 60s）并读取返回
- 返回 ApiResponse

### 交付物
- Redis 配置项
- CacheKeys 工具类
- CacheTestController

### 验收（你必须给出）
- 启动 iam-service
- curl：
    - `curl http://localhost:8081/api/cache/test`

### 禁止扩展
- 不缓存 token 原文
- 不做分布式锁（后续需要再加）
