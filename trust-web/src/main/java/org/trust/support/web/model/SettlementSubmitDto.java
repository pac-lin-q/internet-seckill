package org.trust.support.web.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class SettlementSubmitDto implements Serializable {

    private String code;
    private String message;
    private String payPageUrl;

    public void SettlementInitDTO(String code, String message, String payPageUrl){
        this.code=code;
        this.message=message;
        this.payPageUrl=payPageUrl;
    }
}
