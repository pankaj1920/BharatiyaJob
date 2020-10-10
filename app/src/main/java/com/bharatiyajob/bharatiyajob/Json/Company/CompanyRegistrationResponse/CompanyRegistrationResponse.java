package com.bharatiyajob.bharatiyajob.Json.Company.CompanyRegistrationResponse;

public class CompanyRegistrationResponse {
    private String error;
    private String message;

    public CompanyRegistrationResponse(String error, String message) {
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
