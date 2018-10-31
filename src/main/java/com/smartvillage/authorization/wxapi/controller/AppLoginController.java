package com.smartvillage.authorization.wxapi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smartvillage.manager.loginInfoService;
import com.smartvillage.model.AuthModel;
import com.smartvillage.model.LoginModel;
import com.smartvillage.model.ResultModel;
import com.smartvillage.model.SendModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("app/use")
@Api(tags = "用户注册登陆控制器")
public class AppLoginController {
	
	@Autowired
	private loginInfoService loginInfoService;
	
	/*
	 * 用户登录注册
	 * msg_code  短信验证码
	 * req_type 操作类型0—注册，1-改密
	 * */
	@RequestMapping(value = "usrreg" , method = RequestMethod.POST)
	@ApiOperation(value = "用户注册")
	public ResponseEntity<ResultModel> usrreg(AuthModel authModel) throws Exception{
		ResponseEntity<ResultModel> userreg = loginInfoService.userreg(authModel);
		return userreg;
	}
	@RequestMapping(value = "login" , method = RequestMethod.POST)
	@ApiOperation(value = "用户登陆")
	public ResponseEntity<ResultModel> login(HttpServletRequest request,LoginModel LoginModel){
		ResponseEntity<ResultModel> login = loginInfoService.login(LoginModel);
		return login;
	}
	@RequestMapping(value = "sendcode" , method = RequestMethod.POST)
	@ApiOperation(value = "校验码发送")
	public ResponseEntity<ResultModel> sendcode(SendModel sendModel){
		ResponseEntity<ResultModel> sendcode = loginInfoService.sendcode(sendModel);
		return sendcode;
		
	}
}
