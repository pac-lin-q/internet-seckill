package org.trust.web.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDetailDTO implements Serializable {

    private String productName;
    private String productPrice;
    private String productPictureUrl;
    private Integer tag;
    private Integer isAvailable;
}
