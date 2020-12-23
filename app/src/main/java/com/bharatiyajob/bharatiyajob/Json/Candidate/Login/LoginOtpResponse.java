package com.bharatiyajob.bharatiyajob.Json.Candidate.Login;

public class    LoginOtpResponse {

    final String status;
    final String message;
    final String id;
    final String name;
    final String email;
    final String mobile;
    final String reg_type;
    final String token;

    public LoginOtpResponse(String status, String message, String id, String name, String email, String mobile, String reg_type, String token) {
        this.status = status;
        this.message = message;
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.reg_type = reg_type;
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getReg_type() {
        return reg_type;
    }

    public String getToken() {
        return token;
    }
}
