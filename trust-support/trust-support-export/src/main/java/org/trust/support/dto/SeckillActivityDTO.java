package org.trust.support.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class SeckillActivityDTO implements Serializable {
    private long id;
    private String activityName;
    private String productId;
    private Date activityStart;
    private Date activityEnd;
    private Integer limitNum;
    private Integer stockNum;
    private String activityPictureUrl;
    private BigDecimal activityPrice;
    private Integer status;
}
