package com.smartvillage.utils;

import com.smartvillage.Result;
import com.smartvillage.enums.ResultCode;

@SuppressWarnings("rawtypes")
public class ResultUtils {

    
	public static Result success(Object data) {
        return new Result<>(ResultCode.SUCCESS, data);
    }

    public static Result warn(ResultCode resultCode, String msg) {
        Result<Object> result = new Result<>(resultCode);
        result.setMsg(msg);
        return result;
    }

    public static Result warn(ResultCode resultCode) {
        return new Result(resultCode);
    }

}
