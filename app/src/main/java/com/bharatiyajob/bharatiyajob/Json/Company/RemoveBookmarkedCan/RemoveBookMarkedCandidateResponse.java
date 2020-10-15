package com.bharatiyajob.bharatiyajob.Json.Company.RemoveBookmarkedCan;

public class RemoveBookMarkedCandidateResponse {

    private final String status;
    private final String message;

    public RemoveBookMarkedCandidateResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
