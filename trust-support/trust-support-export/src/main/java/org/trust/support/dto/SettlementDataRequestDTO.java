package org.trust.support.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SettlementDataRequestDTO implements Serializable {
    private String userId;
    private Integer buyNum;
    private String productId;
}
