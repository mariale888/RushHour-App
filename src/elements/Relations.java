package elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Relations extends Elements {
	
	public enum Type {
		route, 		    // Route: used to describe routes of many types, including major numbered roads like E26, A1, M6, I 80, US 53; or hiking routes, cycle routes and bus routes.
		multipolygon,  // Multipolygon: used for defining larger Areas such as river banks and administrative boundaries.
		boundary,	  // Boundary to exclusively define administrative boundaries
		restriction  // Restriction: describe a restrictions such as 'no left turn', 'no U-turn' etc.
	}
	
	private List<String> tags;
	private HashSet<Elements> members;
	public Type relationType;
	
	
	public Relations()
	{
		tags    = new ArrayList<String>();
		members = new HashSet<Elements>();
	}
	public Relations(List<String> tags_, HashSet<Elements> elem_, Type type_)
	{
		tags    = tags_;
		members = elem_;
		relationType = type_;
	}
}
