package com.example.cillin.map;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends Activity
{
    private GoogleMap mMap;
    private ArrayList<MyMarker> mMyMarkersArray = new ArrayList<MyMarker>();
    private HashMap<Marker, MyMarker> mMarkersHashMap;
    private static final float DEFAULTZOOM = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Initialize the HashMap for Markers and MyMarker object
        mMarkersHashMap = new HashMap<Marker, MyMarker>();
        //Dublin
        mMyMarkersArray.add(new MyMarker("D1", "icondefault", Double.parseDouble("53.3496214"), Double.parseDouble("-6.260767900000019")));
        mMyMarkersArray.add(new MyMarker("D2", "icondefault", Double.parseDouble("53.37295"), Double.parseDouble("-6.361157300000059")));
        mMyMarkersArray.add(new MyMarker("D3", "icondefault", Double.parseDouble("53.3206882"), Double.parseDouble("-6.279172799999969")));
        mMyMarkersArray.add(new MyMarker("D4", "icondefault", Double.parseDouble("53.3332893"), Double.parseDouble("-6.2371981000000005")));
        mMyMarkersArray.add(new MyMarker("D5", "icondefault", Double.parseDouble("53.35869719999999"), Double.parseDouble("-6.250459099999944")));
        mMyMarkersArray.add(new MyMarker("D6", "icondefault", Double.parseDouble("53.32294640000001"), Double.parseDouble("-6.272985999999946")));
        mMyMarkersArray.add(new MyMarker("D7", "icondefault", Double.parseDouble("53.3631958"), Double.parseDouble("-6.27065170000003")));
        mMyMarkersArray.add(new MyMarker("D8", "icondefault", Double.parseDouble("53.3384887"), Double.parseDouble("-6.289918700000044")));
        mMyMarkersArray.add(new MyMarker("D9", "icondefault", Double.parseDouble("53.3630556"), Double.parseDouble("-6.270738899999969")));
        mMyMarkersArray.add(new MyMarker("D10", "icondefault", Double.parseDouble("53.34122559999999"), Double.parseDouble("-6.300473399999987")));
        mMyMarkersArray.add(new MyMarker("D11", "icondefault", Double.parseDouble("53.3515184"), Double.parseDouble("-6.25813340000002")));
        mMyMarkersArray.add(new MyMarker("D12", "icondefault", Double.parseDouble("53.3515328"), Double.parseDouble("-6.258030500000018")));
        mMyMarkersArray.add(new MyMarker("D13", "icondefault", Double.parseDouble("53.3448858"), Double.parseDouble("-6.247736499999974")));
        mMyMarkersArray.add(new MyMarker("D14", "icondefault", Double.parseDouble("53.3190691"), Double.parseDouble("-6.229579999999942")));
        mMyMarkersArray.add(new MyMarker("D15", "icondefault", Double.parseDouble("53.38301490000001"), Double.parseDouble("-6.416528900000003")));
        mMyMarkersArray.add(new MyMarker("D16", "icondefault", Double.parseDouble("53.2798603"), Double.parseDouble("-6.278982000000042")));
        mMyMarkersArray.add(new MyMarker("D17", "icondefault", Double.parseDouble("53.3447691"), Double.parseDouble("-6.247868599999947")));
        mMyMarkersArray.add(new MyMarker("D18", "icondefault", Double.parseDouble("53.3447407"), Double.parseDouble("-6.247902100000033")));
        mMyMarkersArray.add(new MyMarker("D19", "icondefault", Double.parseDouble("53.3359996"), Double.parseDouble("-6.282349299999964")));
        mMyMarkersArray.add(new MyMarker("D20", "icondefault", Double.parseDouble("53.3392932"), Double.parseDouble("-6.279809500000056")));
        mMyMarkersArray.add(new MyMarker("D22", "icondefault", Double.parseDouble("53.34137870000001"), Double.parseDouble("-6.299486399999978")));
        mMyMarkersArray.add(new MyMarker("D24", "icondefault", Double.parseDouble("53.3362355"), Double.parseDouble("-6.270165099999986")));

        //Kildare
        mMyMarkersArray.add(new MyMarker("KilW", "icondefault", Double.parseDouble("53.18527560691649"), Double.parseDouble("-7.011191835156296")));
        mMyMarkersArray.add(new MyMarker("KilE", "icondefault", Double.parseDouble("53.23051212346622"), Double.parseDouble("-6.526420106640671")));
        mMyMarkersArray.add(new MyMarker("KilN", "icondefault", Double.parseDouble("53.37249035669105"), Double.parseDouble("-6.791465272656296")));
        mMyMarkersArray.add(new MyMarker("KilS", "icondefault", Double.parseDouble("52.92444496812726"), Double.parseDouble("-6.846396913281296")));
        mMyMarkersArray.add(new MyMarker("KilC", "icondefault", Double.parseDouble("53.18527560691649"), Double.parseDouble("-6.759879579296921")));

        //Wicklow
        mMyMarkersArray.add(new MyMarker("WikW", "icondefault", Double.parseDouble("52.96416654259141"), Double.parseDouble("-6.709067811718796")));
        mMyMarkersArray.add(new MyMarker("WikE", "icondefault", Double.parseDouble("52.96085780426767"), Double.parseDouble("-6.078727235546921")));
        mMyMarkersArray.add(new MyMarker("WikN", "icondefault", Double.parseDouble("53.16058101173315"), Double.parseDouble("-6.317679872265671")));
        mMyMarkersArray.add(new MyMarker("WikS", "icondefault", Double.parseDouble("52.78097903439996"), Double.parseDouble("-6.500327577343796")));
        mMyMarkersArray.add(new MyMarker("WikC", "icondefault", Double.parseDouble("52.99724000037819"), Double.parseDouble("-6.331412782421921")));

        //Carlow
        mMyMarkersArray.add(new MyMarker("CarW", "icondefault", Double.parseDouble("52.74358356655464"), Double.parseDouble("-7.024924745312546")));
        mMyMarkersArray.add(new MyMarker("CarE", "icondefault", Double.parseDouble("52.7153079133867"), Double.parseDouble("-6.667869081250046")));
        mMyMarkersArray.add(new MyMarker("CarN", "icondefault", Double.parseDouble("52.87225504213309"), Double.parseDouble("-6.700828065625046")));
        mMyMarkersArray.add(new MyMarker("CarS", "icondefault", Double.parseDouble("52.548609146748234"), Double.parseDouble("-6.877982606640671")));
        mMyMarkersArray.add(new MyMarker("CarC", "icondefault", Double.parseDouble("52.72861638730675"), Double.parseDouble("-6.787345399609421")));

        //Wexford
        mMyMarkersArray.add(new MyMarker("WexW", "icondefault", Double.parseDouble("52.55278435800634"), Double.parseDouble("-6.754386415234421")));
        mMyMarkersArray.add(new MyMarker("WexE", "icondefault", Double.parseDouble("52.45916438880156"), Double.parseDouble("-6.330039491406296")));
        mMyMarkersArray.add(new MyMarker("WexN", "icondefault", Double.parseDouble("52.724457925451155"), Double.parseDouble("-6.306693544140671")));
        mMyMarkersArray.add(new MyMarker("WexS", "icondefault", Double.parseDouble("52.27132581249872"), Double.parseDouble("-6.714560975781296")));
        mMyMarkersArray.add(new MyMarker("WexC", "icondefault", Double.parseDouble("52.513521687436835"), Double.parseDouble("-6.529166688671921")));

        //Kilkenny
        mMyMarkersArray.add(new MyMarker("KikW", "icondefault", Double.parseDouble("52.54693895101734"), Double.parseDouble("-7.391593446484421")));
        mMyMarkersArray.add(new MyMarker("KikE", "icondefault", Double.parseDouble("52.541927982459654"), Double.parseDouble("-6.985099305859421")));
        mMyMarkersArray.add(new MyMarker("KikN", "icondefault", Double.parseDouble("52.82332175344236"), Double.parseDouble("-7.265250673046921")));
        mMyMarkersArray.add(new MyMarker("KikS", "icondefault", Double.parseDouble("52.32087863770545"), Double.parseDouble("-7.152640809765671")));
        mMyMarkersArray.add(new MyMarker("KikC", "icondefault", Double.parseDouble("52.571985213723785"), Double.parseDouble("-7.180106630078171")));

        //Waterford
        mMyMarkersArray.add(new MyMarker("WatW", "icondefault", Double.parseDouble("52.19647170903787"), Double.parseDouble("-7.925803651562546")));
        mMyMarkersArray.add(new MyMarker("WatE", "icondefault", Double.parseDouble("52.19142082266953"), Double.parseDouble("-7.072989930859421")));
        mMyMarkersArray.add(new MyMarker("WatN", "icondefault", Double.parseDouble("52.31836034029933"), Double.parseDouble("-7.508323182812546")));
        mMyMarkersArray.add(new MyMarker("WatS", "icondefault", Double.parseDouble("51.99990712812827"), Double.parseDouble("-7.755515565625046")));
        mMyMarkersArray.add(new MyMarker("WatC", "icondefault", Double.parseDouble("52.17036928498166"), Double.parseDouble("-7.596213807812546")));

        //Laois
        mMyMarkersArray.add(new MyMarker("LaoW", "icondefault", Double.parseDouble("52.97243728060307"), Double.parseDouble("-7.675864686718796")));
        mMyMarkersArray.add(new MyMarker("LaoE", "icondefault", Double.parseDouble("52.99889300872788"), Double.parseDouble("-7.115561952343796")));
        mMyMarkersArray.add(new MyMarker("LaoN", "icondefault", Double.parseDouble("53.1226883555814"), Double.parseDouble("-7.325675477734421")));
        mMyMarkersArray.add(new MyMarker("LaoS", "icondefault", Double.parseDouble("52.87971459429728"), Double.parseDouble("-7.300956239453171")));
        mMyMarkersArray.add(new MyMarker("LaoC", "icondefault", Double.parseDouble("52.98236007737649"), Double.parseDouble("-7.360007753125046")));

        //Tipperary
        mMyMarkersArray.add(new MyMarker("TipW", "icondefault", Double.parseDouble("52.60869387043701"), Double.parseDouble("-8.174369325390671")));
        mMyMarkersArray.add(new MyMarker("TipE", "icondefault", Double.parseDouble("52.67868866729732"), Double.parseDouble("-7.586600770703171")));
        mMyMarkersArray.add(new MyMarker("TipN", "icondefault", Double.parseDouble("53.0021988355828"), Double.parseDouble("-8.146903505078171")));
        mMyMarkersArray.add(new MyMarker("TipS", "icondefault", Double.parseDouble("52.37959823328749"), Double.parseDouble("-7.765128602734421")));
        mMyMarkersArray.add(new MyMarker("TipC", "icondefault", Double.parseDouble("52.62370218610875"), Double.parseDouble("-7.809073915234421")));

        //Cork
        mMyMarkersArray.add(new MyMarker("CorW", "icondefault", Double.parseDouble("51.992297124229026"), Double.parseDouble("-9.064261903515671")));
        mMyMarkersArray.add(new MyMarker("CorE", "icondefault", Double.parseDouble("52.07846810988747"), Double.parseDouble("-8.097465028515671")));
        mMyMarkersArray.add(new MyMarker("CorN", "icondefault", Double.parseDouble("52.27720786012349"), Double.parseDouble("-8.586356630078171")));
        mMyMarkersArray.add(new MyMarker("CorS", "icondefault", Double.parseDouble("51.62208385715535"), Double.parseDouble("-9.034049501171921")));
        mMyMarkersArray.add(new MyMarker("CorC", "icondefault", Double.parseDouble("51.98553159035917"), Double.parseDouble("-8.600089540234421")));

        //Kerry
        mMyMarkersArray.add(new MyMarker("KerW", "icondefault", Double.parseDouble("52.177948986491465"), Double.parseDouble("-9.874503602734421")));
        mMyMarkersArray.add(new MyMarker("KerE", "icondefault", Double.parseDouble("52.066650552641455"), Double.parseDouble("-9.248282899609421")));
        mMyMarkersArray.add(new MyMarker("KerN", "icondefault", Double.parseDouble("52.52522062217707"), Double.parseDouble("-9.498221864453171")));
        mMyMarkersArray.add(new MyMarker("KerS", "icondefault", Double.parseDouble("51.83982528271116"), Double.parseDouble("-9.833304872265671")));
        mMyMarkersArray.add(new MyMarker("KerC", "icondefault", Double.parseDouble("52.08015607701568"), Double.parseDouble("-9.509208192578171")));

        //Limerick
        mMyMarkersArray.add(new MyMarker("LimW", "icondefault", Double.parseDouble("52.47673332318061"), Double.parseDouble("-9.215323915234421")));
        mMyMarkersArray.add(new MyMarker("LimE", "icondefault", Double.parseDouble("52.44995881477817"), Double.parseDouble("-8.429801454296921")));
        mMyMarkersArray.add(new MyMarker("LimN", "icondefault", Double.parseDouble("52.64537178199148"), Double.parseDouble("-8.641288270703171")));
        mMyMarkersArray.add(new MyMarker("LimS", "icondefault", Double.parseDouble("52.387980380040865"), Double.parseDouble("-8.707206239453171")));
        mMyMarkersArray.add(new MyMarker("LimC", "icondefault", Double.parseDouble("52.50516339809591"), Double.parseDouble("-8.720939149609421")));

        //Clare
        mMyMarkersArray.add(new MyMarker("ClaW", "icondefault", Double.parseDouble("52.87142612380369"), Double.parseDouble("-9.369132508984421")));
        mMyMarkersArray.add(new MyMarker("ClaE", "icondefault", Double.parseDouble("52.83493803340351"), Double.parseDouble("-8.509452333203171")));
        mMyMarkersArray.add(new MyMarker("ClaN", "icondefault", Double.parseDouble("52.97905273163226"), Double.parseDouble("-8.907706727734421")));
        mMyMarkersArray.add(new MyMarker("ClaS", "icondefault", Double.parseDouble("52.71031618863242"), Double.parseDouble("-8.839042176953171")));
        mMyMarkersArray.add(new MyMarker("ClaC", "icondefault", Double.parseDouble("52.858161276140365"), Double.parseDouble("-8.913199891796921")));

        //Offaly
        mMyMarkersArray.add(new MyMarker("OffW", "icondefault", Double.parseDouble("53.18033782491882"), Double.parseDouble("-7.962882508984421")));
        mMyMarkersArray.add(new MyMarker("OffE", "icondefault", Double.parseDouble("53.28719587155457"), Double.parseDouble("-7.078483094921921")));
        mMyMarkersArray.add(new MyMarker("OffN", "icondefault", Double.parseDouble("53.37904440852968"), Double.parseDouble("-7.625052919140671")));
        mMyMarkersArray.add(new MyMarker("OffS", "icondefault", Double.parseDouble("52.96747502770004"), Double.parseDouble("-7.880485048046921")));
        mMyMarkersArray.add(new MyMarker("OffC", "icondefault", Double.parseDouble("53.24941505333632"), Double.parseDouble("-7.479484071484421")));

        //Galway
        mMyMarkersArray.add(new MyMarker("GalW", "icondefault", Double.parseDouble("53.44125760849885"), Double.parseDouble("-9.841544618359421")));
        mMyMarkersArray.add(new MyMarker("GalE", "icondefault", Double.parseDouble("53.20995599055065"), Double.parseDouble("-8.201835145703171")));
        mMyMarkersArray.add(new MyMarker("GalN", "icondefault", Double.parseDouble("53.586611565598304"), Double.parseDouble("-8.583610048046921")));
        mMyMarkersArray.add(new MyMarker("GalS", "icondefault", Double.parseDouble("53.036894737108604"), Double.parseDouble("-8.465507020703171")));
        mMyMarkersArray.add(new MyMarker("GalC", "icondefault", Double.parseDouble("53.3839592855321"), Double.parseDouble("-8.880240907421921")));

        //Mayo
        mMyMarkersArray.add(new MyMarker("MayW", "icondefault", Double.parseDouble("53.90657938046142"), Double.parseDouble("-9.888236512890671")));
        mMyMarkersArray.add(new MyMarker("MayE", "icondefault", Double.parseDouble("53.817493382112474"), Double.parseDouble("-8.778617372265671")));
        mMyMarkersArray.add(new MyMarker("MayN", "icondefault", Double.parseDouble("54.098680461188636"), Double.parseDouble("-9.138419618359421")));
        mMyMarkersArray.add(new MyMarker("MayS", "icondefault", Double.parseDouble("53.66642519947376"), Double.parseDouble("-9.358146180859421")));
        mMyMarkersArray.add(new MyMarker("MayC", "icondefault", Double.parseDouble("53.84828999190444"), Double.parseDouble("-9.201591005078171")));

        //Westmeath
        mMyMarkersArray.add(new MyMarker("WstW", "icondefault", Double.parseDouble("53.431440520633394"), Double.parseDouble("-7.864005555859421")));
        mMyMarkersArray.add(new MyMarker("WstE", "icondefault", Double.parseDouble("53.575197293264125"), Double.parseDouble("-7.053763856640671")));
        mMyMarkersArray.add(new MyMarker("WstN", "icondefault", Double.parseDouble("53.7460879499687"), Double.parseDouble("-7.328422059765671")));
        mMyMarkersArray.add(new MyMarker("WstS", "icondefault", Double.parseDouble("53.35118272195343"), Double.parseDouble("-7.473990907421921")));
        mMyMarkersArray.add(new MyMarker("WstC", "icondefault", Double.parseDouble("53.521345629267174"), Double.parseDouble("-7.336661805859421")));

        //Meath
        mMyMarkersArray.add(new MyMarker("MthW", "icondefault", Double.parseDouble("53.74365153180485"), Double.parseDouble("-7.169120301953171")));
        mMyMarkersArray.add(new MyMarker("MthE", "icondefault", Double.parseDouble("53.64200848514939"), Double.parseDouble("-6.336905946484421")));
        mMyMarkersArray.add(new MyMarker("MthN", "icondefault", Double.parseDouble("53.846669682218106"), Double.parseDouble("-6.784598817578171")));
        mMyMarkersArray.add(new MyMarker("MthS", "icondefault", Double.parseDouble("53.44125760849896"), Double.parseDouble("-6.743400087109421")));
        mMyMarkersArray.add(new MyMarker("MthC", "icondefault", Double.parseDouble("53.63875185472496"), Double.parseDouble("-6.682975282421921")));

        //Louth
        mMyMarkersArray.add(new MyMarker("LthW", "icondefault", Double.parseDouble("53.89848849059008"), Double.parseDouble("-6.644523133984421")));
        mMyMarkersArray.add(new MyMarker("LthE", "icondefault", Double.parseDouble("53.86125021170893"), Double.parseDouble("-6.277854432812546")));
        mMyMarkersArray.add(new MyMarker("LthN", "icondefault", Double.parseDouble("54.030174697793264"), Double.parseDouble("-6.453635682812546")));
        mMyMarkersArray.add(new MyMarker("LthS", "icondefault", Double.parseDouble("53.73390444636305"), Double.parseDouble("-6.371238221875046")));
        mMyMarkersArray.add(new MyMarker("LthC", "icondefault", Double.parseDouble("53.90172503457363"), Double.parseDouble("-6.500327577343796")));

        //Longford
        mMyMarkersArray.add(new MyMarker("LngW", "icondefault", Double.parseDouble("53.74933628776614"), Double.parseDouble("-7.876365175000046")));
        mMyMarkersArray.add(new MyMarker("LngE", "icondefault", Double.parseDouble("53.693267254331516"), Double.parseDouble("-7.549521913281296")));
        mMyMarkersArray.add(new MyMarker("LngN", "icondefault", Double.parseDouble("53.81992551674191"), Double.parseDouble("-7.585227479687546")));
        mMyMarkersArray.add(new MyMarker("LngS", "icondefault", Double.parseDouble("53.57193550647722"), Double.parseDouble("-7.854392518750046")));
        mMyMarkersArray.add(new MyMarker("LngC", "icondefault", Double.parseDouble("53.71927957942502"), Double.parseDouble("-7.732169618359421")));

        //Roscommon
        mMyMarkersArray.add(new MyMarker("RosW", "icondefault", Double.parseDouble("53.77450239117986"), Double.parseDouble("-8.638541688671921")));
        mMyMarkersArray.add(new MyMarker("RosE", "icondefault", Double.parseDouble("53.77612549287838"), Double.parseDouble("-7.905204286328171")));
        mMyMarkersArray.add(new MyMarker("RosN", "icondefault", Double.parseDouble("53.99143915080375"), Double.parseDouble("-8.197715272656296")));
        mMyMarkersArray.add(new MyMarker("RosS", "icondefault", Double.parseDouble("53.35200244339473"), Double.parseDouble("-8.086478700390671")));
        mMyMarkersArray.add(new MyMarker("RosC", "icondefault", Double.parseDouble("53.706275426944835"), Double.parseDouble("-8.208701600781296")));

        //Sligo
        mMyMarkersArray.add(new MyMarker("SliW", "icondefault", Double.parseDouble("54.217691563315945"), Double.parseDouble("-8.992850770703171")));
        mMyMarkersArray.add(new MyMarker("SliE", "icondefault", Double.parseDouble("54.10673253456238"), Double.parseDouble("-8.351523866406296")));
        mMyMarkersArray.add(new MyMarker("SliN", "icondefault", Double.parseDouble("54.33876259553471"), Double.parseDouble("-8.479239930859421")));
        mMyMarkersArray.add(new MyMarker("SliS", "icondefault", Double.parseDouble("54.042272162293195"), Double.parseDouble("-8.628928651562546")));
        mMyMarkersArray.add(new MyMarker("SliC", "icondefault", Double.parseDouble("54.167877213088325"), Double.parseDouble("-8.587729921093796")));

        //Leitrim
        mMyMarkersArray.add(new MyMarker("LeiW", "icondefault", Double.parseDouble("54.179130862591855"), Double.parseDouble("-8.211448182812546")));
        mMyMarkersArray.add(new MyMarker("LeiE", "icondefault", Double.parseDouble("54.01000443324115"), Double.parseDouble("-7.656638612500046")));
        mMyMarkersArray.add(new MyMarker("LeiN", "icondefault", Double.parseDouble("54.432328137536054"), Double.parseDouble("-8.289725770703171")));
        mMyMarkersArray.add(new MyMarker("LeiS", "icondefault", Double.parseDouble("53.86367980611678"), Double.parseDouble("-7.850272645703171")));
        mMyMarkersArray.add(new MyMarker("LeiC", "icondefault", Double.parseDouble("54.10834276163331"), Double.parseDouble("-7.971122255078171")));

        //Cavan
        mMyMarkersArray.add(new MyMarker("CavW", "icondefault", Double.parseDouble("53.91224207106775"), Double.parseDouble("-7.508323182812546")));
        mMyMarkersArray.add(new MyMarker("CavE", "icondefault", Double.parseDouble("53.929225536739686"), Double.parseDouble("-6.862876405468796")));
        mMyMarkersArray.add(new MyMarker("CavN", "icondefault", Double.parseDouble("54.19680898350161"), Double.parseDouble("-7.899711122265671")));
        mMyMarkersArray.add(new MyMarker("CavS", "icondefault", Double.parseDouble("53.81668263920075"), Double.parseDouble("-7.186973085156296")));
        mMyMarkersArray.add(new MyMarker("CavC", "icondefault", Double.parseDouble("53.967211119944686"), Double.parseDouble("-7.221305360546921")));

        //Monaghan
        mMyMarkersArray.add(new MyMarker("MonW", "icondefault", Double.parseDouble("54.14174086435596"), Double.parseDouble("-7.214438905468796")));
        mMyMarkersArray.add(new MyMarker("MonE", "icondefault", Double.parseDouble("54.12363680019094"), Double.parseDouble("-6.675422181835984")));
        mMyMarkersArray.add(new MyMarker("MonN", "icondefault", Double.parseDouble("54.39196917466502"), Double.parseDouble("-7.020118226757859")));
        mMyMarkersArray.add(new MyMarker("MonS", "icondefault", Double.parseDouble("53.96519148131463"), Double.parseDouble("-6.722800721875046")));
        mMyMarkersArray.add(new MyMarker("MonC", "icondefault", Double.parseDouble("54.11961260030068"), Double.parseDouble("-6.929481019726609")));

        //Donegal
        mMyMarkersArray.add(new MyMarker("DonW", "icondefault", Double.parseDouble("54.802481950261814"), Double.parseDouble("-8.413321962109421")));
        mMyMarkersArray.add(new MyMarker("DonE", "icondefault", Double.parseDouble("54.79139858089316"), Double.parseDouble("-7.572867860546921")));
        mMyMarkersArray.add(new MyMarker("DonN", "icondefault", Double.parseDouble("55.065999411600636"), Double.parseDouble("-7.644278993359421")));
        mMyMarkersArray.add(new MyMarker("DonS", "icondefault", Double.parseDouble("54.570286588074715"), Double.parseDouble("-7.994468202343796")));
        mMyMarkersArray.add(new MyMarker("DonC", "icondefault", Double.parseDouble("53.3362355"), Double.parseDouble("-6.270165099999986")));

        setUpMap();

        plotMarkers(mMyMarkersArray);
    }

    private void plotMarkers(ArrayList<MyMarker> markers)
    {
        if(markers.size() > 0)
        {
            for (MyMarker myMarker : markers)
            {

                // Create user marker with custom icon and other options
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()));
                markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.currentlocation_icon));

                Marker currentMarker = mMap.addMarker(markerOption);
                mMarkersHashMap.put(currentMarker, myMarker);

                mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
            }
        }
    }

    private void gotoLocation(double lat, double lng, float zoom)
    {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);
    }

    public void geoLocate(View v) throws IOException
    {
        hideSoftKeyBoard(v);

        EditText et = (EditText) findViewById(R.id.edittext1);
        String location = et.getText().toString();

        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(location, 1);
        Address add = list.get(0);
        String locality = add.getLocality();
        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

        double lat = add.getLatitude();
        double lng = add.getLongitude();
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Marker"));
        mMyMarkersArray.add(new MyMarker("Marker", "icon1", lng, lat));

        gotoLocation(lat, lng, DEFAULTZOOM);
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Marker"));
        mMyMarkersArray.add(new MyMarker("Marker", "icon1", lng, lat));
        plotMarkers(mMyMarkersArray);
    }

    private void hideSoftKeyBoard(View v)
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
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

    private int manageMarkerIcon(String markerIcon)
    {
        if (markerIcon.equals("icon1"))
            return R.mipmap.icon1;
        else if(markerIcon.equals("icon2"))
            return R.mipmap.icon2;
        else if(markerIcon.equals("icon3"))
            return R.mipmap.icon3;
        else if(markerIcon.equals("icon4"))
            return R.mipmap.icon4;
        else if(markerIcon.equals("icon5"))
            return R.mipmap.icon5;
        else if(markerIcon.equals("icon6"))
            return R.mipmap.icon6;
        else if(markerIcon.equals("icon7"))
            return R.mipmap.icon7;
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

            ImageView markerIcon = (ImageView) v.findViewById(R.id.marker_icon);

            TextView markerLabel = (TextView)v.findViewById(R.id.marker_label);

            TextView anotherLabel = (TextView)v.findViewById(R.id.another_label);
            anotherLabel.setOnClickListener(newsfeed);

            markerIcon.setImageResource(manageMarkerIcon(myMarker.getmIcon()));
            anotherLabel.setText("Newsfeed");
            markerLabel.setText(myMarker.getmLabel());


            return v;
        }
    }
    View.OnClickListener newsfeed = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent newsfeedIntent = new Intent(getApplicationContext(), Newsfeed.class);
            startActivity(newsfeedIntent);
        }
    };
}