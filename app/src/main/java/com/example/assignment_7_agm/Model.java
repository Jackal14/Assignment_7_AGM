package com.example.assignment_7_agm;

import com.google.gson.annotations.SerializedName;

public class Model {
    //Strings to contain the data we'll be storing in our Json
    @SerializedName("name")
    private String civName; //civName will be the primary key for our map
    @SerializedName("leader")
    private String civLeader;
    @SerializedName("description")
    private String civDescription;

    //Initializer for model
    public Model(final String name, final String year, final String latestConsole) {
        this.civName = name;
        this.civLeader = year;
        this.civDescription = latestConsole;
    }

    //Getters
    public String getName() {return civName;}

    public String getCivLeader() {return civLeader;}

    public String getCivDescription() {return civDescription;}

    //Setters
    public void setName(final String name) {this.civName = name;}

    public void setCivLeader(final String year) {this.civLeader = year;}

    public void setCivDescription(final String civDescription) {this.civDescription = civDescription;}

}
