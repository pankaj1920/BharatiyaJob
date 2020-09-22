package com.bharatiyajob.bharatiyajob.Json.Candidate.SavedJob;

import java.util.List;

public class BookmarkJobResponse {

   private List<BookmarkJobData> data;
    private String status;

    public BookmarkJobResponse(String status, List<BookmarkJobData> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BookmarkJobData> getData() {
        return data;
    }

    public void setData(List<BookmarkJobData> data) {
        this.data = data;
    }
}
