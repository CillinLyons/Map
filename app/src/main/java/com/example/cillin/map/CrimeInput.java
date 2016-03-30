package com.example.cillin.map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;
import android.widget.Toast;


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
 * Created by Cillin on 24/01/2016.
 */
public class CrimeInput extends  Activity
{
    private MobileServiceClient mClient;
    /**
     * Mobile Service Table used to access data
     */
    private MobileServiceTable<Crime> mToDoTable;

    /**
     * EditText containing the "New To Do" text
     */
    private Spinner crimeSpinnerVar;
    private EditText neighborhood;
    private String county;
    private Spinner timeSpinnerVar;
    private DatePicker datePickerVar;
    private EditText dateTextVar;
    private Activity mActivity;
    private Date date;
    private Button dateButton;
    private TextView mLocation;
    private int day, month, year;
    static final int DIALOG_ID = 0;
    private String theDate;
    private ImageView mIcon;

    final DialogBox dialogBox = new DialogBox();


    /**
     * Progress spinner to use for table operations
     */
    private ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crime_input);

        mActivity = this;

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

        mIcon = (ImageView) findViewById(R.id.CountyimageView4);
        ChangeIcon.setIcon(county, mIcon);
        mLocation = (TextView) findViewById(R.id.crime_input_location);
        mLocation.setText(county);

        try {
            // Create the Mobile Service Client instance, using the provided

            // Mobile Service URL and key
            mClient = new MobileServiceClient(
                    "https://smartneighborhoodwatch.azure-mobile.net/",
                    "iYkvhkWHEsIcBuVkpBqznTqhFQhxOp89",
                    this).withFilter(new ProgressFilter());

            // Get the Mobile Service Table instance to use
            mToDoTable = mClient.getTable(Crime.class);

            //Init local storage
            initLocalStore().get();
        }
        catch (MalformedURLException e) {
            createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
        }
        catch (Exception e) {
            createAndShowDialog(e, "Error");
        }

        final Calendar cal = Calendar.getInstance();

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        crimeSpinnerVar = (Spinner) findViewById(R.id.crimeSpinner);
        timeSpinnerVar = (Spinner) findViewById(R.id.timeSpinner);
        //datePickerVar = (DatePicker) findViewById(R.id.datePicker);
        neighborhood = (EditText) findViewById(R.id.neighborhoodText);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> crime_adapter = ArrayAdapter.createFromResource(this,
                R.array.crime_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        crime_adapter.setDropDownViewResource(R.layout.spinner_item);
        // Apply the adapter to the spinner
        crimeSpinnerVar.setAdapter(crime_adapter);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> time_adapter = ArrayAdapter.createFromResource(this,
                R.array.time_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        time_adapter.setDropDownViewResource(R.layout.spinner_item);
        // Apply the adapter to the spinner
        timeSpinnerVar.setAdapter(time_adapter);

        showDialogOnButtonClick();
    }

    public void showDialogOnButtonClick()
    {
        dateButton = (Button) findViewById(R.id.btnDate);

        dateButton.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        showDialog(DIALOG_ID);
                    }
                }
        );
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        if(id == DIALOG_ID)
            return new DatePickerDialog(mActivity, pickListener, year, month, day);
        return  null;
    }

    private DatePickerDialog.OnDateSetListener pickListener = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int theYear, int theMonth, int theDay)
        {
            year = theYear;
            day = theDay;
            month = theMonth + 1;
            Toast date = Toast.makeText(mActivity, year+ "-" + month + "-" + day, Toast.LENGTH_SHORT);
            date.show();
            day = theDay+1;
            theDate = year + "-" + month + "-" + day;
        }
    };

    public void addItem(View view) {
        if (mClient == null) {
            return;
        }
        // Create a new item
        final Crime crime = new Crime();

        crime.setCounty(county);
        crime.setNeighborhood(neighborhood.getText().toString());
        crime.setCrime(crimeSpinnerVar.getSelectedItem().toString());
        crime.setTime(timeSpinnerVar.getSelectedItem().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(theDate);
            crime.setDate(date);
        } catch (Exception e) {

        }
        if (neighborhood.getText().toString().equals("") ||
                crimeSpinnerVar.getSelectedItem().toString().equals("Select Crime") ||
                timeSpinnerVar.getSelectedItem().toString().equals("Select Time"))
        {
            dialogBox.emptyFields(mActivity);
        }
        else
        // Insert the new item
        {
            AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params)
                {
                    try
                    {
                        final Crime entity = addItemInTable(crime);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mToDoTable.insert(entity);
                            }
                        });
                    } catch (final Exception e) {
                        dialogBox.emptyFields(mActivity);
                    }
                    return null;
                }
            };


            runAsyncTask(task);
            crimeSpinnerVar.setSelection(0);
            timeSpinnerVar.setSelection(0);
            //dateTextVar.setText("");
            neighborhood.setText("");
            mActivity.finish();
            Intent firstActivity = new Intent(mActivity, InfoWindowList.class);
            String countyString = "Kildare South";
            firstActivity.putExtra("COUNTY", countyString);
            startActivity(firstActivity);
        }

    }

    /**
     * Add an item to the Mobile Service Table
     */
    public Crime addItemInTable(Crime item) throws ExecutionException, InterruptedException
    {
        Crime entity = mToDoTable.insert(item).get();
        return entity;
    }

    /**
     * Initialize local storage
     * @return
     * @throws MobileServiceLocalStoreException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private AsyncTask<Void, Void, Void> initLocalStore() throws MobileServiceLocalStoreException, ExecutionException, InterruptedException {

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
