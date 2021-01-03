package com.kakaopay.homework.web;

import com.kakaopay.homework.code.Code;
import com.kakaopay.homework.model.request.CouponRequest;
import com.kakaopay.homework.model.request.IssueRequest;
import com.kakaopay.homework.model.response.CouponListResponse;
import com.kakaopay.homework.model.response.CouponResponse;
import com.kakaopay.homework.model.response.IssueResponse;
import com.kakaopay.homework.model.response.KakaoPayResponse;
import com.kakaopay.homework.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 쿠폰 API Controller
 * @author : JiYoung
 **/

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CouponController {

    private final CouponService couponService;

    /**
     * 쿠폰 발행 API
     * @param issueRequest 요청객체[IssueRequest.class]
     * @return IssueResponse.class
     */
    @PostMapping("/issue")
    public KakaoPayResponse issue(@RequestBody @Valid IssueRequest issueRequest) {
        return new IssueResponse(Code.SUCCESS, couponService.issue(issueRequest.getNumber()));
    }

    /**
     * 쿠폰 지급 API[한 건씩 지급한다]
     * @return CouponResponseclass
     */
    @PostMapping("/pay")
    public KakaoPayResponse pay() {
        return new CouponResponse(Code.SUCCESS, couponService.pay());
    }

    /**
     * 지급 쿠폰 조회 API
     * @return CouponListResponse.class
     */
    @GetMapping("/search/payed")
    public KakaoPayResponse searchPayCoupons() {
        return new CouponListResponse(Code.SUCCESS, couponService.searchPayCoupons());
    }

    /**
     * 쿠폰 사용 API[지급된 쿠폰을 사용한다]
     * @param couponRequest 요청객체[CouponRequest.class]
     * @return CouponResponse.class
     */
    @PostMapping("/use")
    public KakaoPayResponse use(@RequestBody @Valid CouponRequest couponRequest) {
        return new CouponResponse(Code.SUCCESS, couponService.use(couponRequest.getCouponNo()));
    }

    /**
     * 쿠폰 사용 취소 API
     * @param couponRequest 요청객체[CouponRequest.class]
     * @return CouponResponse.class
     */
    @PostMapping("/use/cancel")
    public KakaoPayResponse useCancel(@RequestBody @Valid CouponRequest couponRequest){
        return new CouponResponse(Code.SUCCESS, couponService.useCancel(couponRequest.getCouponNo()));
    }

    /**
     * 만료 쿠폰 리스트 조회 API[당일 만료된 쿠폰을 조회한다]
     * @return CouponListResponse.class
     */
    @GetMapping("/search/expired")
    public KakaoPayResponse expiredCoupons() {
        return new CouponListResponse(Code.SUCCESS, couponService.expiredCoupons());
    }
}