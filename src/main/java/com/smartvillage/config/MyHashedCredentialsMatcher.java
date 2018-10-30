package com.smartvillage.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.stereotype.Component;

@Component
public class MyHashedCredentialsMatcher extends HashedCredentialsMatcher{

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		if (WeChatToken.class.isAssignableFrom(token.getClass())) {
            return true;
        }
        return super.doCredentialsMatch(token, info);
	}
}
