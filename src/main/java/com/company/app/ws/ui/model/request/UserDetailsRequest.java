package com.company.app.ws.ui.model.request;

public class UserDetailsRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int age;
    private boolean privateOrNot;

    public UserDetailsRequest(String firstName, String lastName, String email, String password, int age,
                              boolean privateOrNot) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.age = age;
        this.privateOrNot = privateOrNot;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isPrivateOrNot() {
        return privateOrNot;
    }

    public void setPrivateOrNot(boolean privateOrNot) {
        this.privateOrNot = privateOrNot;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " +
                email + " " + password + " " + age + " " + privateOrNot;
    }

}
