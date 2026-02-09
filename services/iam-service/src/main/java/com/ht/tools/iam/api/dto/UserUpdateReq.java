package com.ht.tools.iam.api.dto;

/**
 * 用途：用户更新请求 DTO。
 * 职责/边界：仅用于 API 输入，不参与持久化。
 * 调用时机：PUT /api/users/{id} 入参。
 * 线程模型：不可变 DTO，线程安全。
 */
public class UserUpdateReq {

    private String externalSub;
    private String username;
    private String email;
    private String phone;
    private Boolean enabled;

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

    /**
     * @return 是否启用
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled 是否启用
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
