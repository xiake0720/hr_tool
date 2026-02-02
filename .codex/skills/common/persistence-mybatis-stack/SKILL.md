---
name: common-persistence-mybatis-stack
description: "MyBatis persistence stack for Boot 3.5.x: recommend MyBatis-Plus spring-boot3-starter, pagination, optimistic lock, logical delete, code generator, and mapper conventions."
metadata:
  short-description: "MyBatis ecosystem choices for 2026"
  tags: [common, mybatis, mybatis-plus, persistence, mysql]
---

# Purpose
Standardize persistence layer using MyBatis for MySQL 9.5, aligned with Boot 3.5.x.

# Recommended Components (2026-friendly)
- Primary: MyBatis-Plus `mybatis-plus-spring-boot3-starter` (Boot 3.5.x aligned).
- Optional add-ons:
    - MyBatis-Plus Generator (code generation)
    - Optimistic Locker plugin
    - Pagination plugin
    - Logical delete (soft delete) if needed
    - MapStruct for DTO mapping (recommended)
- Prefer HikariCP (default in Boot) for datasource pool.

# Rules
- Mapper interfaces must be small and focused; keep SQL readable.
- Do not embed tenant_id from client: set it in service layer and include it in mapper WHERE conditions.
- Use transaction boundaries in service layer (`@Transactional`).
- Use naming conventions:
    - `XxxDO` (DB object) or `XxxEntity` for persistence model (pick one and keep consistent)
    - `XxxMapper` for MyBatis mapper

# Deliverables
- MyBatis-Plus starter dependency usage
- Mapper scanning configuration
- Pagination + optimistic lock configuration templates

# Verification
- CRUD works with tenant-aware WHERE clause
- Pagination works for list endpoints

# Anti-Patterns
- Writing dynamic SQL that bypasses tenant filtering
- Controller directly calling mapper
