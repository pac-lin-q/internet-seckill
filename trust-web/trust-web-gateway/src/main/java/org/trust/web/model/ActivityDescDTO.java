package org.trust.web.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ActivityDescDTO {
    private String activityName;
    private String productId;
    private String activityStartStr;
    private String activityEndStr;
    private Integer limitNum;
    private Integer stockNum;
    private BigDecimal activityPrice;
    private String statusStr;
}
