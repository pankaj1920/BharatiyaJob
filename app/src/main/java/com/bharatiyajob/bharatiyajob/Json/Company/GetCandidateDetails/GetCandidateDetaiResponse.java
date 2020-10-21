package com.bharatiyajob.bharatiyajob.Json.Company.GetCandidateDetails;

public class GetCandidateDetaiResponse {
    private String status;
    private GetCandidateDetailData data;

    public GetCandidateDetaiResponse(String status, GetCandidateDetailData data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GetCandidateDetailData getData() {
        return data;
    }

    public void setData(GetCandidateDetailData data) {
        this.data = data;
    }
}
