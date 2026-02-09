package com.ht.tools.iam.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用途：声明接口所需的权限标识。
 * 职责/边界：仅提供权限声明，不包含校验逻辑。
 * 调用时机：权限拦截器读取该注解并执行校验。
 * 线程模型：注解元数据，线程安全。
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermission {

    /**
     * @return 权限标识
     */
    String value();
}
