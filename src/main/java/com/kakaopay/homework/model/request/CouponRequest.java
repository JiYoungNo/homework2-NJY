package com.kakaopay.homework.model.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 쿠폰 요청 객체
 * @author : JiYoung
 **/

@ToString
@Getter
public class CouponRequest {

    @Size(max = 21, min = 21, message = "쿠폰 번호는 반드시 21자리 입니다.")
    @NotBlank
    private String couponNo;
}
