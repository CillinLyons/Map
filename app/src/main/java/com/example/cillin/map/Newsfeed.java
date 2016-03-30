package com.example.cillin.map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;

import java.net.MalformedURLException;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by Cillin on 03/02/2016.
 */
public class Newsfeed extends Activity
{
    /**
     * Mobile Service Client reference
     */
    private MobileServiceClient mClient;

    /**
     * Mobile Service Table used to access data
     */
    private MobileServiceTable<NewsfeedItems> mToDoTable;
    private MobileServiceTable<UserMembership> mToDoTable2;
    private MobileServiceTable<UserNeighborhood> mToDoTable3;

    /**
     * Adapter to sync the items list with the view
     */
    private NewsfeedAdapter mAdapter;

    /**
     * EditText containing the "New To Do" text
     */
    private TextView mTxtUsername;
    private TextView mTxtMembership;
    private TextView mTxtArea;
    private TextView mTxtMessage;
    private TextView mTxtNewsfeedCounty;
    private ImageView mIcon;
    private ImageView mNHW;

    private ImageView mBtnMessage;
    private EditText mMessage;


    private ProgressBar mProgressBar;
    private TextView county;
    private String countyString;
    private String mUsername;
    private String mMembership;
    private String mNeighborhood;
    private String message;

    private final DialogBox dialogBox = new DialogBox();

    private final String TAG = "Newsfeed";
    private Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsfeed);

        mActivity = this;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                countyString= null;
            } else {
                countyString= extras.getString("COUNTY");
            }
        } else {
            countyString= (String) savedInstanceState.getSerializable("COUNTY");
        }

        county = (TextView)findViewById(R.id.newsfeed_location);
        county.setText(countyString);
        mIcon = (ImageView) findViewById(R.id.CountyimageView);
        ChangeIcon.setIcon(countyString, mIcon);

        try {
            // Create the Mobile Service Client instance, using the provided

            // Mobile Service URL and key
            mClient = new MobileServiceClient(
                    "https://smartneighborhoodwatch.azure-mobile.net/",
                    "iYkvhkWHEsIcBuVkpBqznTqhFQhxOp89",
                    this).withFilter(new ProgressFilter());


            mToDoTable = mClient.getTable(NewsfeedItems.class);
            mToDoTable2 = mClient.getTable("Accounts", UserMembership.class);
            mToDoTable3 = mClient.getTable("Accounts", UserNeighborhood.class);

            mTxtUsername = (TextView) findViewById(R.id.DBusername);
            mTxtMembership = (TextView) findViewById(R.id.DBmembership);
            mTxtArea = (TextView) findViewById(R.id.DBarea);
            mTxtMessage = (TextView) findViewById(R.id.DBmessage);

            // Create an adapter to bind the items with the view
            mAdapter = new NewsfeedAdapter(this, R.layout.newsfeed_items);
            ListView listViewToDo = (ListView) findViewById(R.id.listViewDB);
            listViewToDo.setAdapter(mAdapter);

        }
        catch (MalformedURLException e)
        {
            createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
        }
        catch (Exception e)
        {
            createAndShowDialog(e, "Error");
        }

        mMessage = (EditText) findViewById(R.id.Message);
        mBtnMessage = (ImageView) findViewById(R.id.messageBtn);

        mBtnMessage.setOnClickListener(sendNewsfeedMessage);

        displayTable();
    }

    View.OnClickListener sendNewsfeedMessage = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (mMessage.getText().toString().equals("")) {

                dialogBox.emptyFields(mActivity);
            } else {
                NBHAuthenticationApplication myApp = (NBHAuthenticationApplication) getApplication();
                AuthService authService = myApp.getAuthService();
                //Fetch auth data (the username) on load
                authService.getAuthData(new TableJsonQueryCallback() {
                    @Override
                    public void onCompleted(JsonElement result, Exception exception,
                                            ServiceFilterResponse response) {
                        if (exception == null) {
                            JsonArray results = result.getAsJsonArray();
                            JsonElement item = results.get(0);
                            mUsername = item.getAsJsonObject().getAsJsonPrimitive("UserName").getAsString();
                            getUserInfo();
                        } else {
                            dialogBox.NotLoggedIn(mActivity);
                            Intent login = new Intent(mActivity, CoverPage.class);
                            startActivity(login);
                        }
                    }
                });

                //mMessage.setText("");
                mActivity.finish();
                Intent backToMenu = new Intent(mActivity, InfoWindowList.class);
                backToMenu.putExtra("COUNTY", county.getText().toString());
                startActivity(backToMenu);
            }
        }
    };

    public void displayTable()
    {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Calendar c = Calendar.getInstance();
                    int lastMonth = c.get(Calendar.MONTH);
                    int thisMonth = lastMonth + 1;

                    final MobileServiceList<NewsfeedItems> result = mToDoTable.where().field("location").eq(countyString).
                            and().month("date").eq(thisMonth).
                            orderBy("date", QueryOrder.Descending).top(100).execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            mAdapter.clear();
                            for (NewsfeedItems item : result)
                            {
                                mAdapter.add(item);
                            }
                        }
                    });
                }
                catch (Exception exception)
                {
                    dialogBox.NotLoggedIn(mActivity);
                }
                return null;
            }
        }.execute();
    }

    public NewsfeedItems addItemInTable(NewsfeedItems item) throws ExecutionException, InterruptedException
    {
        NewsfeedItems entity = mToDoTable.insert(item).get();
        return entity;
    }


    public void getUserInfo()
    {
        final String county2 = county.getText().toString();
        message = mMessage.getText().toString();

            AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        final MobileServiceList<UserMembership> result = mToDoTable2.where().field("username").eq(mUsername).execute().get();
                        final MobileServiceList<UserNeighborhood> result2 = mToDoTable3.where().field("username").eq(mUsername).execute().get();

                        final String membership = result.toString();
                        String membership2 = membership.replace("]", "");
                        String membership3 = membership2.replace("[", "");

                        final String neighborhood = result2.toString();
                        String neighborhood2 = neighborhood.replace("]", "");
                        String neighborhood3 = neighborhood2.replace("[", "");

                        final NewsfeedItems items = new NewsfeedItems();


                        items.setMembership(membership3);
                        items.setArea(neighborhood3);
                        items.setMessage(message);
                        items.setUsername(mUsername);
                        items.setLocation(county2);
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Date date = new Date();
                        dateFormat.format(date);
                        items.setDate(date);
                        final NewsfeedItems entity = addItemInTable(items);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // mMembership = test;
                                //mNeighborhood = test2;
                                mToDoTable.insert(entity);
                            }
                        });
                    } catch (Exception exception) {
                        dialogBox.NotLoggedIn(mActivity);
                    }
                    return null;
                }
            }.execute();

    }


    /**
     * Creates a dialog and shows it
     *
     * @param exception
     *            The exception to show in the dialog
     * @param title
     *            The dialog title
     */
    private void createAndShowDialogFromTask(final Exception exception, String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                createAndShowDialog(exception, "Error");
            }
        });
    }


    /**
     * Creates a dialog and shows it
     *
     * @param exception
     *            The exception to show in the dialog
     * @param title
     *            The dialog title
     */
    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if(exception.getCause() != null){
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }

    /**
     * Creates a dialog and shows it
     *
     * @param message
     *            The dialog message
     * @param title
     *            The dialog title
     */
    private void createAndShowDialog(final String message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }

    /**
     * Run an ASync task on the corresponding executor
     * @param task
     * @return
     */
    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
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

