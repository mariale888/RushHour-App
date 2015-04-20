package elements;

public class Elements {

	protected int id;
	protected String tag;
	protected String role;
	
	public Elements()
	{
		id = 0;
		tag = "";
		role = "";
	}
	
	public Elements(int id_, String tag_)
	{
		id  = id_;
		tag = tag_;
		role = "";
	}
	
	public int getId()
	{
		return id;
	}
	public String getTag()
	{
		return tag;
	}
	
	// Roles are used for relation elements - they are optional parameters that an element can have
	public void setRole(String r)
	{
		role = r;
	}
	public String getRole()
	{
		return role;
	}
}
