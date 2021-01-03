package com.kakaopay.homework.web;

import com.kakaopay.homework.code.Code;
import com.kakaopay.homework.model.response.CouponListResponse;
import com.kakaopay.homework.model.response.CouponResponse;
import com.kakaopay.homework.model.response.IssueResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * REST API 테스트
 * @author : JiYoung
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CouponControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void 발행_지급_사용_사용취소_성공한다() {
        발행_성공한다(10);
        사용취소_성공한다(사용_성공한다(지급_성공한다()));
    }

    @Test
    public void 쿠폰은_재사용할수없다() {
        발행_성공한다(1);
        재사용요청으로_실패한다(사용_성공한다(지급_성공한다()));
    }

    @Test
    public void 사용취소된_쿠폰은_재사용할수있다() {
        발행_성공한다(1);
        사용_성공한다(사용취소_성공한다(사용_성공한다(지급_성공한다())));
    }

    @Test
    public void 지급된_쿠폰을_조회한다() {

        //지급된 쿠폰 번호
        발행_성공한다(1);
        String couponNo = 지급_성공한다();

        //지급 리스트 조회
        ResponseEntity<CouponListResponse> payedRes = payedCouponsTest();
        assertThat(true).isEqualTo(!ObjectUtils.isEmpty(payedRes.getBody()));
        checkSucess(payedRes.getStatusCode(), payedRes.getBody().getResultCode());
        assertThat(couponNo).isEqualTo(payedRes.getBody().getList().get(payedRes.getBody().getList().size()-1).getCouponNo());
    }

    @Test
    public void 금일_만료된_쿠폰을조회한다() {
        ResponseEntity<CouponListResponse> expiredRes = expiredCouponsTest();
        assertThat(true).isEqualTo(!ObjectUtils.isEmpty(expiredRes.getBody()));
        checkSucess(expiredRes.getStatusCode(), expiredRes.getBody().getResultCode());
    }

    private void 재사용요청으로_실패한다(String couponNo) {
        //재사용
        ResponseEntity<CouponResponse> useResponseRePlay = useTest(couponNo);
        assertThat(true).isEqualTo(!ObjectUtils.isEmpty(useResponseRePlay.getBody()));
        assertThat(HttpStatus.INTERNAL_SERVER_ERROR).isEqualTo(useResponseRePlay.getStatusCode());
        assertThat(Code.ERROR_USED_COUPON.getCode()).isEqualTo(useResponseRePlay.getBody().getResultCode());
    }

    private String 사용취소_성공한다(String couponNo) {
        //사용 취소
        ResponseEntity<CouponResponse> useCancelRes = useCancelTest(couponNo);
        assertThat(true).isEqualTo(!ObjectUtils.isEmpty(useCancelRes.getBody()));
        checkSucess(useCancelRes.getStatusCode(), useCancelRes.getBody().getResultCode());

        return useCancelRes.getBody().getCoupon().getCouponNo();
    }

    private String 사용_성공한다(String couponNo) {
        //사용
        ResponseEntity<CouponResponse> useRes = useTest(couponNo);
        assertThat(true).isEqualTo(!ObjectUtils.isEmpty(useRes.getBody()));
        checkSucess(useRes.getStatusCode(), useRes.getBody().getResultCode());
        return  useRes.getBody().getCoupon().getCouponNo();
    }

    private String 지급_성공한다() {
        //지급
        ResponseEntity<CouponResponse> payRes = payTest();
        assertThat(true).isEqualTo(!ObjectUtils.isEmpty(payRes.getBody()));
        checkSucess(payRes.getStatusCode(), payRes.getBody().getResultCode());
        return payRes.getBody().getCoupon().getCouponNo();
    }

    private void 발행_성공한다(int number) {
        //발행
        ResponseEntity<IssueResponse> issueRes = issueTest(number);
        assertThat(true).isEqualTo(!ObjectUtils.isEmpty(issueRes.getBody()));
        checkSucess(issueRes.getStatusCode(), issueRes.getBody().getResultCode());
    }

    private void checkSucess(HttpStatus status, String resultCode) {
        assertThat(HttpStatus.OK).isEqualTo(status);
        assertThat(Code.SUCCESS.getCode()).isEqualTo(resultCode);
    }

    private ResponseEntity<IssueResponse> issueTest(int number) {
        Map<String, Integer> issueReq = new HashMap<>();
        issueReq.put("number", number);
        return this.restTemplate.postForEntity("/api/issue", issueReq, IssueResponse.class);
    }

    private ResponseEntity<CouponResponse> payTest() {
        return this.restTemplate.postForEntity("/api/pay", null, CouponResponse.class);
    }

    private ResponseEntity<CouponListResponse> payedCouponsTest() {
        return this.restTemplate.getForEntity("/api/search/payed", CouponListResponse.class);
    }

    private ResponseEntity<CouponResponse> useTest(String couponNo) {
        Map<String, String> useReq = new HashMap<>();
        useReq.put("couponNo", couponNo);
        return this.restTemplate.postForEntity("/api/use", useReq, CouponResponse.class);
    }

    private ResponseEntity<CouponResponse> useCancelTest(String couponNo) {
        Map<String, String> useCancelReq = new HashMap<>();
        useCancelReq.put("couponNo", couponNo);
        return this.restTemplate.postForEntity("/api/use/cancel", useCancelReq, CouponResponse.class);
    }

    private ResponseEntity<CouponListResponse> expiredCouponsTest() {
        return this.restTemplate.getForEntity("/api/search/expired", CouponListResponse.class);
    }
}