package com.smartvillage.model;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class SendModel {
	@ApiParam(value = "手机号",required = true)
	private String mobile;
}
