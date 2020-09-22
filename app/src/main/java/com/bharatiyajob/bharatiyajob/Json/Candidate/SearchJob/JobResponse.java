package com.bharatiyajob.bharatiyajob.Json.Candidate.SearchJob;

import java.util.List;

public class JobResponse {

    List<JobData> data;
    String status;

    public JobResponse(List<JobData> data, String status) {
        this.data = data;
        this.status = status;
    }

    public List<JobData> getData() {
        return data;
    }

    public void setData(List<JobData> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
