---
name: common-cache-redis
description: "Redis cache standard with Spring Data Redis: key naming, TTL strategy, cache-aside patterns, distributed locks (optional), and avoiding sensitive token caching."
metadata:
  short-description: "Redis caching patterns"
  tags: [common, redis, cache, spring-data-redis]
---

# Purpose
Use Redis safely for performance, idempotency, and coordination.

# Recommended Libraries
- Use Spring Data Redis (Lettuce client) via spring-boot-starter-data-redis.

# Rules
- Key namespace: `hr:<service>:<purpose>:...`
- TTL must be set for all caches unless explicitly permanent.
- Prefer cache-aside pattern:
    - read-through with fallback to DB
    - write: update DB then invalidate cache
- Do NOT cache raw access tokens.
- For idempotency:
    - store `event:<eventId>` keys with TTL.

# Deliverables
- Redis configuration template
- Cache key conventions doc
- Idempotency helper (set-if-absent with TTL)

# Verification
- Cache keys are namespaced and TTLs are applied
- Permission cache expires and refreshes correctly

# Anti-Patterns
- No TTL for hot keys
- Caching sensitive raw tokens
