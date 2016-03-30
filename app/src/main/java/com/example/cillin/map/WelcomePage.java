package com.example.cillin.map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableJsonQueryCallback;

import java.net.MalformedURLException;

/**
 * Created by Cillin on 14/03/2016.
 */
public class WelcomePage extends Activity
{
    private final String TAG = "WelcomePage";
    private Activity mActivity;
    private Button mWelcome;
    private TextView mUsername;
    private ImageView mIcon;
    private String username;

    private String neighborhood;
    private String mCounty;

    private final DialogBox dialogBox = new DialogBox();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        mActivity = this;
        mIcon = (ImageView) findViewById(R.id.imageViewWelcomePage);
        mIcon.setImageResource(R.mipmap.app_icon);
        mWelcome = (Button) findViewById(R.id.buttonWelcome);
        mUsername = (TextView) findViewById(R.id.textViewUsername);

        NBHAuthenticationApplication myApp = (NBHAuthenticationApplication) getApplication();
        AuthService authService = myApp.getAuthService();
        final AuthService mAuthService = new AuthService(mActivity);
        final NBHAuthService mNBHAuthService = new NBHAuthService(mActivity);

        //Fetch auth data (the username) on load
        authService.getAuthData(new TableJsonQueryCallback() {
            @Override
            public void onCompleted(JsonElement result, Exception exception,
                                    ServiceFilterResponse response) {
                if (exception == null) {
                    JsonArray results = result.getAsJsonArray();
                    JsonElement item = results.get(0);
                    mUsername.setText(item.getAsJsonObject().getAsJsonPrimitive("UserName").getAsString());
                } else {
                    Log.e(TAG, "There was an exception getting auth data: " + exception.getMessage());
                    dialogBox.NotLoggedIn(mActivity);
                    mAuthService.logout(true);
                    mNBHAuthService.logout(true);
                    //Intent login = new Intent(mActivity, CoverPage.class);
                    //startActivity(login);
                }
            }
        });

        mWelcome.setOnClickListener(gotoMap);

    }

    public void NotLoggedIn()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mActivity);
        String emptyFields = "Not Logged In";
        String emptyFieldsMessage = "You must be logged in to enter data into the newsfeed. Press OK to be brought to the login page";

        dialogBuilder.setTitle(emptyFields);
        dialogBuilder.setMessage(emptyFieldsMessage);
        dialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent login = new Intent(mActivity, CoverPage.class);
                startActivity(login);

            }
        });
        dialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent login = new Intent(mActivity, CoverPage.class);
                startActivity(login);
            }
        });

        dialogBuilder.create();
        dialogBuilder.show();
    }


    View.OnClickListener gotoMap = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent WelcomeActivity = new Intent(mActivity, MapsActivity.class);
            startActivity(WelcomeActivity);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        return super.onOptionsItemSelected(item);
    }

}
