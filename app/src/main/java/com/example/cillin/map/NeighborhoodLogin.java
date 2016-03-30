package com.example.cillin.map;

/**
 * Created by Cillin on 12/03/2016.
 */
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.google.gson.JsonObject;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableJsonOperationCallback;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.app.Activity;
import android.widget.Toast;

import java.net.MalformedURLException;

public class NeighborhoodLogin extends NBHBaseActivity
{
    private final String TAG = "NeighorhoodLogin";
    private Button mBtnLogin;
    private EditText mTxtNeighborhood;
    private EditText mTxtPassword;
    private TextView mRegister;
    private Activity mActivity;
    private ProgressDialog mProgressBar;
    final DialogBox dialogBox = new DialogBox();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.neighborhood_sign_in);

        mActivity = this;

        //Get UI objects
        mRegister = (TextView) findViewById(R.id.textViewNeighborhoodRegister);
        mBtnLogin = (Button) findViewById(R.id.btnNeighborhoodLogin);
        mTxtNeighborhood = (EditText) findViewById(R.id.editSignInNeighborhood);
        mTxtPassword = (EditText) findViewById(R.id.editNeighborhoodSignInPassword);
        mProgressBar = new ProgressDialog(mActivity);

        //Add on click listeners
        mRegister.setOnClickListener(registerNeighborhoodClickListener);
        mBtnLogin.setOnClickListener(loginNeighborhoodClickListener);


    }

    View.OnClickListener NeighborhoodClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent registerIntent = new Intent(mActivity, NeighborhoodRegister.class);
            startActivity(registerIntent);
            mActivity.finish();
        }
    };

    View.OnClickListener loginNeighborhoodClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mTxtPassword.getText().toString().equals("") ||
                    mTxtNeighborhood.getText().toString().equals("")) {
                //We're just logging this here, we should show something to the user
                dialogBox.emptyFields(mActivity);
                return;
            }
            mProgressBar.setTitle("Logging In...");
            mProgressBar.setMessage("Please wait...");
            mProgressBar.setIndeterminate(true);
            mProgressBar.setCancelable(true);
            mProgressBar.show();
            mNBHAuthService.NBHlogin(mTxtNeighborhood.getText().toString(), mTxtPassword.getText().toString(), new TableJsonOperationCallback() {
                @Override
                public void onCompleted(JsonObject jsonObject, Exception exception,
                                        ServiceFilterResponse response) {
                    final String neighborhood = mTxtNeighborhood.getText().toString();
                    if (exception == null) {
                        //If they've registered successfully, we'll save and set the userdata and then
                        //show the logged in activity
                        mNBHAuthService.setNBHUserAndSaveData(jsonObject);
                        Toast login = Toast.makeText(mActivity, "Neighborhood Login Successful", Toast.LENGTH_SHORT);
                        login.show();
                        Intent loggedInIntent = new Intent(mActivity, WelcomePage.class);
                        mProgressBar.dismiss();
                        startActivity(loggedInIntent);

                    } else {
                        dialogBox.RegistrationError(mActivity);
                        mProgressBar.dismiss();
                    }
                }
            });
        }
    };

    View.OnClickListener registerNeighborhoodClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent registerIntent = new Intent(getApplicationContext(), NeighborhoodRegister.class);
            startActivity(registerIntent);
            mActivity.finish();
        }
    };

}
