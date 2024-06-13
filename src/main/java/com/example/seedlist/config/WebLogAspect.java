package com.example.seedlist.config;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * 日志切面，用户打印web请求和响应日志
 */
@Slf4j
@Component
@Aspect
public class WebLogAspect {

    @Pointcut("execution(public * com.example.seedlist.controller..*.*(..))")
    public void pointCut() {
        //这是一个切面
    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String className = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = proceedingJoinPoint.getSignature().getName();
        String prefix = className + "." + methodName;
        log.info("[{}] Request-Arg:{}", prefix, JSONUtil.toJsonStr(proceedingJoinPoint.getArgs()));
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        //打印出参
        log.info("[{}] Response-result:{}", prefix, JSONUtil.toJsonStr(result));
        //执行耗时
        log.info("[{}] Time-Consuming:{} ms", prefix, System.currentTimeMillis() - startTime);
        return result;
    }
}
