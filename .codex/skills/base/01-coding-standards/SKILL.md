---
name: base-01-coding-standards
description: Enforce Java platform coding standards  UTF-8, layered architecture (controller/service/repository), DTO-only APIs, Chinese Javadoc, audit fields, and tenant_id everywhere.
metadata:
  short-description: Java standards & conventions
  tags: [base, java, standards, dto, validation, spring]
---

# Purpose
Make Codex generate consistent, enterprise-grade Spring Boot code.

# Rules
## Encoding & Style
- All source files MUST be UTF-8 (no BOM).
- Chinese comments MUST render correctly; no "??" garbling.

## Layering
- Controller MUST NOT contain business logic.
- Repository MUST NOT be called directly from Controller.
- Service owns business rules + authz checks + tenant checks.

## API Contracts
- Entities MUST NOT be used as API request/response.
- DTOs MUST be used for all API IO: *CreateReq/UpdateReq/QueryReq/Resp*.
- Request DTOs MUST use `jakarta.validation` annotations and `@Valid`.

## Documentation
- Public classes and key methods MUST have Chinese Javadoc:
    - 职责、边界、入参、出参、异常

## Security & Tenant
- tenant_id MUST exist in all business entities and be set from CurrentUserContext (never from client).

# Deliverables
- `ApiResponse<T>` (common API wrapper)
- `BizException` + `ErrorCode` constants/enum
- `GlobalExceptionHandler` (`@RestControllerAdvice`)
- `CurrentUserContext` utility
- DTO packages in each module

# Acceptance Checklist
- [ ] No controller returns JPA entities.
- [ ] Validation errors return SYS_1000 with field errors.
- [ ] Service layer contains tenant checks and permission checks.
- [ ] Chinese Javadoc exists on core services/controllers.

# Anti-Patterns
- “Fat Controller”
- Entity exposed to API
- Client-provided tenant_id
