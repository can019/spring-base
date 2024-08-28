package com.github.can019.base.global.filter;

import jakarta.servlet.*;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@Profile("GENERATE_THREAD_CONTEXT_ID_TEST")
public class ThreadContextCheckFilter implements Filter {
    public final static String EMPTY_THREAD_CONTEXT_MESSAGE = "EMPTY_THREAD_CONTEXT";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(ThreadContext.isEmpty()) throw new RuntimeException(EMPTY_THREAD_CONTEXT_MESSAGE);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
