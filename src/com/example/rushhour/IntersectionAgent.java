public class IntersectionAgent
{
    int intersection_ID;
    double latitude;
    double longitude;
    int no_of_signals;
    int[] signals_ID;
    int no_of_roads;
    int[] roads_ID;
    
    public IntersectionAgent()
    {
	signals_ID = new int[no_of_signals];
	roads_ID = new int[no_of_roads];
    }
}
