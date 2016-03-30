package com.example.cillin.map;

/**
 * Created by Cillin on 16/03/2016.
 */
import java.sql.Timestamp;
import java.util.Date;

public class UserMembership
{
    /**
     * Item county
     */
    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    /**
     * Item area
     */
    @com.google.gson.annotations.SerializedName("_createdAt")
    private Date mCreated;

    /**
     * Item crime
     */
    @com.google.gson.annotations.SerializedName("_updatedAt")
    private Date mUpdated;

    /**
     * Item time
     */
    @com.google.gson.annotations.SerializedName("_version")
    private Timestamp mVersion;

    /**
     * Item time
     */
    @com.google.gson.annotations.SerializedName("_deleted")
    private Boolean mDeleted;

    /**
     * Item Id
     */
    @com.google.gson.annotations.SerializedName("username")
    private String mUsername;

    /**
     * Item Id
     */
    @com.google.gson.annotations.SerializedName("password")
    private String mPassword;

    /**
     * Item Id
     */
    @com.google.gson.annotations.SerializedName("salt")
    private String mSalt;

    /**
     * Item Id
     */
    @com.google.gson.annotations.SerializedName("neighbourhood")
    private String mNeighborhood;

    /**
     * Item Id
     */
    @com.google.gson.annotations.SerializedName("membership")
    private String mMember;

    /**
     * Item Id
     */
    @com.google.gson.annotations.SerializedName("email")
    private String mEmail;

    /**
     * ToDoItem constructor
     */
    public UserMembership() {

    }

    @Override
    public String toString() {
        return getMembership();
    }

    public String getMembership() {
        return mMember;
    }

    public final void setMembership(String member) {mMember = member;}


    @Override
    public boolean equals(Object o) {
        return o instanceof UserMembership && ((UserMembership) o).mId == mId;
    }
}
