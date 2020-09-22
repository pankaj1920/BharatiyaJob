package com.bharatiyajob.bharatiyajob.Json.Candidate.GetUserDetails;

public class GetUserDetailResponse {
    private String status;
    GetUserDetailData data;

    public GetUserDetailResponse(String status, GetUserDetailData data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GetUserDetailData getData() {
        return data;
    }

    public void setData(GetUserDetailData data) {
        this.data = data;
    }
}
