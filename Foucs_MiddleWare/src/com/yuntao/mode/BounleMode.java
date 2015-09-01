package com.yuntao.mode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by jomeslu on 15-9-1.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BounleMode extends  BaseResponse  {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
