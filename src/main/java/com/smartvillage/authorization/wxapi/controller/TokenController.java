package com.smartvillage.authorization.wxapi.controller;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartvillage.authorization.annotation.Authorization;
import com.smartvillage.authorization.annotation.CurrentUser;
import com.smartvillage.authorization.manager.TokenManager;
import com.smartvillage.authorization.model.TokenModel;
import com.smartvillage.config.ResultStatus;
import com.smartvillage.domain.Users;
import com.smartvillage.model.ResultModel;
import com.smartvillage.repository.UsersRepository;
import com.smartvillage.repository.UsersCredentialRepository;

/**
 * 获取和删除token的请求地址，在Restful设计中其实就对应着登录和退出登录的资源映射
 * 
 */
@RestController
@RequestMapping("/tokens")
public class TokenController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private TokenManager tokenManager;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "登录")
    public ResponseEntity<ResultModel> login(@RequestParam String phone, @RequestParam String password) {
        Assert.notNull(phone, "phone can not be empty");
        Assert.notNull(password, "password can not be empty");
/*
        Users user = usersRepository.findByMobile(phone);
        if (user == null ||  //未注册
        		!user.getPassword().equals(password)) {  //密码错误
            //提示用户名或密码错误
           return new ResponseEntity<>(ResultModel.error(ResultStatus.USERNAME_OR_PASSWORD_ERROR), HttpStatus.NOT_FOUND);
        }
        //生成一个token，保存用户登录状态
        TokenModel model = tokenManager.createToken(user.getId());*/
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @Authorization
    @ApiOperation(value = "退出登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> logout(@CurrentUser Users user) {
        tokenManager.deleteToken(user.getId());
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

}
