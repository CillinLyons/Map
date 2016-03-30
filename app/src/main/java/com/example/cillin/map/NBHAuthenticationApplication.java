package com.example.cillin.map;

/**
 * Created by Cillin on 11/03/2016.
 */

import android.app.Activity;
import android.app.Application;

public class NBHAuthenticationApplication extends Application
{
    private AuthService mAuthService;
    private Activity mCurrentActivity;
    private NBHAuthService mNBHAuthService;
    private Activity mCurrentNBHActivity;

    public NBHAuthenticationApplication() {}
    //public AuthenticationApplication() {}

    public AuthService getAuthService() {
        if (mAuthService == null) {
            mAuthService = new AuthService(this);
        }
        return mAuthService;
    }

    public void setCurrentActivity(Activity activity) {
        mCurrentActivity = activity;
    }

    public Activity getCurrentNBHActivity() {
        return mCurrentActivity;
    }


    public NBHAuthService getNBHAuthService() {
        if (mNBHAuthService == null) {
            mNBHAuthService = new NBHAuthService(this);
        }
        return mNBHAuthService;
    }

    public void setCurrentNBHActivity(Activity NBHactivity) {
        mCurrentNBHActivity = NBHactivity;
    }

    public Activity getCurrentActivity() {
        return mCurrentNBHActivity;
    }
}
