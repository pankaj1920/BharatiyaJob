package com.bharatiyajob.bharatiyajob.Json.Company.remove_candidate;

public class RemoveCandidateResponse {
    private String status;
    private String jobstatus;
    private String message;

    public RemoveCandidateResponse(String status, String jobstatus, String message) {
        this.status = status;
        this.jobstatus = jobstatus;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJobstatus() {
        return jobstatus;
    }

    public void setJobstatus(String jobstatus) {
        this.jobstatus = jobstatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
