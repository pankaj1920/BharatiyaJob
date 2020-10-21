package com.bharatiyajob.bharatiyajob.Json.Candidate.JobDetails;

public class JobDetailsResponse {
    private String status;
    private  JobDetailsData data;

    public JobDetailsResponse(String status, JobDetailsData data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JobDetailsData getData() {
        return data;
    }

    public void setData(JobDetailsData data) {
        this.data = data;
    }
}
