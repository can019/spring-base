package com.github.can019.base.api.global.aop.logger;

import com.github.can019.base.api.global.aop.logger.resource.LogAspectTestController;
import com.github.can019.base.api.global.aop.logger.resource.LogAspectTestRepository;
import com.github.can019.base.api.global.aop.logger.resource.LogAspectTestService;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import({LogAspectTestController.class, LogAspectTestService.class, LogAspectTestRepository.class, AnnotationAwareAspectJAutoProxyCreator.class})
@Profile("test")
public class LayeredArchitectureLogAspectIntegrationTest {
    @Autowired
    private LogAspectTestController testController;

    @Autowired
    private ApplicationContext ac;

    @SpyBean
    private LayeredArchitectureLogAspect.BaseLogAspect baseLogAspect;


    void findBeanByType(String beanName, Class clazz) {
        boolean isBeanPresent = ac.containsBeanDefinition(beanName);
        boolean isBeanPresentByClass = ac.getBeansOfType(clazz).size() > 0;

        assertThat(isBeanPresent || isBeanPresentByClass).isTrue();
    }

    @Test
    @DisplayName("LayeredArchitectureLogAspect.BaseLogAspect.class가 bean으로 등록되어있어야 한다")
    void isLayeredArchitectureLogAspectIsInApplicationContext() {
        findBeanByType("layeredArchitectureLogAspect.BaseLogAspect", LayeredArchitectureLogAspect.BaseLogAspect.class);
    }
}