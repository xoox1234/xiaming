package com.smartvillage.config;

import org.apache.shiro.authc.UsernamePasswordToken;

public class WeChatToken extends UsernamePasswordToken{
	private Integer userId;

    public WeChatToken(){
    }

    public WeChatToken(Integer userId){
        this.userId = userId;
    }

    @Override
    public Object getPrincipal() {
        return this.getUserId();
    }

    public Integer getUserId() {
        return userId;
    }
}
