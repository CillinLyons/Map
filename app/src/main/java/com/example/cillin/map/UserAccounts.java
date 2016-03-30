package com.example.cillin.map;

/**
 * Created by Cillin on 16/03/2016.
 */

import java.sql.Timestamp;
import java.util.Date;

public class UserAccounts
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
    public UserAccounts() {

    }

    @Override
    public String toString() {
        return getUsername();
    }

    public String getUsername() {
        return mUsername;
    }

    public final void setUsername(String username) {mUsername = username;}


    @Override
    public boolean equals(Object o) {
        return o instanceof UserAccounts && ((UserAccounts) o).mId == mId;
    }
}
