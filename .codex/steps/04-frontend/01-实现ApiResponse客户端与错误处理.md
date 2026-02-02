# Step 19：实现 ApiResponse 客户端封装 + 统一错误处理

## CODEX 执行指令（必须严格照做）
在 web-frontend 实现 axios client，统一解析后端 ApiResponse，并处理 401/403。UTF-8（无 BOM）。

### 任务清单
1）实现 `src/api/apiClient.ts`：
- baseURL 从 env `VITE_API_BASE_URL`
- 解析 ApiResponse：code/message/data/requestId
- 对 AUTH_4001：跳转到 /login 或弹出“请登录”
- 对 AUTH_4003：toast 提示“无权限”
  2）增加 `src/api/iam.ts`：
- getMe(): GET /api/me
- listUsers(): GET /api/users?page=&pageSize=
  3）实现一个简单 toast 机制（可先用 alert，占位也行，但要统一入口）

### 交付物
- apiClient.ts
- iam.ts

### 验收（你必须给出）
- 在 /me 页面调用 getMe()，打印/展示 data
- 若后端未带 token，页面能提示“请登录”或跳转 /login

### 禁止扩展
- 不引入复杂状态管理（Pinia 仅存 user/me）
