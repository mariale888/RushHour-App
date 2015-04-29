package com.example.rushhour;

import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import elements.Nodes;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
 * Fragment that shows google maps.
 *  
 * 
 * */

public class MapViewFragment extends Fragment {
	
	private static View view;
	private static GoogleMap mMap;
	private static LatLng myLocation;
	
	public static String MAP_TAG;
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		
		
		view = inflater.inflate(R.layout.maps_fragment, container, false);
	    // Passing hard coded values for latitude & longitude. Please change as per your need. This is just used to drop a Marker on the Map
	 
		MAP_TAG = MainActivity.MAP_TAG;
		
	    setUpMapIfNeeded(); // For setting up the MapFragment
	  
	   
	    return view;

    }
	
	/***** Sets up the map if it is possible to do so *****/
	public static void setUpMapIfNeeded() {
	    // Do a null check to confirm that we have not already instantiated the map.
	    if (mMap == null) {
	        // Try to obtain the map from the SupportMapFragment.
	        mMap = ((SupportMapFragment) MainActivity.fragmentManager
	                .findFragmentById(R.id.location_map)).getMap();
	        // Check if we were successful in obtaining the map.
	        if (mMap != null)
	            setUpMap();
	    }
	}

	/**
	 * This is where we can add markers or lines, add listeners or move the
	 * camera.
	 * <p>
	 * This should only be called once and when we are sure that {@link #mMap}
	 * is not null.
	 */
	
	private static void setUpMap() {
	   
		UserAgent user = UserAgent.getInstance();
		// For showing a move to my location button
	    mMap.setMyLocationEnabled(true);       
        Location location = mMap.getMyLocation();
        
        if (location != null) {
            myLocation = new LatLng(location.getLatitude(), location.getLongitude());
        }
        else
        	myLocation = new LatLng(MainActivity.latitude, MainActivity.longitude);
        
        
        // Adding beginning Marker in map
	   // mMap.addMarker(new MarkerOptions().position(new LatLng(myLocation.latitude, myLocation.longitude)).title("Current Location").snippet("")
	    //		 .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
	    
        
	    // line for rout
	    Polyline line = mMap.addPolyline(new PolylineOptions()
	    // .add(myLocation)
	     .width(5)
	     .color(Color.RED));
	    
	    List<LatLng> points = line.getPoints();
	    for(int i=0;i< user.finalWay.getNodeList().size() - 1;i++)
	    {
	    	Nodes node  = user.finalWay.getNode(i);
	    	LatLng temp = new LatLng(node.getRealCoordinates()[0], node.getRealCoordinates()[1]);
	    	points.add(temp);
	    	//Log.d(MAP_TAG, Double.toString(temp.latitude));
	    	//Log.d(MAP_TAG, Double.toString(temp.longitude));
	    	if(i==0){
	    	  mMap.addMarker(new MarkerOptions().position(new LatLng(temp.latitude, temp.longitude)).title("Current Location").snippet("")
	 	    		 .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
	    	}
	    }
	    
	  line.setPoints(points) ; // adding all nodes in path
	  
	  // End node Marker in map
	  Nodes node  = user.finalWay.getNode(user.finalWay.getNodeList().size() - 1);
   	  LatLng temp = new LatLng(node.getRealCoordinates()[0], node.getRealCoordinates()[1]);
	  mMap.addMarker(new MarkerOptions().position(new LatLng(temp.latitude, temp.longitude))
			  .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
	 
	// For zooming automatically to the Dropped PIN Location
	  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 12.0f));	    
	    
	  
	}

	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
	    // TODO Auto-generated method stub
	    if (mMap != null)
	        setUpMap();

	    if (mMap == null) {
	        // Try to obtain the map from the SupportMapFragment.
	        mMap = ((SupportMapFragment) MainActivity.fragmentManager
	                .findFragmentById(R.id.location_map)).getMap(); // getMap is deprecated
	        // Check if we were successful in obtaining the map.
	        if (mMap != null)
	            setUpMap();
	    }
	}

	/**** The mapfragment's id must be removed from the FragmentManager
	 **** or else if the same it is passed on the next time then 
	 **** app will crash ****/
	@Override
	public void onDestroyView() {
	    super.onDestroyView();
	    if (mMap != null) {
	        MainActivity.fragmentManager.beginTransaction()
	            .remove(MainActivity.fragmentManager.findFragmentById(R.id.location_map)).commit();
	        mMap = null;
	    }
	}
}
