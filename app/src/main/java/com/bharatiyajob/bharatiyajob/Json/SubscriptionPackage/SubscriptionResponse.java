package com.bharatiyajob.bharatiyajob.Json.SubscriptionPackage;

import java.util.List;

public class SubscriptionResponse {

    private String status;
    private List<SubscriptionData> data;

    public SubscriptionResponse(String status, List<SubscriptionData> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SubscriptionData> getData() {
        return data;
    }

    public void setData(List<SubscriptionData> data) {
        this.data = data;
    }
}
