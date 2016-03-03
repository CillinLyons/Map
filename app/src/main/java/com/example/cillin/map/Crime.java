package com.example.cillin.map;

import android.content.ContentValues;
import android.widget.DatePicker;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * Created by Cillin on 27/01/2016.
 */
public class Crime
{
    /**
     * Item county
     */
    @com.google.gson.annotations.SerializedName("county")
    private String mCounty;

    /**
     * Item area
     */
    @com.google.gson.annotations.SerializedName("neighborhood")
    private String mNeighborhood;

    /**
     * Item crime
     */
    @com.google.gson.annotations.SerializedName("crime")
    private String mCrime;

    /**
     * Item time
     */
    @com.google.gson.annotations.SerializedName("time")
    private String mTime;

    /**
     * Item time
     */
    @com.google.gson.annotations.SerializedName("date")
    private Date mDate;


    /**
     * Item Id
     */
    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    /**
     * ToDoItem constructor
     */
    public Crime() {

    }

    @Override
    public String toString() {
        return getCounty();
    }

    /**
     * Initializes a new ToDoItem
     */
    public Crime(String county, String neighborhood, String crime, String time, String date, String id) {
        this.setCounty(county);
        this.setNeighborhood(neighborhood);
        this.setCrime(crime);
        this.setCrime(time);
        this.setCrime(date);
        this.setId(id);
    }

    /**
     * Returns the item text
     */
    public String getCounty() {
        return mCounty;
    }

    public String getNeighborhood() {
        return mNeighborhood;
    }

    public String getCrime() {
        return mCrime;
    }

    /**

     */
    public final void setCounty(String county) {
        mCounty = county;
    }

    public final void setNeighborhood(String neighborhood) {mNeighborhood = neighborhood;}

    public final void setCrime(String crime) {mCrime = crime;}

    public final void setTime(String time) {mTime = time;}

    public final void setDate(Date date) {mDate = date;}

    /**
     * Returns the item id
     */
    public String getId() {
        return mId;
    }

    /**

     */
    public final void setId(String id) {
        mId = id;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Crime && ((Crime) o).mId == mId;
    }
}
