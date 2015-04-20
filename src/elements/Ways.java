package elements;

import java.util.HashSet;

public class Ways extends Elements {

	private HashSet<Nodes> nodeList;
	
	// Constructors and getters.
	public Ways(){
		id  = 0;
		tag = "";
		nodeList = new HashSet<Nodes>();
	}
	public Ways(int id_,HashSet<Nodes>  nlist_, String tag_)
	{
		id = id_;
		tag = tag_;
		nodeList = nlist_;
	}
	public HashSet<Nodes> getNodeList()
	{
		return nodeList;
	}
	
	
}
