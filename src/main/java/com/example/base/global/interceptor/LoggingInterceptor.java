package com.example.base.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if(ThreadContext.isEmpty()) {
            ThreadContext.put("id", UUID.randomUUID().toString().substring(0,8));
        }
        ThreadContext.put("ipAddress", request.getRemoteAddr());
        ThreadContext.put("hostName", request.getServerName());

        log.trace("Add ipAddress :: {} and hostName :: {} to ThreadContext",
                request.getRemoteAddr(), request.getServerName() );

        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        log.info("Response {} {} {} {}",request.getMethod(),
                request.getRequestURI(), response.getContentType(), response.getStatus());
        log.trace("Clear ThreadContext");
        ThreadContext.clearMap();
    }


}
