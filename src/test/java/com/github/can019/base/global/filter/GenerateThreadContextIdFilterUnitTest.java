package com.github.can019.base.global.filter;

import com.github.can019.base.global.filter.GenerateThreadContextIdFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.apache.logging.log4j.ThreadContext;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import static org.mockito.Mockito.*;

import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

public class GenerateThreadContextIdFilterUnitTest {

    @Test
    void filter는_전역적으로_적용되어야_한다() throws ServletException, IOException {
        var request = new MockHttpServletRequest();
        var response = new MockHttpServletResponse();
        var filterChain = mock(FilterChain.class);
        var generateThreadContextIdFilter = new GenerateThreadContextIdFilter();

        request.setRequestURI("/"+ UUID.randomUUID().toString());

        generateThreadContextIdFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void 요청이_끝나면_Thread_Context는_초기화되어야한다() throws ServletException, IOException {
        var request = new MockHttpServletRequest();
        var response = new MockHttpServletResponse();
        var filterChain = mock(FilterChain.class);
        var generateThreadContextIdFilter = new GenerateThreadContextIdFilter();

        String randomId = UUID.randomUUID().toString().substring(0,8);
        ThreadContext.put("id", randomId);

        request.setRequestURI("/"+ UUID.randomUUID().toString());

        generateThreadContextIdFilter.doFilter(request, response, filterChain);
        assertThat(ThreadContext.isEmpty()).isTrue();
    }
}
