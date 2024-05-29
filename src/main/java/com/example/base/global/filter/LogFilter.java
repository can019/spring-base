package com.example.base.global.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(ThreadContext.isEmpty()) {
            ThreadContext.put("id", UUID.randomUUID().toString().substring(0,8));
        }
        String idAddress = request.getRemoteAddr();
        String hostName = request.getServerName();
        ThreadContext.put("ipAddress", request.getRemoteAddr());
        ThreadContext.put("hostName", request.getServerName());
        log.trace("Add ipAddress :: {} and hostName :: {} to ThreadContext",idAddress, hostName );
        chain.doFilter(request, response);
        log.trace("Clear ThreadContext");
        ThreadContext.clearMap();
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}