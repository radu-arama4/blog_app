package com.company.app.ws.ui.model.request;

public class MakePostRequest {
    private String title;
    private String content;

    public MakePostRequest(String title, String content, String userId) {
        this.title = title;
        this.content = content;
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
}
