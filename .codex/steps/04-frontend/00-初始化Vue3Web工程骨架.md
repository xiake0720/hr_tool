# Step 18：初始化 Vue3 Web 工程骨架（Vite + TS + Pinia + Router）

## CODEX 执行指令（必须严格照做）
在 `web-frontend/` 初始化 Vue3 工程骨架，并提供 AdminLayout 与基础页面结构。UTF-8（无 BOM）。

### 任务清单
1）创建 web-frontend 工程（Vite + Vue + TypeScript）
2）安装并配置：
- vue-router
- pinia
- axios
  3）建立目录结构：
- src/api/
- src/pages/
- src/components/
- src/layouts/
- src/router/
- src/stores/
- src/styles/
  4）实现最小页面：
- /me（后续调用 /api/me）
- 一个基础 Layout（侧边栏+顶部+内容区域）
  5）实现 ui-ux-pro-max-skill 风格基础：
- styles/tokens.ts（间距、字体层级、基础颜色变量）
- styles/global.css（极简、干净的后台风）

### 交付物
- web-frontend 全套工程文件
- README.md 补充前端启动命令

### 验收（你必须给出）
- 前端启动命令：
    - `cd web-frontend && npm i && npm run dev`
- 浏览器能打开并看到 /me 页面占位

### 禁止扩展
- 不做复杂组件库选型（先用原生+少量自建组件）
