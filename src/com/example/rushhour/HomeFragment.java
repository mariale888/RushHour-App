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

/*
 * Fragment that contains users trip.
 * 
 *  - list table of current trips
 *  - capability to edit/delete a trip
 *  - notification for current trip departure time
 * 
 * */

public class HomeFragment extends Fragment {
	
	  private TableLayout t1;
	 
	  public TextView calendar;
	  public TextView edit;
	  private View v;
	   
	  
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
    	UserAgent user = UserAgent.getInstance();
    
         for(int i=0;i< user.myTrips.size();i++)
    	{
        	  calendarRow(t1, user.myTrips.get(i).destinationStr, user.myTrips.get(i).getDepartureStr(), 
        			  user.myTrips.get(i).getArrivalStr() );
    	
    	}
       if(user.myTrips.size() == 0)
       {
    	   calendarRow(t1, "No Trips", "NAN", "NAN" );
       }

    }
    
    private void calendarRow(TableLayout table, String col1, String col2, String col3){
    	
    	TableRow newRow =  new TableRow(v.getContext());
         //Create text views to be added to the row.
        TextView txDir = new TextView(v.getContext());
        TextView tvDeparture = new TextView(v.getContext());
        TextView tvArrival = new TextView(v.getContext());
        tvArrival.setPadding(45, 0, 0, 0);
       

        //Put the data into the text view by passing it to a user defined function createView()
        createView(newRow, txDir, col1);
        createView(newRow, tvDeparture, col2);
        createView(newRow, tvArrival, col3);
      
        
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
