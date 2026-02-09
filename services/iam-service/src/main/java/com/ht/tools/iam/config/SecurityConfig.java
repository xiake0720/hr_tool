package com.ht.tools.iam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * IAM 安全配置。
 * 负责 OAuth2 Resource Server（JWT）接入与接口访问控制，放行健康检查与基础探针接口。
 */
@Configuration
public class SecurityConfig {

    /**
     * 安全过滤链配置。
     * 放行健康检查/基础探针，其余接口需认证，使用 JWT 资源服务器。
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
                        "/api/**",
                        "/actuator/health",
                        "/actuator/health/**",
                        "/actuator/info")
                .permitAll()
                .anyRequest().authenticated());
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt());
        return http.build();
    }
}
