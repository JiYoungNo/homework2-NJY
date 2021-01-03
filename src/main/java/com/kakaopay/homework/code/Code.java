package com.kakaopay.homework.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 공통 코드 정의
 * @author : JiYoung
 **/

@AllArgsConstructor
@Getter
public enum Code {

    //API 응답 코드 정의
    //###########################################
    /** 0000 성공[success] */
    SUCCESS("0000", "성공[success]"),
    /** 1000 요청 오류[요청 정보를 확인해 주세요.] */
    ERROR_PARAMETER("1000", "요청 오류[check your request parameter]"),
    /** 2000 지급 가능한 쿠폰이 존재하지 않습니다. */
    ERROR_NOT_EXIST_PAY_COUPON("2000", "지급 가능한 쿠폰이 존재하지 않습니다."),
    /** 2010 존재하지 않는 쿠폰 번호입니다. */
    ERROR_NOT_EXIST_USE_COUPON("2010", "존재하지 않는 쿠폰 번호입니다."),
    /** 2020 사용처리된 쿠폰 입니다. */
    ERROR_USED_COUPON("2020", "사용처리된 쿠폰 입니다."),
    /** 2030 사용 취소된 쿠폰 입니다. */
    ERROR_USE_CANCELED_COUPON("2030", "사용 취소된 쿠폰 입니다."),
    /** 2040 지급되지 않은 쿠폰 입니다. */
    ERROR_NOT_PAYED_COUPON("2040", "지급되지 않은 쿠폰 입니다."),
    /** 2050 사용되지 않은 쿠폰 입니다. */
    ERROR_NOT_USED_COUPON("2050", "사용되지 않은 쿠폰 입니다."),
    /** 2060 만료된 쿠폰입니다. */
    ERROR_EXPIRED_COUPON("2060", "만료된 쿠폰입니다."),
    /** 2070 쿠폰 발행에 실패하였습니다.*/
    ERROR_ISSUE("2070", "쿠폰 발행에 실패하였습니다."),
    /** 9999 서비스 오류[정의 되지 않은 오류, 관리자에게 문의요망] */
    ERROR_UNKNOWN_SERVICE("9999", "서비스 오류[정의 되지 않은 오류, 관리자에게 문의요망]"),
    //###########################################

    //공통 코드 정의
    //###########################################
    /** "I","발행상태" */
    STATE_I("I","발행상태"),
    /** "P","지급상태" */
    STATE_P("P","지급상태"),
    /** "U","사용상태" */
    STATE_U("U","사용상태"),
    /** "C","사용취소상태" */
    STATE_C("C","사용취소상태"),

    K("K", "쿠폰 맨 앞자리"),
    /** "MMdd-HHmmss-", "쿠폰번호에 사용할 10자리 형식"  */
    COUPON_DATE_PATTERN("MMdd-HHmmss-", "쿠폰번호에 사용할 10자리 형식"),
    /** "yyyyMMdd", "년/월/일" */
    yyyyMMdd("yyyyMMdd", "년/월/일"),

    Y("Y", "Y코드"),
    N("N", "N코드"),
    //###########################################

    //공통로깅 구문
    //###########################################
    /** 오른쪽 로깅 구문 default */
    LOGGING_LEFT("----------->","오른쪽 로깅 구문"),
    /** 왼쪽 로깅 구문 default */
    LOGGING_RIGTH("<-----------", "왼쪽 로깅 구문");
    //###########################################

    String code;
    String msg;
}
