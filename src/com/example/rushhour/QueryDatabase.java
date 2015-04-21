import java.sql.*;
import java.util.ArrayList;

public class QueryDatabase
{
    public ArrayList<ArrayList<String>> getResult(String query)
    {
	Class.forName("com.mysql.jdbc.Driver");	
	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/<Database Name>", "<Username>", "<Password>");
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(query);
        ResultSetMetaData metadata = result.getMetaData();
        int columns = metadata.getColumnCount();
        ArrayList<ArrayList<String>> resultArray = new ArrayList<ArrayList<String>>();

        while(result.next())
        {
            ArrayList<String> temp = new ArrayList<String>();
            for(int i=1; i<=columns; i++)
            {
                temp.add(result.getString(i));
            }
            resultArray.add(temp);
        }
	
	metadata.close();
	result.close();
	statement.close();
	connection.close();
    }
}
