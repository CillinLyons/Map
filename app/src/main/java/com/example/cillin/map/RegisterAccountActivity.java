package com.example.cillin.map;

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

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RegisterAccountActivity extends BaseActivity {

    private final String TAG = "RegisterAccountActivity";
    private Button btnRegister;
    private EditText mUsername;
    private EditText mEmail;
    private EditText mNeighbourhood;
    private Spinner mMembership;
    private EditText mPassword;
    private EditText mConfirm;
    private TextView mLoginUser;
    private Activity mActivity;
    private ProgressDialog mProgressBar;
    final DialogBox dialogBox = new DialogBox();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_account);

        mActivity = this;

        //Get UI elements
        btnRegister = (Button) findViewById(R.id.btnRegister);
        mUsername = (EditText) findViewById(R.id.editUser);
        mEmail = (EditText) findViewById(R.id.editEmail);
        mNeighbourhood = (EditText) findViewById(R.id.RegisterNeighbourhood);
        mMembership = (Spinner) findViewById(R.id.RegisterMembershipSpinner);
        mPassword = (EditText) findViewById(R.id.RegisterPassword);
        mConfirm = (EditText) findViewById(R.id.RegisterConfirm);
        mLoginUser = (TextView) findViewById(R.id.textViewUserLogin);
        mProgressBar = new ProgressDialog(mActivity);

        //cursor.moveToFirst();
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> member_adapter = ArrayAdapter.createFromResource(this,
                R.array.membership_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        member_adapter.setDropDownViewResource(R.layout.spinner_item);
        // Apply the adapter to the spinner
        mMembership.setAdapter(member_adapter);

        /*ArrayList<String> array = new ArrayList<String>();
        array.add("item0");
        ArrayAdapter<String> mAdapter;
        mMembership= (Spinner) findViewById(R.id.RegisterMembershipSpinner);
        mAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, array);
        mMembership.setAdapter(mAdapter);*/

        //Set click listeners
        btnRegister.setOnClickListener(registerClickListener);
        mLoginUser.setOnClickListener(loginClickListener);
    }

    View.OnClickListener loginClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent registerIntent = new Intent(mActivity, CustomLoginActivity.class);
            startActivity(registerIntent);
            mActivity.finish();
        }
    };

    View.OnClickListener registerClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            //We're just logging the validation errors, we should be showing something to the user
            if (mUsername.getText().toString().equals("") ||
                    mEmail.getText().toString().equals("") ||
                    mNeighbourhood.getText().toString().equals("") ||
                    mMembership.getSelectedItem().toString().equals("Select Membership") ||
                    mPassword.getText().toString().equals("") ||
                    mConfirm.getText().toString().equals("")) {

                dialogBox.emptyFields(mActivity);
                return;
            } else if (!mPassword.getText().toString().equals(mConfirm.getText().toString())) {
                dialogBox.NonMatchingPasswords(mActivity);
                return;
            } else
            {
                mProgressBar.setTitle("Registering User...");
                mProgressBar.setMessage("Please wait...");
                mProgressBar.setIndeterminate(true);
                mProgressBar.setCancelable(true);
                mProgressBar.show();
                mAuthService.registerUser(mPassword.getText().toString(),
                        mConfirm.getText().toString(),
                        mUsername.getText().toString(),
                        mNeighbourhood.getText().toString(),
                        mMembership.getSelectedItem().toString(),
                        mEmail.getText().toString(),
                        new TableJsonOperationCallback() {
                            @Override
                            public void onCompleted(JsonObject jsonObject, Exception exception,
                                                    ServiceFilterResponse response) {
                                if (exception == null) {
                                    //If that was successful, set and save the user data
                                    mAuthService.setUserAndSaveData(jsonObject);
                                    Toast login = Toast.makeText(mActivity, "User Registration Successful", Toast.LENGTH_SHORT);
                                    login.show();
                                    //Finish this activity and run the logged in activity
                                    mUsername.setText("");
                                    mEmail.setText("");
                                    mNeighbourhood.setText("");
                                    mMembership.setSelection(0);
                                    mPassword.setText("");
                                    mConfirm.setText("");
                                    mActivity.finish();
                                    Intent loggedInIntent = new Intent(mActivity, UserNeighborhoodLogin.class);
                                    mProgressBar.dismiss();
                                    startActivity(loggedInIntent);
                                } else {
                                    dialogBox.UserAlreadyRegistered(mActivity);
                                    mProgressBar.dismiss();
                                }
                            }
                        });
            }
        }
    };

}
