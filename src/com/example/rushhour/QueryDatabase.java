package com.example.rushhour;

import java.sql.*;

public class QueryDatabase
{
    public ResultSet getResult(String query) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
	Class.forName("com.mysql.jdbc.Driver").newInstance();	
	Connection connection = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/rushHour", "root", "");
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(query);

        if(!query.toLowerCase().substring(0, 6).contains("select"))
        {
        	return null;
        }
	
	statement.close();
	connection.close();
	return result;
    }
}
