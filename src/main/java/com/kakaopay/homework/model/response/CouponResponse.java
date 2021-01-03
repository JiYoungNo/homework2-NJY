package com.kakaopay.homework.model.response;

import com.kakaopay.homework.code.Code;
import com.kakaopay.homework.model.dto.CouponsDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 쿠폰 응답 객체
 * @author : JiYoung
 **/

@ToString(callSuper = true)
@NoArgsConstructor
@Getter
public class CouponResponse extends KakaoPayResponse {

    /** 쿠폰 */
    private CouponsDto coupon;

    public CouponResponse(Code result, CouponsDto coupon) {
        super.resultCode = result.getCode();
        super.resultMsg = result.getMsg();
        this.coupon  = coupon;
    }
}
