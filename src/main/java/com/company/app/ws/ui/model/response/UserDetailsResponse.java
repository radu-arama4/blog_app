package com.company.app.ws.ui.model.response;

public class UserDetailsResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean privateOrNot;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPrivateOrNot() {
        return privateOrNot;
    }

    public void setPrivateOrNot(boolean privateOrNot) {
        this.privateOrNot = privateOrNot;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
