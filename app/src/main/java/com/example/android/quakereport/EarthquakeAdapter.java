package com.example.android.quakereport;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.graphics.drawable.GradientDrawable;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        super(context,0,earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent ,false);
        }

        Earthquake earthquake= getItem(position);

        TextView magnitude = (TextView)listItemView.findViewById(R.id.magnitude);
        String formattedMagnitude = formatMagnitude(earthquake.getMagnitude());
        magnitude.setText(formattedMagnitude);
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();
        int magnitudeColor = getMagnitudeColor(earthquake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);

        String rawLocation = earthquake.getLocation();
        String locationOffset;
        String originalLocation;
        if(rawLocation.contains("of")){
            int indexOfOf = rawLocation.indexOf("of");
            originalLocation = rawLocation.substring(indexOfOf+2);
            locationOffset=rawLocation.substring(0,indexOfOf+2);
        }else {
            originalLocation=rawLocation;
            locationOffset="Near the";
        }

        TextView location = (TextView)listItemView.findViewById(R.id.original_location);
        location.setText(originalLocation);
        TextView offsetLocation = (TextView)listItemView.findViewById(R.id.offset_location);
        offsetLocation.setText(locationOffset);

        Date dateObject = new Date(earthquake.getTimeInMilliseconds());

        TextView date = (TextView)listItemView.findViewById(R.id.date);
        String formattedDate = formatDate(dateObject);
        date.setText(formattedDate);

        TextView time = (TextView)listItemView.findViewById(R.id.time);
        String formattedTime = formatTime(dateObject);
        time.setText(formattedTime);

        return listItemView;
    }

    private String formatDate(Date dateObject){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("LLL DD, yyyy");
        return simpleDateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        return simpleDateFormat.format(dateObject);
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceID;

        switch ((int) magnitude){
            case 10:
                magnitudeColorResourceID = getContext().getResources().getColor(R.color.magnitude10plus);
                break;
            case 9:
                magnitudeColorResourceID = getContext().getResources().getColor(R.color.magnitude9);
                break;
            case 8:
                magnitudeColorResourceID = getContext().getResources().getColor(R.color.magnitude8);
                break;
            case 7:
                magnitudeColorResourceID = getContext().getResources().getColor(R.color.magnitude7);
                break;
            case 6:
                magnitudeColorResourceID = getContext().getResources().getColor(R.color.magnitude6);
                break;
            case 5:
                magnitudeColorResourceID = getContext().getResources().getColor(R.color.magnitude5);
                break;
            case 4:
                magnitudeColorResourceID = getContext().getResources().getColor(R.color.magnitude4);
                break;
            case 3:
                magnitudeColorResourceID = getContext().getResources().getColor(R.color.magnitude3);
                break;
            case 2:
                magnitudeColorResourceID = getContext().getResources().getColor(R.color.magnitude2);
                break;
            default:
                if(magnitude>10)
                    magnitudeColorResourceID =getContext().getResources().getColor(R.color.magnitude10plus);
                else
                    magnitudeColorResourceID = getContext().getResources().getColor(R.color.magnitude1);
                break;
        }
        return magnitudeColorResourceID;
    }
}
