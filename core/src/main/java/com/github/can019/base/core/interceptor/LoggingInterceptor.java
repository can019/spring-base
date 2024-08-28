package com.github.can019.base.core.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

/**
 * Logging interceptor
 *
 * @since 0.0.3
 */
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {
    /**
     * Ip address, host name을 로깅
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
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
//        ThreadContext.clearMap();
    }
}
