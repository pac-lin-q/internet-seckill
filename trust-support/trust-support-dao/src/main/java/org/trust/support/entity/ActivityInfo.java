package org.trust.support.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ActivityInfo {
    private long id;
    private String activityName;
    private String productId;
    private Date activityStart;
    private Date activityEnd;
    private Integer limitNum;
    private Integer stockNum;
    private Integer status;//0:未开始  1：已开始  2：已结束
    private String activityPictureUrl;
    private BigDecimal activityPrice;
}
