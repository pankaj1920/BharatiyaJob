package com.bharatiyajob.bharatiyajob.Json.SubscriptionPackage;

public class SubscriptionData {

    private String package_id;
    private String packagetype;
    private String packagecategory;
    private String features;
    private String cost;
    private String days;
    private String datetime;

    public SubscriptionData(String package_id, String packagetype, String packagecategory, String features, String cost, String days, String datetime) {
        this.package_id = package_id;
        this.packagetype = packagetype;
        this.packagecategory = packagecategory;
        this.features = features;
        this.cost = cost;
        this.days = days;
        this.datetime = datetime;
    }

    public String getPackage_id() {
        return package_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }

    public String getPackagetype() {
        return packagetype;
    }

    public void setPackagetype(String packagetype) {
        this.packagetype = packagetype;
    }

    public String getPackagecategory() {
        return packagecategory;
    }

    public void setPackagecategory(String packagecategory) {
        this.packagecategory = packagecategory;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
