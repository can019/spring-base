package com.github.can019.base.api.config;

import com.github.can019.base.core.spring.boot.web.filter.GenerateThreadContextIdFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<GenerateThreadContextIdFilter> registerGlobalThreadContextFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new GenerateThreadContextIdFilter());

        return registrationBean;
    }
}
