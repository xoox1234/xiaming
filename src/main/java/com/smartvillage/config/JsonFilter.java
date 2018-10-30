package com.smartvillage.config;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartvillage.model.ResultModel;

public class JsonFilter extends AuthenticationFilter{
	/**
     * 未登录提示
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = this.getSubject(request, response);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        if(subject.getPrincipal() == null) {
        	ResultModel result = ResultModel.error(ResultStatus.UNAUTHORIZED);
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            ObjectMapper mapper = new ObjectMapper();
            httpServletResponse.getOutputStream().write(mapper.writeValueAsString(result).getBytes("utf-8"));
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        return false;
    }
}
