package com.example.rushhour;

import java.sql.SQLException;

import org.json.JSONArray;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncTaskRushHour extends AsyncTask<String, Void, JSONArray> {
   
	
	protected JSONArray doInBackground(String... queries) {
        String query = queries[0];
        JSONArray result = null;
        try {
			result = new QueryDatabase().getResult(query);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       
        return result;
    }


    protected void onPostExecute(JSONArray result) {
       // Log.d("Query", result.toString());
    }
}

