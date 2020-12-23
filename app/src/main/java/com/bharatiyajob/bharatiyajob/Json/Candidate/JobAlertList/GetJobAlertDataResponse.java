package com.bharatiyajob.bharatiyajob.Json.Candidate.JobAlertList;

import java.util.List;

public class GetJobAlertDataResponse {
    private String status;
    private List<GetJobAlertData> data;

    public String getStatus() {
        return status;
    }

    public List<GetJobAlertData> getData() {
        return data;
    }
}
