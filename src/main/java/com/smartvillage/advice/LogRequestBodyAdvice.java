package com.smartvillage.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import com.smartvillage.utils.JsonDesUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author xm
 * @date 2018/10/9
 */
@ControllerAdvice
public class LogRequestBodyAdvice implements RequestBodyAdvice {

    private Logger logger = LoggerFactory.getLogger(LogRequestBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage,
                                  MethodParameter parameter, Type targetType,
                                  Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage,
                                           MethodParameter parameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage,
                                MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        if (logger.isDebugEnabled()) {
            Method method = parameter.getMethod();
            String classMappingUri = getClassMappingUri(method.getDeclaringClass());
            String methodMappingUri = getMethodMappingUri(method);
            if (!methodMappingUri.startsWith("/") && !classMappingUri.endsWith("/")) {
                methodMappingUri = "/" + methodMappingUri;
            }
            logger.debug("uri={} | requestBody={}", classMappingUri + methodMappingUri, JsonDesUtils.toLogString(body));
        }
        return body;
    }

    private String getMethodMappingUri(Method method) {
        return getMappingUri(method.getDeclaredAnnotations());
    }

    private String getClassMappingUri(Class<?> declaringClass) {
        return getMappingUri(declaringClass.getDeclaredAnnotations());
    }

    private String getMappingUri(Annotation[] declaredAnnotations) {
        String mappingUri = "";
        for (Annotation declaredAnnotation : declaredAnnotations) {
            if (declaredAnnotation instanceof RequestMapping) {
                mappingUri = getMaxLengthUri(((RequestMapping) declaredAnnotation).value());
            } else if (declaredAnnotation instanceof PostMapping) {
                mappingUri = getMaxLengthUri(((PostMapping) declaredAnnotation).value());
            } else if (declaredAnnotation instanceof GetMapping) {
                mappingUri = getMaxLengthUri(((GetMapping) declaredAnnotation).value());
            }
        }
        return mappingUri;
    }

    private String getMaxLengthUri(String[] strings) {
        String maxLengthUri = "";
        for (String string : strings) {
            if (string.length() > maxLengthUri.length()) {
                maxLengthUri = string;
            }
        }
        return maxLengthUri;
    }
}
