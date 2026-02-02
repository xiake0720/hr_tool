package com.ht.tools.iam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 用途：IAM 服务应用入口，负责触发 Spring Boot 启动与自动配置。
 * 边界：仅承担进程启动职责，不包含任何业务逻辑或初始化数据。
 * 线程模型：类本身无状态，线程模型由 Spring Boot 容器管理。
 * 调用时机：JVM 进程启动时由 main 方法触发。
 * 清理责任：无；资源释放由 Spring 容器与 JVM 生命周期负责。
 * 误用风险：在此类中加入业务逻辑会导致启动时机不受控、难以测试。
 * 与其它组件关系：加载 IAM 服务的全部 Bean，依赖 application.yml 中的配置。
 */
@SpringBootApplication
public class Application {

    /**
     * 入参：args - 启动参数（可空，通常为命令行参数）。
     * 返回：无。
     * 副作用：启动 Spring 应用上下文并开启监听端口。
     * 是否可空：args 可空。
     * 异常：启动失败时可能抛出运行时异常（由 Spring Boot 处理）。
     * 线程安全：线程安全（无共享可变状态）。
     * 使用建议：仅作为进程入口调用，不要在此处编写业务逻辑。
     *
     * @param args 启动参数
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}