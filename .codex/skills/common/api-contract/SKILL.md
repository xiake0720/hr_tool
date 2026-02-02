---
name: common-api-contract
description: "Standardize /api endpoints, ApiResponse wrapper, pagination, and consistent HTTP->error code mapping. Must support GET /api/me contract."
metadata:
  short-description: "API contract & error semantics"
  tags: [common, api, contract, errors, pagination]
---

# Purpose
Keep all service APIs consistent for Vue web and future clients.

# Rules
- Base path: `/api`
- Response: `ApiResponse { code, message, data, requestId }`
- Pagination: `PageResponse { items, page, pageSize, total }`
- Error mapping:
    - 400 -> SYS_1000
    - 401 -> AUTH_4001
    - 403 -> AUTH_4003
    - 404 -> SYS_1001 (or domain)
    - 409 -> SYS_1002 (or domain)
    - 500 -> SYS_2000

# Deliverables
- `ApiResponse<T>`
- `PageResponse<T>`
- `GlobalExceptionHandler` mapping validation/auth/access/biz/fallback

# Verification
- `/api/me` returns ApiResponse wrapper and includes requestId.
- Invalid request triggers SYS_1000.

# Anti-Patterns
- Mixed response formats between services
- Returning stack traces to client
