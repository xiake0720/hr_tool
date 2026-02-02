---
name: common-base-standards
description: "Enterprise coding standards for Spring Boot 3.5 microservices: UTF-8, layered architecture, DTO-only APIs, Chinese Javadoc, validation, clean module boundaries, and common-module governance."
metadata:
  short-description: "Java standards + layering + common modules"
  tags: [common, standards, java, springboot3.5, microservices]
---

# Purpose
让 Codex 在本项目中持续生成一致、可维护、可审计的企业级代码：分层清晰、API 统一、异常统一、并且注释信息量足够（以 RequestIdContext 的注释密度为最低标准）。

---

# Tech Baseline（路线 A：稳定可落地）
- Spring Boot：3.5.x
- Spring Cloud：2025.0.x
- Spring Cloud Alibaba：2025.0.0.0
- JDK：25（允许；Boot 3.5 最低要求 Java 17）

---

# Hard Rules（硬性约束）
## 1) 编码与文件
- 所有文件必须使用 UTF-8（无 BOM）。
- 任意文件出现 “???”、“锛�” 等乱码，必须先修复编码再继续生成/开发。

## 2) 依赖版本与 BOM 管理（强制）
- 父 POM 必须通过 dependencyManagement 同时 import：
  - spring-boot-dependencies
  - spring-cloud-dependencies
  - spring-cloud-alibaba-dependencies
- 子模块尽量不写依赖版本号（除非 BOM 未覆盖且说明原因），避免版本漂移与冲突。
- 任何 ClassNotFound/NoSuchMethodError 优先检查 BOM 对齐与重复依赖。

## 3) 分层与职责边界
- Controller：
  - 只负责参数校验、DTO 组装、调用 Service、返回 ApiResponse。
  - 禁止写业务逻辑/权限/租户/事务；禁止直接调用 Mapper。
- Service：
  - 负责业务规则、事务边界、权限校验、租户校验、幂等/一致性处理。
- Persistence（MyBatis Mapper）：
  - 只负责数据访问与 SQL 映射；不得包含业务判断。
- API 输入输出：
  - 禁止直接使用 DO/Entity 作为入参/出参，必须使用 DTO（jakarta.validation + @Valid）。

## 4) API 统一返回与错误处理
- 统一返回：ApiResponse<T> { code, message, data, requestId }
- 分页返回：PageResponse<T> { items, page, pageSize, total }
- GlobalExceptionHandler 必须映射：
  - validation -> SYS_1000（包含字段错误列表）
  - 401 -> AUTH_4001
  - 403 -> AUTH_4003
  - 500 -> SYS_2000
- 禁止 Controller 内 try-catch 吞异常（统一由全局异常处理）。

## 5) 多租户（tenant）规则
- tenantId 必须来自 CurrentUserContext（由 JWT/DB 映射获得）。
- 禁止客户端 DTO 传 tenantId 并写库。
- 租户域查询必须带 tenantId 过滤条件，避免越权。

---

# Common Modules Governance（公共模块规范：强制）
## 模块规划
- `ht-tools-common-core`（纯 Java，无 Spring 依赖）：
  - 放：ApiResponse/PageResponse、ErrorCode、BizException、常量（Header/Topic/CacheKey）、纯工具类、跨服务 DTO/枚举（如有）
  - 禁止：任何 Spring 依赖、Web 过滤器、Security 配置、Mapper/Repository
- `ht-tools-common-web`（Web 通用组件，面向后端服务）：
  - 放：GlobalExceptionHandler、RequestIdContext/Filter、Web 常量与通用拦截器
  - 禁止：业务逻辑、具体服务的领域模型、与网关强耦合的 WebFlux 专用实现（如需要单独拆 common-webflux）

## 依赖方向（禁止循环依赖）
- service -> common（允许）
- common -> service（禁止）
- common-core -> common-web（允许）
- common-web -> common-core（允许）
- 任意模块不得形成循环依赖

## 复制粘贴治理
- 发现多个服务出现相同公共逻辑：必须抽到 common 模块，禁止跨服务复制粘贴长期存在。
- 迁移公共代码时：只允许“包迁移 + import 修复”，禁止顺手重构业务逻辑。

---

# 中文注释规范（强制执行）
## A) 类注释（public 类必须写）
类注释必须至少包含：
1. 用途（解决什么问题、为何存在）
2. 职责/边界（负责什么、不负责什么）
3. 调用时机（链路中的位置：Filter/Interceptor/Service/Mapper）
4. 线程模型/并发注意（ThreadLocal/缓存/异步风险）
5. 与其它组件关系（网关、异常处理、鉴权、缓存、消息）
6. 踩坑点/误用风险（必须遵守的清理/顺序/约束）
7. 约定（如适用：命名、格式、错误码、缓存 key、topic）

## B) 方法注释（public 方法必须写）
每个 public 方法必须包含：
- 行为描述（输入 -> 处理 -> 输出）
- @param 入参含义与约束（是否可空/格式）
- @return 返回值含义与可空性
- 副作用（ThreadLocal/缓存/发消息/写库）
- 线程安全说明（如适用）
- 异常/错误码说明（如适用）

## C) 特殊类型必须加“设计原因 + 风险点”
以下类型必须额外说明“为什么这样设计”和“如何避免踩坑”：
- ThreadLocal/上下文类（RequestIdContext/CurrentUserContext）
- Security 配置与鉴权/权限守卫（401/403 行为与错误码）
- 全局异常处理/错误码/统一响应包装
- MyBatis Mapper/租户隔离/避免越权
- Kafka Producer/Consumer（幂等、重试、DLQ 思路）
- Redis 缓存（key 规范、TTL、敏感信息避免）
- Gateway 路由/过滤器（header 透传、链路追踪）

---

# Verification（自检要求）
- 每次修改后必须可编译：
  - mvn -q -f services/pom.xml -DskipTests clean package
- Controllers 只返回 ApiResponse，不得返回 DO/Entity。
- 校验错误输出 SYS_1000 且包含字段错误信息（field list）。
- 抽检至少 3 个 public 类：类注释与方法注释满足本规范。
- 若出现乱码，必须先修复编码再继续。
