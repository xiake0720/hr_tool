package com.ht.tools.iam.config;

import com.ht.tools.iam.security.RequirePermissionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 用途：注册权限守卫拦截器。
 * 职责/边界：仅做拦截器注册，不包含业务逻辑。
 * 调用时机：Spring MVC 启动时加载。
 * 线程模型：无状态配置类，线程安全。
 */
@Configuration
public class PermissionWebMvcConfig implements WebMvcConfigurer {

    private final RequirePermissionInterceptor requirePermissionInterceptor;

    public PermissionWebMvcConfig(RequirePermissionInterceptor requirePermissionInterceptor) {
        this.requirePermissionInterceptor = requirePermissionInterceptor;
    }

    /**
     * 行为：注册权限拦截器，拦截所有 /api/** 请求。
     * 入参：registry 拦截器注册器。
     * 出参：无。
     *
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requirePermissionInterceptor)
                .addPathPatterns("/api/**");
    }
}
