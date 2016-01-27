package com.example.cillin.map;

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
     * Item compass
     */
    @com.google.gson.annotations.SerializedName("compass")
    private String mCompass;

    /**
     * Item area
     */
    @com.google.gson.annotations.SerializedName("area")
    private String mArea;

    /**
     * Item crime
     */
    @com.google.gson.annotations.SerializedName("crime")
    private String mCrime;


    /**
     * Item Id
     */
    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    /**
     * Indicates if the item is completed
     */
    @com.google.gson.annotations.SerializedName("complete")
    private boolean mComplete;

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
    public Crime(String county, String compass, String area, String crime, String id) {
        this.setCounty(county);
        this.setCompass(compass);
        this.setArea(area);
        this.setCrime(crime);
        this.setId(id);
    }

    /**
     * Returns the item text
     */
    public String getCounty() {
        return mCounty;
    }

    public String getCompass() {
        return mCompass;
    }

    public String getArea() {
        return mArea;
    }

    public String getCrime() {
        return mCrime;
    }

    /**

     */
    public final void setCounty(String county) {
        mCounty = county;
    }

    public final void setCompass(String compass) {
        mCompass = compass;
    }

    public final void setArea(String area) {
        mArea = area;
    }

    public final void setCrime(String crime) {
        mCrime = crime;
    }

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

    /**
     * Indicates if the item is marked as completed
     */
    public boolean isComplete() {
        return mComplete;
    }

    /**
     * Marks the item as completed or incompleted
     */
    public void setComplete(boolean complete) {
        mComplete = complete;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Crime && ((Crime) o).mId == mId;
    }
}
