package com.example.base.global.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointCut {

    @Pointcut("execution(* com.example.base..*.*(..))")
    public void basePackage() {}

    @Pointcut("execution(* *..*Controller.*(..))")
    public void allController() {}

    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {}

    @Pointcut("execution(* *..*Repository.*(..))")
    public void allRepository(){}


    @Pointcut("basePackage() && allController()")
    public void allControllerUnderBasePackage() {}

    @Pointcut("basePackage() && allService()")
    public void allServiceUnderBasePackage() {}

    @Pointcut("basePackage() && allRepository()")
    public void allRepositoryUnderBasePackage(){}

    @Pointcut("basePackage() && (allRepository() || allService() || allController()) ")
    public void allControllerServiceRepositoryUnderBasePackage(){}
}
