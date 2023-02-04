package com.example.krishi.data;

import com.google.gson.annotations.SerializedName;

public class News {
    @SerializedName("title")
    private String title;

    @SerializedName("urlToImage")
    private String urlToImage;

    @SerializedName("url")
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
