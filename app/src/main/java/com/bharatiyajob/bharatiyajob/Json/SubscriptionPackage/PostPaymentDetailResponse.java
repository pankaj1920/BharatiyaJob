package com.bharatiyajob.bharatiyajob.Json.SubscriptionPackage;

public class PostPaymentDetailResponse {
    private String  status;
    private String message;

    public PostPaymentDetailResponse(String status, String message) {
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
