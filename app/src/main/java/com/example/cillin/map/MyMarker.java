package com.example.cillin.map;

import com.example.cillin.map.clustering.ClusterItem;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Cillin on 14/10/2015.
 */

public class MyMarker implements ClusterItem {
    private String mLabel;
    private String mIcon;
    private Double mLatitude;
    private Double mLongitude;
    private final LatLng mPosition;

    public MyMarker(String label, String icon, Double latitude, Double longitude)
    {
        this.mLabel = label;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mIcon = icon;
        mPosition = new LatLng(latitude, longitude);
    }

    //mPosition = LatLng(mLatitude, mLongitude);
    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public String getmLabel()
    {
        return mLabel;
    }

    public void setmLabel(String mLabel)
    {
        this.mLabel = mLabel;
    }

    public String getmIcon()
    {
        return mIcon;
    }

    public void setmIcon(String icon)
    {
        this.mIcon = icon;
    }

    public Double getmLatitude()
    {
        return mLatitude;
    }

    public void setmLatitude(Double mLatitude)
    {
        this.mLatitude = mLatitude;
    }

    public Double getmLongitude()
    {
        return mLongitude;
    }

    public void setmLongitude(Double mLongitude)
    {
        this.mLongitude = mLongitude;
    }
}
