---
name: base-02-api-conventions
description: Standardize REST APIs  /api prefix, ApiResponse wrapper, pagination (page/pageSize/total), validation errors, and must implement GET /api/me returning tenantId/userId/roles/permissions/requestId.
metadata:
  short-description: API contract conventions
  tags: [base, api, contract, pagination, api-me]
---

# Purpose
Ensure every endpoint looks and behaves predictably for future web/app clients.

# Rules
- All endpoints MUST be under `/api`.
- All responses MUST be wrapped in `ApiResponse`.
- Pagination MUST return `{items,page,pageSize,total}`.
- Validation failures MUST return SYS_1000 with field-level details.
- HTTP statuses MUST be consistent:
    - 200 OK for success
    - 400 for validation (SYS_1000)
    - 401 for unauthenticated (AUTH_4001)
    - 403 for forbidden (AUTH_4003)
    - 404 for not found (SYS_1001 / ORG_5101 etc.)
    - 409 for conflict (unique constraint, duplicate binding)
    - 500 for unexpected (SYS_2000)

# Required Endpoint (Phase 1)
## GET /api/me
Return current user context:
- tenantId (Long)
- userId (Long)
- username/email (String, optional)
- roles (Set<String>)
- permissions (Set<String>)
- orgUnitId (Long, optional)
- requestId (String)

# Deliverables
- `ApiResponse<T>` and `PageResponse<T>` models
- `/api/me` controller + service
- Global validation error formatting

# Acceptance Checklist
- [ ] `/api/me` returns required fields and `requestId`.
- [ ] All endpoints return ApiResponse wrapper (no raw objects).
- [ ] Paging endpoints follow page/pageSize/total.

# Anti-Patterns
- Mixed response formats
- Leaking stack traces to clients
