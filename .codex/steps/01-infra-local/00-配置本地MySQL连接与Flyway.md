# Step 06：配置本地 MySQL 连接与 Flyway（先通再建表）

## CODEX 执行指令（必须严格照做）
让 iam-service 可以连接本地 MySQL 9.5，并启用 Flyway（先不写 V1 表结构，下一步再写）。UTF-8（无 BOM）。

### 任务清单
1）在 iam-service `application.yml` 增加 datasource 配置（使用占位符 env）：
- url / username / password
- 默认值可写 localhost:3306/hr_iam_dev，但密码必须通过 env 或本地 profile 提供
  2）启用 Flyway：
- locations 指向 `classpath:db/migration`
  3）增加 `application-local.yml`（仅本地，加入 .gitignore 或提示不提交密码）：
- 允许写本地开发密码，但必须提示“不要提交到仓库”
  4）在 `infra/LOCAL_ENV.md` 补充：
- 如何创建数据库 hr_iam_dev（仅命令示例）

### 交付物
- iam-service application.yml（datasource + flyway）
  -（可选）application-local.yml（含提示）
- 更新 infra/LOCAL_ENV.md

### 验收（你必须给出）
- 启动 iam-service，日志中能看到：
    - 成功连接数据库
    - Flyway 启动（即使暂无迁移也不报错）

### 禁止扩展
- 这一 step 不写任何表结构 SQL
