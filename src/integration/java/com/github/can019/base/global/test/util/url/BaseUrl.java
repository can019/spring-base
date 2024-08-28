package com.github.can019.base.global.test.util.url;

public enum BaseUrl {
    LOCALHOST_URL("http://localhost");

    BaseUrl(String url) {
        this.url = url;
    }

    private String url;

    public String getUrl(){
        return this.url;
    }
}
