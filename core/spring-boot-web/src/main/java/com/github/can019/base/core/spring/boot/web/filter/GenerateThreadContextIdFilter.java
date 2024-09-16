package com.github.can019.base.core.spring.boot.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import java.io.IOException;
import java.util.UUID;

/**
 * Thread id를 부여하는 filter.
 *
 * <p>Logging시 필요한 thread-id를 생성. Log4j2의 ThreadContext class에 id를 저장.</p>
 *
 * @see <a href="https://logging.apache.org/log4j/2.x/manual/thread-context.html">Thread context</a>
 * @since 0.0.1
 */
@Slf4j
@WebFilter(urlPatterns = "/*")
public class GenerateThreadContextIdFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(ThreadContext.isEmpty()) {
            ThreadContext.put("id", UUID.randomUUID().toString().substring(0,8));
        }

        chain.doFilter(request, response);

        log.trace("Clear ThreadContext");
        ThreadContext.clearMap();
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}