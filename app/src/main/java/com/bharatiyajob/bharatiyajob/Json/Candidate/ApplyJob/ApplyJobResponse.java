package com.bharatiyajob.bharatiyajob.Json.Candidate.ApplyJob;

public class ApplyJobResponse {
    private String status;
    private String message;

    public ApplyJobResponse(String status, String message) {
        this.status = status;
        this.message = message;
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
}
