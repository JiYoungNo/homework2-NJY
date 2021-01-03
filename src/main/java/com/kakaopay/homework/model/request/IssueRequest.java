package com.kakaopay.homework.model.request;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 발행 요청 객체
 * @author : JiYoung
 **/

@ToString
@Getter
public class IssueRequest {

    @Max(10000000)
    @Min(1)
    @NotNull
    private Integer number;
}
