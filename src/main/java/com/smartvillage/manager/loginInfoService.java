package com.smartvillage.manager;


import org.springframework.http.ResponseEntity;

import com.smartvillage.model.AuthModel;
import com.smartvillage.model.LoginModel;
import com.smartvillage.model.ResultModel;
import com.smartvillage.model.SendModel;

public interface loginInfoService {
	ResponseEntity<ResultModel> userreg(AuthModel authModel) throws Exception;
	ResponseEntity<ResultModel> login(LoginModel loginModel);
	ResponseEntity<ResultModel> sendcode(SendModel sendModel);
} 
