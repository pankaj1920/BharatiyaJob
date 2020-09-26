package com.bharatiyajob.bharatiyajob.Json.Company.GetBookmarkedCanList;

import java.util.List;

public class GetBookMarkedCandidateResponse {
    private String status;
    private List<GetBookMarkedCandidate> data;

    public GetBookMarkedCandidateResponse(String status, List<GetBookMarkedCandidate> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public List<GetBookMarkedCandidate> getData() {
        return data;
    }


}
