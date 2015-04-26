package com.example.rushhour;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/*
 * Fragment that controls user input.
 *  - address
 *  - departure time
 *  - arrival time
 *  - time window 
 * 
 * */

public class CalendarFragment extends Fragment {
	
	public Button addButton;
	public TextView finalDestination;
	public TextView arrivalTime;
	public TextView departureTime;
	public SeekBar timeWindow;
	
	private View v;
	 
	private static String INPUT;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.calendar_fragment, container, false);
        
        INPUT = "INPUT";
        addButton        = (Button) v.findViewById(R.id.addButton);
        finalDestination = (TextView)v.findViewById(R.id.finalDesText);
        arrivalTime      = (TextView)v.findViewById(R.id.arrivalText);
        departureTime    = (TextView)v.findViewById(R.id.timeDepText);
        timeWindow       = (SeekBar)v.findViewById(R.id.timeWindowBar);
        
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               
            	
            	// Perform action on click
            	Log.d(INPUT, "final des: "    + finalDestination.toString());
            	Log.d(INPUT, "arrival time: " + arrivalTime.toString());
            	Log.d(INPUT, "departure time: " + departureTime.toString());
            	Log.d(INPUT, "time window: "    + timeWindow.toString());
            	
            	
            	
            	
            	
            	
            }
        });
        
        return v;
    }
}
