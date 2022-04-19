package org.trust.web.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SettlementInitDTO implements Serializable {

    private String productId;
    private String productPrice;
    private String productPictureUrl;
    private String activityName;
    private String limitNum;
    private String payType;
    private String address;
    private String totalPrice;

}
