package elements;

import java.lang.StringBuffer;
import org.json.JSONObject;

import android.util.Log;

public class Nodes extends Elements {

	private double latitude; 
	private double longitude;
	
	private long latitude_int; 
	private long longitude_int;
	
	
	// Constructors and getters.
	public Nodes()
	{
		id  = (long) 0;
		tag = "";
		latitude  = 0.0;
		longitude = 0.0;
		
		latitude_int  = 0;
		longitude_int = 0;
	}
	
	public Nodes(JSONObject obj)
	{
		
		loadJSON(obj);
	}
	public void loadJSON(JSONObject obj)
	{
		
		try {
			id        = Long.parseLong( obj.get("node_id").toString() );
			latitude_int  = Long.parseLong( obj.get("latitude").toString() );
			longitude_int = Long.parseLong( obj.get("longitude").toString() );

			latitude  = convertIntDouble(latitude_int);
			longitude = convertIntDouble(longitude_int);
		}
		catch (Exception e) {
			
			id  = (long) 0;
			tag = "";
			latitude  = 0.0;
			longitude = 0.0;
			latitude_int  = 0;
			longitude_int = 0;
			
            e.printStackTrace();
        }
	}
	
	public Nodes(Long id_, int latitude_, int longitude_, String tag_)
	{
		id = id_;
		tag = tag_;
		latitude_int  = latitude_;
		longitude_int = longitude_;
		
		latitude  = convertIntDouble(latitude_int);
		longitude = convertIntDouble(longitude_int);
	}
	
	public double[] getRealCoordinates()
	{
		double[] coordinates = { latitude, longitude };
		return coordinates;
	}
	
	public long[] geIntCoordinates()
	{
		long[] coordinates = { latitude_int, longitude_int };
		return coordinates;
	}
	
	
	private double convertIntDouble(long num)
	{
		double newN = 0.0;
		//Log.d("NODE", Integer.toString(num) );
		boolean isNeg = false;
		if(num < 0)
		{
			num *=-1;
			isNeg = true;
		}
		
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(Long.toString(num));
		String st = strBuf.insert(2, ".").toString();
		
		newN = Double.parseDouble(st);
		 
		if(isNeg)
			newN *= -1;
		
		//Log.d("NODE", Double.toString(newN));
		return newN;
	}
}
