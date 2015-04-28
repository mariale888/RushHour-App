package com.example.rushhour;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;

import elements.Nodes;
import elements.Trips;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
	public TextView title;
	public EditText finalDestination;
	public TimePicker arrivalTime;
	public TimePicker departureTime;
	public SeekBar timeWindow;
	
	private View v;
	 
	private static String INPUT;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.calendar_fragment, container, false);
        
        INPUT = "INPUT";
        title			 = (TextView) v.findViewById(R.id.textView2);
        addButton        = (Button) v.findViewById(R.id.addButton);
        finalDestination = (EditText)v.findViewById(R.id.finalDesText);
        arrivalTime      = (TimePicker)v.findViewById(R.id.arrivalText);
        departureTime    = (TimePicker)v.findViewById(R.id.timeDepText);
        timeWindow       = (SeekBar)v.findViewById(R.id.timeWindowBar);
        
        arrivalTime.setIs24HourView(true);
        departureTime.setIs24HourView(true);
       // title.setTextColor(");
        
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               
            	String destination = finalDestination.getText().toString();
            	int timeW 		   = timeWindow.getProgress();
            	
            	Integer arrivalHour   =  arrivalTime.getCurrentHour();
            	Integer arrivalMinute =  arrivalTime.getCurrentMinute();
            	
            	Integer departureHour   =  departureTime.getCurrentHour();
            	Integer departureMinute =  departureTime.getCurrentMinute();

            		
            	// Perform action on click
            	Log.d(INPUT, "final des: "    + destination);
            	Log.d(INPUT, "arrival time: " + arrivalHour.toString() + " " + arrivalMinute.toString());
            	Log.d(INPUT, "departure time: " + departureHour.toString() + " " + departureMinute.toString());
            	Log.d(INPUT, "time window: "    + timeW);
            	
            	
            	if (destination.matches("")  ) {
            	   Toast.makeText(v.getContext(), "You did notenter final destination", Toast.LENGTH_SHORT).show();
            	    return;
            	}
            	
            	Toast.makeText(v.getContext(), "Your trip is being calculated!", Toast.LENGTH_SHORT).show();
            	
            	// user trips
            	UserAgent user = UserAgent.getInstance();
            	String departure = departureHour.toString() + "," + departureMinute.toString();
            	String arrival   = arrivalHour.toString() + "," + arrivalMinute.toString();
            	// Getting coordinates from destination
            	
            	Geocoder coder = new Geocoder(v.getContext());
            	List<Address> address;
            	
            	
            	 try {
            		 address = coder.getFromLocationName(destination,5);
					
            	    if (address == null) {
                    	Toast.makeText(v.getContext(), "Destination not found!", Toast.LENGTH_SHORT).show();
            	        return;
            	    }
            	    
            	    // coordinates that Google Maps found of destinations
            	    Address location = address.get(0);    
            	   
            	   Log.d(INPUT, "address "+  location.getLatitude() + " " +  location.getLongitude());
            	   String mLatitudeText  = Double.toString(location.getLatitude() ).replace(".", "");
            	   String mLongitudeText = Double.toString(location.getLongitude() ).replace(".", "");
           	       
           	     
            	    // find node:
            	    String query = "SELECT * FROM nodes WHERE latitude LIKE '" + mLatitudeText.substring(0, mLatitudeText.length() - 4) + 
          	    		  "%' AND longitude LIKE '" + mLongitudeText.substring(0, mLongitudeText.length() - 4) + "%'";
          	   
	          	   // try to run initial query
	          	   try {
	          		   JSONArray rs = new AsyncTaskRushHour().execute(query).get();	  
	          	       try {
	          	    	   
	          	    	  int R = 6371;
	          	    	  double min = Double.MAX_VALUE;
	    	    		  Nodes finalN = new Nodes();
	          	    	   for(int n = 0;n< rs.length(); n++)
	          	    	   {
	          	    		 Nodes newN = new Nodes(rs.getJSONObject(n));
	    		    		 
	          	    		double dLat = (newN.getRealCoordinates()[0] -  location.getLatitude()) * Math.PI / 180;
	    		    		double dLon = (newN.getRealCoordinates()[1] -  location.getLatitude()) * Math.PI / 180;
	    		    		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	    		    		          Math.cos(location.getLatitude() * Math.PI/180 ) * Math.cos( newN.getRealCoordinates()[0] * Math.PI/180 ) * 
	    		    		          Math.sin(dLon/2) * Math.sin(dLon/2); 
	    		    		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
	    		    		double distance = R * c;
	    		    		
	    		    		 if(distance < min)
	    		    		 {
	    		    			 min = distance;
	    		    			 finalN = newN;
	    		    		 } 
	          	    	   }
	          	    	   if(min != Double.MAX_VALUE)
	          	    	   {
	          	    		   user.myTrips.add(new Trips(destination,finalN, departure,arrival, timeW));
	          	    		   Log.d(INPUT, "added new trip  " + finalN.getId() + " origin " + user.getCurnNode().getId() );
	          	    		   
	          	    	   }
	          		    	 Log.d(INPUT, Integer.toString(rs.length()) );
	          	       }
	          	       catch (Exception e) {
	          	    	   e.printStackTrace();
	          	    	}
	          	      
	            	 } catch (InterruptedException e) {
	         			// TODO Auto-generated catch block
	         			e.printStackTrace();
	         		  } catch (ExecutionException e) {
	         			// TODO Auto-generated catch block
	         			e.printStackTrace();
	         		  }
            	
            	 } catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            	
            	 
            	// adding to DB
            	/*
            	// insert into DB
            	 String query1 = "INSERT INTO users VALUES(" + user.ID + "," + user.getCurnNode().getId() + "," + departure + "," + arrival +")";
       	     
       	      	Log.d(INPUT, query1);
       	      	try {
					new AsyncTaskRushHour().execute(query).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
       	      
       	     */
       			  
            }
        });
        
        return v;
    }
}
