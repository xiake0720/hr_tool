package com.ht.tools.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 用途：网关服务应用入口，启动 Spring Cloud Gateway。
 * 边界：仅负责进程启动，不包含路由或业务逻辑。
 * 线程模型：类本身无状态，线程模型由 Spring Boot 容器管理。
 * 调用时机：JVM 进程启动时由 main 方法触发。
 * 清理责任：无；资源释放由 Spring 容器与 JVM 生命周期负责。
 * 误用风险：在此类中加入业务逻辑会干扰启动流程。
 * 与其它组件关系：加载网关服务全部 Bean，依赖 application.yml 中的路由配置。
 */
@SpringBootApplication
public class Application {

    /**
     * 入参：args - 启动参数（可空）。
     * 返回：无。
     * 副作用：启动 Spring 应用上下文并开启监听端口。
     * 是否可空：args 可空。
     * 异常：启动失败时可能抛出运行时异常（由 Spring Boot 处理）。
     * 线程安全：线程安全（无共享可变状态）。
     * 使用建议：仅作为进程入口调用。
     *
     * @param args 启动参数
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}