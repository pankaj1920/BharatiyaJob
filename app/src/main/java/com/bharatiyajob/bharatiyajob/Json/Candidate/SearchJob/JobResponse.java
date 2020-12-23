package com.bharatiyajob.bharatiyajob.Json.Candidate.SearchJob;

import java.util.List;

public class JobResponse {

    List<JobData> data;
    String status;

    public List<JobData> getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }
}
