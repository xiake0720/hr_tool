---
name: common-spring-cloud-microservices
description: "Spring Cloud baseline for Boot 3.5.x microservices: use Boot 3.5.x-compatible Spring Cloud release train (prefer 2025.0.x), Gateway, OpenFeign+LoadBalancer, Resilience4j circuit breaker, config baseline, and tracing via Micrometer."
metadata:
  short-description: "Spring Cloud component set for 2026"
  tags: [common, spring-cloud, gateway, feign, resilience4j, tracing]
---

# Purpose
Define the minimal, modern Spring Cloud stack that matches Spring Boot 3.5.x.

# Rules
- Use Spring Cloud release train compatible with Spring Boot 3.5.x (prefer 2025.0.x for Boot 3.5.x).
- Use components:
    - Spring Cloud Gateway (edge)
    - OpenFeign + Spring Cloud LoadBalancer (service-to-service)
    - Resilience4j (via Spring Cloud Circuit Breaker) for timeouts/retries/bulkhead
    - Config baseline (Spring Cloud Config or equivalent external config)
    - Observability: Micrometer metrics + tracing (do not rely on deprecated Sleuth patterns)
- Keep services minimal in Phase 1:
    - gateway-service
    - iam-service
- Avoid over-splitting; prefer modular design inside iam-service.

# Deliverables
- Dependency management via Spring Cloud BOM aligned to Boot 3.5.x
- Gateway routing to iam-service
- Feign client sample between services (if needed)
- Standard resilience configuration template

# Verification
- Gateway routes /api/* to iam-service
- A sample Feign call works in local docker-compose network
- Circuit breaker config is present and validated by unit test or smoke test

# Anti-Patterns
- Mixing incompatible Spring Cloud train with Boot 3.5.x
- Introducing service discovery complexity before it is needed
