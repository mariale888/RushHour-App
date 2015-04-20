package elements;

public class Nodes extends Elements {

	private double latitude; 
	private double longitude;

	// Constructors and getters.
	public Nodes()
	{
		id  = 0;
		tag = "";
		latitude  = 0.0;
		longitude = 0.0;
	}
	
	public Nodes(int id_, double latitude_, double longitude_, String tag_)
	{
		id = id_;
		tag = tag_;
		latitude  = latitude_;
		longitude = longitude_;
	}
	public double[] getCoordinates()
	{
		double[] coordinates = { latitude, longitude };
		return coordinates;
	}
	
}
