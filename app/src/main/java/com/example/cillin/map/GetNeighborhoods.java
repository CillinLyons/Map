package com.example.cillin.map;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Cillin on 13/03/2016.
 */
public class GetNeighborhoods
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
    @com.google.gson.annotations.SerializedName("county")
    private String mCounty;

    /**
     * ToDoItem constructor
     */
    public GetNeighborhoods() {

    }

    @Override
    public String toString() {
        return getUsername();
    }

    /**
     * Initializes a new ToDoItem
     */
    public GetNeighborhoods(String id, Date created, Date updated, Timestamp version, Boolean deleted, String username, String password, String salt, String county) {
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
     * Returns the item text
     */
    public String getCounty() {
        return mCounty;
    }

    public String getUsername() {
        return mUsername;
    }

    public Date getCreated() {
        return mCreated;
    }
    public Date getUpdated() {
        return mUpdated;
    }
    public Timestamp getVersion() {
        return mVersion;
    }
    public String getPassword() {
        return mPassword;
    }
    public String getSalt() {
        return mSalt;
    }
    public Boolean getDeleted() {
        return mDeleted;
    }
    public String getId() {
        return mId;
    }


    /**

     */
    public final void setCounty(String county) {
        mCounty = county;
    }

    public final void setUsername(String username) {mUsername = username;}

    public final void setCreated(Date created) {mCreated = created;}

    public final void setUpdated(Date updated) {mUpdated = updated;}

    public final void setVersion(Timestamp version) {mVersion = version;}

    public final void setDeleted(Boolean deleted) {mDeleted = deleted;}

    public final void setSalt(String salt) {mSalt = salt;}

    public final void setPassword(String password) {mPassword = password;}

    public final void setId(String id) {mId = id;}


    @Override
    public boolean equals(Object o) {
        return o instanceof GetNeighborhoods && ((GetNeighborhoods) o).mId == mId;
    }
}
