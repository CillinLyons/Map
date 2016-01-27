package com.example.cillin.map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.microsoft.windowsazure.mobileservices.*;

import java.net.MalformedURLException;

/**
 * Created by Cillin on 22/01/2016.
 */
public class MainMenu extends Activity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        populateMenu();
        changePage();
    }

    private void populateMenu()
    {
        //Create items for the menu
        String[] menuItems = {"Map", "Crime Report"};

        //Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.main_menu_items, menuItems);

        //Configure menu
        ListView menu = (ListView) findViewById(R.id.menu);
        menu.setAdapter(adapter);
    }

    private void changePage()
    {
        ListView menu = (ListView) findViewById(R.id.menu);
        menu.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                switch(position)
                {
                    case 0:  Intent firstActivity = new Intent(MainMenu.this, MapsActivity.class);
                        startActivity(firstActivity);
                        break;
                    case 1:  Intent secondActivity = new Intent(MainMenu.this, CrimeInput.class);
                        startActivity(secondActivity);
                        break;

                }
            }
        });
    }

}
