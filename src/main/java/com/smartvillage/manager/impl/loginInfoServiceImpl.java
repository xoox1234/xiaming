package com.smartvillage.manager.impl;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smartvillage.authorization.jpush.JSMSExample;
import com.smartvillage.authorization.wxapi.manager.WeiXinAuthService;
import com.smartvillage.config.ResultStatus;
import com.smartvillage.domain.Users;
import com.smartvillage.domain.UsersCredential;
import com.smartvillage.manager.loginInfoService;
import com.smartvillage.model.AuthModel;
import com.smartvillage.model.LoginModel;
import com.smartvillage.model.ResultModel;
import com.smartvillage.model.SendModel;
import com.smartvillage.repository.UsersCredentialRepository;
import com.smartvillage.repository.UsersRepository;
import com.smartvillage.tools.JSONUtils;


@Service
public class loginInfoServiceImpl implements loginInfoService{
	private static final Logger LOGGER = LoggerFactory.getLogger(loginInfoServiceImpl.class);
	@Autowired
	private RedisTemplate<String, Object> redistemplate;
	@Autowired 
	private UsersRepository usersRepository;
	@Autowired 
	private UsersCredentialRepository userscredentialrepository;
	@Autowired
	private WeiXinAuthService weiXinAuthService;
	@Override
	public ResponseEntity<ResultModel> userreg(AuthModel authModel) throws Exception {
		ResponseEntity<ResultModel> res = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		String phone = authModel.getPhone();
		if("null".equals(phone) || phone == null) {
			Assert.notNull(phone, "手机号不能空");
			return res;
		}
		if(phone.length() != 11) {
			Assert.notNull(phone, "手机号不正确");
			return res;
		}
		/*String pwd = authModel.getPwd();
		if("null".equals(pwd) || pwd == null) {
			Assert.notNull(pwd,"请输入密码");
			return res;
		}*/
		String msg_code = authModel.getMsgCode();
		if("null".equals(msg_code) || msg_code == null) {
			Assert.notNull(msg_code,"请输入验证码");
			return res;
		}else {
			String msg_id = redistemplate.opsForValue().get("SEND_CODE_"+phone).toString();
			LOGGER.info(msg_id);
			String json = JSMSExample.sendValidSMSCode(msg_id,msg_code);
			String info = JSONUtils.jsonErrorTostr(json);
			if("true".equals(info)) {
				//Assert.isNull(sendValidSMSCode,"验证码错误");
				return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
			}else if(info != ""){
				Assert.isNull(info,info);
				return res;
			}
		}
		String req_type = authModel.getLoginType();
		if("0".equals(req_type)) {//注册
			Users us = usersRepository.findByMobile(phone);
			if(us != null) {
				Assert.isNull(us.getMobile(),"该手机号已注册过");
				return res;
			}else {
				Users us2 = new Users();
				us2.setMobile(phone);
				us2.setLoginType("phone");
				us2.setCreateat(new Date());
				us2 =  usersRepository.save(us2);
				//凭证类
				UsersCredential uc = new UsersCredential();
				//uc.setPassword(PasswordUtils.encryptPassword(us2.getMobile(), pwd, 2));
				uc.setUserId(us2.getId());
				uc.setSalt(uc.getCredentialsSalt());//加盐 
				uc = userscredentialrepository.save(uc);
				
				String token = UUID.randomUUID().toString();
				redistemplate.opsForValue().set("APP_TEL_TOKEN"+phone, token);
				return  new ResponseEntity<>(ResultModel.ok(us2),HttpStatus.OK);
			}
		}else if("1".equals(req_type)){//改密
			Users us = usersRepository.findByMobile(phone);
			if(us != null) {
				UsersCredential usersCredential = userscredentialrepository.findById(us.getId()).get();
				//usersCredential.setPassword(PasswordUtils.encryptPassword(phone, pwd, 2));
				userscredentialrepository.save(usersCredential);//暂时用报错 后面修改update更新操作
				Assert.notNull("SUNCCESS","密码修改成功");
				return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@Override
	public ResponseEntity<ResultModel> login(LoginModel loginModel) {
		String loginType = loginModel.getLoginType();
		switch (loginType) {
		case "1"://手机验证码登陆
		case "2"://微信登陆
			String code = loginModel.getCode();
			Users checkLogin = weiXinAuthService.checkLogin(code);
			return new ResponseEntity<>(ResultModel.ok(checkLogin),HttpStatus.OK);
		case "3"://手机+密码登陆
			/*
            1、验证用户是否已经登录
            2.1、已登录则直接返回
            2.2、未登录则登录验证
			*/
			Subject currentUser = SecurityUtils.getSubject();
			Users user = (Users)currentUser.getPrincipal();
			if(user != null) {
				if(!StringUtils.equals(loginModel.getMobile(), user.getMobile())) {
					currentUser.logout();
				}/*else {
					
					return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
				}*/
			}
			try {
				UsernamePasswordToken token = new UsernamePasswordToken(loginModel.getMobile(),loginModel.getPasswrod());
				currentUser.login(token);
				return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
			} catch( UnknownAccountException uae ) {
				return new ResponseEntity<>(ResultModel.error(ResultStatus.UNKNOWN_ACCOUNT),HttpStatus.INTERNAL_SERVER_ERROR);
	        } catch ( IncorrectCredentialsException ice ) {
	        	return new ResponseEntity<>(ResultModel.error(ResultStatus.INCORRECT_CREDENTIALS),HttpStatus.INTERNAL_SERVER_ERROR);
	        } catch ( LockedAccountException lae ) {
	        	return new ResponseEntity<>(ResultModel.error(ResultStatus.LOCKED_ACCOUNT),HttpStatus.INTERNAL_SERVER_ERROR);
	        } catch ( ExcessiveAttemptsException eae ) {
	        	return new ResponseEntity<>(ResultModel.error(ResultStatus.EXCESSIVE_ATTEMPTS),HttpStatus.INTERNAL_SERVER_ERROR);
	        } catch ( AuthenticationException ae ) {
	        	return new ResponseEntity<>(ResultModel.error(ResultStatus.LOGIN_FAIL),HttpStatus.INTERNAL_SERVER_ERROR);
	        }

		default:
			Assert.notNull(loginType,"请输入登录类型");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@Override
	public ResponseEntity<ResultModel> sendcode(SendModel sendModel) {
		String mobile = sendModel.getMobile();
		ResponseEntity<ResultModel> res = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		if("null".equals(mobile) || mobile == null) {
			Assert.notNull(mobile,"请输入手机号");
			return res;
		}
		if(mobile.length() != 11) {
			Assert.isNull(mobile,"手机号不正确");
			return res;
		}
		String json = JSMSExample.SendSMSCode(mobile);
		String msg_id = JSON.parseObject(json).get("msg_id").toString();
		redistemplate.opsForValue().set("SEND_CODE_"+mobile, msg_id,3000,TimeUnit.SECONDS);
		System.out.println("====================="+msg_id);
		return new ResponseEntity<>(ResultModel.ok(msg_id),HttpStatus.OK);
	}
	
	public static void main(String[] args) {
		String json ="{\"is_valid\":\"true\"}";
		String a = "{\"is_valid\":false,\"error\":{\"code\":50011,\"message\":\"expired code\"}}";
		JSONObject parseObject = JSON.parseObject(a);
		System.out.println(parseObject.get("is_valid"));
		String err = parseObject.get("error").toString();
		System.out.println(err);
	}
	
}
