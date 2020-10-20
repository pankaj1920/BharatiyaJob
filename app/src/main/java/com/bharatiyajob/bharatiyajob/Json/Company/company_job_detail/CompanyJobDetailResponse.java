package com.bharatiyajob.bharatiyajob.Json.Company.company_job_detail;

public class CompanyJobDetailResponse {
    private String status;
    private  CompanyJobDetailData data;

    public CompanyJobDetailResponse(String status, CompanyJobDetailData data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CompanyJobDetailData getData() {
        return data;
    }

    public void setData(CompanyJobDetailData data) {
        this.data = data;
    }
}
