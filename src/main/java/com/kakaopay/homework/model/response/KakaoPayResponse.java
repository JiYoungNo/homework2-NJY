package com.kakaopay.homework.model.response;


import com.kakaopay.homework.code.Code;
import lombok.*;

/**
 * 공통 응답 객체 [응답 상위 객체]  
 * @author : JiYoung
 **/

@ToString
@NoArgsConstructor
@Getter
public class KakaoPayResponse {

    /** 응답 코드 */
    protected String resultCode;
    /** 응답 메세지 */
    protected String resultMsg;

    public KakaoPayResponse(Code code) {
        this.resultCode = code.getCode();
        this.resultMsg = code.getMsg();
    }
}
