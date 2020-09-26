package com.bharatiyajob.bharatiyajob.Json.Company.CompanyJobList;

import java.util.List;

public class CompanyJobListResponse {
    private String status;
    private List<CompanyJobListData> data;

    public CompanyJobListResponse(String status, List<CompanyJobListData> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CompanyJobListData> getData() {
        return data;
    }

    public void setData(List<CompanyJobListData> data) {
        this.data = data;
    }
}
