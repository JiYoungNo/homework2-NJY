package com.kakaopay.homework.config.exception;

import com.kakaopay.homework.code.Code;
import com.kakaopay.homework.model.response.KakaoPayResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 에러 처리 Advice
 * @author : JiYoung
**/


@RequiredArgsConstructor
@RestControllerAdvice
public class CustomExceptionAdvice {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected KakaoPayResponse unknownServerException(Exception e) {
        logger.error("{}:", new Object(){}.getClass().getEnclosingMethod().getName(), e);
        return new KakaoPayResponse(Code.ERROR_UNKNOWN_SERVICE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected KakaoPayResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("{}:", new Object(){}.getClass().getEnclosingMethod().getName(), e);
        return new KakaoPayResponse(Code.ERROR_PARAMETER);
    }

    @ExceptionHandler(KakaoPayException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected KakaoPayResponse kakaoPayException(KakaoPayException e) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logger.error("{}:",methodName, e);
        logger.error("{} reponse: [code: {}, msg {}]", methodName, e.getErrorCode().getCode(), e.getErrorCode().getMsg());
        return new KakaoPayResponse(e.getErrorCode());
    }
}
