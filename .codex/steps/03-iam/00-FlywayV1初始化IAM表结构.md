# Step 13：Flyway V1__init.sql 初始化 IAM 表结构（MySQL）

## CODEX 执行指令（必须严格照做）
在 iam-service 创建 `db/migration/V1__init.sql`，包含用户/组织/角色/权限所需表，并包含 tenant_id 与审计字段。UTF-8（无 BOM）。

### 任务清单
1）创建 `services/iam-service/src/main/resources/db/migration/V1__init.sql`，包含表：
- tenant
- org_unit（树：parent_id、path）
- user（业务用户：external_sub、username、email、phone、enabled）
- user_org_unit
- role（tenant_id + code 唯一）
- permission（tenant_id + action 唯一）
- user_role
- role_permission
  -（保留你之前 ping 表迁移：若已用 V0，保留；若没有，将 ping 合并到 V1，并删除 V0，确保 flyway 顺序正确）

2）所有租户域表必须包含：
- tenant_id BIGINT NOT NULL（tenant 表本身可不含 tenant_id）
- created_at/created_by/updated_at/updated_by
  3）为关键字段建索引与唯一约束：
- org_unit(tenant_id,parent_id)
- user(tenant_id,external_sub) 唯一
- role(tenant_id,code) 唯一
- permission(tenant_id,action) 唯一

### 交付物
- V1__init.sql

### 验收（你必须给出）
- 清空本地 hr_iam_dev（提示用户自己执行），重启 iam-service：
    - Flyway 能成功执行 V1
- 给出验证 SQL：
    - SHOW TABLES;
    - 查看关键表存在

### 禁止扩展
- 不引入业务域表（简历、候选人等）
