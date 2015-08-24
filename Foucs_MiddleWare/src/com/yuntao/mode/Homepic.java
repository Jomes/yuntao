package com.yuntao.mode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by jomeslu on 15-8-24.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Homepic extends  BaseResponse {

    private int state;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getState() {

        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
