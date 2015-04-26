package com.example.rushhour;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import elements.Nodes;
import elements.Ways;
import android.R.integer;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;


/*
 * Main Fragment controls
 *  - navigation controller menu
 * 
 * */

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, 
        ConnectionCallbacks, OnConnectionFailedListener
        {

	public static FragmentManager fragmentManager;
	public static Ways finalWay;
	public static Double latitude, longitude;
	
	private String mLatitudeText;
	private String mLongitudeText;
	
	private GoogleApiClient mGoogleApiClient;
	public static String MAP_TAG; 
	private String QUERY;
	
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
        
      
        buildGoogleApiClient();
        
        mTitle    = getTitle();
        finalWay = new Ways();
        MAP_TAG   = "CONNECT";
        QUERY     = "QUERY";
        latitude  = 40.4380673;
	    longitude = -79.9229868;
	    
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        
        
     // Initializing the object of the FragmentManager. Here I'm passing getSupportFragmentManager(). You can pass getFragmentManager() if you are coding for Android 3.0 or above.
        fragmentManager = getSupportFragmentManager();
        
    }

    
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

    //------
    // GOOGLE MAPS
  
    protected synchronized void buildGoogleApiClient() {
  	  Log.d(MAP_TAG, "init map connection");
      mGoogleApiClient = new GoogleApiClient.Builder(this)
          .addConnectionCallbacks(this)
          .addOnConnectionFailedListener(this)
          .addApi(LocationServices.API)
          .build();
  }
  public void onStart(){
      super.onStart();
      Log.e(MAP_TAG, String.valueOf(mGoogleApiClient.isConnected()));
      mGoogleApiClient.connect();
      Log.e(MAP_TAG, String.valueOf(mGoogleApiClient.isConnected()));
  }
    /**
     * Callback called when connected to GCore. Implementation of {@link ConnectionCallbacks}.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
    	
    	Log.d(MAP_TAG, "Connected google maps");
    	Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation( mGoogleApiClient);
    	
    	//if location found, replace default
        if (mLastLocation != null) {
        	Log.d(MAP_TAG, "location found");  
        	latitude  = mLastLocation.getLatitude();
        	longitude = mLastLocation.getLongitude();
        }
        else 
        	 Log.d(MAP_TAG, "location not found");  
        
        mLatitudeText  = Double.toString(latitude).replace(".", "");
        mLongitudeText = Double.toString(longitude).replace(".", "");
       
      String query = "SELECT * FROM nodes JOIN way_nodes ON way_nodes.way_id = '268548985' AND nodes.node_id = way_nodes.node_id";// AND nodes.node_id = '105013085'";
      
       // String query = "SELECT * FROM nodes JOIN tempTable ON tempTable.node_id = nodes.node_id LIMIT 200";
     // String query = "SELECT * FROM nodes WHERE latitude = '" + mLatitudeText + "' AND longitude = '" + mLongitudeText + "'";
     
      
      // try to run initial query
      try {
    	  JSONArray rs = new AsyncTaskRushHour().execute(query).get();
		  
    	  try {
	    	  for(int n=0;n< rs.length();n++)
	    	  {
	    		 Nodes newN = new Nodes(rs.getJSONObject(n));
	    		  finalWay.addNode(newN);
	    		  //Log.d(MAP_TAG, rs.getJSONObject(n).toString() );
	    	  }
	    	  
	    	  //Log.d(MAP_TAG, Integer.toString(finalWay.getNodeList().size()) );
    	  }
    	  catch (Exception e) {
    		  e.printStackTrace();
    	  }
    	  Log.d(MAP_TAG, "json " + Integer.toString(rs.length())); 
    	  Log.d(MAP_TAG, "way " + Integer.toString(finalWay.getNodeList().size())); 
		
	  } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  } catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
      
      
        
    }

 
    /**
     * Implementation of {@link OnConnectionFailedListener}.
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Do nothing
    	Log.d("CONNECT", result.toString());
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		Log.d("CONNECT", Integer.toString(arg0));
	}
    //-----
    // UI BARS
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
