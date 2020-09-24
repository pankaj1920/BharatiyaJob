package com.bharatiyajob.bharatiyajob.Json.Candidate.ProfileForm;

public class CanProfileResponse {
    private boolean error;
    private String message;

    public CanProfileResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
