package anhkiet.dev.course_management.util;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import anhkiet.dev.course_management.domain.responce.RestResponce;
import anhkiet.dev.course_management.config.ApiMessage;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
@ControllerAdvice
public class FormatRestResponce implements ResponseBodyAdvice<Object>{

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {  
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();

        int status = servletResponse.getStatus();

        RestResponce<Object> res = new RestResponce<Object>();
        res.setStatusCode(status);
        if(body instanceof String || body instanceof RestResponce || body instanceof Resource){
            return body;
        }
        if (status >= 400) {
            return body;
        } else {
            ApiMessage message = returnType.getMethodAnnotation(ApiMessage.class);
            res.setData(body);
            res.setMessage(message != null ? message.value() : "Call API Successlly");
        }
        return res;
    }
    
}
