# Step 04：完善 IAM 服务 iam-service（最小可运行骨架）

## CODEX 执行指令（必须严格照做）
为 iam-service 增加基础工程规范：统一返回体、全局异常、错误码、requestId、基础 health。UTF-8（无 BOM）。

### 任务清单
1）在 iam-service 建立 common 基础代码（可在 iam-service 内部先做，后续再抽 common）：
- `ApiResponse<T>`：{code,message,data,requestId}
- `ErrorCode`：至少包含 SYS_1000/SYS_2000/AUTH_4001/AUTH_4003
- `BizException`
- `GlobalExceptionHandler`：映射 validation/auth/access/biz/fallback

2）实现 requestId 生成与透传：
- 如果请求头有 X-Request-Id 则沿用，否则生成
- 放入 MDC（可选）并在 ApiResponse 中返回 requestId

3）提供最小健康接口：
- GET `/api/health` 返回 ApiResponse ok（注意：在 /api 下，走统一返回）

### 交付物
- iam-service 中新增 common 目录（包）与上述类
- `HealthController`

### 验收（你必须给出）
- 启动 iam-service
- curl：
    - `curl http://localhost:8081/api/health` 返回 ApiResponse 且包含 requestId

### 禁止扩展
- 不写用户/组织/角色业务接口（下一阶段做）
