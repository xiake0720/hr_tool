---
name: common-base-standards
description: "Enterprise coding standards for Spring Boot 4 microservices: UTF-8, layered architecture, DTO-only APIs, Chinese Javadoc, validation, and clean module boundaries."
metadata:
  short-description: "Java standards + layering"
  tags: [common, standards, java, springboot4, microservices]
---

# Purpose
让 Codex 在本项目中持续生成一致、可维护、可审计的企业级代码：分层清晰、API 统一、异常统一、并且**注释信息量足够**（以 RequestIdContext 为最低标准）。

---

# Hard Rules（硬性约束）
## 1) 编码与文件
- 所有文件必须使用 **UTF-8（无 BOM）**。
- 任何文件出现 “???”、“锛�” 等乱码，必须先修复编码再继续开发/生成。

## 2) 分层与职责边界
- Controller 层：
  - 只负责：参数校验、DTO 组装、调用 Service、返回 ApiResponse。
  - 禁止：业务逻辑、权限判断、租户判断、直接调用 Mapper/DAO。
- Service 层：
  - 负责：业务规则、事务边界、权限校验、租户校验、幂等/一致性处理。
- Persistence 层（MyBatis Mapper）：
  - 只负责：数据访问与 SQL 映射。
  - Mapper 不得包含业务规则判断。
- API 输入输出：
  - **禁止直接使用 DO/Entity** 作为入参/出参。
  - 必须使用 DTO（`jakarta.validation` + `@Valid`）。

## 3) API 统一返回与错误处理
- 所有接口返回统一结构：`ApiResponse<T> { code, message, data, requestId }`
- 分页统一结构：`PageResponse<T> { items, page, pageSize, total }`
- 必须提供 `GlobalExceptionHandler`：
  - validation -> SYS_1000
  - 401 -> AUTH_4001
  - 403 -> AUTH_4003
  - 500 -> SYS_2000
- 禁止在 Controller 中写 try-catch 吞异常（统一由全局异常处理）。

## 4) 多租户（tenant）规则
- tenantId 必须来自 CurrentUserContext（JWT/DB 映射后获得）。
- 禁止从客户端 DTO 传入 tenantId 并写入数据库。
- 所有租户域查询必须带 tenantId 过滤条件（避免越权数据访问）。

---

# 中文注释规范（强制执行）
> 注释不足会导致维护成本爆炸：因此将注释质量作为“必须通过”的门槛。

## A) 类注释（public 类必须写）
类注释必须至少包含以下信息点（可用段落，不强制标题，但信息必须齐全）：
1. **用途**：解决什么问题、为什么需要它
2. **职责/边界**：负责什么、不负责什么
3. **调用时机**：在链路的哪个位置调用（例如 Filter/Interceptor/Service）
4. **线程模型/并发注意**：是否线程安全；ThreadLocal/缓存/异步的注意点
5. **与其它组件的关系**：与网关、异常处理、鉴权、缓存、消息的配合方式
6. **踩坑点/误用风险**：常见误用、必须遵守的清理/顺序/约束
7. **约定**（如适用）：命名、格式、错误码、缓存 key、topic 规则等

## B) 方法注释（public 方法必须写）
每个 public 方法必须包含：
- 方法行为描述（输入 -> 处理 -> 输出）
- @param：入参含义、约束（是否允许为空/格式要求）
- @return：返回值含义、是否可能为 null
- 副作用：是否写 ThreadLocal/缓存/发消息/写库
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

# Deliverables（必须产出/保持）
- DTO 命名规范：
  - `XxxCreateReq`, `XxxUpdateReq`, `XxxQueryReq`, `XxxResp`
- 统一返回：`ApiResponse<T>`
- 分页返回：`PageResponse<T>`
- 全局异常处理：`GlobalExceptionHandler`
- 业务异常：`BizException` + `ErrorCode`
- requestId 支撑：`RequestIdContext`（推荐配套 `RequestIdFilter`）

---

# Verification（自检要求）
- Controllers 只返回 ApiResponse，不得返回 DO/Entity。
- 校验错误必须输出 SYS_1000 且包含字段错误信息（field list）。
- 每次提交后必须可编译：
  - `mvn -q -f services/pom.xml -DskipTests package`
- 抽检至少 3 个 public 类：
  - 类注释信息点是否满足 A)
  - public 方法注释是否满足 B)
- 若出现乱码，必须先修复编码再继续。

---

# Anti-Patterns（反例）
- 胖 Controller（业务逻辑/权限/租户/事务写在 Controller）
- DO/Entity 直接作为 API 出入参
- 无价值注释（“工具类”“实现xxx功能”）
- 为了补注释而改业务逻辑或大范围重构
