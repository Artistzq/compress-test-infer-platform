package com.kerbalogy.boot.kafka;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-04
 * @description
 **/
@Slf4j
@Aspect
public class ConsumerErrorHandler {

    @Pointcut("@annotation(org.springframework.kafka.annotation.KafkaListener)")
    public void listenerPointcut() {
    }

    @Around("listenerPointcut()")
    public Object handleError(ProceedingJoinPoint joinPoint) {
        String consumeMethod = joinPoint.getSignature().getDeclaringType().getSimpleName() + "#" + joinPoint.getSignature().getName();
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("kafka consumer error, {}", consumeMethod, throwable);
        }
        return null;
    }

}
