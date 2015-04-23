package com.example.rushhour;

import java.sql.ResultSet;
import java.sql.SQLException;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncTaskRushHour extends AsyncTask<String, Void, ResultSet> {
    protected ResultSet doInBackground(String... queries) {
        String query = queries[0];
        ResultSet result = null;
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


    protected void onPostExecute(ResultSet result) {
        Log.d("Query", result.toString());
    }
}

