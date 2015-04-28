package com.example.rushhour;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import elements.Nodes;
import elements.Trips;
import elements.Ways;

public class UserAgent {

    private static UserAgent instance = new UserAgent();

    public static UserAgent getInstance() {
        if (instance == null) {
            instance = new UserAgent();
        }
        return instance;
    }

    UUID ID;

    float src_lat;
    float src_long;
    float dest_lat;
    float dest_long;
    Timestamp time_window_start;
    Timestamp time_window_end;

   
    float[] get_source_location() {
        float[] src_location = {src_lat, src_long};
        return src_location;
    }

    public Ways finalWay;
    private Nodes curLocation;
    public List<Trips> myTrips;


    protected UserAgent() {
        ID = UUID.randomUUID();
        myTrips = new ArrayList<Trips>();
        curLocation = new Nodes();
        finalWay = new Ways();
    }

    Timestamp[] get_time_window() {
        Timestamp[] time_window = {time_window_start, time_window_end};
        return time_window;
    }

    void set_time_window(Timestamp ts, Timestamp te) {
        time_window_start = ts;
        time_window_end = te;
    }

    public Nodes getCurnNode() {
        return curLocation;
    }

    public void setCurNode(Nodes n) {
        curLocation = n;
    }

}
