package com.fosterpet.backend.notification;

public class ExpoNotification {
    private final String to;
    private final String title;
    private final String body;

    public ExpoNotification(String to, String title, String body) {
        this.to = to;
        this.title = title;
        this.body = body;
    }


    public String getTo() {
        return to;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

}
