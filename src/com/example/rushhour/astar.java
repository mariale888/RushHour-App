/*import java.io.*;
import java.util.*;

public class astar
{
    public static ArrayList<String> executeAstar (String start_nodeID, String goal_nodeID)
    {
		String startIntersection = getNearestIntersection(start_nodeID); //Function to get the intersection from which to start the search, using heuristic
		String startWay = getWay(start_nodeID); //Function to get the way on which the node lies
		String endWay = getWay(goal_nodeID);
		String currentWay = startWay; //Initialise the way
		String currentIntersection = startIntersection; //Initialize intersection
		ArrayList<String> nodesToTraverse = new ArrayList<String>(); //The list of nodes to travel in our path
		nodesToTraverse.add(start_nodeID);
		nodesToTraverse.add(currentIntersection);

		while(currentWay.equals(endWay))
		{
			ArrayList<String> neighbors = getNeighbors(currentIntersection); //Get neighboring intersections of current node
			double min_time = Double.MAX_VALUE;
			String min_intersection = "";
			for(int i=0; i<neighbors.size(); i++) //Check which intersection is best to go to
			{
				String node = neighbors.get(i);
				if(nodesToTraverse.contains(node))
				{
					continue;
				}
				else
				{
					double time_from_current = getTimeOnPath(currentIntersection, node);
					double euclidean_distance_from_node = getDistance(node, goal_nodeID);
					double heuristic_time = euclidean_distance_from_node/60;

					if(time_from_current + heuristic_time <= min_time)
					{
						min_time = time_from_current + heuristic_time;
						min_intersection = node;
					}
				}
			}

			currentWay = getWayBetweenNodes(currentIntersection, min_intersection); //Function to get the way between the two intersections
			if(currentWay.equals(endWay))
			{
				nodesToTraverse.add(goal_nodeID);
				break;
			}
			else
			{
				nodesToTraverse.add(min_intersection);
				currentIntersection = min_intersection;
			}
		}

		return nodesToTraverse;
    }
}*/

