package com.example.cillin.map;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {

    protected AuthService mAuthService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        NBHAuthenticationApplication myApp = (NBHAuthenticationApplication) getApplication();
        myApp.setCurrentActivity(this);
        mAuthService = myApp.getAuthService();
    }
}
