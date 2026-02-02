# HR Tools Platform — Global Agent Rules (Phase 1 Foundation)

> Global always-on rules for Codex.
> Phase 1: platform foundation first. Business HR tools come later.

## 0. Scope & Phase Boundaries (Non-Negotiable)

### Phase 1 MUST deliver
- Backend foundation: **JDK 25 + Spring Boot 3.5.x**
- Microservices stack: **Spring Cloud 2025.0.x (compatible with Boot 3.5.x)**, with gateway + service-to-service calls + resilience baseline
- Service discovery: **Nacos** (via Spring Cloud Alibaba)
- Security: **Spring Security OAuth2 Resource Server (JWT)**
- Authorization: business-layer **RBAC** (roles/permissions) + extensible scope model
- Data isolation: strict multi-tenancy (`tenant_id` on tenant-scoped tables)
- Organization tree foundation (`org_unit`)
- Unified API conventions (ApiResponse, error codes, pagination)
- Request tracing (`requestId`) + audit fields (created_at/created_by/updated_at/updated_by)
- Messaging: **Kafka** for domain events / async processing
- Cache: **Redis** for hot data, permission cache, idempotency keys
- Minimal runnable endpoints including **GET /api/me**

### Phase 1 MUST NOT include
- Resume parsing/matching/evaluation/talent inventory business features
- App or personal-domain features
- Any auth bypass or tenant isolation shortcut
- “Big-bang” microservice over-splitting (keep a bounded set of services; prefer fewer services first)

---

## 1. Tech Baseline (Route A: Stable & Deliverable)

- JDK: 25
- Spring Boot: 3.5.x
- Spring Cloud: 2025.0.x
- Spring Cloud Alibaba BOM: 2025.0.0.0
- Database: **MySQL Server 9.5**
- Migrations: Flyway
- Persistence: **MyBatis ecosystem** (preferred: MyBatis-Plus spring-boot3 starter)
- Messaging: Kafka
- Cache: Redis (Spring Data Redis)
- Frontend Web: Vue 3 + Vite + TypeScript
- File encoding: **UTF-8 (no BOM)** for all source/config/docs

Hard rule:
- Parent POM must import BOMs via dependencyManagement:
  - spring-boot-dependencies
  - spring-cloud-dependencies
  - spring-cloud-alibaba-dependencies
- Submodules should avoid explicit dependency versions unless BOM doesn’t cover them.

---

## 2. Spring Cloud Microservices Baseline

- API Gateway: Spring Cloud Gateway
- Service-to-service calls: OpenFeign + Spring Cloud LoadBalancer
- Resilience: Resilience4j (via Spring Cloud Circuit Breaker)
- Observability baseline: Micrometer (avoid deprecated Sleuth patterns)

Notes:
- Phase 1 focuses on “can build + can run + can trace + can secure”.
- Don’t introduce complex distributed transactions in Phase 1.

---

## 3. Authentication (AuthN) & Authorization (AuthZ)

### AuthN
- Backend MUST be OAuth2 Resource Server (JWT validation).
- Token validation MUST use standard issuer/jwk configuration properties.
- No local password auth in backend (Phase 1).

### AuthZ
- Authorization MUST be enforced in backend (service/aspect), not in controller.
- Use `@RequirePermission("...")` (AOP/interceptor) or equivalent.
- RBAC evaluation must be based on CurrentUserContext and persisted bindings.

---

## 4. Multi-Tenancy Rules (Critical)

- `tenant_id` must be derived from CurrentUserContext (from JWT -> mapped business user).
- `tenant_id` must NEVER be accepted from client requests (DTOs must not contain tenant_id).
- All tenant-scoped reads/writes MUST include tenant filtering and tenant stamping.

---

## 5. Messaging (Kafka) Rules

- Use Kafka for domain events and async tasks.
- Topic naming must be stable and versionable: `hr.<domain>.<event>.v1`
- Consumers must be idempotent (use unique keys or a dedup store, e.g., Redis).
- Failures should be prepared for DLQ routing (Phase 1 can keep TODO, but leave extension points).

---

## 6. Cache (Redis) Rules

- Use Redis for: hot lookups, permission cache, idempotency keys, rate-limit keys, distributed locks when necessary.
- Cache keys must be namespaced: `hr:<service>:<entity>:...`
- Avoid caching sensitive raw tokens; store derived user context if needed with short TTL.

---

## 7. Module Boundaries (Common Modules Governance)

公共能力必须优先抽取到 common 模块，禁止跨服务复制粘贴长期存在。

### Common modules
- `ht-tools-common-core` (pure Java, no Spring dependencies):
  - ApiResponse/PageResponse, ErrorCode/BizException, headers/topic/cache key constants, pure utilities, cross-service DTO/enums
- `ht-tools-common-web` (Web common components for backend services):
  - GlobalExceptionHandler, RequestIdContext/Filter, web constants/interceptors

### Dependency direction (no cycles)
- service -> common (allowed)
- common -> service (forbidden)
- gateway-service should depend on common-core only by default (avoid MVC/WebFlux coupling)
- common-core can be used everywhere; common-web only for MVC services (Phase 1: iam-service)

Migration rule:
- When extracting to common modules, only “move package + fix imports” is allowed. Do not refactor business logic while migrating.

---

## 8. Repository Layout (Fixed)

- `services/` : Spring Boot microservices
  - `services/gateway-service`
  - `services/iam-service`
  - `services/common/ht-tools-common-core`
  - `services/common/ht-tools-common-web`
- `web-frontend/` : Vue 3 web client
- `infra/` : local environment notes/scripts (Phase 1 no docker requirement)
- `steps/` : executable work orders
- `.codex/` : agent rules and skills

---

## 9. API Contract (Global)

- All endpoints under `/api`
- Unified response wrapper: `{ code, message, data, requestId }`
- Pagination: `{ items, page, pageSize, total }`
- Required Phase 1 endpoint: `GET /api/me`
  - returns: tenantId/userId/roles/permissions/requestId (+ optional orgUnitId, username/email)

---

## 10. Error Code & HTTP Mapping (Global)

- Error codes are strings and stable: SYS_*, AUTH_*, IAM_*, ORG_*
- Mapping:
  - 400 validation -> SYS_1000
  - 401 unauthenticated -> AUTH_4001
  - 403 forbidden -> AUTH_4003
  - 404 not found -> SYS_1001 (or domain specific)
  - 409 conflict -> SYS_1002 (or domain specific)
  - 500 unexpected -> SYS_2000

---

## 11. Coding Standards (Global)

- UTF-8 (no BOM) for all source/config/docs.
- Controller: no business logic; only validation + orchestration.
- Service: business rules + permission checks + tenant checks.
- Persistence: MyBatis Mapper; do not expose DB model to API.
- Entities are not API IO. Use DTOs with jakarta validation.
- Public classes and public methods must have **Chinese Javadoc** with sufficient information:
  - 职责/边界/调用时机/线程模型与风险点/入参/出参/副作用/异常或错误码
- The detailed comment quality bar and templates are defined in:
  - `.codex/skills/common/base-standards/SKILL.md`

---

## 12. Frontend (Vue Web) Rules

- Vue 3 + Vite + TypeScript
- Router: Vue Router; State: Pinia
- API client: axios with unified ApiResponse parsing + global error handling
- UI style MUST follow `ui-ux-pro-max-skill` (clean admin, spacing, typography, consistent states)

---

## 13. Output Requirements for Codex

When implementing any task:
- List created/modified files.
- Provide verification commands (mvn) and curl examples.
- Keep changes minimal and aligned with these rules.
- If build fails, fix to a compilable state before continuing.
