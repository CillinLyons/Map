package com.example.cillin.map;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * The Crime2 class represents the crime table.
 * The toString method in this class returns the neighborhood value from the crime table
 * which is how it differs from the crime class.
 */
public class Crime2
{
    /**
     * The following are a list of the columns in the crime table.
     * Item county
     */
    @com.google.gson.annotations.SerializedName("county")
    private String mCounty;

    /**
     * Item neighbourhood
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
     * Item date
     */
    @com.google.gson.annotations.SerializedName("date")
    private Date mDate;

    /**
     * Item Id
     */
    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    /**
     * Crime2 constructor
     */
    public Crime2() {

    }

    /**
     * toString calls the getCounty function to return the neighborhood value
     * from the crime table
     */
    @Override
    public String toString() {
        return getNeighborhood();
    }

    /**
     * Initializes a new Crime
     */
    public Crime2(String county, String crime, String time, String date, String neighborhood, String id) {
        this.setCounty(county);
        this.setCrime(crime);
        this.setCrime(time);
        this.setCrime(date);
        this.setNeighborhood(neighborhood);
        this.setId(id);
    }

    /**
     * getId retrieves the id value from crime
     */
    public String getId() {
        return mId;
    }

    /**
     * getCounty retrieves the county value from crime
     */
    public String getCounty() {
        return mCounty;
    }

    /**
     * getNeighborhood retrieves the neighborhood value from crime
     */
    public String getNeighborhood() {
        return mNeighborhood;
    }

    /**
     * getCrime retrieves the crime value from crime
     */
    public String getCrime() {
        return mCrime;
    }

    /**
     * @param county
     * setCounty can be used to set a value to the county column
     */
    public final void setCounty(String county) {
        mCounty = county;
    }

    /**
     * @param neighborhood
     * setNeighborhood can be used to set a value to the neighborhood column
     */
    public final void setNeighborhood(String neighborhood) {mNeighborhood = neighborhood;}

    /**
     * @param crime
     * setCrime can be used to set a value to the crime column
     */
    public final void setCrime(String crime) {mCrime = crime;}

    /**
     * @param time
     * setTime can be used to set a value to the time column
     */
    public final void setTime(String time) {mTime = time;}

    /**
     * @param date
     * setDate can be used to set a value to the date column
     */
    public final void setDate(Date date) {mDate = date;}

    /**
     * @param id
     * setId can be used to set a value to the id column
     */
    public final void setId(String id) {
        mId = id;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Crime2 && ((Crime2) o).mId == mId;
    }
}

