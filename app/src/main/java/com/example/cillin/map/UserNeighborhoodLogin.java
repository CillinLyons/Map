package com.example.cillin.map;

/**
 * Created by Cillin on 13/03/2016.
 */
import com.google.gson.JsonObject;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableJsonOperationCallback;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserNeighborhoodLogin extends NBHBaseActivity
{
    private final String TAG = "UserNeighorhoodLogin";
    private Button mBtnLogin;
    private TextView mBtnRegisterForAccount;
    private EditText mTxtNeighborhood;
    private EditText mTxtPassword;
    private Activity mActivity;
    private ProgressDialog mProgressBar;
    final DialogBox dialogBox = new DialogBox();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_neighborhood_sign_in);

        mActivity = this;

        //Get UI objects
        mBtnLogin = (Button) findViewById(R.id.UserNeighborhoodloginbtn);
        mTxtNeighborhood = (EditText) findViewById(R.id.editUserSignInNeighborhood);
        mTxtPassword = (EditText) findViewById(R.id.editNeighborhoodUserSignInPassword);
        mProgressBar = new ProgressDialog(mActivity);


        //Add on click listeners
        mBtnLogin.setOnClickListener(loginNeighborhoodClickListener);


    }

    View.OnClickListener cancelNeighborhoodClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mActivity.finish();
        }
    };

    View.OnClickListener loginNeighborhoodClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mTxtPassword.getText().toString().equals("") ||
                    mTxtNeighborhood.getText().toString().equals("")) {
                //We're just logging this here, we should show something to the user
                dialogBox.IncorrectNeighborhoodCredentials(mActivity);
                return;
            }
            mProgressBar.setTitle("Loading...");
            mProgressBar.setMessage("Please wait...");
            mProgressBar.setIndeterminate(true);
            mProgressBar.setCancelable(true);
            mProgressBar.show();
            mNBHAuthService.NBHlogin(mTxtNeighborhood.getText().toString(), mTxtPassword.getText().toString(), new TableJsonOperationCallback() {
                @Override
                public void onCompleted(JsonObject jsonObject, Exception exception,
                                        ServiceFilterResponse response) {
                    if (exception == null) {
                        //If they've registered successfully, we'll save and set the userdata and then
                        //show the logged in activity
                        mNBHAuthService.setNBHUserAndSaveData(jsonObject);
                        Intent loggedInIntent = new Intent(mActivity, WelcomePage.class);
                        String mCounty = mTxtNeighborhood.getText().toString();
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

}
