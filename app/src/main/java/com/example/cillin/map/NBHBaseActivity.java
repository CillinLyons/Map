package com.example.cillin.map;

/**
 * Created by Cillin on 11/03/2016.
 */

import android.app.Activity;
import android.os.Bundle;

public class NBHBaseActivity extends Activity
{
    protected NBHAuthService mNBHAuthService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        NBHAuthenticationApplication myNBHApp = (NBHAuthenticationApplication) getApplication();
        myNBHApp.setCurrentNBHActivity(this);
        mNBHAuthService = myNBHApp.getNBHAuthService();
    }
}
