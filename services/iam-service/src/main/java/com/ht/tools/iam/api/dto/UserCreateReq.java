package com.ht.tools.iam.api.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 用途：用户创建请求 DTO。
 * 职责/边界：仅用于 API 输入，不参与持久化。
 * 调用时机：POST /api/users 入参。
 * 线程模型：不可变 DTO，线程安全。
 */
public class UserCreateReq {

    @NotBlank
    private String loginName;

    @NotBlank
    private String password;

    private String externalSub;
    private String username;
    private String email;
    private String phone;

    /**
     * @return 登录名
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * @param loginName 登录名
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * @return 密码哈希（不允许明文）
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password 密码哈希（不允许明文）
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return 外部身份标识
     */
    public String getExternalSub() {
        return externalSub;
    }

    /**
     * @param externalSub 外部身份标识
     */
    public void setExternalSub(String externalSub) {
        this.externalSub = externalSub;
    }

    /**
     * @return 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone 手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
