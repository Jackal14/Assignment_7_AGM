package com.example.assignment_7_agm;

import com.google.gson.annotations.SerializedName;

public class Model {
    //Strings to contain the data we'll be storing in our Json
    @SerializedName("name")
    private String companyName; //civName will be the primary key for our map
    @SerializedName("year")
    private Integer releaseYear;
    @SerializedName("recentConsole")
    private String recentConsole;

    //Initializer for model
    public Model(final String name, final Integer year, final String latestConsole) {
        this.companyName = name;
        this.releaseYear = year;
        this.recentConsole = latestConsole;
    }

    //Getters
    public String getName() {return companyName;}

    public Integer getYear() {return releaseYear;}

    public String getRecentConsole() {return recentConsole;}

    //Setters
    public void setName(final String name) {this.companyName = name;}

    public void setYear(final Integer year) {this.releaseYear = year;}

    public void setRecentConsole(final String recentConsole) {this.recentConsole = recentConsole;}

}
