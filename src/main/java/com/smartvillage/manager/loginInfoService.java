package com.smartvillage.manager;


import org.springframework.http.ResponseEntity;

import com.smartvillage.model.AuthModel;
import com.smartvillage.model.LoginModel;
import com.smartvillage.model.ResultModel;

public interface loginInfoService {
	ResponseEntity<ResultModel> userreg(AuthModel authModel) throws Exception;
	ResponseEntity<ResultModel> login(LoginModel loginModel);
	ResponseEntity<ResultModel> sendcode(String phone);
} 
