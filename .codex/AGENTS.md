# HR Tools Platform 鈥?Global Agent Rules (Phase 1 Foundation)

> Global always-on rules for Codex.
> Phase 1: platform foundation first. Business HR tools come later.

## 0. Scope & Phase Boundaries (Non-Negotiable)
### Phase 1 MUST deliver
- Backend foundation: **JDK 25 + Spring Boot 4**
- Microservices stack: **Spring Cloud (release train compatible with Boot 4)**, with gateway + service-to-service calls + resilience + config baseline
- Security: **Spring Security OAuth2 Resource Server (JWT)**
- Authorization: business-layer **RBAC** (roles/permissions) + extensible scope model
- Data isolation: strict multi-tenancy (`tenant_id` on tenant-scoped tables)
- Organization tree foundation (`org_unit`)
- Unified API conventions (ApiResponse, error codes, pagination)
- Request tracing (`requestId`) + audit fields
- Messaging: **Kafka** for domain events / async processing
- Cache: **Redis** for hot data, token/user context cache, idempotency keys
- Minimal runnable endpoints including **GET /api/me**

### Phase 1 MUST NOT include
- Resume parsing/matching/evaluation/talent inventory business features
- App or personal-domain features
- Any auth bypass or tenant isolation shortcut
- 鈥淏ig-bang鈥?microservice over-splitting (keep bounded set of services; prefer modular monolith if you have only 1-2 services initially)

## 1. Fixed Tech Stack (Phase 1)
- JDK: 25
- Backend: Spring Boot 4.x (Java 25 supported)
- Spring Cloud: **use the Boot 4 compatible release train (prefer 2025.1.x for Boot 4.0.1+)**
- Security: Spring Security OAuth2 Resource Server (JWT)
- Database: **MySQL Server 9.5**
- Migrations: Flyway
- Persistence: **MyBatis ecosystem** (preferred: MyBatis-Plus spring-boot4-starter)
- Messaging: Kafka (producer/consumer + optional Stream abstraction)
- Cache: Redis (Spring Data Redis)

## 2. Spring Cloud Microservices Baseline
- API Gateway: Spring Cloud Gateway
- Service-to-service calls: OpenFeign + Spring Cloud LoadBalancer
- Resilience: Resilience4j (via Spring Cloud Circuit Breaker)
- Config: Spring Cloud Config (optional but recommended baseline; can be replaced by K8s ConfigMap/Secret later)
- Observability: Micrometer + tracing (do not rely on deprecated Sleuth patterns)

## 3. Authentication (AuthN) & Authorization (AuthZ)
### AuthN
- Backend MUST be OAuth2 Resource Server (JWT validation).
- Token validation MUST use standard issuer/jwk properties.
- No local password auth in backend.

### AuthZ
- Authorization MUST be enforced in backend (service/aspect), not in controller.
- Use `@RequirePermission("...")` (AOP/interceptor) or equivalent.
- RBAC evaluation must be based on current user context and persisted bindings.

## 4. Multi-Tenancy Rules (Critical)
- `tenant_id` must be derived from CurrentUserContext (from JWT -> mapped business user).
- `tenant_id` must NEVER be accepted from client requests (DTOs must not contain tenant_id).
- All tenant-scoped reads/writes MUST include tenant filtering and tenant stamping.

## 5. Messaging (Kafka) Rules
- Use Kafka for domain events and async tasks.
- Topic naming must be stable and versionable: `hr.<domain>.<event>.v1`
- Consumers must be idempotent (use unique keys or dedup store).
- Failures must go to DLQ topic if configured.

## 6. Cache (Redis) Rules
- Use Redis for: hot lookups, permission cache, idempotency keys, rate-limit keys, distributed locks when necessary.
- Cache keys must be namespaced: `hr:<service>:<entity>:...`
- Avoid caching sensitive raw tokens; store derived user context if needed with short TTL.

## 7. Repository Layout (Fixed)
- `services/` : Spring Boot microservices (recommended)
    - `services/gateway-service`
    - `services/iam-service`
    - (future) `services/recruitment-service`
- `web-frontend/` : Vue 3 web client
- `infra/` : docker-compose, kafka/redis/mysql local env, scripts
- `steps/` : executable work orders (added later)
- `.codex/` : agent rules and skills

## 8. API Contract (Global)
- All endpoints under `/api`
- Unified response wrapper: `{ code, message, data, requestId }`
- Pagination: `{ items, page, pageSize, total }`
- Required Phase 1 endpoint: `GET /api/me` returns tenantId/userId/roles/permissions/requestId (optional orgUnitId, username/email)

## 9. Error Code & HTTP Mapping (Global)
- Error codes are strings and stable: SYS_*, AUTH_*, IAM_*, ORG_*
- Mapping:
    - 400 validation -> SYS_1000
    - 401 unauthenticated -> AUTH_4001
    - 403 forbidden -> AUTH_4003
    - 404 not found -> SYS_1001 (or domain specific)
    - 409 conflict -> SYS_1002 (or domain specific)
    - 500 unexpected -> SYS_2000

## 10. Coding Standards (Global)
- UTF-8 (no BOM) for all source/config.
- Controller: no business logic; only validation + orchestration.
- Service: business rules + permission checks + tenant checks.
- Persistence: MyBatis Mapper + XML (or annotation) + Service; do not expose DB model to API.
- Entities are not API IO. Use DTOs with jakarta validation.
- Public classes and key methods must have Chinese Javadoc: 鑱岃矗/杈圭晫/鍏ュ弬/鍑哄弬/寮傚父.

## 11. Frontend (Vue Web) Rules
- Vue 3 + Vite + TypeScript
- Router: Vue Router; State: Pinia
- API client: axios with unified ApiResponse parsing + global error handling
- UI style MUST follow `ui-ux-pro-max-skill` (clean admin, spacing, typography, consistent states)

## 12. Output Requirements for Codex
When implementing any task:
- List created/modified files.
- Provide verification commands (mvn/gradle) and curl examples.
- Keep changes minimal and aligned with these rules.
