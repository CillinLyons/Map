package com.example.cillin.map;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MapsActivity extends Activity
{
    private final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private ArrayList<MyMarker> mMyMarkersArray = new ArrayList<MyMarker>();
    private HashMap<Marker, MyMarker> mMarkersHashMap;
    private TextView anotherLabel;
    private String mCounty;
    private String mUserRegEmail;
    private String mUserLoginEmail;
    private String mGardaRegEmail;
    private String mGardaLoginEmail;
    private String neighborhood;
    private Activity mActivity;

    private MobileServiceTable<Accounts> mToDoTable;
    private MobileServiceClient mClient;
    private ProgressBar mProgressBar;
    private LatLng latlng;
    private int county_zoom;

    private final DialogBox dialogBox = new DialogBox();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        anotherLabel = (TextView) findViewById(R.id.another_label);

        mActivity = this;

        try {
            // Create the Mobile Service Client instance, using the provided

            // Mobile Service URL and key
            mClient = new MobileServiceClient(
                    "https://neighborhoods.azure-mobile.net/",
                    "YTRhlThmenNFYxgZerBkRixBZMQpCY37",
                    this).withFilter(new ProgressFilter());


            mToDoTable = mClient.getTable(Accounts.class);


        } catch (MalformedURLException e) {
        } catch (Exception e) {
            dialogBox.NoConnection(mActivity);
            Intent login = new Intent(mActivity, CoverPage.class);
            startActivity(login);
        }

       /* final MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        final MapWrapperLayout mapWrapperLayout = (MapWrapperLayout)findViewById(R.id.map_relative_layout);
        mMap = mapFragment.getMap();
        mapWrapperLayout.init(mMap, getPixelsFromDp(this, 39 + 20));

        this.infoWindow = (ViewGroup)getLayoutInflater().inflate(R.layout.infowindow_layout, null);
        this.infoMarkerLabel = (TextView)infoWindow.findViewById(R.id.marker_label);
        this.infoAnotherLabel = (TextView)infoWindow.findViewById(R.id.another_label);
        this.infoImage = (ImageView)infoWindow.findViewById(R.id.marker_icon);

        this.infoAnotherLabel.setOnTouchListener(infoButtonListener);*/



        /*View.OnClickListener newsfeed = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent newsfeedIntent = new Intent(getApplicationContext(), Newsfeed.class);
                startActivity(newsfeedIntent);
            }
        };*/

        // Initialize the HashMap for Markers and MyMarker object
        mMarkersHashMap = new HashMap<Marker, MyMarker>();
        //Dublin
        mMyMarkersArray.add(new MyMarker("D1", "Dublin", Double.parseDouble("53.3496214"), Double.parseDouble("-6.260767900000019")));
        mMyMarkersArray.add(new MyMarker("D2", "Dublin", Double.parseDouble("53.37295"), Double.parseDouble("-6.361157300000059")));
        mMyMarkersArray.add(new MyMarker("D3", "Dublin", Double.parseDouble("53.3206882"), Double.parseDouble("-6.279172799999969")));
        mMyMarkersArray.add(new MyMarker("D4", "Dublin", Double.parseDouble("53.3332893"), Double.parseDouble("-6.2371981000000005")));
        mMyMarkersArray.add(new MyMarker("D5", "Dublin", Double.parseDouble("53.35869719999999"), Double.parseDouble("-6.250459099999944")));
        mMyMarkersArray.add(new MyMarker("D6", "Dublin", Double.parseDouble("53.32294640000001"), Double.parseDouble("-6.272985999999946")));
        mMyMarkersArray.add(new MyMarker("D7", "Dublin", Double.parseDouble("53.3631958"), Double.parseDouble("-6.27065170000003")));
        mMyMarkersArray.add(new MyMarker("D8", "Dublin", Double.parseDouble("53.3384887"), Double.parseDouble("-6.289918700000044")));
        mMyMarkersArray.add(new MyMarker("D9", "Dublin", Double.parseDouble("53.3630556"), Double.parseDouble("-6.270738899999969")));
        mMyMarkersArray.add(new MyMarker("D10", "Dublin", Double.parseDouble("53.34122559999999"), Double.parseDouble("-6.300473399999987")));
        mMyMarkersArray.add(new MyMarker("D11", "Dublin", Double.parseDouble("53.3515184"), Double.parseDouble("-6.25813340000002")));
        mMyMarkersArray.add(new MyMarker("D12", "Dublin", Double.parseDouble("53.3515328"), Double.parseDouble("-6.258030500000018")));
        mMyMarkersArray.add(new MyMarker("D13", "Dublin", Double.parseDouble("53.3448858"), Double.parseDouble("-6.247736499999974")));
        mMyMarkersArray.add(new MyMarker("D14", "Dublin", Double.parseDouble("53.3190691"), Double.parseDouble("-6.229579999999942")));
        mMyMarkersArray.add(new MyMarker("D15", "Dublin", Double.parseDouble("53.38301490000001"), Double.parseDouble("-6.416528900000003")));
        mMyMarkersArray.add(new MyMarker("D16", "Dublin", Double.parseDouble("53.2798603"), Double.parseDouble("-6.278982000000042")));
        mMyMarkersArray.add(new MyMarker("D17", "Dublin", Double.parseDouble("53.3447691"), Double.parseDouble("-6.247868599999947")));
        mMyMarkersArray.add(new MyMarker("D18", "Dublin", Double.parseDouble("53.3447407"), Double.parseDouble("-6.247902100000033")));
        mMyMarkersArray.add(new MyMarker("D19", "Dublin", Double.parseDouble("53.3359996"), Double.parseDouble("-6.282349299999964")));
        mMyMarkersArray.add(new MyMarker("D20", "Dublin", Double.parseDouble("53.3392932"), Double.parseDouble("-6.279809500000056")));
        mMyMarkersArray.add(new MyMarker("D22", "Dublin", Double.parseDouble("53.34137870000001"), Double.parseDouble("-6.299486399999978")));
        mMyMarkersArray.add(new MyMarker("D24", "Dublin", Double.parseDouble("53.3362355"), Double.parseDouble("-6.270165099999986")));

        //Kildare
        mMyMarkersArray.add(new MyMarker("Kildare West", "Kildare", Double.parseDouble("53.18527560691649"), Double.parseDouble("-7.011191835156296")));
        mMyMarkersArray.add(new MyMarker("Kildare East", "Kildare", Double.parseDouble("53.23051212346622"), Double.parseDouble("-6.526420106640671")));
        mMyMarkersArray.add(new MyMarker("Kildare North", "Kildare", Double.parseDouble("53.37249035669105"), Double.parseDouble("-6.791465272656296")));
        mMyMarkersArray.add(new MyMarker("Kildare South", "Kildare", Double.parseDouble("52.92444496812726"), Double.parseDouble("-6.846396913281296")));
        mMyMarkersArray.add(new MyMarker("Kildare Central", "Kildare", Double.parseDouble("53.18527560691649"), Double.parseDouble("-6.759879579296921")));

        //Wicklow
        mMyMarkersArray.add(new MyMarker("Wicklow West", "Wicklow", Double.parseDouble("52.96416654259141"), Double.parseDouble("-6.709067811718796")));
        mMyMarkersArray.add(new MyMarker("Wicklow East", "Wicklow", Double.parseDouble("52.96085780426767"), Double.parseDouble("-6.078727235546921")));
        mMyMarkersArray.add(new MyMarker("Wicklow North", "Wicklow", Double.parseDouble("53.16058101173315"), Double.parseDouble("-6.317679872265671")));
        mMyMarkersArray.add(new MyMarker("Wicklow South", "Wicklow", Double.parseDouble("52.78097903439996"), Double.parseDouble("-6.500327577343796")));
        mMyMarkersArray.add(new MyMarker("Wicklow Central", "Wicklow", Double.parseDouble("52.99724000037819"), Double.parseDouble("-6.331412782421921")));

        //Carlow
        mMyMarkersArray.add(new MyMarker("Carlow West", "Carlow", Double.parseDouble("52.74358356655464"), Double.parseDouble("-7.024924745312546")));
        mMyMarkersArray.add(new MyMarker("Carlow East", "Carlow", Double.parseDouble("52.7153079133867"), Double.parseDouble("-6.667869081250046")));
        mMyMarkersArray.add(new MyMarker("Carlow North", "Carlow", Double.parseDouble("52.87225504213309"), Double.parseDouble("-6.700828065625046")));
        mMyMarkersArray.add(new MyMarker("Carlow South", "Carlow", Double.parseDouble("52.548609146748234"), Double.parseDouble("-6.877982606640671")));
        mMyMarkersArray.add(new MyMarker("Carlow Central", "Carlow", Double.parseDouble("52.72861638730675"), Double.parseDouble("-6.787345399609421")));

        //Wexford
        mMyMarkersArray.add(new MyMarker("Wexford West", "Wexford", Double.parseDouble("52.55278435800634"), Double.parseDouble("-6.754386415234421")));
        mMyMarkersArray.add(new MyMarker("Wexford East", "Wexford", Double.parseDouble("52.45916438880156"), Double.parseDouble("-6.330039491406296")));
        mMyMarkersArray.add(new MyMarker("Wexford North", "Wexford", Double.parseDouble("52.724457925451155"), Double.parseDouble("-6.306693544140671")));
        mMyMarkersArray.add(new MyMarker("Wexford South", "Wexford", Double.parseDouble("52.27132581249872"), Double.parseDouble("-6.714560975781296")));
        mMyMarkersArray.add(new MyMarker("Wexford Central", "Wexford", Double.parseDouble("52.513521687436835"), Double.parseDouble("-6.529166688671921")));

        //Kilkenny
        mMyMarkersArray.add(new MyMarker("Kilkenny West", "Kilkenny", Double.parseDouble("52.54693895101734"), Double.parseDouble("-7.391593446484421")));
        mMyMarkersArray.add(new MyMarker("Kilkenny East", "Kilkenny", Double.parseDouble("52.541927982459654"), Double.parseDouble("-6.985099305859421")));
        mMyMarkersArray.add(new MyMarker("Kilkenny North", "Kilkenny", Double.parseDouble("52.82332175344236"), Double.parseDouble("-7.265250673046921")));
        mMyMarkersArray.add(new MyMarker("Kilkenny South", "Kilkenny", Double.parseDouble("52.32087863770545"), Double.parseDouble("-7.152640809765671")));
        mMyMarkersArray.add(new MyMarker("Kilkenny Central", "Kilkenny", Double.parseDouble("52.571985213723785"), Double.parseDouble("-7.180106630078171")));

        //Waterford
        mMyMarkersArray.add(new MyMarker("Waterford West", "Waterford", Double.parseDouble("52.19647170903787"), Double.parseDouble("-7.925803651562546")));
        mMyMarkersArray.add(new MyMarker("Waterford East", "Waterford", Double.parseDouble("52.19142082266953"), Double.parseDouble("-7.072989930859421")));
        mMyMarkersArray.add(new MyMarker("Waterford North", "Waterford", Double.parseDouble("52.31836034029933"), Double.parseDouble("-7.508323182812546")));
        mMyMarkersArray.add(new MyMarker("Waterford South", "Waterford", Double.parseDouble("51.99990712812827"), Double.parseDouble("-7.755515565625046")));
        mMyMarkersArray.add(new MyMarker("Waterford Central", "Waterford", Double.parseDouble("52.17036928498166"), Double.parseDouble("-7.596213807812546")));

        //Laois
        mMyMarkersArray.add(new MyMarker("Laois West", "Laois", Double.parseDouble("52.97243728060307"), Double.parseDouble("-7.675864686718796")));
        mMyMarkersArray.add(new MyMarker("Laois East", "Laois", Double.parseDouble("52.99889300872788"), Double.parseDouble("-7.115561952343796")));
        mMyMarkersArray.add(new MyMarker("Laois North", "Laois", Double.parseDouble("53.1226883555814"), Double.parseDouble("-7.325675477734421")));
        mMyMarkersArray.add(new MyMarker("Laois South", "Laois", Double.parseDouble("52.87971459429728"), Double.parseDouble("-7.300956239453171")));
        mMyMarkersArray.add(new MyMarker("Laois Central", "Laois", Double.parseDouble("52.98236007737649"), Double.parseDouble("-7.360007753125046")));

        //Tipperary
        mMyMarkersArray.add(new MyMarker("Tipperary West", "Tipperary", Double.parseDouble("52.60869387043701"), Double.parseDouble("-8.174369325390671")));
        mMyMarkersArray.add(new MyMarker("Tipperary East", "Tipperary", Double.parseDouble("52.67868866729732"), Double.parseDouble("-7.586600770703171")));
        mMyMarkersArray.add(new MyMarker("Tipperary North", "Tipperary", Double.parseDouble("53.0021988355828"), Double.parseDouble("-8.146903505078171")));
        mMyMarkersArray.add(new MyMarker("Tipperary South", "Tipperary", Double.parseDouble("52.37959823328749"), Double.parseDouble("-7.765128602734421")));
        mMyMarkersArray.add(new MyMarker("Tipperary Central", "Tipperary", Double.parseDouble("52.62370218610875"), Double.parseDouble("-7.809073915234421")));

        //Cork
        mMyMarkersArray.add(new MyMarker("Cork West", "Cork", Double.parseDouble("51.992297124229026"), Double.parseDouble("-9.064261903515671")));
        mMyMarkersArray.add(new MyMarker("Cork East", "Cork", Double.parseDouble("52.07846810988747"), Double.parseDouble("-8.097465028515671")));
        mMyMarkersArray.add(new MyMarker("Cork North", "Cork", Double.parseDouble("52.27720786012349"), Double.parseDouble("-8.586356630078171")));
        mMyMarkersArray.add(new MyMarker("Cork South", "Cork", Double.parseDouble("51.62208385715535"), Double.parseDouble("-9.034049501171921")));
        mMyMarkersArray.add(new MyMarker("Cork Central", "Cork", Double.parseDouble("51.98553159035917"), Double.parseDouble("-8.600089540234421")));

        //Kerry
        mMyMarkersArray.add(new MyMarker("Kerry West", "Kerry", Double.parseDouble("52.177948986491465"), Double.parseDouble("-9.874503602734421")));
        mMyMarkersArray.add(new MyMarker("Kerry East", "Kerry", Double.parseDouble("52.066650552641455"), Double.parseDouble("-9.248282899609421")));
        mMyMarkersArray.add(new MyMarker("Kerry North", "Kerry", Double.parseDouble("52.52522062217707"), Double.parseDouble("-9.498221864453171")));
        mMyMarkersArray.add(new MyMarker("Kerry South", "Kerry", Double.parseDouble("51.83982528271116"), Double.parseDouble("-9.833304872265671")));
        mMyMarkersArray.add(new MyMarker("Kerry Central", "Kerry", Double.parseDouble("52.08015607701568"), Double.parseDouble("-9.509208192578171")));

        //Limerick
        mMyMarkersArray.add(new MyMarker("Limerick West", "Limerick", Double.parseDouble("52.47673332318061"), Double.parseDouble("-9.215323915234421")));
        mMyMarkersArray.add(new MyMarker("Limerick East", "Limerick", Double.parseDouble("52.44995881477817"), Double.parseDouble("-8.429801454296921")));
        mMyMarkersArray.add(new MyMarker("Limerick North", "Limerick", Double.parseDouble("52.64537178199148"), Double.parseDouble("-8.641288270703171")));
        mMyMarkersArray.add(new MyMarker("Limerick South", "Limerick", Double.parseDouble("52.387980380040865"), Double.parseDouble("-8.707206239453171")));
        mMyMarkersArray.add(new MyMarker("Limerick Central", "Limerick", Double.parseDouble("52.50516339809591"), Double.parseDouble("-8.720939149609421")));

        //Clare
        mMyMarkersArray.add(new MyMarker("Clare West", "Clare", Double.parseDouble("52.87142612380369"), Double.parseDouble("-9.369132508984421")));
        mMyMarkersArray.add(new MyMarker("Clare East", "Clare", Double.parseDouble("52.83493803340351"), Double.parseDouble("-8.509452333203171")));
        mMyMarkersArray.add(new MyMarker("Clare North", "Clare", Double.parseDouble("52.97905273163226"), Double.parseDouble("-8.907706727734421")));
        mMyMarkersArray.add(new MyMarker("Clare South", "Clare", Double.parseDouble("52.71031618863242"), Double.parseDouble("-8.839042176953171")));
        mMyMarkersArray.add(new MyMarker("Clare Central", "Clare", Double.parseDouble("52.858161276140365"), Double.parseDouble("-8.913199891796921")));

        //Offaly
        mMyMarkersArray.add(new MyMarker("Offaly West", "Offaly", Double.parseDouble("53.18033782491882"), Double.parseDouble("-7.962882508984421")));
        mMyMarkersArray.add(new MyMarker("Offaly East", "Offaly", Double.parseDouble("53.28719587155457"), Double.parseDouble("-7.078483094921921")));
        mMyMarkersArray.add(new MyMarker("Offaly North", "Offaly", Double.parseDouble("53.37904440852968"), Double.parseDouble("-7.625052919140671")));
        mMyMarkersArray.add(new MyMarker("Offaly South", "Offaly", Double.parseDouble("52.96747502770004"), Double.parseDouble("-7.880485048046921")));
        mMyMarkersArray.add(new MyMarker("Offaly Central", "Offaly", Double.parseDouble("53.24941505333632"), Double.parseDouble("-7.479484071484421")));

        //Galway
        mMyMarkersArray.add(new MyMarker("Galway West", "Galway", Double.parseDouble("53.44125760849885"), Double.parseDouble("-9.841544618359421")));
        mMyMarkersArray.add(new MyMarker("Galway East", "Galway", Double.parseDouble("53.20995599055065"), Double.parseDouble("-8.201835145703171")));
        mMyMarkersArray.add(new MyMarker("Galway North", "Galway", Double.parseDouble("53.586611565598304"), Double.parseDouble("-8.583610048046921")));
        mMyMarkersArray.add(new MyMarker("Galway South", "Galway", Double.parseDouble("53.036894737108604"), Double.parseDouble("-8.465507020703171")));
        mMyMarkersArray.add(new MyMarker("Galway Central", "Galway", Double.parseDouble("53.3839592855321"), Double.parseDouble("-8.880240907421921")));

        //Mayo
        mMyMarkersArray.add(new MyMarker("Mayo West", "Mayo", Double.parseDouble("53.90657938046142"), Double.parseDouble("-9.888236512890671")));
        mMyMarkersArray.add(new MyMarker("Mayo East", "Mayo", Double.parseDouble("53.817493382112474"), Double.parseDouble("-8.778617372265671")));
        mMyMarkersArray.add(new MyMarker("Mayo North", "Mayo", Double.parseDouble("54.098680461188636"), Double.parseDouble("-9.138419618359421")));
        mMyMarkersArray.add(new MyMarker("Mayo South", "Mayo", Double.parseDouble("53.66642519947376"), Double.parseDouble("-9.358146180859421")));
        mMyMarkersArray.add(new MyMarker("Mayo Central", "Mayo", Double.parseDouble("53.84828999190444"), Double.parseDouble("-9.201591005078171")));

        //Westmeath
        mMyMarkersArray.add(new MyMarker("Westmeath West", "Westmeath", Double.parseDouble("53.431440520633394"), Double.parseDouble("-7.864005555859421")));
        mMyMarkersArray.add(new MyMarker("Westmeath East", "Westmeath", Double.parseDouble("53.575197293264125"), Double.parseDouble("-7.053763856640671")));
        mMyMarkersArray.add(new MyMarker("Westmeath North", "Westmeath", Double.parseDouble("53.7460879499687"), Double.parseDouble("-7.328422059765671")));
        mMyMarkersArray.add(new MyMarker("Westmeath South", "Westmeath", Double.parseDouble("53.35118272195343"), Double.parseDouble("-7.473990907421921")));
        mMyMarkersArray.add(new MyMarker("Westmeath Central", "Westmeath", Double.parseDouble("53.521345629267174"), Double.parseDouble("-7.336661805859421")));

        //Meath
        mMyMarkersArray.add(new MyMarker("Meath West", "Meath", Double.parseDouble("53.74365153180485"), Double.parseDouble("-7.169120301953171")));
        mMyMarkersArray.add(new MyMarker("Meath East", "Meath", Double.parseDouble("53.64200848514939"), Double.parseDouble("-6.336905946484421")));
        mMyMarkersArray.add(new MyMarker("Meath North", "Meath", Double.parseDouble("53.846669682218106"), Double.parseDouble("-6.784598817578171")));
        mMyMarkersArray.add(new MyMarker("Meath South", "Meath", Double.parseDouble("53.44125760849896"), Double.parseDouble("-6.743400087109421")));
        mMyMarkersArray.add(new MyMarker("Meath Central", "Meath", Double.parseDouble("53.63875185472496"), Double.parseDouble("-6.682975282421921")));

        //Louth
        mMyMarkersArray.add(new MyMarker("Louth West", "Louth", Double.parseDouble("53.89848849059008"), Double.parseDouble("-6.644523133984421")));
        mMyMarkersArray.add(new MyMarker("Louth East", "Louth", Double.parseDouble("53.86125021170893"), Double.parseDouble("-6.277854432812546")));
        mMyMarkersArray.add(new MyMarker("Louth North", "Louth", Double.parseDouble("54.030174697793264"), Double.parseDouble("-6.453635682812546")));
        mMyMarkersArray.add(new MyMarker("Louth South", "Louth", Double.parseDouble("53.73390444636305"), Double.parseDouble("-6.371238221875046")));
        mMyMarkersArray.add(new MyMarker("Louth Central", "Louth", Double.parseDouble("53.90172503457363"), Double.parseDouble("-6.500327577343796")));

        //Longford
        mMyMarkersArray.add(new MyMarker("Longford West", "Longford", Double.parseDouble("53.74933628776614"), Double.parseDouble("-7.876365175000046")));
        mMyMarkersArray.add(new MyMarker("Longford East", "Longford", Double.parseDouble("53.693267254331516"), Double.parseDouble("-7.549521913281296")));
        mMyMarkersArray.add(new MyMarker("Longford North", "Longford", Double.parseDouble("53.81992551674191"), Double.parseDouble("-7.585227479687546")));
        mMyMarkersArray.add(new MyMarker("Longford South", "Longford", Double.parseDouble("53.57193550647722"), Double.parseDouble("-7.854392518750046")));
        mMyMarkersArray.add(new MyMarker("Longford Central", "Longford", Double.parseDouble("53.71927957942502"), Double.parseDouble("-7.732169618359421")));

        //Roscommon
        mMyMarkersArray.add(new MyMarker("Roscommon West", "Roscommon", Double.parseDouble("53.77450239117986"), Double.parseDouble("-8.638541688671921")));
        mMyMarkersArray.add(new MyMarker("Roscommon East", "Roscommon", Double.parseDouble("53.77612549287838"), Double.parseDouble("-7.905204286328171")));
        mMyMarkersArray.add(new MyMarker("Roscommon North", "Roscommon", Double.parseDouble("53.99143915080375"), Double.parseDouble("-8.197715272656296")));
        mMyMarkersArray.add(new MyMarker("Roscommon South", "Roscommon", Double.parseDouble("53.35200244339473"), Double.parseDouble("-8.086478700390671")));
        mMyMarkersArray.add(new MyMarker("Roscommon Central", "Roscommon", Double.parseDouble("53.706275426944835"), Double.parseDouble("-8.208701600781296")));

        //Sligo
        mMyMarkersArray.add(new MyMarker("Sligo West", "Sligo", Double.parseDouble("54.217691563315945"), Double.parseDouble("-8.992850770703171")));
        mMyMarkersArray.add(new MyMarker("Sligo East", "Sligo", Double.parseDouble("54.10673253456238"), Double.parseDouble("-8.351523866406296")));
        mMyMarkersArray.add(new MyMarker("Sligo North", "Sligo", Double.parseDouble("54.33876259553471"), Double.parseDouble("-8.479239930859421")));
        mMyMarkersArray.add(new MyMarker("Sligo South", "Sligo", Double.parseDouble("54.042272162293195"), Double.parseDouble("-8.628928651562546")));
        mMyMarkersArray.add(new MyMarker("Sligo Central", "Sligo", Double.parseDouble("54.167877213088325"), Double.parseDouble("-8.587729921093796")));

        //Leitrim
        mMyMarkersArray.add(new MyMarker("Leitrim West", "Leitrim", Double.parseDouble("54.179130862591855"), Double.parseDouble("-8.211448182812546")));
        mMyMarkersArray.add(new MyMarker("Leitrim East", "Leitrim", Double.parseDouble("54.01000443324115"), Double.parseDouble("-7.656638612500046")));
        mMyMarkersArray.add(new MyMarker("Leitrim North", "Leitrim", Double.parseDouble("54.432328137536054"), Double.parseDouble("-8.289725770703171")));
        mMyMarkersArray.add(new MyMarker("Leitrim South", "Leitrim", Double.parseDouble("53.86367980611678"), Double.parseDouble("-7.850272645703171")));
        mMyMarkersArray.add(new MyMarker("Leitrim Central", "Leitrim", Double.parseDouble("54.10834276163331"), Double.parseDouble("-7.971122255078171")));

        //Cavan
        mMyMarkersArray.add(new MyMarker("Cavan West", "Cavan", Double.parseDouble("53.91224207106775"), Double.parseDouble("-7.508323182812546")));
        mMyMarkersArray.add(new MyMarker("Cavan East", "Cavan", Double.parseDouble("53.929225536739686"), Double.parseDouble("-6.862876405468796")));
        mMyMarkersArray.add(new MyMarker("Cavan North", "Cavan", Double.parseDouble("54.19680898350161"), Double.parseDouble("-7.899711122265671")));
        mMyMarkersArray.add(new MyMarker("Cavan South", "Cavan", Double.parseDouble("53.81668263920075"), Double.parseDouble("-7.186973085156296")));
        mMyMarkersArray.add(new MyMarker("Cavan Central", "Cavan", Double.parseDouble("53.967211119944686"), Double.parseDouble("-7.221305360546921")));

        //Monaghan
        mMyMarkersArray.add(new MyMarker("Monaghan West", "Monaghan", Double.parseDouble("54.14174086435596"), Double.parseDouble("-7.214438905468796")));
        mMyMarkersArray.add(new MyMarker("Monaghan East", "Monaghan", Double.parseDouble("54.12363680019094"), Double.parseDouble("-6.675422181835984")));
        mMyMarkersArray.add(new MyMarker("Monaghan North", "Monaghan", Double.parseDouble("54.39196917466502"), Double.parseDouble("-7.020118226757859")));
        mMyMarkersArray.add(new MyMarker("Monaghan South", "Monaghan", Double.parseDouble("53.96519148131463"), Double.parseDouble("-6.722800721875046")));
        mMyMarkersArray.add(new MyMarker("Monaghan Central", "Monaghan", Double.parseDouble("54.11961260030068"), Double.parseDouble("-6.929481019726609")));

        //Donegal
        mMyMarkersArray.add(new MyMarker("Donegal West", "Donegal", Double.parseDouble("54.802481950261814"), Double.parseDouble("-8.413321962109421")));
        mMyMarkersArray.add(new MyMarker("Donegal East", "Donegal", Double.parseDouble("54.79139858089316"), Double.parseDouble("-7.572867860546921")));
        mMyMarkersArray.add(new MyMarker("Donegal North", "Donegal", Double.parseDouble("55.065999411600636"), Double.parseDouble("-7.644278993359421")));
        mMyMarkersArray.add(new MyMarker("Donegal South", "Donegal", Double.parseDouble("54.570286588074715"), Double.parseDouble("-7.994468202343796")));
        mMyMarkersArray.add(new MyMarker("Donegal Central", "Donegal", Double.parseDouble("53.3362355"), Double.parseDouble("-6.270165099999986")));

        setUpMap();
        //mClusterManager.addItems(mMyMarkersArray);
        //startDemo();

       /* mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String title = marker.getTitle();
                Intent newsfeedIntent = new Intent(getApplicationContext(), Newsfeed.class);
                startActivity(newsfeedIntent);
            }
        });

        mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());*/
        //plotMarkers(mMyMarkersArray);
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.2734, -7.778320310000026), 6));
        //mClusterManager = new ClusterManager<MyMarker>(this, mMap);
        //mMap.setOnCameraChangeListener(mClusterManager);
        //mMap.setOnMarkerClickListener(mClusterManager);

        NBHAuthenticationApplication myApp = (NBHAuthenticationApplication) getApplication();
        NBHAuthService authService = myApp.getNBHAuthService();
        final AuthService mAuthService = new AuthService(mActivity);
        final NBHAuthService mNBHAuthService = new NBHAuthService(mActivity);

        //Fetch auth data (the username) on load
        authService.getNBHAuthData(new TableJsonQueryCallback() {
            @Override
            public void onCompleted(JsonElement result, Exception exception,
                                    ServiceFilterResponse response) {
                if (exception == null) {
                    JsonArray results = result.getAsJsonArray();
                    JsonElement item = results.get(0);
                    neighborhood = item.getAsJsonObject().getAsJsonPrimitive("UserName").getAsString();
                    getCounty();

                } else {
                    dialogBox.NotLoggedIn(mActivity);
                    mAuthService.logout(true);
                    mNBHAuthService.logout(true);
                }
            }
        });

    }

    public void getCounty()
    {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MobileServiceList<Accounts> result = mToDoTable.where().field("username").eq(neighborhood).execute().get();

                    final String test=  result.toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCounty = test;
                            plotMarkers(mMyMarkersArray);
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

    private void plotMarkers(ArrayList<MyMarker> markers)
    {
        if(markers.size() > 0)
        {
            for (final MyMarker myMarker : markers)
            {
                LatLng kildare = new LatLng(53.158934, -6.909568);
                LatLng dublin = new LatLng(53.349805, -6.26031);
                LatLng donegal = new LatLng(54.826008, -7.896423);
                LatLng kerry = new LatLng(52.154461, -9.566863);
                LatLng carlow = new LatLng(52.718975, -6.85037);
                LatLng wexford = new LatLng(52.333871, -6.483479);
                LatLng waterford = new LatLng(52.259320, -7.11007);
                LatLng westmeath = new LatLng(53.533778, -7.318268);
                LatLng galway = new LatLng(53.192870, -8.632507);
                LatLng leitrim = new LatLng(54.101281, -7.963715);
                LatLng limerick = new LatLng(52.492815, -8.764343);
                LatLng kilkenny = new LatLng(52.654145, -7.244788);
                LatLng cork = new LatLng(51.984880, -8.583069);
                LatLng wicklow = new LatLng(52.998256, -6.341858);
                LatLng meath = new LatLng(53.605548, -6.656417);
                LatLng longford = new LatLng(53.698333, -7.705536);
                LatLng mayo = new LatLng(53.810382, -9.242249);
                LatLng laois = new LatLng(52.960221, -7.326508);
                LatLng tipperary = new LatLng(52.604716, -7.851105);
                LatLng clare = new LatLng(52.816043, -8.912659);
                LatLng monaghan = new LatLng(54.133478, -6.896667);
                LatLng cavan = new LatLng(53.989719, -7.363332);
                LatLng roscommon = new LatLng(53.680442, -8.194427);
                LatLng offaly = new LatLng(53.184642, -7.741241);
                LatLng louth = new LatLng(53.896976, -6.467097);
                LatLng sligo = new LatLng(54.130260, -8.548737);
                LatLng ireland = new LatLng(53.412910, -8.24389);

                if (mCounty.contains("Cork"))
                {
                    latlng = cork;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Dublin"))
                {
                    latlng = dublin;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Kildare")) {
                    latlng = kildare;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Kilkenny")) {
                    latlng = kilkenny;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Meath")) {
                    latlng = meath;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Mayo")) {
                    latlng = mayo;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Monaghan")) {
                    latlng = monaghan;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Leitrim")) {
                    latlng = leitrim;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Limerick")) {
                    latlng = limerick;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Sligo")) {
                    latlng = sligo;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Wicklow")) {
                    latlng = wicklow;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Wexford")) {
                    latlng = wexford;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Westmeath")) {
                    latlng = westmeath;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Waterford")) {
                    latlng = waterford;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Galway")) {
                    latlng = galway;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Offaly")) {
                    latlng = offaly;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Donegal")) {
                    latlng = donegal;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Carlow")) {
                    latlng = carlow;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Cavan")) {
                    latlng = cavan;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Clare")) {
                    latlng = clare;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Laois")) {
                    latlng = laois;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Longford")) {
                    latlng = longford;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Louth")) {
                    latlng = louth;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Roscommon")) {
                    latlng = roscommon;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Kerry")) {
                    latlng = kerry;
                    county_zoom = 8;
                }
                else if(mCounty.contains("Tipperary")) {
                    latlng = tipperary;
                    county_zoom = 8;
                }
                else {
                    latlng = ireland;
                    county_zoom = 6;
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((latlng), county_zoom));
                // Create user marker with custom icon and other options
                //Calls the lat and long methods to set the longitude and latitude positions of the markers passed in
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()));
                //markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.currentlocation_icon));

                Marker currentMarker = mMap.addMarker(markerOption);
                //Passes markers into hashmap so they can be used by the information window methods
                mMarkersHashMap.put(currentMarker, myMarker);
                //currentMarker.setVisible(false);

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Intent ListIntent = new Intent(getApplicationContext(), InfoWindowList.class);
                        MyMarker myMarker = mMarkersHashMap.get(marker);
                        String title = myMarker.getmLabel();
                        ListIntent.putExtra("COUNTY", title);
                        startActivity(ListIntent);
                    }
                });

                mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
            }

        }
        //mClusterManager.addItems(markers);
    }


    protected GoogleMap getMap() {
        return mMap;
    }
    private void gotoLocation(double lat, double lng, float zoom)
    {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);
    }


    protected List<Address> doInBackground(String... locationName) {
        // Creating an instance of Geocoder class
        Geocoder geocoder = new Geocoder(getBaseContext());
        List<Address> addresses = null;

        try {
            // Getting a maximum of 3 Address that matches the input text
            addresses = geocoder.getFromLocationName(locationName[0], 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses;
    }
    //Use to insert county flags into each information window
    private int manageMarkerIcon(String markerIcon)
    {
        if (markerIcon.equals("Dublin") || markerIcon.equals("D1") || markerIcon.equals("D2") || markerIcon.equals("D3") ||
                markerIcon.equals("D4") || markerIcon.equals("D5") || markerIcon.equals("D6") || markerIcon.equals("D7") ||
                markerIcon.equals("D8") || markerIcon.equals("D9") || markerIcon.equals("D10") || markerIcon.equals("D11") ||
                markerIcon.equals("D12") || markerIcon.equals("D13") || markerIcon.equals("D14") || markerIcon.equals("D15") ||
                markerIcon.equals("D16") || markerIcon.equals("D17") || markerIcon.equals("D18") || markerIcon.equals("D19") ||
                markerIcon.equals("D20") || markerIcon.equals("D22") || markerIcon.equals("D24"))
            return R.mipmap.dublin;
        else if(markerIcon.equals("Kildare"))
            return R.mipmap.kildare;
        else if(markerIcon.equals("Cavan"))
            return R.mipmap.cavan;
        else if(markerIcon.equals("Kilkenny"))
            return R.mipmap.kilkenny;
        else if(markerIcon.equals("Cork"))
            return R.mipmap.cork;
        else if(markerIcon.equals("Laois"))
            return R.mipmap.laois;
        else if(markerIcon.equals("Limerick"))
            return R.mipmap.limerick;
        else if(markerIcon.equals("Leitrim"))
            return R.mipmap.leitrim;
        else if(markerIcon.equals("Wicklow"))
            return R.mipmap.wicklow;
        else if(markerIcon.equals("Waterford"))
            return R.mipmap.waterford;
        else if(markerIcon.equals("Wexford"))
            return R.mipmap.wexford;
        else if(markerIcon.equals("Westmeath"))
            return R.mipmap.westmeath;
        else if(markerIcon.equals("Meath"))
            return R.mipmap.meath;
        else if(markerIcon.equals("Mayo"))
            return R.mipmap.mayo;
        else if(markerIcon.equals("Monaghan"))
            return R.mipmap.monaghan;
        else if(markerIcon.equals("Roscommon"))
            return R.mipmap.roscommon;
        else if(markerIcon.equals("Carlow"))
            return R.mipmap.carlow;
        else if(markerIcon.equals("Kerry"))
            return R.mipmap.kerry;
        else if(markerIcon.equals("Galway"))
            return R.mipmap.galway;
        else if(markerIcon.equals("Longford"))
            return R.mipmap.longford;
        else if(markerIcon.equals("Louth"))
            return R.mipmap.louth;
        else if(markerIcon.equals("Sligo"))
            return R.mipmap.sligo;
        else if(markerIcon.equals("Offaly"))
            return R.mipmap.offaly;
        else if(markerIcon.equals("Clare"))
            return R.mipmap.clare;
        else if(markerIcon.equals("Tipperary"))
            return R.mipmap.tipperary;
        else if(markerIcon.equals("Donegal"))
            return R.mipmap.donegal;
        else
            return R.mipmap.icondefault;
    }


    private void setUpMap()
    {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null)
        {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

            // Check if we were successful in obtaining the map.

            if (mMap != null)
            {
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
                {
                    @Override
                    public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker)
                    {
                        marker.showInfoWindow();
                        return true;
                    }
                });
            }
            else
                Toast.makeText(getApplicationContext(), "Unable to create Maps", Toast.LENGTH_SHORT).show();
        }
    }


    public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter
    {
        public MarkerInfoWindowAdapter()
        {
        }

        @Override
        public View getInfoWindow(Marker marker)
        {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker)
        {

            View v  = getLayoutInflater().inflate(R.layout.infowindow_layout, null);
            MyMarker myMarker = mMarkersHashMap.get(marker);
            //MyMarker mymarker = new MyMarker(marker);

            ImageView markerIcon = (ImageView) v.findViewById(R.id.marker_icon);
            TextView markerLabel = (TextView)v.findViewById(R.id.marker_label);
            TextView anotherLabel = (TextView)v.findViewById(R.id.another_label);

            markerIcon.setImageResource(manageMarkerIcon(myMarker.getmIcon()));
            anotherLabel.setText("Tap here for information");
            markerLabel.setText(myMarker.getmLabel());

            return v;

        }
    }
        /*View.OnClickListener newsfeed = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent newsfeedIntent = new Intent(getApplicationContext(), Newsfeed.class);
                startActivity(newsfeedIntent);
            }
        };*/
        /*mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
            @Override
            public void onClick(Marker marker) {
                Intent intent1 = new Intent(MapsActivity.this, Newsfeed.class);
                startActivity(intent1);
            }
        })*/

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