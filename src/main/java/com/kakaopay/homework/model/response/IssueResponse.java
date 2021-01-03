package com.kakaopay.homework.model.response;


import com.kakaopay.homework.code.Code;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 발행 응답 객체
 * @author : JiYoung
 **/

@ToString(callSuper = true)
@NoArgsConstructor
@Getter
public class IssueResponse extends KakaoPayResponse {

    /** 결과 값 */
    private int resultNumber;

    public IssueResponse(Code result, int resultNumber) {
        super.resultCode = result.getCode();
        super.resultMsg = result.getMsg();
        this.resultNumber  = resultNumber;
    }
}
