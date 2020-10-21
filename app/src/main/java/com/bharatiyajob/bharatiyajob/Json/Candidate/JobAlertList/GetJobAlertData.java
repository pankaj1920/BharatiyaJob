package com.bharatiyajob.bharatiyajob.Json.Candidate.JobAlertList;

public class GetJobAlertData {

    private String alert_name;
    private String location;
    private String experience;
    private String annual_salary;
    private String functional_area;
    private String industry;
    private String datetime;
    private String alert_id;

    public GetJobAlertData(String alert_name, String location, String experience, String annual_salary, String functional_area, String industry, String datetime, String alert_id) {
        this.alert_name = alert_name;
        this.location = location;
        this.experience = experience;
        this.annual_salary = annual_salary;
        this.functional_area = functional_area;
        this.industry = industry;
        this.datetime = datetime;
        this.alert_id = alert_id;
    }

    public String getAlert_name() {
        return alert_name;
    }

    public void setAlert_name(String alert_name) {
        this.alert_name = alert_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAnnual_salary() {
        return annual_salary;
    }

    public void setAnnual_salary(String annual_salary) {
        this.annual_salary = annual_salary;
    }

    public String getFunctional_area() {
        return functional_area;
    }

    public void setFunctional_area(String functional_area) {
        this.functional_area = functional_area;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getAlert_id() {
        return alert_id;
    }

    public void setAlert_id(String alert_id) {
        this.alert_id = alert_id;
    }
}
