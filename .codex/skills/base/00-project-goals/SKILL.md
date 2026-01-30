---
name: base-00-project-goals
description: "Define immutable goals & boundaries for Phase 1 foundation: Java 25 + Spring Boot 4, Keycloak OIDC/JWT auth, RBAC authorization, tenant isolation, org tree, and GET /api/me. Do NOT build recruitment tools yet."
metadata:
  short-description: HR tools platform Phase 1 scope guard
  tags: [base, scope, architecture, spring-boot-4, jdk-25, keycloak, postgres, flyway]
---

# Purpose
Lock the project direction and Phase 1 deliverables so Codex does not drift.

# When to use this skill
- Starting the project
- Any time a task could expand scope (resume parsing/matching/evals/inventory)
- Any time architecture decisions are made

# Non-negotiable Rules (MUST / MUST NOT)
## MUST
- Target runtime: **JDK 25**.
- Framework: **Spring Boot 4.x** (Spring Framework 7 baseline).
- Database: **PostgreSQL** + **Flyway** migrations.
- Authentication: **Keycloak** via OIDC/JWT; Java platform is a **Resource Server**.
- Authorization: **Business-layer RBAC** (roles/permissions/scopes) implemented in Java platform.
- Multi-tenancy: **All business data must be isolated by tenant_id**.
- Provide minimal Phase 1 endpoints to validate foundation, including **GET /api/me**.

## MUST NOT
- Do not implement resume parsing, resume matching, candidate evaluation, or talent inventory business features in Phase 1.
- Do not build “personal tools” or consumer features.
- Do not hardcode auth or bypass tenant checks.
- Do not introduce microservices/DDD over-splitting in Phase 1.

# Deliverables (Phase 1)
- Repo contains `.codex/skills` and uses these skills as constraints.
- Java platform foundation capable of: JWT auth, current user context, RBAC guard, tenant isolation scaffolding, org tree scaffolding, Flyway init.
- `/api/me` returns current user context (tenantId/userId/roles/permissions/requestId).

# Acceptance Checklist
- [ ] Spring Boot 4 project compiles on JDK 25.
- [ ] Keycloak JWT validated; unauthenticated requests return AUTH_4001.
- [ ] `/api/me` works end-to-end with token -> user mapping.
- [ ] All persisted records include tenant_id; no API accepts tenant_id from client.
- [ ] No recruitment-domain features exist yet.

# Anti-Patterns
- Expanding scope to recruitment tools in Phase 1.
- Accepting tenant_id from client input.
- Logging tokens or secrets.
