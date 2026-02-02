---
name: gen-springBoot3.5-cloud-mysql-mybatis-skeleton
description: "Generate Boot 3.5.x microservices skeleton under services/: gateway-service + iam-service, aligned Spring Cloud (Boot3.5 compatible, prefer 2025.0.x), MySQL 9.5 + Flyway, MyBatis-Plus Boot3.5 starter, Kafka/Redis starters, and OAuth2 Resource Server JWT."
metadata:
  short-description: "Microservices skeleton (Boot3.5 + Cloud + MySQL + MyBatis + Kafka + Redis)"
  tags: [generator, springBoot3.5, spring-cloud, mysql, mybatis-plus, kafka, redis, oauth2]
---

# Purpose
Bootstrap the minimal runnable microservice foundation for Phase 1.

# Rules
- Create `services/gateway-service` and `services/iam-service`.
- Use Spring Boot 3.5.x + Spring Cloud release train compatible with Boot 3.5.x (prefer 2025.0.x if Boot 3.5.x.0.1+).
- Security: OAuth2 Resource Server JWT in services exposing APIs.
- Persistence in iam-service: MySQL + Flyway + MyBatis-Plus Boot3.5 starter.
- Infra clients:
    - Kafka client dependencies
    - Redis client dependencies

# Deliverables
- Parent build (Maven multi-module recommended)
- Gateway:
    - Spring Cloud Gateway routes `/api/**` to iam-service
- IAM service:
    - base packages, common configs, health endpoint
    - application.yml with env placeholders:
        - mysql, redis, kafka, jwk/issuer
    - common response/error/logging scaffolding hooks

# Verification
- Build passes for all modules.
- Gateway starts and routes to iam-service in local environment.

# Anti-Patterns
- Hardcoding credentials
- Adding recruitment features in generator
