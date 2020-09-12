package com.bharatiyajob.bharatiyajob.Json.JobDetails;

import java.util.List;

public class JobDetailsResponse {
    private String status;
    private List<JobDetailsData> data;

    public JobDetailsResponse(String status, List<JobDetailsData> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<JobDetailsData> getData() {
        return data;
    }

    public void setData(List<JobDetailsData> data) {
        this.data = data;
    }
}
