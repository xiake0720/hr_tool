# 本地环境说明（Phase 1）

以下仅说明“已安装后如何启动/检查”，不包含安装过程。

## Nacos
- 启动：进入 Nacos 的 bin 目录，执行 startup.cmd -m standalone
- 检查：访问 http://localhost:8848/nacos

## MySQL
- 启动：启动本地 MySQL 服务
- 检查：
  - mysql -h localhost -P 3306 -u <user> -p
  - SHOW DATABASES;
  - 连接目标库：hr_iam_dev

## Redis
- 启动：执行 redis-server
- 检查：redis-cli ping（期望返回 PONG）

## Kafka
- 启动：启动本地 Kafka 服务（保持端口 9092）
- 检查：
  - kafka-topics.bat --bootstrap-server localhost:9092 --list

## 常见连通性检查命令示例
- MySQL：mysql -h localhost -P 3306 -u <user> -p
- Redis：redis-cli -h localhost -p 6379 ping
- Kafka：kafka-topics.bat --bootstrap-server localhost:9092 --list