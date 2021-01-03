package com.kakaopay.homework.domain.coupons;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 쿠폰 Repository
 * @author : JiYoung
 **/

public interface CouponsRepository extends JpaRepository<Coupons, Long> {

    List<Coupons> findByStateAndPayFlagAndUseFlagAndExpiredGreaterThan(String state, String payFlag, String useFlag, String expired);

    List<Coupons> findByPayFlagOrderByIdAsc(String payFlag);

    Coupons findByCouponNo(String couponNo);

    List<Coupons> findByExpired(String expired);

}
