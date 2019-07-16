package com.bjpowernode.seckill.commons;

import java.io.Serializable;

public class CommonsReturnObject implements Serializable {

    private static final long serialVersionUID = 326257344007822118L;

    private String code;

    private String errorMessage;

    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
