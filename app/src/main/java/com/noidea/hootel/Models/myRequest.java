package com.noidea.hootel.Models;

public class myRequest {
    String url;
    String Json;

    public myRequest(String url, String json) {
        this.url = url;
        Json = json;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJson() {
        return Json;
    }

    public void setJson(String json) {
        Json = json;
    }
}
