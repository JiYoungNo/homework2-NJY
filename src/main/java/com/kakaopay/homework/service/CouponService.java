package com.kakaopay.homework.service;

import com.kakaopay.homework.code.Code;
import com.kakaopay.homework.config.exception.KakaoPayException;
import com.kakaopay.homework.domain.coupons.Coupons;
import com.kakaopay.homework.domain.coupons.CouponsRepository;
import com.kakaopay.homework.model.dto.CouponsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponsRepository couponsRepository;

    /**
     * 쿠폰 발행
     * @param N 발행 요청 수
     * @return 발행 성공 수
     */
    @Transactional
    public int issue(int N) {

        //생성된 쿠폰 리스트를 담기 위한 배열 리스트를 생성한다
        List<Coupons> issueCoupons = new ArrayList<>();

        //쿠폰 prefix 계산
        DateTimeFormatter format = DateTimeFormatter.ofPattern(Code.COUPON_DATE_PATTERN.getCode());
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        String prefix = Code.K.getCode()+format.format(zonedDateTime);

        //만료기간 1년 뒤로 설정
        String expired = LocalDateTime.now().plusYears(1).format(DateTimeFormatter.ofPattern(Code.yyyyMMdd.getCode()));

        //쿠폰번호 생성
        ThreadLocalRandom.current().ints(0, 99999999).distinct().limit(N).forEach(
                coupons -> issueCoupons.add(
                        Coupons.builder()
                                .couponNo(prefix+String.format("%08d", coupons))
                                .state(Code.STATE_I.getCode())
                                .payFlag(Code.N.getCode())
                                .useFlag(Code.N.getCode())
                                .expired(expired)
                                .build()
                )
        );

        //저장된 개수
        int saveSize = couponsRepository.saveAll(issueCoupons).size();

        //쿠폰 리스트 개수 체크
        if(N != saveSize)
            throw new KakaoPayException(Code.ERROR_ISSUE);

        return saveSize;
    }

    /**
     * 쿠폰 지급 [발행된 쿠폰이 있을 경우 한 건 지급 처리]
     * @return 지급된 쿠폰
     */
    @Transactional
    public CouponsDto pay() {

        List<Coupons> coupons = couponsRepository.findByStateAndPayFlagAndUseFlagAndExpiredGreaterThan(
                Code.STATE_I.getCode(), Code.N.getCode(), Code.N.getCode(), getToday());

        //지급 가능한 쿠폰 없음
        if(CollectionUtils.isEmpty(coupons))
            throw new KakaoPayException(Code.ERROR_NOT_EXIST_PAY_COUPON);

        Coupons payCoupon = coupons.get(0);
        payCoupon.updatePayCoupon();
        return new CouponsDto(payCoupon);
    }

    /**
     * 지급된 쿠폰 조회
     * @return 조회된 쿠폰리스트
     */
    @Transactional(readOnly = true)
    public List<CouponsDto> searchPayCoupons() {
        return couponsRepository.findByPayFlagOrderByIdAsc(Code.Y.getCode()).stream()
                .map(CouponsDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 쿠폰 사용처리
     * @param couponNo 쿠폰번호
     * @return 사용처리된 쿠폰
     */
    @Transactional
    public CouponsDto use(String couponNo) {

        Coupons useCoupon =  getCoupon(couponNo);

        //조회되는 쿠폰이 사용상태인지 확인
        if(Code.STATE_U.getCode().equals(useCoupon.getState()) || Code.Y.getCode().equals(useCoupon.getUseFlag()))
            throw new KakaoPayException(Code.ERROR_USED_COUPON);

        useCoupon.updateUseCoupon();
        return new CouponsDto(useCoupon);
    }

    /**
     * 쿠폰 사용취소처리
     * @param couponNo 쿠폰번호
     * @return 취소처리된 쿠폰
     */
    @Transactional
    public CouponsDto useCancel(String couponNo) {

        Coupons cancelCoupon =  getCoupon(couponNo);

        //사용된적 없는 쿠폰인지 확인
        if(Code.STATE_I.getCode().equals(cancelCoupon.getState()))
            throw new KakaoPayException(Code.ERROR_NOT_USED_COUPON);
        //조회되는 쿠폰이 사용취소 상태인지 확인
        if(Code.STATE_C.getCode().equals(cancelCoupon.getState()) || Code.N.getCode().equals(cancelCoupon.getUseFlag()))
            throw new KakaoPayException(Code.ERROR_USE_CANCELED_COUPON);

        cancelCoupon.updateUseCancelCoupon();
        return new CouponsDto(cancelCoupon);
    }

    /**
     * 금일 만료된 쿠폰 조회
     * @return 조회된 쿠폰 리스트
     */
    @Transactional(readOnly = true)
    public List<CouponsDto> expiredCoupons() {
        return couponsRepository.findByExpired(getToday()).stream()
                .map(CouponsDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 오늘 년/월/일 리턴 [yyyyMMdd]
     * @return String.class [8자리]
     */
    private String getToday() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(Code.yyyyMMdd.getCode()));
    }

    /**
     * 쿠폰 조회 
     * @param couponNo 쿠폰번호
     * @return 조회된 쿠폰
     */
    private Coupons getCoupon(String couponNo) {

        Coupons coupon =  couponsRepository.findByCouponNo(couponNo);

        //조회되는 쿠폰이 존재하는지 확인
        if(ObjectUtils.isEmpty(coupon))
            throw new KakaoPayException(Code.ERROR_NOT_EXIST_USE_COUPON);
        //지급된 쿠폰인지 확인
        if(Code.N.getCode().equals(coupon.getPayFlag()))
            throw new KakaoPayException(Code.ERROR_NOT_PAYED_COUPON);
        //만료된 쿠폰인지 확인
        if(Integer.parseInt(getToday()) >= Integer.parseInt(coupon.getExpired()))
            throw new KakaoPayException(Code.ERROR_EXPIRED_COUPON);

        return coupon;
    }
}