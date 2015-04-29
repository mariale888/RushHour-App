package com.example.rushhour;

import java.util.concurrent.ExecutionException;
import org.json.JSONArray;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import elements.Nodes;
import elements.Ways;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.support.v4.widget.DrawerLayout;


/*
 * Main Fragment controls
 *  - navigation controller menu
 * 
 * */

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, 
        	LocationListener
        {

	public static FragmentManager fragmentManager;
	public UserAgent user;
	public static Double latitude, longitude;
	
	private String mLatitudeText;
	private String mLongitudeText;
	
	//private GoogleApiClient mGoogleApiClient;
	public static String MAP_TAG; 
	private String QUERY;
	
	 private LocationManager locationManager;
	 public Location location;
	// flag for GPS Status
	 boolean isGPSEnabled = false;
	 // flag for network status
	 boolean isNetworkEnabled = false;
	 boolean canGetLocation = false;
	 
	// The minimum distance to change updates in meters
	    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; 

	    // The minimum time between updates in milliseconds
	    private static final long MIN_TIME_BW_UPDATES = 5; 
	
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        
        mTitle    = getTitle();
        MAP_TAG   = "CONNECT";
        QUERY     = "QUERY";
        latitude  = 40.4380673;
	    longitude = -79.9229868;
        
	    user = UserAgent.getInstance();
        buildGoogleListener();
        
  
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        
        
     // Initializing the object of the FragmentManager. Here I'm passing getSupportFragmentManager(). You can pass getFragmentManager() if you are coding for Android 3.0 or above.
        fragmentManager = getSupportFragmentManager();
      
    }


    //------
    // GOOGLE MAPS
  
	 protected synchronized void buildGoogleListener()
	 {
		 // getting location manager
		 locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
	     if (locationManager == null) {
	    	 Log.d(MAP_TAG, "Location Manager Not Available");
	    	 return;
	     }
	       
        // getting GPS status
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // getting network status
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        // if no GPS or network provided
        if (!isGPSEnabled && !isNetworkEnabled) {
            Log.d(MAP_TAG,"no gps or network enable");
        } 
        else {
        	this.canGetLocation = true;
            // if GPS Enabled get lat/long using GPS Service
            if (isGPSEnabled) {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                Log.d(MAP_TAG, "GPS Enabled");

                location = locationManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location == null){
                	 Log.d(MAP_TAG,"Location null");
                }
                if (location != null) {
                	latitude  = location.getLatitude();
                	longitude = location.getLongitude();
                    Log.d(MAP_TAG,"Location Are GPS: " + latitude + ":" + longitude);
                }
            }

            // First get location from Network Provider
            if (isNetworkEnabled) {
                if (location == null) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    Log.d(MAP_TAG, "Network");

                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        //updateGPSCoordinates();
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        Log.d(MAP_TAG,"Location Are network" + latitude + ":" + longitude);
                    }
                }
	        }
	     }	
        
        // Set up query to google maps to get cur location node
        setUpMapCurLocation();
	 }
	 
	 public void updateGPSCoordinates(Location location) {
	    if (location != null) {
	        latitude      = location.getLatitude();
	        longitude     = location.getLongitude();
	        
	        Log.d(MAP_TAG,latitude.toString() + " " + longitude.toString() );
	    }
	    else
	        Log.d(MAP_TAG,"in update gps, no location");

	}
	 
	 public void setUpMapCurLocation()
	 {
		 Log.d(MAP_TAG, "Connected google maps");
		 mLatitudeText  = Double.toString(latitude).replace(".", "");
	     mLongitudeText = Double.toString(longitude).replace(".", "");
	       
	     // String query = "SELECT * FROM nodes JOIN way_nodes ON way_nodes.way_id = '268548985' AND nodes.node_id = way_nodes.node_id";// AND nodes.node_id = '105013085'";
	      // String query = "SELECT * FROM nodes JOIN tempTable ON tempTable.node_id = nodes.node_id LIMIT 200";
	      String query = "SELECT * FROM nodes WHERE latitude LIKE '" + mLatitudeText.substring(0, mLatitudeText.length() - 2) + 
	    		  "%' AND longitude LIKE '" + mLongitudeText.substring(0, mLongitudeText.length() - 2) + "%'";
	     
	     
	      
	      
	      
	     String query1 ="select * from nodes where node_id = 924785121 or " +
	     		"node_id =472546497 or " +
	     		"node_id =472546570 or " +
	     		"node_id =2766492995 or " +
	     		"node_id =104683082 or " +
	     		"node_id =3192430398 or " +
	     		"node_id =302452261 or " +
	     		"node_id =2766492996 or " +
	     		"node_id =302759445 or " +
	     		"node_id =302756874 or " +
	     		"node_id =106098085 or " +
	     		"node_id =302755545 or " +
	     		"node_id =1705345498 or " +
	     		"node_id =106098077 or " +
	     		"node_id =303349589 or " +
	     		"node_id =302979786 or " +
	     		"node_id =270624664 or " +
	     		"node_id =303189375 or " +
	     		"node_id =270927856 or " +
	     		"node_id =2967210791 or " +
	     		"node_id =105057945 or " +
	     		"node_id =2703812717 or " +
	     		"node_id =269095128 or " +
	     		"node_id =3382338896 or " +
	     		"node_id =3382338895 or " +
	     		"node_id =3382338894 or " +
	     		"node_id =3382338893 or " +
	     		"node_id =3382338900 or " +
	     		"node_id =3382324192 or " +
	     		"node_id =3382324191 or " +
	     		"node_id =3382324190 or " +
	     		"node_id =3382324189 or " +
	     		"node_id =3382324188 or " +
	     		"node_id =3382324187 or " +
	     		"node_id =3382324186 or " +
	     		"node_id =3382324185";
	      Log.d(MAP_TAG, query);
	      
	      // try to run initial query
	      try {
	    	  JSONArray rs = new AsyncTaskRushHour().execute(query).get();
			  
	    	  try {
	    		
	    		  int R = 6371;
	    		  double min = Double.MAX_VALUE;
	    		  Nodes finalN = new Nodes();
	    		  
	    		  //Nodes newN = new Nodes();
		    	  for(int n=0;n< rs.length();n++)
		    	  {
		    		  Nodes newN = new Nodes();
		    		// Nodes newN = new Nodes(rs.getJSONObject(n));
		    		 newN.loadJSON(rs.getJSONObject(n));
		    		 
		    		double dLat = (newN.getRealCoordinates()[0] - latitude) * Math.PI / 180;
		    		double dLon = (newN.getRealCoordinates()[1] - longitude) * Math.PI / 180;
		    		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		    		          Math.cos( latitude*Math.PI/180 ) * Math.cos( newN.getRealCoordinates()[0] * Math.PI/180 ) * 
		    		          Math.sin(dLon/2) * Math.sin(dLon/2); 
		    		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		    		double distance = R * c;
		    		
		    		 if(distance < min)
		    		 {
		    			 min = distance;
		    			 finalN = newN;
		    		 }
		    		 //user.finalWay.addNode(newN);
		    	  }
		    	  if(min != Double.MAX_VALUE) {
		    		  user.setCurNode(finalN);
		    		  //user.finalWay.addNode(finalN);
		    		 Log.d(MAP_TAG, "found init location ID " + finalN.getId());// + " " + finalN.geIntCoordinates()); 
		    	  }
		    	//Log.d(MAP_TAG, rs.getJSONObject(n).toString() );  
		    	 Log.d(MAP_TAG, Integer.toString(user.finalWay.getNodeList().size()) );
	    	  }
	    	  catch (Exception e) {
	    		  e.printStackTrace();
	    	  }
	    	  Log.d(MAP_TAG, "json " + Integer.toString(rs.length())); 
	    	 // Log.d(MAP_TAG, "way " + Integer.toString(finalWay.getNodeList().size())); 
			
		  } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  } catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
	 }
	 
	// on location change save cur location	
	 public void onLocationChanged(Location location) {
		 this.location = location;
		 Log.d(MAP_TAG," location change");
		 updateGPSCoordinates(location);
	 }

	 public void onProviderDisabled(String provider) {}
	        
	 public void onProviderEnabled(String provider) {}
	 
	 @Override
	 public void onStatusChanged(String provider, int status, Bundle extras) {}

	 
        
 
    //-----
    // UI BARS
	
	
	@Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        
        switch (position) {
        case 0:
        	 mTitle = getString(R.string.title_section1);
        	 HomeFragment fragment_h = new HomeFragment();
        	 fragmentManager.beginTransaction()
             .replace(R.id.container, fragment_h)
             .commit(); 
            break;
        case 1:
        	 mTitle = getString(R.string.title_section2);
        	 CalendarFragment fragment_c = new CalendarFragment();
        	 fragmentManager.beginTransaction()
             .replace(R.id.container, fragment_c)
             .commit();
            break;
        case 2:
        	 mTitle = getString(R.string.title_section3);
        	 MapViewFragment fragment_m = new MapViewFragment();
        	 fragmentManager.beginTransaction()
             .replace(R.id.container, fragment_m)
             .commit();
            break;
        }
        
        Log.d("DEBUG", Integer.toString(position));
       
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    
    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

	
   
     

}
