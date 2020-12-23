package com.bharatiyajob.bharatiyajob.Json.Company.CandidateApplied;

import java.util.List;

public class CandidateAppliedResponse {
    private String status;
    private List<CandidateAppliedData> data;

    public String getStatus() {
        return status;
    }

    public List<CandidateAppliedData> getData() {
        return data;
    }
}
