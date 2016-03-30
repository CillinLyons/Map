package com.example.cillin.map;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

/**
 * Created by Cillin on 19/03/2016.
 */
public class RegisteredNeighborhoods extends Activity
{
    private ProgressBar mProgressBar;

    /**
     * Mobile Service Client reference
     */
    private MobileServiceClient mClient;

    /**
     * Mobile Service Table used to access data
     */
    private MobileServiceTable<GetNeighborhoods> mToDoTable;
    private TextView mCounty;
    private TextView mNeighborhoods;
    private Activity mActivity;
    private DialogBox dialogBox = new DialogBox();
    private String countyString;
    private String theCounty;
    private ImageView mIcon;
    private RegisteredNBHAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registered_neighborhoods);

        // Create an adapter to bind the items with the view
        mAdapter = new RegisteredNBHAdapter(this, R.layout.registered_neighborhood_items);
        ListView listViewToDo = (ListView) findViewById(R.id.listViewNBH);
        listViewToDo.setAdapter(mAdapter);

        mCounty = (TextView) findViewById(R.id.textViewRegCounty);
        mNeighborhoods = (TextView) findViewById(R.id.textViewNBHItems);

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
        mIcon = (ImageView) findViewById(R.id.CountyimageView5);
        ChangeIcon.setIcon(countyString, mIcon);

        try {
            // Create the Mobile Service Client instance, using the provided

            // Mobile Service URL and key
            mClient = new MobileServiceClient(
                    "https://neighborhoods.azure-mobile.net/",
                    "YTRhlThmenNFYxgZerBkRixBZMQpCY37",
                    this).withFilter(new ProgressFilter());

            mToDoTable = mClient.getTable("Accounts", GetNeighborhoods.class);
        }
        catch (MalformedURLException e)
        {
        }
        catch (Exception e)
        {
        }

        final String CountyString = countyString.toString();
        String CountyString2 = CountyString.replace("South", "");
        String CountyString3 = CountyString2.replace("North", "");
        String CountyString4 = CountyString3.replace("East", "");
        String CountyString5 = CountyString4.replace("West", "");
        String CountyString6 = CountyString5.replace("Central", "");

        if(CountyString.matches(".*\\d+.*"))
            theCounty = "Dublin";
        else
            theCounty = CountyString6;

        mCounty.setText(theCounty);

        displayTable();
    }

    public void displayTable()
    {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MobileServiceList<GetNeighborhoods> result = mToDoTable.where().field("county").eq(theCounty).execute().get();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.clear();
                                    for (GetNeighborhoods item : result) {
                                        mAdapter.add(item);
                                    }
                            /*if(result.toString().length() < 1)
                            {
                                mNeighborhoods.setText("No neighborhoods in this county are currenly registered with smart neighborhood watch");
                            }
                            else
                                mNeighborhoods.setText(str3);*/
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
