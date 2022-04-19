package org.trust.web.common;

import lombok.Data;

@Data
public class TrustException extends Exception{

    private String errorCode;

    public TrustException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public TrustException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public TrustException(String message) {
        super(message);
    }
}
