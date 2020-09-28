package com.bharatiyajob.bharatiyajob.Json.Company.GetCompanyDetails;

public class GetCompanyDetailData {
    private String company_id;
    private String company_name;
    private String GST_registered;
    private String type_of_firm;
    private String gstno;
    private String gst_certificate;
    private String firm_email;
    private String firm_phone;
    private String address;
    private String city;
    private String state;
    private String country;
    private String owner_name;
    private String aadharno;
    private String owner_mobile;
    private String weburl;

    public GetCompanyDetailData(String company_id, String company_name, String GST_registered, String type_of_firm, String gstno, String gst_certificate, String firm_email, String firm_phone, String address, String city, String state, String country, String owner_name, String aadharno, String owner_mobile, String weburl) {
        this.company_id = company_id;
        this.company_name = company_name;
        this.GST_registered = GST_registered;
        this.type_of_firm = type_of_firm;
        this.gstno = gstno;
        this.gst_certificate = gst_certificate;
        this.firm_email = firm_email;
        this.firm_phone = firm_phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.owner_name = owner_name;
        this.aadharno = aadharno;
        this.owner_mobile = owner_mobile;
        this.weburl = weburl;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getGST_registered() {
        return GST_registered;
    }

    public void setGST_registered(String GST_registered) {
        this.GST_registered = GST_registered;
    }

    public String getType_of_firm() {
        return type_of_firm;
    }

    public void setType_of_firm(String type_of_firm) {
        this.type_of_firm = type_of_firm;
    }

    public String getGstno() {
        return gstno;
    }

    public void setGstno(String gstno) {
        this.gstno = gstno;
    }

    public String getGst_certificate() {
        return gst_certificate;
    }

    public void setGst_certificate(String gst_certificate) {
        this.gst_certificate = gst_certificate;
    }

    public String getFirm_email() {
        return firm_email;
    }

    public void setFirm_email(String firm_email) {
        this.firm_email = firm_email;
    }

    public String getFirm_phone() {
        return firm_phone;
    }

    public void setFirm_phone(String firm_phone) {
        this.firm_phone = firm_phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getAadharno() {
        return aadharno;
    }

    public void setAadharno(String aadharno) {
        this.aadharno = aadharno;
    }

    public String getOwner_mobile() {
        return owner_mobile;
    }

    public void setOwner_mobile(String owner_mobile) {
        this.owner_mobile = owner_mobile;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }
}
