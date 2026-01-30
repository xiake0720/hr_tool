---
name: base-03-error-codes
description: Define stable domain-prefixed error codes (SYS_, AUTH_, IAM_, ORG_) and ensure global exception mapping matches HTTP status semantics.
metadata:
  short-description: Error code system for platform
  tags: [base, errors, exception, contract]
---

# Purpose
Make errors stable, machine-readable, and contract-safe.

# Rules
- Error codes MUST be strings.
- Domain prefixes MUST be used: SYS_, AUTH_, IAM_, ORG_.
- Validation errors MUST map to SYS_1000.
- Unauthenticated MUST map to AUTH_4001.
- Forbidden MUST map to AUTH_4003.
- Do not change meaning of existing codes.

# Deliverables
- `ErrorCode` constants/enum
- `BizException(code, message)`
- `GlobalExceptionHandler` mapping:
    - BizException -> ApiResponse(code,message)
    - Validation -> SYS_1000 + field list
    - AccessDenied -> AUTH_4003
    - Auth failure -> AUTH_4001
    - Fallback -> SYS_2000

# Acceptance Checklist
- [ ] Every error response has code/message/requestId.
- [ ] 401/403/400 errors map to correct codes.

# Anti-Patterns
- Using HTTP status only without error code
- Throwing raw RuntimeException to client
