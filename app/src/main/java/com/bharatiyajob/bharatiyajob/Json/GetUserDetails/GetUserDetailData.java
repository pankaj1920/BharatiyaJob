package com.bharatiyajob.bharatiyajob.Json.GetUserDetails;

public class GetUserDetailData {

    private String name;
    private String mobile;
    private String email;
    private String full_name;
    private String id;
    private String gender;
    private String heighest_qualification;
    private String work_experience;
    private String state;
    private String address;
    private String membership_plan;
    private String profile_pic;
    private String resume;
    private String skill;


    public GetUserDetailData(String name, String mobile, String email, String full_name, String id, String gender, String heighest_qualification, String work_experience, String state, String address, String membership_plan, String profile_pic, String resume, String skill) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.full_name = full_name;
        this.id = id;
        this.gender = gender;
        this.heighest_qualification = heighest_qualification;
        this.work_experience = work_experience;
        this.state = state;
        this.address = address;
        this.membership_plan = membership_plan;
        this.profile_pic = profile_pic;
        this.resume = resume;
        this.skill = skill;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeighest_qualification() {
        return heighest_qualification;
    }

    public void setHeighest_qualification(String heighest_qualification) {
        this.heighest_qualification = heighest_qualification;
    }

    public String getWork_experience() {
        return work_experience;
    }

    public void setWork_experience(String work_experience) {
        this.work_experience = work_experience;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMembership_plan() {
        return membership_plan;
    }

    public void setMembership_plan(String membership_plan) {
        this.membership_plan = membership_plan;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
