package com.bharatiyajob.bharatiyajob.Json.Candidate.Login;

public class    LoginOtpResponse {

    String status;
    String message;
    String id;
    String name;
    String email;
    String mobile;
    String reg_type;
    String token;

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

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getReg_type() {
        return reg_type;
    }

    public void setReg_type(String reg_type) {
        this.reg_type = reg_type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
