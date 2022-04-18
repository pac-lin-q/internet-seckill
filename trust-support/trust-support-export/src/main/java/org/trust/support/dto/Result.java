package org.trust.support.dto;

import org.trust.support.constant.ResultCodeConstant;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private String code;
    private String message;
    private T data;
    public Result() {
    }

    public Result(T data) {
        this.data = data;
        this.code = ResultCodeConstant.SUCCESS;
    }

    public Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
