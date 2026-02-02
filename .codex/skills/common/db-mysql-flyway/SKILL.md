---
name: common-db-mysql-flyway
description: "MySQL 9.5 + Flyway discipline: V1__init.sql, forward-only migrations, tenant-safe unique constraints, and audit fields on all tenant-scoped tables."
metadata:
  short-description: "MySQL + Flyway migration rules"
  tags: [common, mysql, flyway, migrations, schema]
---

# Purpose
Ensure schema is reproducible and stable across microservices.

# Rules
- Database: MySQL Server 9.5
- Flyway must be enabled for each service owning a schema.
- Initial migration must be V1__init.sql.
- Forward-only migrations: never edit applied migrations.
- Tenant-scoped tables must include:
    - tenant_id BIGINT NOT NULL
    - created_at, created_by, updated_at, updated_by
- Unique constraints must include tenant_id where appropriate: (tenant_id, code)

# Deliverables
- `db/migration/V1__init.sql` for iam-service schema
- Indices on tenant_id and foreign keys

# Verification
- Service starts on empty DB and applies migrations
- flyway_schema_history shows applied versions

# Anti-Patterns
- Manual schema edits outside Flyway
- Nullable tenant_id for tenant-scoped data
