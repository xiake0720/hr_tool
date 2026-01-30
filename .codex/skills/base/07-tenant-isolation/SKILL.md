---
name: base-07-tenant-isolation
description: Enforce strict tenant isolation  tenant_id derived only from CurrentUserContext (JWT -> user mapping). All reads/writes must filter by tenant_id; never accept tenant_id from API input.
metadata:
  short-description: Tenant isolation guardrails
  tags: [base, tenant, security, isolation]
---

# Purpose
Make cross-tenant data access impossible by design.

# Rules
- tenant_id MUST be taken from CurrentUserContext only.
- Any query returning business data MUST filter by tenant_id.
- Any write MUST set tenant_id from context.
- Client MUST NOT send tenant_id in requests (reject if present in DTOs).

# Deliverables
- `CurrentUserContext` with `tenantId()` API
- Service-layer guard methods:
    - `requireTenantMatch(entityTenantId)`
- Repository patterns that include tenant filters (e.g., findByIdAndTenantId)

# Acceptance Checklist
- [ ] Attempt to access another tenant’s resource returns AUTH_4003 or ORG_5202.
- [ ] DTOs contain no tenant_id fields.
- [ ] Repositories expose `...AndTenantId(...)` methods for sensitive reads.

# Anti-Patterns
- Using global `findById(id)` without tenant check for tenant-scoped entities
