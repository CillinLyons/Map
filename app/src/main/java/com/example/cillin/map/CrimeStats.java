package com.example.cillin.map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private MobileServiceTable<Crime2> mToDoTable2;


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
    private TextView mCrimeLoc;
    private Activity mActivity;
    private ProgressBar mProgressBar;
    private ProgressDialog mProgressBar2;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        mActivity = this;

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
        mCrimeLoc = (TextView) findViewById(R.id.crime_stats_location);
        mCrimeLoc.setText(countyString);
        mProgressBar2 = new ProgressDialog(mActivity);


        try {
            // Create the Mobile Service Client instance, using the provided

            // Mobile Service URL and key
            mClient = new MobileServiceClient(
                    "https://smartneighborhoodwatch.azure-mobile.net/",
                    "iYkvhkWHEsIcBuVkpBqznTqhFQhxOp89",
                    this).withFilter(new ProgressFilter());


            mToDoTable = mClient.getTable(Crime.class);
            mToDoTable2 = mClient.getTable("Crime", Crime2.class);

            //Init local storage
            //initLocalStore().get();
            mAssault = (TextView) findViewById(R.id.AssaultInputtextView);
            mBurglary = (TextView) findViewById(R.id.BurglaryInputtextView);
            mCriminalDamage = (TextView) findViewById(R.id.CrimeInputtextView);
            mTheft = (TextView) findViewById(R.id.TheftInputtextView);

            mCrimeMP = (TextView) findViewById(R.id.CrimeInput2textView);
            mAreaMP = (TextView) findViewById(R.id.AreaInputtextView);
            mTimeMP = (TextView) findViewById(R.id.TimeInput1textView);

            mCrimeLP = (TextView) findViewById(R.id.CrimeInput3textView);
            mAreaLP = (TextView) findViewById(R.id.AreaInput2textView);
            mTimeLP = (TextView) findViewById(R.id.TimeInput2textView);

            mProgressBar2.setTitle("Loading Stats...");
            mProgressBar2.setMessage("Please wait...");
            mProgressBar2.setIndeterminate(true);
            mProgressBar2.setCancelable(true);
            mProgressBar2.show();
            assaultCount();
            theftCount();
            criminalDamageCount();
            burglaryCount();
            mostPopCrime();
            mostPopNeighborhood();
            mostPopTime();
            leastPopCrime();
            leastPopNeighborhood();
            leastPopTime();
            mProgressBar2.dismiss();

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

    public void assaultCount()
    {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Calendar c = Calendar.getInstance();
                    int month = c.get(Calendar.MONTH);
                    int realMonth = month + 1;

                    final MobileServiceList<Crime> assaultNum = mToDoTable.where().field("county").eq(countyString)
                            .and().field("crime").eq("Assault").and().month("date").eq(realMonth).includeInlineCount().execute().get();
                    String assaultString1 = String.valueOf(assaultNum);
                    String assaultString2 = assaultString1.replace("]", "");
                    String assaultString3 = assaultString2.replace("[", "");
                    int lastIndex = 0;
                    int count = 0;

                    while(lastIndex != -1){

                        lastIndex = assaultString3.indexOf(countyString,lastIndex);

                        if(lastIndex != -1){
                            count ++;
                            lastIndex += countyString.length();
                        }
                    }
                    final int finalCount = count;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAssault.setText(String.valueOf(finalCount));
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
    }

    public void burglaryCount()
    {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Calendar c = Calendar.getInstance();
                    int month = c.get(Calendar.MONTH);
                    int realMonth = month + 1;

                    final MobileServiceList<Crime> burglaryNum = mToDoTable.where().field("county").eq(countyString)
                            .and().field("crime").eq("Burglary").and().month("date").eq(realMonth).includeInlineCount().execute().get();
                    String burglaryString1 = String.valueOf(burglaryNum);
                    String burglaryString2 = burglaryString1.replace("]", "");
                    String burglaryString3 = burglaryString2.replace("[", "");
                    int lastIndex = 0;
                    int count = 0;

                    while(lastIndex != -1){

                        lastIndex = burglaryString3.indexOf(countyString, lastIndex);

                        if(lastIndex != -1){
                            count ++;
                            lastIndex += countyString.length();
                        }
                    }
                    final int finalCount = count;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBurglary.setText(String.valueOf(finalCount));
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
    }

    public void theftCount()
    {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Calendar c = Calendar.getInstance();
                    int month = c.get(Calendar.MONTH);
                    int realMonth = month + 1;

                    final MobileServiceList<Crime> theftNum = mToDoTable.where().field("county").eq(countyString)
                            .and().field("crime").eq("Theft").and().month("date").eq(realMonth).includeInlineCount().execute().get();
                    String theftString1 = String.valueOf(theftNum);
                    String theftString2 = theftString1.replace("]", "");
                    String theftString3 = theftString2.replace("[", "");
                    int lastIndex = 0;
                    int count = 0;

                    while(lastIndex != -1){

                        lastIndex = theftString3.indexOf(countyString,lastIndex);

                        if(lastIndex != -1){
                            count ++;
                            lastIndex += countyString.length();
                        }
                    }
                    final int finalCount = count;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTheft.setText(String.valueOf(finalCount));
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
    }

    public void criminalDamageCount()
    {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Calendar c = Calendar.getInstance();
                    int month = c.get(Calendar.MONTH);
                    int realMonth = month + 1;

                    final MobileServiceList<Crime> criminalDamageNum = mToDoTable.where().field("county").eq(countyString)
                            .and().field("crime").eq("Criminal Damage").and().month("date").eq(realMonth).includeInlineCount().execute().get();
                    String cdString1 = String.valueOf(criminalDamageNum);
                    String cdString2 = cdString1.replace("]", "");
                    String cdString3 = cdString2.replace("[", "");
                    int lastIndex = 0;
                    int count = 0;

                    while(lastIndex != -1){

                        lastIndex = cdString3.indexOf(countyString,lastIndex);

                        if(lastIndex != -1){
                            count ++;
                            lastIndex += countyString.length();
                        }
                    }
                    final int finalCount = count;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCriminalDamage.setText(String.valueOf(finalCount));
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
    }

    public void mostPopCrime()
    {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Calendar c = Calendar.getInstance();
                    int month = c.get(Calendar.MONTH);
                    int realMonth = month + 1;

                    final MobileServiceList<Crime> theftNum = mToDoTable.where().field("county").eq(countyString)
                            .and().field("crime").eq("Theft").and().month("date").eq(realMonth).includeInlineCount().execute().get();

                    final MobileServiceList<Crime> criminalDamageNum = mToDoTable.where().field("county").eq(countyString)
                            .and().field("crime").eq("Criminal Damage").and().month("date").eq(realMonth).includeInlineCount().execute().get();

                    final MobileServiceList<Crime> assaultNum = mToDoTable.where().field("county").eq(countyString)
                            .and().field("crime").eq("Assault").and().month("date").eq(realMonth).includeInlineCount().execute().get();

                    final MobileServiceList<Crime> burglaryNum = mToDoTable.where().field("county").eq(countyString)
                            .and().field("crime").eq("Burglary").and().month("date").eq(realMonth).includeInlineCount().execute().get();

                    String theftString1 = String.valueOf(theftNum);
                    String theftString2 = theftString1.replace("]", "");
                    String theftString3 = theftString2.replace("[", "");
                    int lastIndex = 0;
                    int count = 0;

                    while(lastIndex != -1){

                        lastIndex = theftString3.indexOf(countyString,lastIndex);

                        if(lastIndex != -1){
                            count ++;
                            lastIndex += countyString.length();
                        }
                    }

                    String assaultString1 = String.valueOf(assaultNum);
                    String assaultString2 = assaultString1.replace("]", "");
                    String assaultString3 = assaultString2.replace("[", "");
                    int lastIndex2 = 0;
                    int count2 = 0;

                    while(lastIndex2 != -1){

                        lastIndex2 = assaultString3.indexOf(countyString,lastIndex2);

                        if(lastIndex2 != -1){
                            count2 ++;
                            lastIndex2 += countyString.length();
                        }
                    }

                    String cdString1 = String.valueOf(criminalDamageNum);
                    String cdString2 = cdString1.replace("]", "");
                    String cdString3 = cdString2.replace("[", "");
                    int lastIndex3 = 0;
                    int count3 = 0;

                    while(lastIndex3 != -1){

                        lastIndex3 = cdString3.indexOf(countyString,lastIndex3);

                        if(lastIndex3 != -1){
                            count3 ++;
                            lastIndex3 += countyString.length();
                        }
                    }

                    String burglaryString1 = String.valueOf(burglaryNum);
                    String burglaryString2 = burglaryString1.replace("]", "");
                    String burglaryString3 = burglaryString2.replace("[", "");
                    int lastIndex4 = 0;
                    int count4 = 0;

                    while(lastIndex4 != -1){

                        lastIndex4 = burglaryString3.indexOf(countyString,lastIndex4);

                        if(lastIndex4 != -1){
                            count4 ++;
                            lastIndex4 += countyString.length();
                        }
                    }

                    final int finalCount = count;
                    final int finalCount2 = count2;
                    final int finalCount3 = count3;
                    final int finalCount4 = count4;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(theftNum.toString().length() < 3 & criminalDamageNum.toString().length() < 3 & burglaryNum.toString().length() < 3 & assaultNum.toString().length() < 3)
                            {
                                mCrimeMP.setText("Not Registered");
                            }
                            else if(finalCount >= finalCount2 & finalCount >= finalCount3 & finalCount >= finalCount4) {
                                mCrimeMP.setText("Theft");
                            }
                            else if(finalCount2 >= finalCount & finalCount2 >= finalCount3 & finalCount2 >= finalCount4) {
                                mCrimeMP.setText("Assualt");
                            }
                            else if(finalCount3 >= finalCount & finalCount3 >= finalCount2 & finalCount3 >= finalCount4) {
                                mCrimeMP.setText("Criminal Damage");
                            }
                            else if(finalCount4 >= finalCount & finalCount4 >= finalCount2 & finalCount4 >= finalCount3) {
                                mCrimeMP.setText("Burglary");
                            }
                            else
                                mCrimeMP.setText("error");

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
    }

    public void mostPopNeighborhood()
    {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Calendar c = Calendar.getInstance();
                    int month = c.get(Calendar.MONTH);
                    int realMonth = month + 1;

                    final MobileServiceList<Crime2> result = mToDoTable2.where().field("county").eq(countyString).
                            and().month("date").eq(realMonth).select("neighborhood").execute().get();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(result.toString().length() < 3)
                            {
                                mAreaMP.setText("Not Registered");
                            }
                            else {
                                String str = result.toString();
                                String str2 = str.replace("]", "");
                                String str3 = str2.replace("[", "");
                                String[] arr = str3.split(", ");

                                List<String> list = Arrays.asList(arr);
                                Map<String, Integer> stringsCount = new HashMap<String, Integer>();
                                for (String string : list) {
                                    if (string.length() > 0) {
                                        string = string.toLowerCase();
                                        Integer count = stringsCount.get(string);
                                        if (count == null) count = new Integer(0);
                                        count++;
                                        stringsCount.put(string, count);
                                    }
                                }
                                Map.Entry<String, Integer> mostRepeated = null;
                                for (Map.Entry<String, Integer> e : stringsCount.entrySet()) {
                                    if (mostRepeated == null || mostRepeated.getValue() < e.getValue())
                                        mostRepeated = e;
                                }
                            /*String neighborhood = mostRepeated.getKey().toString();

                            if(neighborhood.contains(null))
                                mAreaMP.setText("Not registered");
                            else*/
                                mAreaMP.setText(mostRepeated.getKey());
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

    }

    public void mostPopTime()
    {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Calendar c = Calendar.getInstance();
                    int month = c.get(Calendar.MONTH);
                    int realMonth = month + 1;

                    final MobileServiceList<Crime> morningNum = mToDoTable.where().field("county").eq(countyString)
                            .and().field("time").eq("Morning").and().month("date").eq(realMonth).includeInlineCount().execute().get();

                    final MobileServiceList<Crime> afternoonNum = mToDoTable.where().field("county").eq(countyString)
                            .and().field("time").eq("Afternoon").and().month("date").eq(realMonth).includeInlineCount().execute().get();

                    final MobileServiceList<Crime> eveningNum = mToDoTable.where().field("county").eq(countyString)
                            .and().field("time").eq("Evening").and().month("date").eq(realMonth).includeInlineCount().execute().get();

                    final MobileServiceList<Crime> nightNum = mToDoTable.where().field("county").eq(countyString)
                            .and().field("time").eq("Night").and().month("date").eq(realMonth).includeInlineCount().execute().get();

                    String morningString1 = String.valueOf(morningNum);
                    String morningString2 = morningString1.replace("]", "");
                    String morningString3 = morningString2.replace("[", "");
                    int lastIndex = 0;
                    int count = 0;

                    while(lastIndex != -1){

                        lastIndex = morningString3.indexOf(countyString,lastIndex);

                        if(lastIndex != -1){
                            count ++;
                            lastIndex += countyString.length();
                        }
                    }

                    String afternoonString1 = String.valueOf(afternoonNum);
                    String afternoonString2 = afternoonString1.replace("]", "");
                    String afternoonString3 = afternoonString2.replace("[", "");
                    int lastIndex2 = 0;
                    int count2 = 0;

                    while(lastIndex2 != -1){

                        lastIndex2 = afternoonString3.indexOf(countyString,lastIndex2);

                        if(lastIndex2 != -1){
                            count2 ++;
                            lastIndex2 += countyString.length();
                        }
                    }

                    String eveningString1 = String.valueOf(eveningNum);
                    String eveningString2 = eveningString1.replace("]", "");
                    String eveningString3 = eveningString2.replace("[", "");
                    int lastIndex3 = 0;
                    int count3 = 0;

                    while(lastIndex3 != -1){

                        lastIndex3 = eveningString3.indexOf(countyString,lastIndex3);

                        if(lastIndex3 != -1){
                            count3 ++;
                            lastIndex3 += countyString.length();
                        }
                    }

                    String nightString1 = String.valueOf(nightNum);
                    String nightString2 = nightString1.replace("]", "");
                    String nightString3 = nightString2.replace("[", "");
                    int lastIndex4 = 0;
                    int count4 = 0;

                    while(lastIndex4 != -1){

                        lastIndex4 = nightString3.indexOf(countyString,lastIndex4);

                        if(lastIndex4 != -1){
                            count4 ++;
                            lastIndex4 += countyString.length();
                        }
                    }

                    final int finalCount = count;
                    final int finalCount2 = count2;
                    final int finalCount3 = count3;
                    final int finalCount4 = count4;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(morningNum.toString().length() < 3 & afternoonNum.toString().length() < 3 & eveningNum.toString().length() < 3 & nightNum.toString().length() < 3)
                            {
                                mTimeMP.setText("Not Registered");
                            }
                            else if(finalCount >= finalCount2 & finalCount >= finalCount3 & finalCount >= finalCount4) {
                                mTimeMP.setText("Morning");
                            }
                            else if(finalCount2 >= finalCount & finalCount2 >= finalCount3 & finalCount2 >= finalCount4) {
                                mTimeMP.setText("Afternoon");
                            }
                            else if(finalCount3 >= finalCount & finalCount3 >= finalCount2 & finalCount3 >= finalCount4) {
                                mTimeMP.setText("Evening");
                            }
                            else if(finalCount4 >= finalCount & finalCount4 >= finalCount2 & finalCount4 >= finalCount3) {
                                mTimeMP.setText("Night");
                            }
                            else
                                mTimeMP.setText("error");

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
    }

    public void leastPopCrime()
    {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Calendar c = Calendar.getInstance();
                    int month = c.get(Calendar.MONTH);
                    int realMonth = month + 1;

                    final MobileServiceList<Crime> theftNum = mToDoTable.where().field("county").eq(countyString)
                            .and().field("crime").eq("Theft").and().month("date").eq(realMonth).includeInlineCount().execute().get();

                    final MobileServiceList<Crime> criminalDamageNum = mToDoTable.where().field("county").eq(countyString)
                            .and().field("crime").eq("Criminal Damage").and().month("date").eq(realMonth).includeInlineCount().execute().get();

                    final MobileServiceList<Crime> assaultNum = mToDoTable.where().field("county").eq(countyString)
                            .and().field("crime").eq("Assault").and().month("date").eq(realMonth).includeInlineCount().execute().get();

                    final MobileServiceList<Crime> burglaryNum = mToDoTable.where().field("county").eq(countyString)
                            .and().field("crime").eq("Burglary").and().month("date").eq(realMonth).includeInlineCount().execute().get();

                    String theftString1 = String.valueOf(theftNum);
                    String theftString2 = theftString1.replace("]", "");
                    String theftString3 = theftString2.replace("[", "");
                    int lastIndex = 0;
                    int count = 0;

                    while(lastIndex != -1){

                        lastIndex = theftString3.indexOf(countyString,lastIndex);

                        if(lastIndex != -1){
                            count ++;
                            lastIndex += countyString.length();
                        }
                    }

                    String assaultString1 = String.valueOf(assaultNum);
                    String assaultString2 = assaultString1.replace("]", "");
                    String assaultString3 = assaultString2.replace("[", "");
                    int lastIndex2 = 0;
                    int count2 = 0;

                    while(lastIndex2 != -1){

                        lastIndex2 = assaultString3.indexOf(countyString,lastIndex2);

                        if(lastIndex2 != -1){
                            count2 ++;
                            lastIndex2 += countyString.length();
                        }
                    }

                    String cdString1 = String.valueOf(criminalDamageNum);
                    String cdString2 = cdString1.replace("]", "");
                    String cdString3 = cdString2.replace("[", "");
                    int lastIndex3 = 0;
                    int count3 = 0;

                    while(lastIndex3 != -1){

                        lastIndex3 = cdString3.indexOf(countyString,lastIndex3);

                        if(lastIndex3 != -1){
                            count3 ++;
                            lastIndex3 += countyString.length();
                        }
                    }

                    String burglaryString1 = String.valueOf(burglaryNum);
                    String burglaryString2 = burglaryString1.replace("]", "");
                    String burglaryString3 = burglaryString2.replace("[", "");
                    int lastIndex4 = 0;
                    int count4 = 0;

                    while(lastIndex4 != -1){

                        lastIndex4 = burglaryString3.indexOf(countyString,lastIndex4);

                        if(lastIndex4 != -1){
                            count4 ++;
                            lastIndex4 += countyString.length();
                        }
                    }

                    final int finalCount = count;
                    final int finalCount2 = count2;
                    final int finalCount3 = count3;
                    final int finalCount4 = count4;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(theftNum.toString().length() < 3 & burglaryNum.toString().length() < 3 & assaultNum.toString().length() < 3 & criminalDamageNum.toString().length() < 3)
                            {
                                mCrimeLP.setText("Not Registered");
                            }
                            else if(finalCount <= finalCount2 & finalCount <= finalCount3 & finalCount <= finalCount4) {
                                mCrimeLP.setText("Theft");
                            }
                            else if(finalCount2 <= finalCount & finalCount2 <= finalCount3 & finalCount2 <= finalCount4) {
                                mCrimeLP.setText("Assault");
                            }
                            else if(finalCount3 <= finalCount & finalCount3 <= finalCount2 & finalCount3 <= finalCount4) {
                                mCrimeLP.setText("Criminal Damage");
                            }
                            else if(finalCount4 <= finalCount & finalCount4 <= finalCount2 & finalCount4 <= finalCount3) {
                                mCrimeLP.setText("Burglary");
                            }
                            else
                                mCrimeLP.setText("error");
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
    }

    public void leastPopNeighborhood()
    {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Calendar c = Calendar.getInstance();
                    int month = c.get(Calendar.MONTH);
                    int realMonth = month + 1;

                    final MobileServiceList<Crime2> result = mToDoTable2.where().field("county").eq(countyString).
                            and().month("date").eq(realMonth).select("neighborhood").execute().get();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(result.toString().length() < 3)
                            {
                                mAreaLP.setText("Not Registered");
                            }
                            else {
                                String str = result.toString();
                                String str2 = str.replace("]", "");
                                String str3 = str2.replace("[", "");
                                String[] arr = str3.split(", ");
                                List<String> list = Arrays.asList(arr);
                                Map<String, Integer> stringsCount = new HashMap<String, Integer>();
                                for (String string : list) {
                                    if (string.length() > 0) {
                                        string = string.toLowerCase();
                                        Integer count = stringsCount.get(string);
                                        if (count == null) count = new Integer(0);
                                        count++;
                                        stringsCount.put(string, count);
                                    }
                                }
                                Map.Entry<String, Integer> mostRepeated = null;
                                for (Map.Entry<String, Integer> e : stringsCount.entrySet()) {
                                    if (mostRepeated == null || mostRepeated.getValue() > e.getValue())
                                        mostRepeated = e;
                                }

                                mAreaLP.setText(mostRepeated.getKey());
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
    }

    public void leastPopTime()
    {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Calendar c = Calendar.getInstance();
                    int month = c.get(Calendar.MONTH);
                    int realMonth = month + 1;

                    final MobileServiceList<Crime> morningNum = mToDoTable.where().field("county").eq(countyString)
                            .and().field("time").eq("Morning").and().month("date").eq(realMonth).includeInlineCount().execute().get();

                    final MobileServiceList<Crime> afternoonNum = mToDoTable.where().field("county").eq(countyString)
                            .and().field("time").eq("Afternoon").and().month("date").eq(realMonth).includeInlineCount().execute().get();

                    final MobileServiceList<Crime> eveningNum = mToDoTable.where().field("county").eq(countyString)
                            .and().field("time").eq("Evening").and().month("date").eq(realMonth).includeInlineCount().execute().get();

                    final MobileServiceList<Crime> nightNum = mToDoTable.where().field("county").eq(countyString)
                            .and().field("time").eq("Night").and().month("date").eq(realMonth).includeInlineCount().execute().get();

                        String morningString1 = String.valueOf(morningNum);
                        String morningString2 = morningString1.replace("]", "");
                        String morningString3 = morningString2.replace("[", "");
                        int lastIndex = 0;
                        int count = 0;

                        while (lastIndex != -1) {

                            lastIndex = morningString3.indexOf(countyString, lastIndex);

                            if (lastIndex != -1) {
                                count++;
                                lastIndex += countyString.length();
                            }
                        }

                        String afternoonString1 = String.valueOf(afternoonNum);
                        String afternoonString2 = afternoonString1.replace("]", "");
                        String afternoonString3 = afternoonString2.replace("[", "");
                        int lastIndex2 = 0;
                        int count2 = 0;

                        while (lastIndex2 != -1) {

                            lastIndex2 = afternoonString3.indexOf(countyString, lastIndex2);

                            if (lastIndex2 != -1) {
                                count2++;
                                lastIndex2 += countyString.length();
                            }
                        }

                        String eveningString1 = String.valueOf(eveningNum);
                        String eveningString2 = eveningString1.replace("]", "");
                        String eveningString3 = eveningString2.replace("[", "");
                        int lastIndex3 = 0;
                        int count3 = 0;

                        while (lastIndex3 != -1) {

                            lastIndex3 = eveningString3.indexOf(countyString, lastIndex3);

                            if (lastIndex3 != -1) {
                                count3++;
                                lastIndex3 += countyString.length();
                            }
                        }

                        String nightString1 = String.valueOf(nightNum);
                        String nightString2 = nightString1.replace("]", "");
                        String nightString3 = nightString2.replace("[", "");
                        int lastIndex4 = 0;
                        int count4 = 0;

                        while (lastIndex4 != -1) {

                            lastIndex4 = nightString3.indexOf(countyString, lastIndex4);

                            if (lastIndex4 != -1) {
                                count4++;
                                lastIndex4 += countyString.length();
                            }
                        }

                        final int finalCount = count;
                        final int finalCount2 = count2;
                        final int finalCount3 = count3;
                        final int finalCount4 = count4;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(morningNum.toString().length() < 3 & afternoonNum.toString().length() < 3 & eveningNum.toString().length() < 3 & nightNum.toString().length() < 3)
                                {
                                    mTimeLP.setText("Not Registered");
                                }
                                else if (finalCount <= finalCount2 & finalCount <= finalCount3 & finalCount <= finalCount4) {
                                    mTimeLP.setText("morning");
                                } else if (finalCount2 <= finalCount & finalCount2 <= finalCount3 & finalCount2 <= finalCount4) {
                                    mTimeLP.setText("afternoon");
                                } else if (finalCount3 <= finalCount & finalCount3 <= finalCount2 & finalCount3 <= finalCount4) {
                                    mTimeLP.setText("evening");
                                } else if (finalCount4 <= finalCount & finalCount4 <= finalCount2 & finalCount4 <= finalCount3) {
                                    mTimeLP.setText("night");
                                } else
                                    mTimeLP.setText("error");

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
