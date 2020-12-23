package com.bharatiyajob.bharatiyajob.Json.Company.CompanyJobPosted;

import java.util.List;

public class CompanyJobPostedResponse {

    private String status;
    private List<CompanyJobPostData> data;

    public String getStatus() {
        return status;
    }

    public List<CompanyJobPostData> getData() {
        return data;
    }
}
