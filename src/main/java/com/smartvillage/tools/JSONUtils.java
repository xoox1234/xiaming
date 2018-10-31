package com.smartvillage.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JSONUtils {
	public static String jsonErrorTostr(String json) {
		String info = null;
		JSONObject parseObject = JSON.parseObject(json);
		String bool = parseObject.get("is_valid").toString();
		if(bool == "true" || "true".equals(bool)) {
			info = "true";
			return info;
		}else {
			String err = parseObject.get("error").toString();
			parseObject = JSON.parseObject(err);
			String stu = parseObject.get("code").toString();
			if("50010".equals(stu)) {
				info = "验证码无效";
			}
			if("50011".equals(stu)) {
				info = "验证码过期";
			}
			if("50012".equals(stu)) {
				info = "验证码已通过";
			}
			if("50014".equals(stu)) {
				info = "可发短信余量不足";
			}
		}
		return info;
	}
}
