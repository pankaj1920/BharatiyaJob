package com.bharatiyajob.bharatiyajob.Json.Register;

public class RegVerifyOtpResponse {
    private String error;
    private String message;

    public RegVerifyOtpResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
