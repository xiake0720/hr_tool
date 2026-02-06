package com.ht.tools.iam.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

/**
 * 通过配置项控制 Redis/Kafka 的启停。
 * 用于本地开发关闭 Redis/Kafka 自动配置与健康检查，避免启动时连接外部依赖。
 *
 * 注意：
 * 1) 仅写入 Spring Boot 确认存在的 AutoConfiguration 类名，避免“排除无效”导致仍然装配。
 * 2) 生效验证：启动日志会输出最终 spring.autoconfigure.exclude。
 */
public class IntegrationToggleEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    private static final Logger log = LoggerFactory.getLogger(IntegrationToggleEnvironmentPostProcessor.class);

    private static final String REDIS_ENABLED_KEY = "app.redis.enabled";
    private static final String KAFKA_ENABLED_KEY = "app.kafka.enabled";
    private static final String AUTOCONFIGURE_EXCLUDE_KEY = "spring.autoconfigure.exclude";

    private static final String REDIS_AUTOCONFIG =
            "org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration";
    private static final String REDIS_REPOS_AUTOCONFIG =
            "org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration";
    private static final String REDIS_REACTIVE_AUTOCONFIG =
            "org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration";
    private static final String KAFKA_AUTOCONFIG =
            "org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        boolean redisEnabled = environment.getProperty(REDIS_ENABLED_KEY, Boolean.class, false);
        boolean kafkaEnabled = environment.getProperty(KAFKA_ENABLED_KEY, Boolean.class, false);

        Map<String, Object> overrides = new HashMap<>();
        List<String> excludes = new ArrayList<>();

        if (!redisEnabled) {
            excludes.add(REDIS_AUTOCONFIG);
            excludes.add(REDIS_REPOS_AUTOCONFIG);
            excludes.add(REDIS_REACTIVE_AUTOCONFIG);
            overrides.put("management.health.redis.enabled", "false");
        }

        if (!kafkaEnabled) {
            excludes.add(KAFKA_AUTOCONFIG);
            overrides.put("management.health.kafka.enabled", "false");
            overrides.put("spring.kafka.admin.enabled", "false");
        }

        if (!excludes.isEmpty()) {
            String existing = environment.getProperty(AUTOCONFIGURE_EXCLUDE_KEY);
            String merged = mergeExcludes(existing, excludes);
            overrides.put(AUTOCONFIGURE_EXCLUDE_KEY, merged);
            log.info("[integration-toggle] {}={}", AUTOCONFIGURE_EXCLUDE_KEY, merged);
        }

        if (!overrides.isEmpty()) {
            PropertySource<?> propertySource = new MapPropertySource("integrationToggleOverrides", overrides);
            environment.getPropertySources().addFirst(propertySource);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    private static String mergeExcludes(String existing, List<String> additional) {
        Set<String> merged = new LinkedHashSet<>();
        if (existing != null && !existing.isBlank()) {
            for (String value : existing.split(",")) {
                String trimmed = value.trim();
                if (!trimmed.isEmpty()) merged.add(trimmed);
            }
        }
        merged.addAll(additional);
        return String.join(",", merged);
    }
}
