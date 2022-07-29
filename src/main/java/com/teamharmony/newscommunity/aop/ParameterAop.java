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


/**
 * 모든 컨트롤러에 Pointcut을 생성해, 파라미터와
 * @author hyeoKing
 */
@Aspect
@Component
@Slf4j
public class ParameterAop {
    @Pointcut("execution(* *..*controller..*.*(..))")
    private void cut(){}


    /**
     * 상기에 적용된 포인트 컷이 실행되기 이전에 joinpoint로부터 args값을 받아 입력 값에 대해 로깅
     * @param joinPoint
     */

    @Before("cut()")
    public void before(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        for(Object obj : args){
            log.info("input parameter: {}", obj!=null ? obj.getClass().getSimpleName() : null);
            log.info("request: {}", obj);
        }
    }


    /**
     * 상기에 적용된 포인트 컷이 실행된 이후에 return 값에 대해 로깅
     * @param joinPoint
     * @param returnObj
     */
    @AfterReturning(value= "cut()", returning =  "returnObj")
    public void afterReturn(JoinPoint joinPoint, Object returnObj){
        log.info("response value: ", returnObj);
    }
}
