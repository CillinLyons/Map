package com.example.cillin.map;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Cillin on 04/02/2016.
 */
public class NewsfeedAdapter extends ArrayAdapter<NewsfeedItems>
{
    /**
     * Adapter context
     */
    Context mContext;

    /**
     * Adapter View layout
     */
    int mLayoutResourceId;

    public NewsfeedAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);

        mContext = context;
        mLayoutResourceId = layoutResourceId;
    }

    /**
     * Returns the view for a specific item on the list
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        final NewsfeedItems currentItem = getItem(position);

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
        }

        row.setTag(currentItem);

        final TextView username = (TextView) row.findViewById(R.id.DBusername);
        final TextView membership = (TextView) row.findViewById(R.id.DBmembership);
        final TextView area = (TextView) row.findViewById(R.id.DBarea);
        final TextView message = (TextView) row.findViewById(R.id.DBmessage);
        final TextView dateVar = (TextView) row.findViewById(R.id.textViewDate);
        //final TextView id = (TextView) row.findViewById(R.id.textID);

        username.setText(currentItem.getUsername());
        membership.setText(", " + currentItem.getMembership() + ", ");
        area.setText(currentItem.getArea());
        message.setText(currentItem.getMessage());
        dateVar.setText(currentItem.getDate().toString().substring(0, 20));
        //id.setText(currentItem.getID());

        return row;
    }

}
