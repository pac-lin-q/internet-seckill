package org.trust.web.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDescDTO {
    private String productId;
    private String productName;
    private BigDecimal productPrice;
    private String tagStr;
}
