package com.bharatiyajob.bharatiyajob.Json.Company.GetCompanyDetails;

public class GetCompanyDetailsResponse {
    private String status;
    private GetCompanyDetailData data;

    public GetCompanyDetailsResponse(String status, GetCompanyDetailData data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GetCompanyDetailData getData() {
        return data;
    }

    public void setData(GetCompanyDetailData data) {
        this.data = data;
    }
}
