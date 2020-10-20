package com.bharatiyajob.bharatiyajob.Json.Candidate.JobAlertList;

import java.util.List;

public class GetJobAlertDataResponse {
    private String status;
    private List<GetJobAlertData> data;

    public GetJobAlertDataResponse(String status, List<GetJobAlertData> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GetJobAlertData> getData() {
        return data;
    }

    public void setData(List<GetJobAlertData> data) {
        this.data = data;
    }
}
