package com.bharatiyajob.bharatiyajob.Json.Candidate.Register;

public class MobileRegisterResponse {

    private String can_id;
    private String error;
    private String message;

    public MobileRegisterResponse(String can_id, String error, String message) {
        this.can_id = can_id;
        this.error = error;
        this.message = message;
    }

    public String getCan_id() {
        return can_id;
    }

    public void setCan_id(String can_id) {
        this.can_id = can_id;
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
