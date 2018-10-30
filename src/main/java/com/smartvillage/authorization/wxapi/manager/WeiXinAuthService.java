package com.smartvillage.authorization.wxapi.manager;

import com.smartvillage.domain.Users;

public interface WeiXinAuthService {
	
	Users checkLogin(String code);

}
