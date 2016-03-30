package com.example.cillin.map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.support.v4.app.DialogFragment;


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

import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
/**
 * Created by Cillin on 05/02/2016.
 */
public class NewsfeedInput extends  Activity
{
    private MobileServiceClient mClient;
    /**
     * Mobile Service Table used to access data
     */
    private MobileServiceTable<NewsfeedItems> mToDoTable;

    /**
     * EditText containing the "New To Do" text
     */
    private EditText mUsername;
    private EditText mMembership;
    private EditText mArea;
    private EditText mMessage;
    private String county;


    /**
     * Progress spinner to use for table operations
     */
    private ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsfeed_input);

        //mProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);

        // Initialize the progress bar
        //mProgressBar.setVisibility(ProgressBar.GONE);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                county= null;
            } else {
                county= extras.getString("COUNTY");
            }
        } else {
            county= (String) savedInstanceState.getSerializable("COUNTY");
        }

        try {
            // Create the Mobile Service Client instance, using the provided

            // Mobile Service URL and key
            mClient = new MobileServiceClient(
                    "https://smartneighborhoodwatch.azure-mobile.net/",
                    "iYkvhkWHEsIcBuVkpBqznTqhFQhxOp89",
                    this).withFilter(new ProgressFilter());

            // Get the Mobile Service Table instance to use
            mToDoTable = mClient.getTable(NewsfeedItems.class);


            //Init local storage
            //initLocalStore().get();


        }
        catch (MalformedURLException e) {
            createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
        }
        catch (Exception e) {
            createAndShowDialog(e, "Error");
        }

        mUsername = (EditText) findViewById(R.id.editUsername);
        mMembership = (EditText) findViewById(R.id.editMembership);
        mArea = (EditText) findViewById(R.id.editArea);
        mMessage = (EditText) findViewById(R.id.editMessage);

    }

    public void addItem(View view) {
        if (mClient == null) {
            return;
        }

        // Create a new item
        final NewsfeedItems crime = new NewsfeedItems();


        String kildare = "Kildare South";
        crime.setUsername(mUsername.getText().toString());
        crime.setMembership(mMembership.getText().toString());
        crime.setArea(mArea.getText().toString());
        crime.setMessage(mMessage.getText().toString());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        dateFormat.format(date);
        crime.setDate(date);
        crime.setLocation(kildare);


        // Insert the new item
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    final NewsfeedItems entity = addItemInTable(crime);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                mToDoTable.insert(entity);

                        }
                    });
                }
                catch (final Exception e) {
                    createAndShowDialogFromTask(e, "Error");
                }
                return null;
            }
        };

        runAsyncTask(task);
    }

    /**
     * Add an item to the Mobile Service Table
     */
    public NewsfeedItems addItemInTable(NewsfeedItems item) throws ExecutionException, InterruptedException
    {
        NewsfeedItems entity = mToDoTable.insert(item).get();
        return entity;
    }

    /**
     * Initialize local storage
     * @return
     * @throws MobileServiceLocalStoreException
     * @throws ExecutionException
     * @throws InterruptedException
     */
   /* private AsyncTask<Void, Void, Void> initLocalStore() throws MobileServiceLocalStoreException, ExecutionException, InterruptedException {

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    MobileServiceSyncContext syncContext = mClient.getSyncContext();

                    if (syncContext.isInitialized())
                        return null;

                    SQLiteLocalStore localStore = new SQLiteLocalStore(mClient.getContext(), "OfflineStore", null, 1);

                    Map<String, ColumnDataType> tableDefinition = new HashMap<String, ColumnDataType>();
                    tableDefinition.put("id", ColumnDataType.String);
                    tableDefinition.put("county", ColumnDataType.String);
                    tableDefinition.put("compass", ColumnDataType.String);
                    tableDefinition.put("area", ColumnDataType.String);
                    tableDefinition.put("crime", ColumnDataType.String);
                    tableDefinition.put("time", ColumnDataType.String);
                    tableDefinition.put("date", ColumnDataType.String);
                    tableDefinition.put("complete", ColumnDataType.Boolean);

                    localStore.defineTable("Crime", tableDefinition);

                    SimpleSyncHandler handler = new SimpleSyncHandler();

                    syncContext.initialize(localStore, handler).get();

                } catch (final Exception e) {
                    createAndShowDialogFromTask(e, "Error");
                }

                return null;
            }
        };

        return runAsyncTask(task);
    }*/

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
