package org.trust.support.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SettlementOrderDTO implements Serializable {
    private Integer payType;
    private String productId;
    private Integer buyNum;
    private String address;
    private String userId;
}
