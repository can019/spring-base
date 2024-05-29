package com.example.base.global.aop.logger;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.hibernate.mapping.Join;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
public class LayeredArchitectureLogAspect {

    private static String DEFAULT_INPUT_MESSAGE_FORMAT = "[{}] >> Input :: {} :: {}";
    private static String DEFAULT_OUTPUT_MESSAGE_FORMAT = "[{}] << Output :: {} :: {}";
    private static String DEFAULT_ERROR_MESSAGE_FORMAT = "[{}] {} :: {}";

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
    public static void errorLog(JoinPoint joinPoint, Exception e){
        log.error(DEFAULT_ERROR_MESSAGE_FORMAT, joinPoint.getSignature().toShortString(),
                e.getMessage(),
                joinPoint.getTarget().getClass() ,e);
    }

    @Aspect
    @Component
    public static class BaseLogAspect{
        @Around("com.example.base.global.aop.PointCut.allControllerUnderBasePackage()")
        public static Object logControllerUnderBasePackage(ProceedingJoinPoint joinPoint) throws Throwable{
            if(ThreadContext.isEmpty()) {
                ThreadContext.put("id", UUID.randomUUID().toString().substring(0,8));
            }
            Object result = null;
            try {
                // Before
                defaultTraceInputLog(joinPoint);

                // Proceed
                result = joinPoint.proceed();

                // After return
                defaultTraceOutputLog(joinPoint, result);

                return result;
            } catch (Exception e) {
                // AfterThrowing
                errorLog(joinPoint, e);
                return result;
            } finally {
                // After
                log.trace("Clear ThreadContext");
                ThreadContext.clearMap();
                return result;
            }
        }
    }
    @Aspect
    @Component
    public static class ServiceLogAspect{
        @Around("com.example.base.global.aop.PointCut.allServiceUnderBasePackage()")
        public static Object logControllerUnderBasePackage(ProceedingJoinPoint joinPoint) throws Throwable{
            Object result = null;
            try {
                defaultTraceInputLog(joinPoint);

                result = joinPoint.proceed();

                defaultTraceOutputLog(joinPoint, result);
                return result;
            } catch (Exception e){
                errorLog(joinPoint, e);
            } finally {
                return result;
            }
        }
    }

    @Aspect
    @Component
    public static class RepositoryLogAspect {
        @Around("com.example.base.global.aop.PointCut.allRepositoryUnderBasePackage()")
        public static Object logControllerUnderBasePackage(ProceedingJoinPoint joinPoint) throws Throwable {
            Object result = null;
            try {
                defaultTraceInputLog(joinPoint);

                result = joinPoint.proceed();

                defaultTraceOutputLog(joinPoint, result);

            } catch (Exception e) {
                errorLog(joinPoint, e);
            } finally {
                return result;
            }
        }
    }
}
