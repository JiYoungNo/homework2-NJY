package com.kakaopay.homework.domain.coupons;


import com.kakaopay.homework.code.Code;
import com.kakaopay.homework.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 쿠폰 Entity
 * @author : JiYoung
 **/

@Getter
@NoArgsConstructor
@Entity
@Table(indexes = {@Index(name = "idx_coupon", columnList = "couponNo")})
public class Coupons extends BaseTimeEntity {

    /** seq */
    @Id //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 쿠폰번호 */
    @Column(length = 21, nullable = false, unique = true)
    private String couponNo;

    /** I: 발행상태,  U:사용상태 , C:사용취소 상태 */
    @Column(length = 1, nullable = false)
    private String state;

    /** 지급 플레그 (Y:지급, N:미지급 ) */
    @Column(length = 1, nullable = false)
    private String payFlag;

    /** 사용 플레그 (Y:사용, N:미사용 or 사용취소 ) */
    @Column(length = 1, nullable = false)
    private String useFlag;

    /** 리딤 시간 */
    private LocalDateTime usedDate;

    /** 만료일자 yyyyMMdd */
    @Column(length = 8, nullable = false)
    private String expired;

    @Builder
    public Coupons(String couponNo, String state, String payFlag, String useFlag, LocalDateTime usedDate, String expired) {
        this.couponNo = couponNo;
        this.state = state;
        this.payFlag = payFlag;
        this.useFlag = useFlag;
        this.usedDate = usedDate;
        this.expired = expired;
    }

    public void updatePayCoupon() {
        this.state = Code.STATE_P.getCode();
        this.payFlag = Code.Y.getCode();
    }

    public void updateUseCoupon() {
        this.state = Code.STATE_U.getCode();
        this.useFlag = Code.Y.getCode();
        this.usedDate = LocalDateTime.now();
    }

    public void updateUseCancelCoupon() {
        this.state = Code.STATE_C.getCode();
        this.useFlag = Code.N.getCode();
    }

}
