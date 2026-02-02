---
name: gen-iam-foundation-scaffold
description: "Scaffold iam-service foundation: MySQL Flyway V1 schema, MyBatis-Plus mappers/services/controllers, OAuth2 JWT current user mapping, RBAC, org tree, @RequirePermission, and GET /api/me."
metadata:
  short-description: "IAM foundation scaffold for iam-service"
  tags: [generator, iam, rbac, org-tree, flyway, mybatis-plus, oauth2, api-me]
---

# Purpose
Create a minimal closed-loop IAM foundation in iam-service.

# Rules
- Must conform to AGENTS.md (Phase 1 scope only).
- Must use MySQL + Flyway V1__init.sql for schema.
- Must use MyBatis (prefer MyBatis-Plus boot3 starter).
- Must implement:
    - JWT -> business user mapping (external_sub)
    - `/api/me`
    - org tree endpoints
    - RBAC endpoints
    - `@RequirePermission` guard

# Deliverables
- `db/migration/V1__init.sql` (tenant/org/user/role/permission tables with audit fields + tenant-safe uniques)
- DTOs + Controllers returning ApiResponse
- Services enforcing tenant isolation + permission checks
- MyBatis mapper interfaces + xml or annotations (consistent choice)
- Seed data script (optional but recommended for local run)

# Verification
- Empty DB -> Flyway applies V1 and service starts.
- `/api/me` returns tenantId/userId/roles/permissions/requestId with a valid token.
- Forbidden endpoints return AUTH_4003.

# Anti-Patterns
- Accepting tenant_id from API request
- No tenant filtering in queries
