---
name: base-04-logging-audit
description: Enforce requestId tracing (MDC), structured logs including tenantId/userId, sensitive-data redaction, and audit fields (created_by/updated_by/created_at/updated_at) on all tables.
metadata:
  short-description: Observability + audit baseline
  tags: [base, logging, audit, mdc, security]
---

# Purpose
Enterprise-grade traceability for HR systems.

# Rules
- Each request MUST have `requestId`:
    - Accept `X-Request-Id` if provided, else generate.
    - Put into MDC and include in response.
- Logs SHOULD include: requestId, tenantId, userId, path, status, latency.
- Sensitive data MUST NOT be logged:
    - JWT/token, passwords, API keys
    - Full phone/email/ID numbers (must be masked)

## Audit fields
All tables MUST have:
- created_at, created_by
- updated_at, updated_by
- optional deleted_at (soft delete later)

# Deliverables
- `RequestIdFilter` (or interceptor) + MDC setup
- Response includes requestId
- JPA auditing or service-layer auto-fill for audit fields

# Acceptance Checklist
- [ ] Logs show requestId for each request.
- [ ] created_by/updated_by auto-populated for write operations.
- [ ] Token never appears in logs.

# Anti-Patterns
- Logging Authorization header
- Missing audit fields
