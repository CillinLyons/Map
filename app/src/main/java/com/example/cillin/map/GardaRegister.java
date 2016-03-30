package com.example.cillin.map;

/**
 * Created by Cillin on 11/03/2016.
 */

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

import android.app.ProgressDialog;
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

public class GardaRegister extends BaseActivity {

    private final String TAG = "RegisterAccountActivity";
    private Button btnRegister;
    private EditText mUsername;
    private EditText mEmail;
    private EditText mNeighbourhood;
    private EditText mPassword;
    private Activity mActivity;
    private String mMembership;
    private TextView mLogin;
    private ProgressDialog mProgressBar;
    final DialogBox dialogBox = new DialogBox();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garda_register);

        mActivity = this;

        //Get UI elements
        btnRegister = (Button) findViewById(R.id.btnGardaRegister);
        mUsername = (EditText) findViewById(R.id.editGardaUser);
        mEmail = (EditText) findViewById(R.id.editGardaEmail);
        mNeighbourhood = (EditText) findViewById(R.id.RegisterGardaNeighbourhood);
        mPassword = (EditText) findViewById(R.id.RegisterGardaPassword);
        mMembership = "Garda";
        mLogin = (TextView) findViewById(R.id.textViewGardaLogin);
        mProgressBar = new ProgressDialog(mActivity);

        btnRegister.setOnClickListener(registerGardaClickListener);
        mLogin.setOnClickListener(loginClickListener);
    }

    View.OnClickListener loginClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent registerIntent = new Intent(mActivity, GardaLoginActivity.class);
            startActivity(registerIntent);
            mActivity.finish();
        }
    };

    View.OnClickListener registerGardaClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            //We're just logging the validation errors, we should be showing something to the user
            if (mUsername.getText().toString().equals("") ||
                    mEmail.getText().toString().equals("") ||
                    mNeighbourhood.getText().toString().equals("") ||
                    mPassword.getText().toString().equals("") ||
                    !mPassword.getText().toString().equals("garda123")) {
                dialogBox.GardaRegistrationError(mActivity);
                return;
            } else {
                mProgressBar.setTitle("Registering Garda...");
                mProgressBar.setMessage("Please wait...");
                mProgressBar.setIndeterminate(true);
                mProgressBar.setCancelable(true);
                mProgressBar.show();
                mAuthService.registerUser(mPassword.getText().toString(),
                        mPassword.getText().toString(),
                        mUsername.getText().toString(),
                        mNeighbourhood.getText().toString(),
                        mMembership,
                        mEmail.getText().toString(),
                        new TableJsonOperationCallback() {
                            @Override
                            public void onCompleted(JsonObject jsonObject, Exception exception,
                                                    ServiceFilterResponse response) {
                                if (exception == null) {
                                    //If that was successful, set and save the user data
                                    mAuthService.setUserAndSaveData(jsonObject);
                                    Toast login = Toast.makeText(mActivity, "Garda Registration Successful", Toast.LENGTH_SHORT);
                                    login.show();
                                    //Finish this activity and run the logged in activity
                                    mUsername.setText("");
                                    mEmail.setText("");
                                    mNeighbourhood.setText("");
                                    mPassword.setText("");
                                    mActivity.finish();
                                    Intent loggedInIntent = new Intent(mActivity, NeighborhoodLogin.class);
                                    mProgressBar.dismiss();
                                    startActivity(loggedInIntent);
                                } else {
                                    dialogBox.GardaAlreadyRegistered(mActivity);
                                    mProgressBar.dismiss();
                                }
                            }
                        });
            }
        }
    };

}
