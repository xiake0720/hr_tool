---
name: base-08-db-migrations
description: Standardize Flyway migrations for Postgres. Initial migration must be V1__init.sql defining tenant/org/user/role/permission tables with audit fields and indexes.
metadata:
  short-description: Flyway + Postgres migration discipline
  tags: [base, flyway, postgres, db, migrations, V1__init.sql]
---

# Purpose
Ensure reproducible schema setup and safe evolution.

# Rules
- Flyway MUST be enabled.
- Initial schema MUST be in `V1__init.sql`.
- Migrations MUST be forward-only; never edit applied migrations.
- Every table MUST include:
    - id (bigint), tenant_id (bigint)
    - created_at, created_by, updated_at, updated_by
- Add indexes:
    - tenant_id
    - unique constraints where needed (e.g., role code per tenant)

# Deliverables
- Flyway config (application.yml)
- `db/migration/V1__init.sql` with IAM schema:
    - tenant
    - org_unit (tree)
    - user
    - user_org_unit
    - role
    - permission
    - user_role
    - role_permission

# Acceptance Checklist
- [ ] App starts on empty DB and Flyway creates schema.
- [ ] flyway_schema_history shows version 1 applied.

# Anti-Patterns
- Manual schema edits
- Storing tenant_id in nullable columns
