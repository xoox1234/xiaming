package com.smartvillage.exception;

import com.smartvillage.enums.ResultCode;

/**
 * 结果异常，会被 ExceptionHandler 捕捉并返回给前端
 *
 * @author xm
 * @date 2018/10/9
 */
public class ResultException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ResultCode resultCode;

    public ResultException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
