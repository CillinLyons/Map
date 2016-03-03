package com.example.cillin.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Cillin on 29/02/2016.
 */
public class InfoWindowList extends Activity
{
    private TextView county;
    private ImageView mIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infowindow_list);

        String countyString;
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

        county = (TextView)findViewById(R.id.list_location);
        county.setText(countyString);
        mIcon = (ImageView) findViewById(R.id.CountyimageView2);
        ChangeIcon.setIcon(countyString, mIcon);

        populateMenu();
        changePage();
    }

    private void populateMenu()
    {
        //Create items for the menu
        String[] menuItems = {"Crime Statistics", "Crime Input", "Newsfeed", "Newsfeed Input"};

        //Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.infowindow_items, menuItems);

        //Configure menu
        ListView menu = (ListView) findViewById(R.id.infowindow_menu);
        menu.setAdapter(adapter);
    }

    private void changePage()
    {
        ListView menu = (ListView) findViewById(R.id.infowindow_menu);
        menu.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                switch(position)
                {
                    case 0:  Intent CrimeStatsActivity = new Intent(InfoWindowList.this, CrimeStats.class);
                        CrimeStatsActivity.putExtra("COUNTY", county.getText().toString());
                        startActivity(CrimeStatsActivity);
                        break;
                    case 1:  Intent CrimeInputActivity = new Intent(InfoWindowList.this, CrimeInput.class);
                        CrimeInputActivity.putExtra("COUNTY", county.getText().toString());
                        startActivity(CrimeInputActivity);
                        break;
                    case 2:  Intent NewsfeedActivity = new Intent(InfoWindowList.this, Newsfeed.class);
                        NewsfeedActivity.putExtra("COUNTY", county.getText().toString());
                        startActivity(NewsfeedActivity);
                        break;
                    case 3:  Intent NewsfeedInputActivity = new Intent(InfoWindowList.this, NewsfeedInput.class);
                        NewsfeedInputActivity.putExtra("COUNTY", county.getText().toString());
                        startActivity(NewsfeedInputActivity);
                        break;
                }
            }
        });
    }
}
