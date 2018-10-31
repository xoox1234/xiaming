package com.smartvillage.authorization.jpush;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jsms.api.SendSMSResult;
import cn.jsms.api.ValidSMSResult;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.SMSPayload;

public class JSMSExample {
	
	protected static final Logger LOG = LoggerFactory.getLogger(JSMSExample.class);
	private static final String appkey = "a7d824139636cd67452dee64";
	private static final String masterSecret = "317396d09207e9e289189967";
	public static void main(String[] args) {
	 	//testSendSMSCode();
	 	//JSMSExample.Boolean s = sendValidSMSCode("620908639827","274421");
	 	//System.out.println(s);
	 	 
	 	}
	
	/**
	 * 发送短信验证码
	 * @param mobile
	 * @return
	 */
	public static String SendSMSCode(String mobile) {
		String resule = null;
		SendSMSResult res = null;
		SMSClient client = new SMSClient(masterSecret, appkey);
		SMSPayload payload = SMSPayload.newBuilder()
		.setMobileNumber(mobile)
		.setTempId(1)
		.build();
		try {
			 res = client.sendSMSCode(payload);
			 String json = res.toString();
				String msg_id = JSON.parseObject(json).get("msg_id").toString();
				if(!"null".equals(msg_id) || msg_id != null) {
					LOG.info("验证码发送成功！"+msg_id);
					//redistemplate.opsForValue().set("SEND_CODE_"+mobile, msg_id, 3000, TimeUnit.SECONDS);
					
				}
			System.out.println("11111111111111111"+res.toString());
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
	/**
	 * 验证码校验
	 * @param msg_id
	 * @param valid
	 * @return
	 */
	public static String sendValidSMSCode(String msg_id,String valid) {
		SMSClient client = new SMSClient(masterSecret, appkey);
		Boolean isValid = false;
		String error = null;
		try {
			ValidSMSResult res = client.sendValidSMSCode(msg_id ,valid);
			  isValid = res.getIsValid();
			  System.out.println("-----------"+res.toString());
			  LOG.info(res.toString());
			  return res.toString();
		} catch (APIConnectionException  e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			LOG.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			error = e.getMessage();
		 	e.printStackTrace();
		 	System.out.println(e.getStatus() + " errorCode: " + e.getErrorCode() + " " + e.getErrorMessage());
		 	LOG.error("Error response from JPush server. Should review and fix it. ", e);
		 	LOG.info("HTTP Status: " + e.getStatus());
		 	LOG.info("Error Message: " + e.getMessage());
		}
		return error;
}
}