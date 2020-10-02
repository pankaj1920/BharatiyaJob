package com.bharatiyajob.bharatiyajob.Json.Company.CompanyJobPosted;

import java.util.List;

public class CompanyJobPostedResponse {

    private String status;
    private List<CompanyJobPostData> data;

    public CompanyJobPostedResponse(String status, List<CompanyJobPostData> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CompanyJobPostData> getData() {
        return data;
    }

    public void setData(List<CompanyJobPostData> data) {
        this.data = data;
    }
}
