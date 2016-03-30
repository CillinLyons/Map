package com.example.cillin.map;

import java.sql.Timestamp;
import java.util.Date;

/**
 * The AccountNeighborhood class represents the users accounts table.
 * The toString method in this class returns the neighborhood value from the accounts table,
 * hence the name AccountNeighborhood.
 */
public class AccountNeighborhood
{
    /**
     * The following are a list of the columns in the users accounts table.
     * Item ID
     */
    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    /**
     * Item password
     */
    @com.google.gson.annotations.SerializedName("password")
    private String mPassword;

    /**
     * Item neighbourhood
     */
    @com.google.gson.annotations.SerializedName("neighbourhood")
    private String mNeighborhood;

    /**
     * Item membership
     */
    @com.google.gson.annotations.SerializedName("membership")
    private String mMembership;

    /**
     * Item salt
     */
    @com.google.gson.annotations.SerializedName("salt")
    private String mSalt;

    /**
     * Item username
     */
    @com.google.gson.annotations.SerializedName("username")
    private String mUsername;

    /**
     * Item email
     */
    @com.google.gson.annotations.SerializedName("email")
    private String mEmail;

    /**
     * Item _createdAt
     */
    @com.google.gson.annotations.SerializedName("_createdAt")
    private String mCreated;

    /**
     * Item _updatedAt
     */
    @com.google.gson.annotations.SerializedName("_updatedAt")
    private Date mUpdated;

    /**
     * Item _version
     */
    @com.google.gson.annotations.SerializedName("_version")
    private Timestamp mVersion;

    /**
     * Item _deleted
     */
    @com.google.gson.annotations.SerializedName("_deleted")
    private Boolean mDeleted;


    /**
     * AccountNeighborhood constructor
     */
    public AccountNeighborhood () {

    }

    /**
     * toString calls the getNeighborhood function to return the neighborhood value
     * from the accounts table
     */
    @Override
    public String toString() {
        return getNeighborhood();
    }

    /**
     * getMembership retrieves the membership value from accounts
     */
    public String getNeighborhood() {
        return mNeighborhood;
    }

    /**
     * @param username
     * setMembership can be used to set a value to the membership column
     */
    public final void setNeighborhood(String username) {mNeighborhood = username;}

    @Override
    public boolean equals(Object o) {
        return o instanceof AccountNeighborhood  && ((AccountNeighborhood ) o).mId == mId;
    }
}

