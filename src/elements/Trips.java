package elements;

public class Trips {

	public Nodes destination;
	public String destinationStr;
	public int timeWindow;
	
	private String departureTime;
	private String arrivalTime;

	
	public Trips(String str, Nodes des, String dep, String arriv, int window)
	{
		destinationStr = str;
		destination   = des;
		departureTime = dep;
		arrivalTime   = arriv;
		timeWindow = window;
	}
	
	
	public String getDepartureStr()
	{
		return departureTime;
	}
	public String getArrivalStr()
	{
		return arrivalTime;
	}
	public int[]getArrivalTime()
	{
		return getTime(arrivalTime);
	}
	public int[]getDepartureTime()
	{
		return  getTime(arrivalTime);
	}
	
	int[] getTime(String st)
	{
		int[] time = new int[2];
		time[0] = Integer.parseInt(st.split(":")[0]);
		time[1] = Integer.parseInt(st.split(":")[1]);
		
		return time;
	}
}
