package com.bharatiyajob.bharatiyajob.Json.Company.CompanyJobList;

import java.util.List;

public class CompanyJobListResponse {
    private String status;
    private List<CompanyJobListData> data;

    public String getStatus() {
        return status;
    }

    public List<CompanyJobListData> getData() {
        return data;
    }
}
