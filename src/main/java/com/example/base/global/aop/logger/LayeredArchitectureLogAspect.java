package com.example.base.global.aop.logger;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

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
        @Before("com.example.base.global.aop.PointCut.allControllerServiceRepositoryUnderBasePackage()")
        public static void doBefore(JoinPoint joinPoint){
            defaultTraceInputLog(joinPoint);
        }

        @AfterReturning(pointcut = "com.example.base.global.aop.PointCut.allControllerServiceRepositoryUnderBasePackage()", returning = "result")
        public void doAfterReturning(JoinPoint joinPoint, Object result){
            defaultTraceOutputLog(joinPoint, result);
        }

        @AfterThrowing(pointcut = "com.example.base.global.aop.PointCut.allControllerUnderBasePackage()", throwing="e")
        public void doAfterThrowing(JoinPoint joinPoint, Throwable e){
            errorLog(joinPoint, e);
        }
    }
}
