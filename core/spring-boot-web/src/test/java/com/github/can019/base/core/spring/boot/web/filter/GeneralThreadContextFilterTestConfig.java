package com.github.can019.base.core.spring.boot.web.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

public class GeneralThreadContextFilterTestConfig {

    @Bean
    public FilterRegistrationBean<GenerateThreadContextIdFilter> registerGlobalThreadContextFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new GenerateThreadContextIdFilter());

        return registrationBean;
    }

    @Bean FilterRegistrationBean<ThreadContextSliceTestCheckFilter> registerThreadContextSliceTestCheckFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new ThreadContextSliceTestCheckFilter());
        return registrationBean;
    }


}
