package com.bharatiyajob.bharatiyajob.Json.Login;

public class LoginOtpResponse {

    private String error;
    private String id;
    private String name;
    private String email;
    private String mobile;
    private String message;

    public LoginOtpResponse(String error, String id, String name, String email, String mobile, String message) {
        this.error = error;
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.message = message;
    }



    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
