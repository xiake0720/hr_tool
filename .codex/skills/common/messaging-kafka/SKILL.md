---
name: common-messaging-kafka
description: "Kafka messaging standard: use Spring for Apache Kafka 4.x and/or Spring Cloud Stream 5.x. Define topic naming, event schema, idempotent consumers, retries and DLQ."
metadata:
  short-description: "Kafka event-driven patterns"
  tags: [common, kafka, messaging, events, dlq]
---

# Purpose
Provide a safe, repeatable Kafka integration pattern for microservices.

# Recommended Libraries
- Prefer Spring for Apache Kafka 4.x for precise control of producer/consumer.
- Use Spring Cloud Stream 5.x when you want binder abstraction and functional style.

# Rules
- Topic naming: `hr.<domain>.<event>.v1`
- Event payload must include:
    - eventId (UUID)
    - eventType
    - occurredAt (ISO-8601)
    - tenantId
    - data (business payload)
- Consumers must be idempotent:
    - store processed eventId in Redis or DB unique constraint
- Retry policy must be explicit; DLQ topics recommended for poison messages.

# Deliverables
- Producer template
- Consumer template with idempotency guard
- Topic list + schema examples in docs

# Verification
- Local docker-compose brings up Kafka and service can produce/consume a test event
- Duplicate eventId is ignored (idempotency works)

# Anti-Patterns
- Fire-and-forget without retry/DLQ
- Non-idempotent consumers
