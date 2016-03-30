package com.example.cillin.map;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Cillin on 11/03/2016.
 */
public class GardaLoginActivity extends BaseActivity
{
    private final String TAG = "GardaLoginActivity";
    private Button mBtnCancel;
    private Button mBtnLogin;
    private TextView mRegisterForAccount;
    private EditText mTxtEmail;
    private EditText mTxtPassword;
    private Activity mActivity;
    final DialogBox dialogBox = new DialogBox();
    private ProgressDialog mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garda_login);

        mActivity = this;

        //Get UI objects
        mBtnLogin = (Button) findViewById(R.id.btnGardaLogin);
        mRegisterForAccount = (TextView) findViewById(R.id.textViewGardaRegister);
        mTxtEmail = (EditText) findViewById(R.id.txtGardaEmail);
        mTxtPassword = (EditText) findViewById(R.id.txtGardaPassword);
        //mProgressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
        mProgressBar = new ProgressDialog(mActivity);

        //Add on click listeners
        mBtnLogin.setOnClickListener(loginClickListener);
        mRegisterForAccount.setOnClickListener(registerGardaClickListener);


    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.custom_login, menu);
        return true;
    }*/


    View.OnClickListener loginClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mTxtPassword.getText().toString().equals("") ||
                    !mTxtPassword.getText().toString().equals("garda123") ||
                    mTxtEmail.getText().toString().equals("")) {
                //We're just logging this here, we should show something to the user
                dialogBox.GardaRegistrationError(mActivity);
                return;
            }
            mProgressBar.setTitle("Logging In...");
            mProgressBar.setMessage("Please wait...");
            mProgressBar.setIndeterminate(true);
            mProgressBar.setCancelable(true);
            mProgressBar.show();
            mAuthService.login(mTxtEmail.getText().toString(), mTxtPassword.getText().toString(), new TableJsonOperationCallback() {
                @Override
                public void onCompleted(JsonObject jsonObject, Exception exception,
                                        ServiceFilterResponse response) {
                    if (exception == null) {
                        //If they've registered successfully, we'll save and set the userdata and then
                        //show the logged in activity
                        mAuthService.setUserAndSaveData(jsonObject);
                        Toast login = Toast.makeText(mActivity, "Garda Login Successful", Toast.LENGTH_SHORT);
                        login.show();
                        Intent loggedInIntent = new Intent(mActivity, NeighborhoodLogin.class);
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

    View.OnClickListener registerGardaClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent registerIntent = new Intent(getApplicationContext(), GardaRegister.class);
            startActivity(registerIntent);
            mActivity.finish();
        }
    };

}
