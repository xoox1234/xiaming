package com.smartvillage.model;


import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class LoginModel {
	@ApiParam(value = "手机号")
	private String mobile;
	@ApiParam(value = "密码")
	private String passwrod;
	@ApiParam(value = "登录类型（3:phone,2:wechat,1:validate）",required = true)
	private String loginType;
	@ApiParam(value = "验证码")
	private String msg_code;
	@ApiParam(value = "微信登录时需要code来获取access_token")
	private String code;

}
