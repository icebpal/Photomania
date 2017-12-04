package edu.illinois.finalproject;

/**
 * Created by icebp on 11/28/2017.
 */

public class Image {
    private String username;
    private String url;

    public Image(String username, String url) {
        this.username = username;
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public String getUrl() {
        return url;
    }
}
