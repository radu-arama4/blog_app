package com.company.app.ws.ui.model.request;

public class UpdatePostRequest {
    private String title;
    private String userId;
    private String newTitle;
    private String newContent;

    public UpdatePostRequest(String title, String userId, String newTitle, String newContent) {
        this.title = title;
        this.userId = userId;
        this.newTitle = newTitle;
        this.newContent = newContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    public String getNewContent() {
        return newContent;
    }

    public void setNewContent(String newContent) {
        this.newContent = newContent;
    }
}
