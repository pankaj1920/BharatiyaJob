package com.bharatiyajob.bharatiyajob.Json.SubscriptionPackage;

import java.util.List;

public class SubscriptionResponse {

    private String status;
    private List<SubscriptionData> data;

    public String getStatus() {
        return status;
    }

    public List<SubscriptionData> getData() {
        return data;
    }
}
