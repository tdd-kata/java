package org.xpdojo.designpatterns._02_structural_patterns._07_proxy.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerfAspect {

    @Around("bean(gameService)")
    public Object timestamp(ProceedingJoinPoint point) throws Throwable {
        long before = System.currentTimeMillis();

        Object result = point.proceed();

        System.out.println("\n" + (System.currentTimeMillis() - before) + "ms");

        return result;
    }
}
