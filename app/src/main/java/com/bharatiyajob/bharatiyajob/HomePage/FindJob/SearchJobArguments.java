package com.bharatiyajob.bharatiyajob.HomePage.FindJob;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchJobArguments implements Parcelable {

    private String skills;
    private String  location;

    public SearchJobArguments(String skills, String location) {
        this.skills = skills;
        this.location = location;
    }

    protected SearchJobArguments(Parcel in) {
        skills = in.readString();
        location = in.readString();
    }

    public static final Creator<SearchJobArguments> CREATOR = new Creator<SearchJobArguments>() {
        @Override
        public SearchJobArguments createFromParcel(Parcel in) {
            return new SearchJobArguments(in);
        }

        @Override
        public SearchJobArguments[] newArray(int size) {
            return new SearchJobArguments[size];
        }
    };

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(skills);
        parcel.writeString(location);
    }
}
