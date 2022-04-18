package org.trust.support.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductInfoDTO implements Serializable {

    private long id;
    private String productId;
    private String productName;
    private String pictureUrl;
    private BigDecimal productPrice;
    private Integer tag;
}
