package elements;

public class Elements {

	protected Long id;
	protected String tag;
	protected String role;
	
	public Elements()
	{
		id = (long) 0;
		tag = "";
		role = "";
	}
	
	public Elements(Long id_, String tag_)
	{
		id  = id_;
		tag = tag_;
		role = "";
	}
	
	public Long getId()
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
