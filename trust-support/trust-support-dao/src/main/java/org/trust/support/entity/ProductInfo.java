package org.trust.support.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfo {
    private long id;
    private String productId;
    private String productName;
    private String pictureUrl;
    private BigDecimal productPrice;
    private Integer tag;
}
