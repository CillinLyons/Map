package com.example.cillin.map;

/**
 * Created by Cillin on 11/03/2016.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import com.google.gson.JsonObject;
import com.microsoft.windowsazure.mobileservices.table.TableJsonOperationCallback;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;

public class NeighborhoodRegister extends NBHBaseActivity
{
    private final String TAG = "NeighborhoodRegister";
    private TextView mRegister;
    private EditText mTxtNeighborhood;
    private Spinner mTxtCounty;
    private EditText mTxtPassword;
    private EditText mTxtConfirm;
    private TextView mSignIn;
    private Activity mActivity;
    private ProgressDialog mProgressBar;
    final DialogBox dialogBox = new DialogBox();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.neighborhood_register);

        mActivity = this;

        //Get UI elements
        mRegister = (Button) findViewById(R.id.NeighborhoodRegisterloginbtn);
        mTxtNeighborhood = (EditText) findViewById(R.id.editRegisterNeighborhood);
        mTxtCounty = (Spinner) findViewById(R.id.countySpinner);
        mTxtPassword = (EditText) findViewById(R.id.editNeighborhoodRegisterPassword);
        mTxtConfirm = (EditText) findViewById(R.id.editConfirmNeighborhoodRegisterPassword);
        mSignIn =(TextView) findViewById(R.id.neighborhood_register_signin);
        mProgressBar = new ProgressDialog(mActivity);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> county_adapter = ArrayAdapter.createFromResource(this,
                R.array.county_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        county_adapter.setDropDownViewResource(R.layout.spinner_item);
        // Apply the adapter to the spinner
        mTxtCounty.setAdapter(county_adapter);

        //Set click listeners
        mRegister.setOnClickListener(registerNeighborhoodClickListener);
        mSignIn.setOnClickListener(goToSignIn);
    }

    View.OnClickListener goToSignIn = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            Intent loginIntent = new Intent(mActivity, NeighborhoodLogin.class);
            startActivity(loginIntent);
            mActivity.finish();
        }
    };


    View.OnClickListener registerNeighborhoodClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //We're just logging the validation errors, we should be showing something to the user
            if (mTxtNeighborhood.getText().toString().equals("") ||
                    mTxtCounty.getSelectedItem().equals("Select County") ||
                    mTxtPassword.getText().toString().equals("") ||
                    mTxtConfirm.getText().toString().equals("")) {
                dialogBox.emptyFields(mActivity);
                return;
            } else if (!mTxtPassword.getText().toString().equals(mTxtConfirm.getText().toString())) {
                dialogBox.NonMatchingPasswords(mActivity);
                return;
            } else {
                mProgressBar.setTitle("Registering Neighborhood...");
                mProgressBar.setMessage("Please wait...");
                mProgressBar.setIndeterminate(true);
                mProgressBar.setCancelable(true);
                mProgressBar.show();
                mNBHAuthService.registerNBHUser(mTxtPassword.getText().toString(),
                        mTxtCounty.getSelectedItem().toString(),
                        mTxtConfirm.getText().toString(),
                        mTxtNeighborhood.getText().toString(),
                        new TableJsonOperationCallback() {
                            @Override
                            public void onCompleted(JsonObject jsonObject, Exception exception,
                                                    ServiceFilterResponse response) {
                                if (exception == null) {
                                    //If that was successful, set and save the user data
                                    mNBHAuthService.setNBHUserAndSaveData(jsonObject);
                                    Toast login = Toast.makeText(mActivity, "Neighborhood Registration Successful", Toast.LENGTH_SHORT);
                                    login.show();
                                    //Finish this activity and run the logged in activity
                                    String county = mTxtNeighborhood.getText().toString();
                                    mTxtNeighborhood.setText("");
                                    mTxtCounty.setSelection(0);
                                    mTxtPassword.setText("");
                                    mTxtConfirm.setText("");
                                    mActivity.finish();
                                    Intent loggedInIntent = new Intent(mActivity, WelcomePage.class);
                                    mProgressBar.dismiss();
                                    startActivity(loggedInIntent);
                                } else {
                                    dialogBox.NeighborhoodAlreadyRegistered(mActivity);
                                    mProgressBar.dismiss();
                                }
                            }
                        });
            }
        }
    };
}
