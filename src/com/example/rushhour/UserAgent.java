package com.example.rushhour;

//import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import elements.Nodes;
import elements.Trips;
import elements.Ways;

public class UserAgent {

	private static UserAgent instance = new UserAgent( );
	
	 public static UserAgent getInstance() {
	      if(instance == null) {
	         instance = new UserAgent();
	      }
	      return instance;
	   }
	 
    UUID ID;
	public Ways finalWay;
    private Nodes curLocation;
    public List<Trips> myTrips;
    //LocalTimes time_window_start;
    //LocalTime time_window_end;

    protected UserAgent()
    {
        ID          = UUID.randomUUID();
        myTrips     = new ArrayList<Trips>();
        curLocation = new Nodes();
        finalWay    = new Ways();
    }

    public Nodes getCurnNode(){
       return curLocation;
    }

   
   // LocalTime[] get_time_window(){
    //    LocalTime [] time_window = {time_window_start,time_window_end};
    //    return time_window;
    //}

   // void set_time_window(LocalTime ts, LocalTime te){
   //     time_window_start = ts;
   //     time_window_end = te;
   // }

    public void setCurNode(Nodes n) {
    	curLocation = n;
    }

}
