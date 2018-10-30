package com.smartvillage.config;

/**
 * 自定义请求状态码
 *
 */
public enum ResultStatus {
    SUCCESS(100, "成功"),
   // USERNAME_OR_PASSWORD_ERROR(-1001, "用户名或密码错误"),
    USER_NOT_FOUND(-1002, "用户不存在"),
    USER_NOT_LOGIN(-1003, "用户未登录"),
	
	// 用户登录认证错误码
    UNKNOWN_ACCOUNT(20020,"账户不存在"),
    INCORRECT_CREDENTIALS(20021,"用户名或密码不正确"),
    LOCKED_ACCOUNT(20022,"账户被锁定"),
    EXCESSIVE_ATTEMPTS(20023,"密码错误次数过多"),
    LOGIN_FAIL(20024,"内部错误"),
    UNAUTHORIZED(20025,"未登录"),
    FORBIDDEN(20026,"无访问权限");
	
	
    /**
     * 返回码
     */
    private int code;

    /**
     * 返回结果描述
     */
    private String message;

    ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
