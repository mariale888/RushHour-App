package com.example.rushhour;

/* 
 * In the commented out main function, check the dummy values for which this works! It won't work for 
 * every pair of nodes, because the nodes in the database are not fully tagged, and the algorithm will get 
 * stuck if other pairs are run!
 */

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.sql.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class astar
{
	public static ArrayList<Long> getNeighbors(long currentNode) throws SQLException, ClassNotFoundException
	{
		//Class.forName("com.mysql.jdbc.Driver");
		//Connection con = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/newRushHour", "root", "");
		//Statement st = con.createStatement();
		//ResultSet rs = st.executeQuery("select * from pathTable where start_node_id = " + currentNode);
		ArrayList<Long> neighbors = new ArrayList<Long>();
		//while(rs.next())
		//{
		//	long temp = rs.getLong("end_node_id");
		//	neighbors.add(temp);
		//}
		//rs.close();
		//st.close();
		//con.close();
		String query = "select * from pathTable where start_node_id = " + currentNode  + ";";
		
		try {
			JSONArray rs = new AsyncTaskRushHour().execute(query).get();
			for(int n=0;n< rs.length();n++) {
				JSONObject obj;
				try {
					obj = rs.getJSONObject(n);
					long temp = Long.parseLong( obj.get("end_node_id").toString() );
					neighbors.add(temp);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		
		return neighbors;
	}

	public static double getDistance(long node1, long node2) throws SQLException, ClassNotFoundException
	{
		//Class.forName("com.mysql.jdbc.Driver");
		//Connection con = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/newRushHour", "root", "");
		//Statement st = con.createStatement();
		//Statement st1 = con.createStatement();
		//ResultSet rs1 = st.executeQuery("select * from nodes where node_id = " + node1);
		//ResultSet rs2 = st1.executeQuery("select * from nodes where node_id = " + node2);
		
		double lat1 = 0.0;
		double long1 = 0.0;
		double lat2 = 0.0;
		double long2 = 0.0;
		double distance = 0;
		
		String query = "select * from nodes where node_id = " + node1  + ";";
		String query1 = "select * from nodes where node_id = " + node2  + ";";
		
		try {
			JSONArray rs = new AsyncTaskRushHour().execute(query).get();
			JSONArray rs1 = new AsyncTaskRushHour().execute(query1).get();
			// first query
			for(int n=0;n< rs.length();n++) {
				JSONObject obj;
				try {
					obj = rs.getJSONObject(n);
					lat1 = Long.parseLong( obj.get("latitude").toString() )/1E7;
					long1 = Long.parseLong( obj.get("longitude").toString() )/1E7;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//second query
			for(int n=0;n< rs1.length();n++) {
				JSONObject obj;
				try {
					obj   = rs1.getJSONObject(n);
					lat2  = Long.parseLong( obj.get("latitude").toString() )/1E7;
					long2 = Long.parseLong( obj.get("longitude").toString() )/1E7;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		
			int R = 6371; // km Radius of earth
			double dLat = Math.toRadians(lat2 - lat1);
			double dLon = Math.toRadians(long2 - long1);
			 
			double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon/2) * Math.sin(dLon/2); 
			double c = 2 * Math.asin(Math.sqrt(a)); 
			distance = R * c;
	
			
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		
		
		
		/*
		if(rs1.next())
		{
		lat1 = rs1.getLong("latitude")/1E7;
		long1 = rs1.getLong("longitude")/1E7;
		}
		if(rs2.next())
		{
		lat2 = rs2.getLong("latitude")/1E7;
		long2 = rs2.getLong("longitude")/1E7;
		}*/
		//rs2.close();
		//rs1.close();
		//st1.close();
		//st.close();
		//con.close();

		return distance;
	}

    public static ArrayList<Long> executeAstar (long start_nodeID, long goal_nodeID) throws SQLException, ClassNotFoundException
    {
		ArrayList<Long> nodesToTraverse = new ArrayList<Long>(); //The list of nodes to travel in our path
		nodesToTraverse.add(start_nodeID);
		long currentNode = start_nodeID; 

		while(currentNode != goal_nodeID)
		{
			ArrayList<Long> neighbors = getNeighbors(currentNode); //Get neighboring intersections of current node
			double min_time = Double.MAX_VALUE;
			long min_node = 0;
			double min_distance_to_goal = 0;

			for(int i=0; i<neighbors.size(); i++) //Check which intersection is best to go to
			{
				long node = neighbors.get(i);
				if(nodesToTraverse.contains(node))
				{
					continue;
				}

				/*Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/newRushHour", "root", "");
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select * from node_tags where node_id = " + node);
				
				if(rs.next())
				{
					tag = rs.getString("v");
				}
				rs.close();
				st.close();
				con.close();*/
				String query = "select * from node_tags where node_id = " + node  + ";";
				String tag = "";
				try {
					JSONArray rs = new AsyncTaskRushHour().execute(query).get();
					for(int n=0;n< rs.length();n++) {
						JSONObject obj;
						try {
							obj = rs.getJSONObject(n);
							tag = obj.get("v").toString() ;
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ExecutionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				

				
				if(tag.equals("parking") || tag.equals("deadend") || tag.equals("building") || tag.equals("pedestrian"))
				{
					continue;
				}
				else
				{
					double time_from_current = getDistance(currentNode, node)/35; //We need the actual traffic speed here on the way, instead of 35!
					double euclidean_distance_from_goal = getDistance(node, goal_nodeID);
					double heuristic_time = euclidean_distance_from_goal/35;

					if(Math.abs(time_from_current + heuristic_time - min_time) <= 0.00001)
					{
						if(heuristic_time < min_distance_to_goal)
						{
							min_time = time_from_current + heuristic_time;
							min_node = node;
							min_distance_to_goal = heuristic_time;
						}
					}
					else if(min_time - (time_from_current + heuristic_time) > 0.00001)
					{
						min_time = time_from_current + heuristic_time;
						min_node = node;
						min_distance_to_goal = heuristic_time;
					}
				}
			}

			
			System.out.println(min_node);
			nodesToTraverse.add(min_node);
			currentNode = min_node;
		}

		return nodesToTraverse;
    }

	//public static void main(String[] args) throws SQLException, ClassNotFoundException
	//{
	//	ArrayList<Long> path = executeAstar(1706009163, 105143789);
	//	for(int i=0; i<path.size(); i++)
	//	System.out.println(path.get(i));
	//}
}

