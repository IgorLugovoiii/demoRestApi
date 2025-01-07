package com.RestApiExample.demo.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.RestApiExample.demo.services.*.*(..))")//execution вказує до яких методів застосовувати аспект
    //* — будь-який модифікатор доступу
    //com.RestApiExample.demo.services.* — усі класи в пакеті services
    //* — будь-який метод у цих класах
    //(..) — будь-яка кількість аргументів
    //JoinPoint — це поточний стан виконання методу, який містить усі дані про нього
    public void logBefore(JoinPoint joinPoint){
        logger.debug("Arguments: {}", Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "execution(* com.RestApiExample.demo.services.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result){
        logger.debug("Method {} executed successfully", joinPoint.getSignature().toShortString());//toShortString повертає findById(Long), а getName findById, а toLongString повертає повністью
        if(result != null){
            logger.debug("Returned value: {}", result);
        }
    }

    @AfterThrowing(pointcut = "execution(* com.RestApiExample.demo.services.*.*(..))", throwing = "exception")
    public void logAfterReturning(JoinPoint joinPoint, Throwable exception){
        logger.error("Exception int method: {} with message: {}", joinPoint.getSignature().toShortString(), exception.getMessage(), exception);
    }
}
