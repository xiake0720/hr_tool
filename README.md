# HR 工具平台 Phase 1（基础架构）

## 项目简介
本项目为 HR 工具平台 Phase 1，聚焦基础架构与平台能力建设。

## 技术架构
Spring Cloud 微服务 + Nacos + OAuth2 Resource Server + MySQL + MyBatis + Redis + Kafka + Vue3

## 本地依赖地址（占位写法，不写死密码）
- Nacos：http://localhost:8848/nacos
- MySQL：localhost:3306，db=hr_iam_dev
- Redis：localhost:6379
- Kafka：localhost:9092

## 启动方式
- 后端：mvn 启动 gateway-service / iam-service
- 前端：npm run dev

## Phase 1 范围
仅做登录骨架、用户/组织/角色基础管理，不做简历解析等业务能力。

## 后端启动顺序与验证
1. 确保 Nacos 已启动
2. 启动 iam-service
3. 启动 gateway-service

验证命令：
- curl http://localhost:8080/api/health
- curl -H "X-Request-Id: test-123" http://localhost:8080/api/health