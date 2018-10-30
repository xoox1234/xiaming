package com.smartvillage.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jsms.api.SendSMSResult;
import cn.jsms.api.ValidSMSResult;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.SMSPayload;

public class JSMSExample {
	
	protected static final Logger LOG = LoggerFactory.getLogger(JSMSExample.class);
	private static final String appkey = "a7d824139636cd67452dee64";
	private static final String masterSecret = "4e4f5e6f1c62bfc744df1bfe";
	
	public static void main(String[] args) {
	 	testSendSMSCode();
	 	//testSendValidSMSCode();
	 	 
	 	}
	
	//发送短信验证码
	public static String testSendSMSCode() {
		String resule = null;
		SendSMSResult res = null;
		SMSClient client = new SMSClient(masterSecret, appkey);
		SMSPayload payload = SMSPayload.newBuilder()
		.setMobileNumber("17345433303")
		.setTempId(1)
		.build();
		try {
			 res = client.sendSMSCode(payload);
			System.out.println(res.toString());
			LOG.info(res.toString());
		} catch (APIConnectionException  e) {
			LOG.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException   e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Message: " + e.getMessage());
		}
				resule = res.toString();
				return resule;
	}
	//验证码校验
	public static Boolean testSendValidSMSCode(String msg_id,String valid) {
		SMSClient client = new SMSClient(masterSecret, appkey);
		Boolean isValid = false;
		try {
			ValidSMSResult res = client.sendValidSMSCode(msg_id ,valid);
			  isValid = res.getIsValid();
			  System.out.println(res.toString());
			  LOG.info(res.toString());
			  return isValid;
		} catch (APIConnectionException  e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			LOG.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
		 	e.printStackTrace();
		 	if (e.getErrorCode() == 50010) {
		 	 	System.out.println("50010");
		 	 	}
		 	System.out.println(e.getStatus() + " errorCode: " + e.getErrorCode() + " " + e.getErrorMessage());
		 	LOG.error("Error response from JPush server. Should review and fix it. ", e);
		 	LOG.info("HTTP Status: " + e.getStatus());
		 	LOG.info("Error Message: " + e.getMessage());
		}
		return isValid;
}
}