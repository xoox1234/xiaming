package com.smartvillage.config;

/**
 * 常量
 * 
 */
public class Constants {

    /**
     * 存储当前登录用户id的字段名
     */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

    /**
     * token有效期（小时）
     */
    public static final int TOKEN_EXPIRES_HOUR = 72;

    /**
     * 存放Authorization的header字段
     */
    public static final String AUTHORIZATION = "authorization";
    /**
     * 应用密钥AppID
     */
    public static final String WE_CAT_APP_ID = "xxxxxxxxxxx"; 
    /**
     * 应用密钥AppSecret
     */
    public static final String WE_CAT_APP_SECRET = "xxxxxxxxxxx";
    /**
     * 获取CODE的回调地址
     */
    public static final String WE_CAT_REDIRECT_URL = "xxxxxxxxxxx";
    /**
     * 获取access_token
     */
    public static final String WX_AUTH_LOGIN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    /**
     * 获取用户个人信息
     */
    public static final String WX_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo";
    /**
     * 刷新access_token或续期
     */
    public static final String WX_REFRESH="https://api.weixin.qq.com/sns/oauth2/refresh_token";

}
