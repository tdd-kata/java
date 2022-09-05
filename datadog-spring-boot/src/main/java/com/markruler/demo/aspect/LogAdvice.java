package com.markruler.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAdvice {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.markruler.demo.controller.DemoController.home*(..))")
    public void home() {
        // Pointcut methods should have empty body
    }

    @Pointcut("execution(* com.markruler.demo.controller.DemoController.invalid*(..))")
    public void invalid() {
        // Pointcut methods should have empty body
    }

    @Before("home() || invalid()")
    public void beforeAspect() {
        log.info("Before aspect");
    }

    @AfterReturning(
            value = "home()",
            returning = "returnValue")
    public void writeSuccessLog(JoinPoint joinPoint, Object returnValue) {
        log.info("AfterReturning {}", returnValue);
        // logJoinPoint(joinPoint);
    }

    @AfterThrowing(
            value = "invalid()",
            throwing = "exception")
    public void writeFailLog(JoinPoint joinPoint, Exception exception) {
        log.error("AfterThrowing {}", exception.getMessage(), exception);
        // logJoinPoint(joinPoint);
    }

    @After("home() || invalid()")
    public void afterAspect() {
        log.info("After Aspect");
    }

    private void logJoinPoint(JoinPoint joinPoint) {
        log.info("signature > {}", joinPoint.getSignature());
        log.info("target > {}", joinPoint.getTarget());
    }
}
