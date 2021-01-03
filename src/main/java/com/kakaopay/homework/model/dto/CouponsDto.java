package com.kakaopay.homework.model.dto;

import com.kakaopay.homework.domain.coupons.Coupons;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;

/**
 * 쿠폰 DTO
 * @author : JiYoung
 **/

@ToString
@NoArgsConstructor
@Getter
public class CouponsDto {

    /** seq */
    private Long id;
    /** 쿠폰번호 */
    private String couponNo;
    /** I: 발행상태,  U:사용상태 , C:사용취소 상태 */
    private String state;
    /** 지급 플레그 (Y:지급, N:미지급 ) */
    private String payFlag;
    /** 사용 플레그 (Y:사용, N:미사용 or 사용취소 ) */
    private String useFlag;
    /** 리딤 시간 */
    private LocalDateTime usedDate;
    /** 만료일자 yyyyMMdd */
    private String expired;

    public CouponsDto(Coupons entity) {
        this.id = entity.getId();
        this.couponNo = entity.getCouponNo();
        this.state = entity.getState();
        this.payFlag = entity.getPayFlag();
        this.useFlag = entity.getUseFlag();
        this.usedDate = entity.getUsedDate();
        this.expired = entity.getExpired();
    }

}
