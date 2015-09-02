package com.yuntao.mode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by jomeslu on 15-9-1.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PushMessage extends BaseResponse {

    private String title;
    private String content;
    private String url;
    private int type;

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
