package com.bharatiyajob.bharatiyajob.Json.Company.GetBookmarkedCanList;

public class GetBookMarkedCandidate {
    private String candidate_id;
    private String Candidate_name;
    private String Candidate_email;
    private String Candidate_number;
    private String gender;
    private String heighest_qualification;
    private String work_experience;
    private String state;
    private String address;
    private String skills;
    private String profile_pic;
    private String resume;

    public GetBookMarkedCandidate(String candidate_id, String candidate_name, String candidate_email, String candidate_number, String gender, String heighest_qualification, String work_experience, String state, String address, String skills, String profile_pic, String resume) {
        this.candidate_id = candidate_id;
        Candidate_name = candidate_name;
        Candidate_email = candidate_email;
        Candidate_number = candidate_number;
        this.gender = gender;
        this.heighest_qualification = heighest_qualification;
        this.work_experience = work_experience;
        this.state = state;
        this.address = address;
        this.skills = skills;
        this.profile_pic = profile_pic;
        this.resume = resume;
    }

    public String getCandidate_id() {
        return candidate_id;
    }

    public String getCandidate_name() {
        return Candidate_name;
    }

    public String getCandidate_email() {
        return Candidate_email;
    }

    public String getCandidate_number() {
        return Candidate_number;
    }

    public String getGender() {
        return gender;
    }

    public String getHeighest_qualification() {
        return heighest_qualification;
    }

    public String getWork_experience() {
        return work_experience;
    }

    public String getState() {
        return state;
    }

    public String getAddress() {
        return address;
    }

    public String getSkills() {
        return skills;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public String getResume() {
        return resume;
    }
}