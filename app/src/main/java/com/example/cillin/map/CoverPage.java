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
 * Created by Cillin on 29/01/2016.
 */
public class CoverPage extends BaseActivity
{
    private TextView mLogin;
    private TextView mRegister;
    private Activity mActivity;
    private final String TAG = "CoverPage";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cover_page);

        mLogin = (TextView) findViewById(R.id.signin1);
        mRegister = (TextView) findViewById(R.id.register1);
        mActivity = this;

        mLogin.setOnClickListener(loginClickListener);
        mRegister.setOnClickListener(registerClickListener);

        //If user is already authenticated, bypass logging in
        if (mAuthService.isUserAuthenticated())
        {
            Intent loggedInIntent = new Intent(mActivity, MainMenu.class);
            startActivity(loggedInIntent);
        }

        /*mLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customLoginIntent = new Intent(getApplicationContext(), CustomLoginActivity.class);
                startActivity(customLoginIntent);
            }
        });

        mRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customLoginIntent = new Intent(getApplicationContext(), RegisterAccountActivity.class);
                startActivity(customLoginIntent);
            }
        });*/
    }

   View.OnClickListener loginClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent loginIntent = new Intent(getApplicationContext(), CustomLoginActivity.class);
            startActivity(loginIntent);
        }
    };

    View.OnClickListener registerClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent registerIntent = new Intent(getApplicationContext(), RegisterAccountActivity.class);
            startActivity(registerIntent);
        }
    };

}
