package com.kakaopay.homework.model.response;

import com.kakaopay.homework.code.Code;
import com.kakaopay.homework.model.dto.CouponsDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 쿠폰 리스트 응답 객체
 * @author : JiYoung
 **/

@ToString(callSuper = true)
@NoArgsConstructor
@Getter
public class CouponListResponse extends KakaoPayResponse {

    /** 쿠폰 리스트 */
    private List<CouponsDto> list;

    public CouponListResponse(Code result, List<CouponsDto> list) {
        super.resultCode = result.getCode();
        super.resultMsg = result.getMsg();
        this.list  = list;
    }

}
