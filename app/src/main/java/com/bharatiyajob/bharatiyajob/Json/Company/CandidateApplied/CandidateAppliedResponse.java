package com.bharatiyajob.bharatiyajob.Json.Company.CandidateApplied;

import java.util.List;

public class CandidateAppliedResponse {
    private String status;
    private List<CandidateAppliedData> data;

    public CandidateAppliedResponse(String status, List<CandidateAppliedData> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CandidateAppliedData> getData() {
        return data;
    }

    public void setData(List<CandidateAppliedData> data) {
        this.data = data;
    }
}
