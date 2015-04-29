package elements;

import java.util.ArrayList;
import java.util.List;


public class Ways extends Elements {

	private ArrayList<Nodes> nodeList;
	
	// Constructors and getters.
	public Ways(){
		id  = (long) 0;
		tag = "";
		nodeList = new ArrayList<Nodes>();
	}
	public Ways(Long id_, ArrayList<Nodes> nlist_, String tag_)
	{
		id = id_;
		tag = tag_;
		nodeList = nlist_;
	}
	public ArrayList<Nodes> getNodeList()
	{
		return nodeList;
	}
	
	public Nodes getNode(int i)
	{
		return nodeList.get(i);
	}
	public void addNode(Nodes n)
	{
		nodeList.add(n);

	}
	
	
}
