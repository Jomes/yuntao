package com.yuntao.mode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by jomeslu on 15-8-24.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Homepic extends  BaseResponse {

    private int state;
    private String content;
    private String message;

    private String advPicUrl;
    private String advJumpUrl;

    public String getAdvPicUrl() {
        return advPicUrl;
    }

    public void setAdvPicUrl(String advPicUrl) {
        this.advPicUrl = advPicUrl;
    }

    public String getAdvJumpUrl() {
        return advJumpUrl;
    }

    public void setAdvJumpUrl(String advJumpUrl) {
        this.advJumpUrl = advJumpUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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
