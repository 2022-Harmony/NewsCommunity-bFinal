package com.teamharmony.newscommunity.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Aspect
@Component
@Slf4j
public class ParameterAop {
    @Pointcut("execution(* *..*controller..*.*(..))")
    private void cut(){}

    @Before("cut()")
    public void before(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println(method.getName());
        Object[] args = joinPoint.getArgs();
        for(Object obj : args){
            log.info("input parameter: {}", obj!=null ? obj.getClass().getSimpleName() : null);
            log.info("request: {}", obj);
        }
    }

    @AfterReturning(value= "cut()", returning =  "returnObj")
    public void afterReturn(JoinPoint joinPoint, Object returnObj){
        log.info("response value: ", returnObj);
        System.out.println(returnObj);
    }
}
