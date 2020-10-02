package com.bharatiyajob.bharatiyajob.Json.Candidate.Login;

public class LoginOtpResponse {

    boolean error;
    String id;
    String name;
    String email;
    String mobile;
    String reg_type;
    String token;

    public LoginOtpResponse(boolean error, String id, String name, String email, String mobile, String reg_type, String token) {
        this.error = error;
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.reg_type = reg_type;
        this.token = token;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
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
