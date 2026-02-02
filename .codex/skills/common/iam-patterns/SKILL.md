---
name: common-iam-patterns
description: "IAM foundation patterns for iam-service: tenant/org tree/user/role/permission schema, RBAC APIs, @RequirePermission, and GET /api/me context mapping."
metadata:
  short-description: "IAM model + API + guard templates"
  tags: [common, iam, rbac, org-tree, tenant]
---

# Purpose
Provide concrete IAM scaffolding patterns for Phase 1.

# Data Model Rules (iam-service)
- tenant: id, name, code, status, audit fields
- org_unit (tree): id, tenant_id, parent_id, name, type, path, enabled, audit fields
    - must prevent cycles when moving nodes
- user (business user): id, tenant_id, external_sub, username, email, phone, enabled, audit fields
- relations:
    - user_org_unit(user_id, org_unit_id, is_primary)
    - role(id, tenant_id, code, name, builtin)
    - permission(id, tenant_id, action, name, builtin)
    - user_role(user_id, role_id)
    - role_permission(role_id, permission_id)

# Minimal APIs (Phase 1)
- GET  /api/me
- Org:
    - POST /api/org-units
    - GET  /api/org-units/tree
- RBAC:
    - POST /api/roles
    - POST /api/permissions
    - POST /api/roles/{id}/permissions
    - POST /api/users/{id}/roles

# Authorization Rules
- Use @RequirePermission("ORG_UNIT:MANAGE") etc.
- Permission evaluation uses CurrentUserContext permissions set.
- Phase 1 can assume scope=TENANT, but keep extensible design.

# Deliverables
- JWT `sub` -> user.external_sub mapping -> (userId, tenantId)
- @RequirePermission + AOP/interceptor implementation
- Org tree service with cycle prevention

# Verification
- /api/me returns tenantId/userId/roles/permissions/requestId
- Without permission, org write returns 403 AUTH_4003
- org tree returns a correct hierarchy

# Anti-Patterns
- Skipping business-user DB mapping
- No cycle prevention for org tree
