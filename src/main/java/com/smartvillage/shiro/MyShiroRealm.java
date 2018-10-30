package com.smartvillage.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.smartvillage.config.WeChatToken;
import com.smartvillage.domain.Users;
import com.smartvillage.domain.UsersCredential;
import com.smartvillage.repository.UsersCredentialRepository;
import com.smartvillage.repository.UsersRepository;


public class MyShiroRealm extends AuthorizingRealm  {
	
	@Override
	public String getName() {
		return "MyShiroRealm";
	}
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private UsersCredentialRepository UcRepository;
	
	/**
	 * 认证信息.(身份验证) : Authentication 是用来验证用户身份
	 * 
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		//微信登陆
		if(token instanceof WeChatToken) {
			Long userid = (Long)token.getPrincipal();
			Users users = usersRepository.findById(userid).get();
			if(users == null) {
				 return null;
			}
			UsersCredential usersCredential = UcRepository.findById(users.getId()).get();
			
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(users,usersCredential.getPassword(),ByteSource.Util.bytes(users.getMobile()),getName());
			return info;
		}
		
		 // 获取用户登录账号
        String username = (String) token.getPrincipal();
		System.out.println(token.getCredentials());
		Users users = usersRepository.findByMobile(username);
		if(users == null) {
			return null;
		}
		UsersCredential usersCredential = UcRepository.findById(users.getId()).get();
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(users,usersCredential.getPassword(),ByteSource.Util.bytes(users.getMobile()),getName());
		/*SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				users, //用户名
				users.getPassword(), //密码
	            ByteSource.Util.bytes(userauths.getCredentialsSalt()),//salt=username+salt
	            getName()  //realm name
	    );*/
		return info;
	}
	
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		return null;
	}
}
