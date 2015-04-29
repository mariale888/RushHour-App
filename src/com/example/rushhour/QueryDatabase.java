package com.example.rushhour;

import java.sql.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class QueryDatabase
{
    public JSONArray getResult(String query) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
    	
    	JSONArray resultSet = new JSONArray();
    	 
		Class.forName("com.mysql.jdbc.Driver").newInstance();	
		Connection connection = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/newRushHour", "root", "");
	        Statement statement = connection.createStatement();
	        ResultSet result = statement.executeQuery(query);
	
	        if(!query.toLowerCase().substring(0, 6).contains("select"))
	        {
	        	return null;
	        }
		
	      
	        try {
	        	int columnCount = 0;
	        	if(result != null)
	        		 columnCount = result.getMetaData().getColumnCount();
	        	
				while(result.next()){
					 JSONObject rowObject = new JSONObject();
		             for (int i = 1; i <= columnCount; i++) {
		            	
		            	 try {
		            		 rowObject.put(result.getMetaData().getColumnName(i), (result.getString(i) != null) ? result.getString(i) : "");
		            	 } catch (JSONException e) {
		            		 // TODO Auto-generated catch block
		            		 e.printStackTrace();
		            	 }
		             }
		             resultSet.put(rowObject);
					/*
				         //Retrieve by column name
				         int id = result.getInt("node_id");
				         int la = result.getInt("latitude");
				         int lo = result.getInt("longitude");
				    
				         //Display values
				         Log.d("Query", "ID: " + id);
				         Log.d("Query", "LAT: " + la);
				         Log.d("Query", "LONG: " + lo);*/
				        
				  }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	    statement.close();
	    connection.close();
	    
		return resultSet;
    }
}
