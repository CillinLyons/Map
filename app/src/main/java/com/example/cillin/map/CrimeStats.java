package com.example.cillin.map;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.ExecutableQuery;

import java.net.MalformedURLException;

/**
 * Created by Cillin on 29/02/2016.
 */
public class CrimeStats extends Activity
{

   /* public ExecutableQuery<E> includeInlineCount()
    {
      return this.where().includeInlineCount();
    }*/

    /**
     * Mobile Service Client reference
     */
    private MobileServiceClient mClient;

    /**
     * Mobile Service Table used to access data
     */
    private MobileServiceTable<Crime> mToDoTable;


    //Criminal Activities
    private TextView mAssault;
    private TextView mBurglary;
    private TextView mCriminalDamage;
    private TextView mTheft;

    //County
    private String countyString;

    //Most Popular
    private TextView mCrimeMP;
    private TextView mAreaMP;
    private TextView mTimeMP;

    //Least Popular
    private TextView mCrimeLP;
    private TextView mAreaLP;
    private TextView mTimeLP;
    private ImageView mIcon;

    /**
     * Progress spinner to use for table operations
     */
    private ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crime_stats);

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

        mIcon = (ImageView) findViewById(R.id.CountyimageView3);
        ChangeIcon.setIcon(countyString, mIcon);


        try {
            // Create the Mobile Service Client instance, using the provided

            // Mobile Service URL and key
            mClient = new MobileServiceClient(
                    "https://smartneighborhoodwatch.azure-mobile.net/",
                    "iYkvhkWHEsIcBuVkpBqznTqhFQhxOp89",
                    this).withFilter(new ProgressFilter());


            mToDoTable = mClient.getTable(Crime.class);

            //Init local storage
            //initLocalStore().get();
            mAssault = (TextView) findViewById(R.id.AssaultInputtextView);
            mBurglary = (TextView) findViewById(R.id.BurglaryInputtextView);
            mCriminalDamage = (TextView) findViewById(R.id.CrimeInputtextView);
            mTheft = (TextView) findViewById(R.id.TheftInputtextView);

            mCrimeMP = (TextView) findViewById(R.id.CrimeInput2textView);
            mAreaMP = (TextView) findViewById(R.id.AssaultInputtextView);
            mTimeMP = (TextView) findViewById(R.id.TimeInput1textView);

            mCrimeLP = (TextView) findViewById(R.id.CrimeInput3textView);
            mAreaLP = (TextView) findViewById(R.id.AreaInput2textView);
            mTimeLP = (TextView) findViewById(R.id.TimeInput2textView);

        }
        catch (MalformedURLException e)
        {
            createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
        }
        catch (Exception e)
        {
            createAndShowDialog(e, "Error");
        }
    }


   /*public void displayTable()
    {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    //MyMarker myMarker = mMarkersHashMap.get(marker);
                    //String title = myMarker.getmLabel();

                    //final MobileServiceList<Crime> result = mToDoTable.where("sdcsdc").eq("dsd").execute().get();



                    //.orderBy("date", QueryOrder.Descending).execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            mAssault.setText("");
                            mBurglary.setText("");
                            mCriminalDamage.setText("");
                            mTheft.setText("");
                            mCrimeMP.setText("");
                            mAreaMP.setText("");
                            mTimeMP.setText("");
                            mCrimeLP.setText("");
                            mAreaLP.setText("");
                            mTimeLP.setText("");
                            for (Crime item : result) {
                                mAssault.setText(item.toString());
                            }
                        }
                    });
                }
                catch (Exception exception)
                {
                    createAndShowDialog(exception, "Error");
                }
                return null;
            }
        }.execute();
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
