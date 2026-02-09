package com.ht.tools.iam.config;

import com.ht.tools.iam.security.ApiAccessDeniedHandler;
import com.ht.tools.iam.security.ApiAuthEntryPoint;
import com.ht.tools.iam.security.CurrentUserContextFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;

/**
 * 用途：IAM 安全配置，启用 OAuth2 Resource Server（JWT）。
 * 职责/边界：仅负责认证与授权配置，不包含业务权限判断。
 * 调用时机：Spring Boot 启动时加载。
 * 线程模型：无状态配置类，线程安全。
 * 关系说明：依赖自定义 401/403 处理器与 CurrentUserContext 过滤器。
 */
@Configuration
public class SecurityConfig {

    private final ApiAuthEntryPoint authEntryPoint;
    private final ApiAccessDeniedHandler accessDeniedHandler;
    private final CurrentUserContextFilter currentUserContextFilter;

    public SecurityConfig(ApiAuthEntryPoint authEntryPoint,
                          ApiAccessDeniedHandler accessDeniedHandler,
                          CurrentUserContextFilter currentUserContextFilter) {
        this.authEntryPoint = authEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.currentUserContextFilter = currentUserContextFilter;
    }

    /**
     * 行为：配置安全过滤链，启用 JWT 资源服务器并统一 401/403 响应。
     * 入参：HttpSecurity。
     * 出参：SecurityFilterChain。
     * 异常：配置异常时抛出 Exception。
     *
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception 配置异常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                        "/api/health",
                        "/api/ping/**",
                        "/actuator/health",
                        "/actuator/health/**",
                        "/actuator/info")
                .permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll());
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
        }));
        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint(authEntryPoint)
                .accessDeniedHandler(accessDeniedHandler));
        http.addFilterAfter(currentUserContextFilter, BearerTokenAuthenticationFilter.class);
        return http.build();
    }
}
