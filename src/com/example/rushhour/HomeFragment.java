package com.example.rushhour;

import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class HomeFragment extends Fragment {
	
	  TableLayout t1;
	 
	  TextView calendar;
	  TextView edit;
	  View v;
	  
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v =  inflater.inflate(R.layout.home_fragment, container, false);
        
        t1 = (TableLayout) v.findViewById(R.id.calendar_table_layout);
        calendar = (TextView)v.findViewById(R.id.calendar_table_txtView);
       
        // Populate calendar
       
        populate();
       
        return v;
    }
	
	/**
     * Populates the lists with the users calendar
     */
    private void populate()
    {
        String[] calendarNames = {"trip 1:", "trip 2:", "trip 3:"};
        String[] t1calInfo = { "10:20am", "12:00am", "2:0Opm"};
 
        // Adding the rows for both tables
        for(int i = 0; i < calendarNames.length; i++)
          calendarRow(t1, calendarNames[i] , t1calInfo[i]);
       
    }
    
    private void calendarRow(TableLayout table, String col1, String col2){
    	
    	TableRow newRow =  new TableRow(v.getContext());
         //Create text views to be added to the row.
        TextView txDir = new TextView(v.getContext());
        TextView tvDeparture = new TextView(v.getContext());
        TextView tvArrival = new TextView(v.getContext());
       
        //Put the data into the text view by passing it to a user defined function createView()
        createView(newRow, txDir, col1);
        createView(newRow, tvDeparture, col2);
        createView(newRow, tvArrival, col2);
        
        // button
        Button newButton = new Button(v.getContext());
        newButton.setText("edit");
        //Log.d("DEBUG_BUTTON",Integer.toString(newButton.getWidth()));
        // you could initialize them here
    	//newButton.setOnClickListener(listener);
        newRow.addView(newButton);
        Log.d("DEBUG_BUTTON",Integer.toString(newButton.getWidth()));
        //Add the new row to our tableLayout newRow
        table.addView(newRow);
    }
    private void createView(TableRow row, TextView txtV, String viewdata) {
    	txtV.setText(viewdata);
    	row.addView(txtV); // add TextView to row.
    }
       

    public void onResume(){
        super.onResume();
        //populate();
    }


}
