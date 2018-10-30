package com.smartvillage.authorization.wxapi.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import com.smartvillage.authorization.wxapi.manager.WeiXinAuthService;
import com.smartvillage.config.Constants;
import com.smartvillage.domain.Users;
import com.smartvillage.domain.UsersWechat;
import com.smartvillage.repository.UsersRepository;
import com.smartvillage.repository.UsersCredentialRepository;
import com.smartvillage.tools.WXUtils;

@Service
public class WeiXinAuthServiceImpl implements WeiXinAuthService{
	
	private static final Logger logger = LoggerFactory.getLogger(WeiXinAuthServiceImpl.class);
	
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private UsersCredentialRepository userRepository;
	@Override
	public Users checkLogin(String code) {
		//获取授权——access_token
		StringBuilder loginUrl = new StringBuilder();
		loginUrl.append(Constants.WX_AUTH_LOGIN_URL)
		.append("?appid=")
		.append(Constants.WE_CAT_APP_ID)
		.append("&secret=")
		.append(Constants.WE_CAT_APP_SECRET)
		.append("&code=")
		.append(code)
		.append("&grant_type=authorization_code");
		String loginRet =WXUtils.get(loginUrl.toString());
		//存储基本信息
		Users users = new Users();
		try {
			JSONObject grantObj  = new JSONObject(loginRet);
			String errcode = grantObj.optString("errcode");
			if(StringUtils.isNotBlank(errcode)) {
				logger.error("login weixin error"+loginRet);
				return null;
			}
			String openId = grantObj.optString("openid");
			if (StringUtils.isEmpty(openId)) 
			{
				logger.error("login weixin getOpenId error"+loginRet);
				return null;
			}
			
			String accessToken = grantObj.optString("access_token");
			String expiresIn = grantObj.optString("expires_in");
			String refreshToken = grantObj.optString("refresh_token");
			String scope = grantObj.optString("scope");
			
			//获取用户信息
			StringBuffer userUrl = new StringBuffer();
			userUrl.append(Constants.WX_USERINFO_URL)
			.append("?access_token=")
			.append(accessToken)
			.append("&openid=")
			.append(openId);
			String userRet  = WXUtils.get(userUrl.toString());
			JSONObject userObj = new JSONObject(userRet);
			
			String nickname = userObj.optString("nickname");
			String sex = userObj.optString("sex");
			String userImg = userObj.optString("headimgurl");
			String unionid = userObj.optString("unionid");
			
			//set用户基本信息
			users.setNickname(nickname);
			users.setLoginType("wechat");//登录类型（3:phone,2:wechat,1:validate） 后期用常量表示
			users.setToken(accessToken);
			Users usersinfo = usersRepository.save(users);
			
			//保存用户第三方登录授权信息
			UsersWechat userswechat = new UsersWechat();
			userswechat.setUserId(usersinfo.getId());
			userswechat.setOpenid(openId);
			//需要绑定公众号unionid
			userswechat.setUnionid(unionid);
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public static void main(String[] args) {
		String a = "123";
		if(StringUtils.isEmpty(a)) {
			System.out.println("===");
		}
	}
}
