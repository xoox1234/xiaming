package com.smartvillage.model;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class AuthModel {
	@ApiParam(value = "手机号",required = true)
	private String phone;
	@ApiParam(value = "密码",hidden= true)
	private String pwd;
	@ApiParam(value = "验证码")
	private String msgCode;
	@ApiParam(value = "0注册，1修改密码",required = true)
	private String loginType;
}
