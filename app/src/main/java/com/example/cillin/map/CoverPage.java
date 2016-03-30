package com.example.cillin.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceAuthenticationProvider;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceUser;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.UserAuthenticationCallback;

/**
 * Cover page is the first page of the app, hence the name.
 * This page provides the options garda login and registration
 * and user login and registration.
 */
public class CoverPage extends BaseActivity
{
    /**
     * Variables for the TextViews on the xml page
     */
    private TextView mGardaLogin;
    private TextView mMemberLogin;
    private TextView mUserRegister;
    private  TextView mGardaRegister;

    /**
     * Variable for the current activity
     */
    private Activity mActivity;

    /**
     * Variable for this activities name
     */
    private final String TAG = "CoverPage";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        /**
         * Linking this class to the XML layout cover_page
         */
        setContentView(R.layout.cover_page);

        /**
         * Assigning the TextView variables to the appropriate XML Textviews
         */
        mGardaLogin = (TextView) findViewById(R.id.gardaSignIn);
        mMemberLogin = (TextView) findViewById(R.id.memberSignIn);
        mUserRegister = (TextView) findViewById(R.id.register1);
        mGardaRegister = (TextView) findViewById(R.id.gardaRegistration);

        /**
         * Assigning the current asctivity to the activity variable
         */
        mActivity = this;

        /**
         * Setting onClickListeners to the TextViews to allow the user to go
         * to their desired page
         */
        mGardaLogin.setOnClickListener(gardaLoginClickListener);
        mMemberLogin.setOnClickListener(memberLoginClickListener);
        mUserRegister.setOnClickListener(registerClickListener);
        mGardaRegister.setOnClickListener(gardaRegisterClickListener);

        //If user is already authenticated, bypass logging in
        /*if (mAuthService.isUserAuthenticated())
        {
            Intent loggedInIntent = new Intent(mActivity, WelcomePage.class);
            startActivity(loggedInIntent);
        }*/
    }

    /**
     * Setting an intent that will take the user to the garda login page
     */
    View.OnClickListener gardaLoginClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent loginIntent = new Intent(mActivity, GardaLoginActivity.class);
            startActivity(loginIntent);
        }
    };

    /**
     * Setting an intent that will take the user to the member login page
     */
   View.OnClickListener memberLoginClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent loginIntent = new Intent(mActivity, CustomLoginActivity.class);
            startActivity(loginIntent);
        }
    };

    /**
     * Setting an intent that will take the user to the member registration page
     */
    View.OnClickListener registerClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent registerIntent = new Intent(mActivity, RegisterAccountActivity.class);
            startActivity(registerIntent);
        }
    };

    /**
     * Setting an intent that will take the user to the garda registration page
     */
    View.OnClickListener gardaRegisterClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent registerIntent = new Intent(mActivity, GardaRegister.class);
            startActivity(registerIntent);
        }
    };

}
