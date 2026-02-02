---
name: common-auth-oauth2-resource-server
description: "Configure Spring Security OAuth2 Resource Server (JWT) for Boot 4: issuer/jwk validation, 401/403 handling, and building CurrentUserContext from JWT claims + DB mapping."
metadata:
  short-description: "OAuth2 JWT resource server baseline"
  tags: [common, security, oauth2, jwt, spring-security]
---

# Purpose
Ensure authentication is standards-based and consistent across microservices.

# Rules
- Each service exposing APIs MUST be configured as OAuth2 Resource Server (JWT).
- Use `issuer-uri` or `jwk-set-uri` configuration (no custom token parsing).
- 401 unauthenticated -> AUTH_4001; 403 forbidden -> AUTH_4003.
- Build `CurrentUserContext` from:
    - JWT claims (sub, preferred_username/email)
    - DB mapping to business user (external_sub -> userId, tenantId)
- Never log Authorization header or token.

# Deliverables
- Spring Security configuration for Resource Server JWT
- Authentication entrypoint & access denied handler mapping to ApiResponse codes
- `CurrentUserContext` provider (filter/interceptor)

# Verification
- Without token: 401 + AUTH_4001
- With token but no permission: 403 + AUTH_4003
- With valid mapped user: `/api/me` returns tenantId/userId

# Anti-Patterns
- Local password login in backend
- Trusting client-provided tenantId/userId
