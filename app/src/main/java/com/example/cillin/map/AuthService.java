package com.example.cillin.map;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.http.StatusLine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ProgressBar;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.google.gson.JsonObject;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceJsonTable;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponseCallback;
import com.microsoft.windowsazure.mobileservices.table.TableJsonOperationCallback;
import com.microsoft.windowsazure.mobileservices.table.TableJsonQueryCallback;
import com.microsoft.windowsazure.mobileservices.UserAuthenticationCallback;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceAuthenticationProvider;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceUser;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceJsonTable;
import com.microsoft.windowsazure.mobileservices.table.TableJsonOperationCallback;
import com.microsoft.windowsazure.mobileservices.table.TableJsonQueryCallback;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;
//import com.google.android.gms.internal.zzid.runOnUiThread;

/**
 * Auth services handles the login, logout, registration and authentication of this app.
 */

public class AuthService extends Activity
{
    //Connection variable to database
    private MobileServiceClient mClient;
    //Connection variable to Accounts table
    private MobileServiceJsonTable mTableAccounts;
    //Connection variable to AuthData table
    private MobileServiceJsonTable mTableAuthData;
    //Connection variable to BadAuth table
    private MobileServiceJsonTable mTableBadAuth;
    //Variable for AuthService context
    private Context mContext;
    //Variable for class
    private final String TAG = "AuthService";
    private boolean mShouldRetryAuth;
    private boolean mIsCustomAuthProvider = false;
    private MobileServiceAuthenticationProvider mProvider;
    private ProgressBar mProgressBar;

    public AuthService(Context context)
    {
        //Setting context variable
        mContext = context;

        //Connecting to the database SmartNeighborhoodWatch
        try {
            //Setting the connection variable to the connection value given in the Azure portal
            mClient = new MobileServiceClient("https://smartneighborhoodwatch.azure-mobile.net/",
                    "iYkvhkWHEsIcBuVkpBqznTqhFQhxOp89", mContext)
                    .withFilter(new ProgressFilter());
            //Setting the Accounts table to the accounts variable
            mTableAccounts = mClient.getTable("Accounts");
            //Setting the AuthData table to the authdata variable
            mTableAuthData = mClient.getTable("AuthData");
            //Setting the BadAuth table to the badauth variable
            mTableBadAuth = mClient.getTable("BadAuth");
        } catch (MalformedURLException e) {
            Log.e(TAG, "There was an error creating the Mobile Service.  Verify the URL");
        }
    }

    public void setContext(Context context) {
        mClient.setContext(context);
    }

    public String getUserId() {
        return mClient.getCurrentUser().getUserId();
    }

    //Show the login dialog
    public void login(Context activityContext, MobileServiceAuthenticationProvider provider, UserAuthenticationCallback callback) {
        mProvider = provider;
        mClient.setContext(activityContext);
        mClient.login(provider, callback);
    }

    /**
     * Handles logging in with custom auth
     * @param email
     * @param password
     * @param callback
     */
    public void login(String email, String password, TableJsonOperationCallback callback) {
        JsonObject customUser = new JsonObject();
        customUser.addProperty("email", email);
        customUser.addProperty("password", password);

        List<Pair<String,String>> parameters = new ArrayList<Pair<String, String>>();
        parameters.add(new Pair<String, String>("login", "true"));

        mTableAccounts.insert(customUser, parameters, callback);
    }

    public void getAuthData(TableJsonQueryCallback callback) {
        mTableAuthData.where().execute(callback);
    }

    /**
     * Checks to see if we have userId and token stored on the device and sets them if so
     * @return
     */
    public boolean isUserAuthenticated() {
        SharedPreferences settings = mContext.getSharedPreferences("UserData", 0);
        if (settings != null) {
            String userId = settings.getString("userid", null);
            String token = settings.getString("token", null);
            if (userId != null && !userId.equals("")) {
                setUserData(userId, token);
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a nwe MobileServiceUser using a userId and token passed in.
     * Also sets the current provider
     * @param userId
     * @param token
     */
    public void setUserData(String userId, String token) {
        MobileServiceUser user = new MobileServiceUser(userId);
        user.setAuthenticationToken(token);
        mClient.setCurrentUser(user);

        //Check for custom provider
        String provider = userId.substring(0, userId.indexOf(":"));
        if (provider.equals("Custom")) {
            mProvider = null;
            mIsCustomAuthProvider = true;
        } else if (provider.equals("Facebook"))
            mProvider = MobileServiceAuthenticationProvider.Facebook;
        else if (provider.equals("Twitter"))
            mProvider = MobileServiceAuthenticationProvider.Twitter;
        else if (provider.equals("MicrosoftAccount"))
            mProvider = MobileServiceAuthenticationProvider.MicrosoftAccount;
        else if (provider.equals("Google"))
            mProvider = MobileServiceAuthenticationProvider.Google;
    }

    /***
     * Pulls the user ID and token out of a json object from the server
     * @param jsonObject
     */
    public void setUserAndSaveData(JsonObject jsonObject) {
        String userId = jsonObject.getAsJsonPrimitive("userId").getAsString();
        String token = jsonObject.getAsJsonPrimitive("token").getAsString();
        setUserData(userId, token);
        saveUserData();
    }

    /**
     * Saves userId and token to SharedPreferences.
     * NOTE:  This is not secure and is just used as a storage mechanism.  In reality, you would want to
     * come up with a more secure way of storing this information.
     */
    public void saveUserData() {
        SharedPreferences settings = mContext.getSharedPreferences("UserData", 0);
        SharedPreferences.Editor preferencesEditor = settings.edit();
        preferencesEditor.putString("userid", mClient.getCurrentUser().getUserId());
        preferencesEditor.putString("token", mClient.getCurrentUser().getAuthenticationToken());
        preferencesEditor.commit();
    }


    public void registerUser(String password, String confirm, String username,
                             String neighbourhood, String membership, String email,
                             TableJsonOperationCallback callback) {
        JsonObject newUser = new JsonObject();
        newUser.addProperty("password", password);
        newUser.addProperty("username", username);
        newUser.addProperty("neighbourhood", neighbourhood);
        newUser.addProperty("membership", membership);
        newUser.addProperty("email", email);
        mTableAccounts.insert(newUser, callback);
    }

    /**
     * Handles logging the user out including:
     * -deleting cookies so their login with a provider won't be cached in the web view
     * -removing the userdata from the shared preferences
     * -setting the current user object on the client to logged out
     * -optionally redirects to the login page if requested
     * @param shouldRedirectToLogin
     */
    public void logout(boolean shouldRedirectToLogin) {
        //Clear the cookies so they won't auto login to a provider again
        CookieSyncManager.createInstance(mContext);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        //Clear the user id and token from the shared preferences
        SharedPreferences settings = mContext.getSharedPreferences("UserData", 0);
        SharedPreferences.Editor preferencesEditor = settings.edit();
        preferencesEditor.clear();
        preferencesEditor.commit();
        //Clear the user and return to the auth activity
        mClient.logout();
        //Take the user back to the auth activity to relogin if requested
        if (shouldRedirectToLogin) {
            Intent logoutIntent = new Intent(mContext, CoverPage.class);
            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(logoutIntent);
        }
    }

    /**
     * Calls a method on the server that will auto trigger a 401 result
     * @param shouldRetry
     * @param callback
     */
    public void testForced401(boolean shouldRetry,
                              TableJsonOperationCallback callback) {
        JsonObject data = new JsonObject();
        data.addProperty("data", "data");
        mShouldRetryAuth = shouldRetry;
        mTableBadAuth.insert(data, callback);
    }

    private class ProgressFilter implements ServiceFilter {

        @Override
        public ListenableFuture<ServiceFilterResponse> handleRequest(ServiceFilterRequest request, NextServiceFilterCallback nextServiceFilterCallback) {

            final SettableFuture<ServiceFilterResponse> resultFuture = SettableFuture.create();


            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.VISIBLE);
                }
            });

            ListenableFuture<ServiceFilterResponse> future = nextServiceFilterCallback.onNext(request);

            Futures.addCallback(future, new FutureCallback<ServiceFilterResponse>() {
                @Override
                public void onFailure(Throwable e) {
                    resultFuture.setException(e);
                }

                @Override
                public void onSuccess(ServiceFilterResponse response) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.GONE);
                        }
                    });

                    resultFuture.set(response);
                }
            });

            return resultFuture;
        }
    }
}