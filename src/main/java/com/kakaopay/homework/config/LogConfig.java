package com.kakaopay.homework.config;

import com.kakaopay.homework.code.Code;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 공통 로깅 정의
 * @author : JiYoung
 **/

@Component
@Aspect
public class LogConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("within(com.kakaopay.homework.web..*)")
    public Object loggingController(ProceedingJoinPoint pjp) throws Throwable {

        long startAt = System.currentTimeMillis();

        String methodPath = pjp.getSignature().getDeclaringTypeName();
        String methodName = pjp.getSignature().getName();

        logger.info(Code.LOGGING_LEFT.getCode() + "###################################################" + Code.LOGGING_RIGTH.getCode());
        logger.info(Code.LOGGING_LEFT.getCode() + "REQUEST     : {}({})", methodPath, methodName);

        Object result = pjp.proceed();

        long endAt = System.currentTimeMillis();

        logger.info(Code.LOGGING_LEFT.getCode() + "RESPONSE    : {}({}) = {} processing time: ({}ms)", methodPath, methodName, result, endAt - startAt);
        logger.info(Code.LOGGING_LEFT.getCode() + "###################################################" + Code.LOGGING_RIGTH.getCode());

        return result;
    }
}
