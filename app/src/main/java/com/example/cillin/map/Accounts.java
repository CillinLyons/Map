package com.example.cillin.map;

import java.sql.Timestamp;
import java.util.Date;

/**
 * The Accounts class represents the neighborhoods accounts table.
 * The toString method in this class returns the county value from the accounts table.
 */
public class Accounts
{
    /**
     * The following are a list of the columns in the neighborhood accounts table.
     * Item id
     */
    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    /**
     * Item _createdAt
     */
    @com.google.gson.annotations.SerializedName("_createdAt")
    private Date mCreated;

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
     * Item username
     */
    @com.google.gson.annotations.SerializedName("username")
    private String mUsername;

    /**
     * Item password
     */
    @com.google.gson.annotations.SerializedName("password")
    private String mPassword;

    /**
     * Item salt
     */
    @com.google.gson.annotations.SerializedName("salt")
    private String mSalt;

    /**
     * Item county
     */
    @com.google.gson.annotations.SerializedName("county")
    private String mCounty;

    /**
     * Accounts constructor
     */
    public Accounts() {

    }

    /**
     * toString calls the getCounty function to return the county value
     * from the accounts table
     */
    @Override
    public String toString() {
        return getCounty();
    }

    /**
     * Initializes a new Accounts
     */
    public Accounts(String id, Date created, Date updated, Timestamp version, Boolean deleted, String username, String password, String salt, String county) {
        this.setId(id);
        this.setCreated(created);
        this.setUpdated(updated);
        this.setVersion(version);
        this.setDeleted(deleted);
        this.setUsername(username);
        this.setPassword(password);
        this.setSalt(salt);
        this.setCounty(county);
    }

    /**
     * getCounty retrieves the county value from accounts
     */
    public String getCounty() {
        return mCounty;
    }

    /**
     * getUsername retrieves the username value from accounts
     */
    public String getUsername() {
    return mUsername;
    }

    /**
     * getCreated retrieves the _createdAt value from accounts
     */
    public Date getCreated() {
        return mCreated;
    }

    /**
     * getUpdated retrieves the _updatedAt value from accounts
     */
    public Date getUpdated() {
        return mUpdated;
    }

    /**
     * getVersion retrieves the version value from accounts
     */
    public Timestamp getVersion() {
        return mVersion;
    }

    /**
     * getPassword retrieves the password value from accounts
     */
    public String getPassword() {
        return mPassword;
    }

    /**
     * getSalt retrieves the salt value from accounts
     */
    public String getSalt() {
        return mSalt;
    }

    /**
     * getDeleted retrieves the _deleted value from accounts
     */
    public Boolean getDeleted() {
        return mDeleted;
    }

    /**
     * getId retrieves the id value from accounts
     */
    public String getId() {
        return mId;
    }


    /**
     * @param county
     * setCounty can be used to set a value to the county column
     */
    public final void setCounty(String county) {
        mCounty = county;
    }

    /**
     * @param username
     * setUsername can be used to set a value to the username column
     */
    public final void setUsername(String username) {mUsername = username;}

    /**
     * @param created
     * setCreated can be used to set a value to the _createdAt column
     */
    public final void setCreated(Date created) {mCreated = created;}

    /**
     * @param updated
     * setUpdated can be used to set a value to the _updatedAt column
     */
    public final void setUpdated(Date updated) {mUpdated = updated;}

    /**
     * @param version
     * setVersion can be used to set a value to the version column
     */
    public final void setVersion(Timestamp version) {mVersion = version;}

    /**
     * @param deleted
     * setDeleted can be used to set a value to the _deleted column
     */
    public final void setDeleted(Boolean deleted) {mDeleted = deleted;}

    /**
     * @param salt
     * setSalt can be used to set a value to the salt column
     */
    public final void setSalt(String salt) {mSalt = salt;}

    /**
     * @param password
     * setPassword can be used to set a value to the password column
     */
    public final void setPassword(String password) {mPassword = password;}

    /**
     * @param id
     * setId can be used to set a value to the id column
     */
    public final void setId(String id) {mId = id;}

    @Override
    public boolean equals(Object o) {
        return o instanceof Accounts && ((Accounts) o).mId == mId;
    }
}
