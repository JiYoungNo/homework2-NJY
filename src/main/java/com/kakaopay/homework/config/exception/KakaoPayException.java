package com.kakaopay.homework.config.exception;

import com.kakaopay.homework.code.Code;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 커스텀 Exception
 * @author : JiYoung
 **/


@EqualsAndHashCode(callSuper = true)
@Getter
public class KakaoPayException extends RuntimeException {

    private Code errorCode;

    public KakaoPayException(Code errorCode) {
        super();
        this.errorCode = errorCode;
    }
}
