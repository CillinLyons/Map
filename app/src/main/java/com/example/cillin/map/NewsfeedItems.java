package com.example.cillin.map;

import android.widget.TextView;

import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Created by Cillin on 04/02/2016.
 */
public class NewsfeedItems
{

    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    @com.google.gson.annotations.SerializedName("username")
    private String mUsername;

    @com.google.gson.annotations.SerializedName("membership")
    private String mMembership;

    @com.google.gson.annotations.SerializedName("neighborhood")
    private String mArea;

    @com.google.gson.annotations.SerializedName("message")
    private String mMessage;

    @com.google.gson.annotations.SerializedName("date")
    private Date mDate;

    @com.google.gson.annotations.SerializedName("location")
    private String mLocation;

    public NewsfeedItems()
    {

    }

    /*public NewsfeedItems(String username, String id) {
        this.setUsername(username);
        this.setId(id);
    }*/


    public String getID()
    {
        return mId;
    }

    public String getUsername()
    {
        return mUsername;
    }

    public String getMembership()
    {
        return mMembership;
    }

    public String getArea()
    {
        return mArea;
    }

    public String getMessage()
    {
        return mMessage;
    }
    public String getLocation()
    {
        return mLocation;
    }

    public Date getDate()
    {
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        /*DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        String reportDate = df.format(today);

        return reportDate;*/
        return mDate;
    }

    public final void setUsername(String username) {mUsername = username;}

    public final void setMembership(String membership) {mMembership = membership;}

    public final void setArea(String area) {mArea = area;}

    public final void setMessage(String message) {mMessage = message;}
    public final void setLocation(String location) {mLocation = location;}
    public final void setDate(Date date)
    {
        /*DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        //String reportDate = df.format(today);*/
        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //Date date = new Date();
        //dateFormat.format(date);
        //return date;

        mDate = date;
    }


    //@Override
    /*public boolean equals(Object o) {
        return o instanceof ToDoItem && ((ToDoItem) o).mId == mId;
    }*/
}
