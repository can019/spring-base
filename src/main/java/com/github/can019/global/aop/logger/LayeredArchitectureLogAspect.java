package com.github.can019.global.aop.logger;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * AOP logger
 *
 * <p>com.github.can019 package 내 Controller, Service, Repository 로깅</p>
 *
 * @since 0.0.1
 * @see com.github.can019.global.aop.PointCut
 */
@Slf4j
public class LayeredArchitectureLogAspect {

    private static String DEFAULT_INPUT_MESSAGE_FORMAT = "[{}] >> Input :: {} :: {}";
    private static String DEFAULT_OUTPUT_MESSAGE_FORMAT = "[{}] << Output :: {} :: {}";
    private static String DEFAULT_ERROR_MESSAGE_FORMAT = "[{}] {} :: {} {}";

    public static void defaultTraceInputLog(JoinPoint joinPoint){
        log.trace(DEFAULT_INPUT_MESSAGE_FORMAT, joinPoint.getSignature().toShortString(),
                joinPoint.getArgs(),
                joinPoint.getTarget().getClass());
    }

    public static void defaultTraceOutputLog(JoinPoint joinPoint, Object result){
        log.trace(DEFAULT_OUTPUT_MESSAGE_FORMAT, joinPoint.getSignature().toShortString(),
                result,
                joinPoint.getTarget().getClass());
    }
    public static void errorLog(JoinPoint joinPoint, Throwable e){
        log.error(DEFAULT_ERROR_MESSAGE_FORMAT, joinPoint.getSignature().toShortString() ,
                e.getMessage(), e.getStackTrace()[0], e.getClass().getName());
    }

    @Aspect
    @Component
    public static class BaseLogAspect{
        @Before("com.github.can019.global.aop.PointCut.allControllerServiceRepositoryUnderBasePackage()")
        public static void doBefore(JoinPoint joinPoint){
            defaultTraceInputLog(joinPoint);
        }

        @AfterReturning(pointcut = "com.github.can019.global.aop.PointCut.allControllerServiceRepositoryUnderBasePackage()", returning = "result")
        public void doAfterReturning(JoinPoint joinPoint, Object result){
            defaultTraceOutputLog(joinPoint, result);
        }

        @AfterThrowing(pointcut = "com.github.can019.global.aop.PointCut.allControllerUnderBasePackage()", throwing="e")
        public void doAfterThrowing(JoinPoint joinPoint, Throwable e){
            errorLog(joinPoint, e);
        }
    }
}
