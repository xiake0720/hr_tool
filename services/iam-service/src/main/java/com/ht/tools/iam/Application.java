package com.ht.tools.iam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 用途：IAM 服务应用入口，负责触发 Spring Boot 启动与自动配置。
 * 职责/边界：仅承担进程启动职责，不包含业务逻辑或初始化数据。
 * 调用时机：JVM 进程启动时由 main 方法触发。
 * 线程模型：类本身无状态，线程模型由 Spring Boot 容器管理。
 * 关系说明：扫描 com.ht.tools 下所有组件，包含 common 模块配置。
 */
@SpringBootApplication(scanBasePackages = "com.ht.tools")
public class Application {

    /**
     * 行为：启动 Spring Boot 应用上下文。
     * 入参：args 启动参数（可空）。
     * 出参：无。
     * 异常：启动失败时可能抛出运行时异常（由 Spring Boot 处理）。
     *
     * @param args 启动参数
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
