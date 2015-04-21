package com.example.rushhour;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
 * Fragment that controls user input.
 *  - address
 *  - departure time
 *  - arrival time
 *  - time window 
 * 
 * */

public class CalendarFragment extends Fragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.calendar_fragment, container, false);
    }
}
