package org.trust.support.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SettlementDataDTO implements Serializable {
    private Integer payType;
    private BigDecimal totalPrice;
    private String address;
    private String assets;
}
