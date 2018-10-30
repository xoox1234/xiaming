package com.smartvillage.authorization.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.smartvillage.authorization.annotation.Authorization;
import com.smartvillage.authorization.manager.TokenManager;
import com.smartvillage.authorization.model.TokenModel;
import com.smartvillage.config.Constants;
import com.smartvillage.tools.Signature;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义拦截器，判断此次请求是否有权限
 * @see com.scienjus.authorization.annotation.Authorization
 *
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationInterceptor.class);
    @Autowired
    private TokenManager manager;
    
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ex != null) {
            LOGGER.error(handler.toString());
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    public void postHandle(HttpServletRequest req, HttpServletResponse res,
                           Object arg2, ModelAndView arg3) throws Exception {
    }
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //从header中得到token
        String authorization = request.getHeader(Constants.AUTHORIZATION);
        
        String sign = request.getParameter("sign");
		Enumeration<?> pNames =  request.getParameterNames();
		Map<String, String> params = new HashMap<String, String>();
		while (pNames.hasMoreElements()) {
			String pName = (String) pNames.nextElement();
			if("sign".equals(pName))continue;
			String pValue = request.getParameter(pName);
			params.put(pName, pValue);
		}
		if(null == request.getParameterNames()) {
			LOGGER.error("APP请求数据为空");
			return false;
		}
		LOGGER.info("接收到APP请求数据:" + JSON.toJSON(params));

		String SvrivcSign = Signature.createSign(params, true);//获取移动端的参数
		System.out.println("服务端签名数据"+SvrivcSign);
        
        //验证token
        TokenModel model = manager.getToken(authorization);
        if (manager.checkToken(model)) {
            //如果token验证成功，将token对应的用户id存在request中，便于之后注入
            request.setAttribute(Constants.CURRENT_USER_ID, model.getUserId());
            return true;
        }
        //如果验证token失败，并且方法注明了Authorization，返回401错误
        if (method.getAnnotation(Authorization.class) != null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }
}
