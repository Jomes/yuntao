package com.yuntao.mode;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 所有的服务器返回接口model继承自本类
 * 
 * @author lbritney
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1512436581912046915L;
    private int errorCode = 0;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
