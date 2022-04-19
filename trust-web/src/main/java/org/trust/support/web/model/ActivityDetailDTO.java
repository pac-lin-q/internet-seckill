package org.trust.support.web.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ActivityDetailDTO implements Serializable {

    private String productName;
    private String productPrice;
    private String productPictureUrl;
    private Integer isAvailable;
}
