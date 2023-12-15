import java.sql.*;
import java.util.*;
/* This is a helper class that adds the publisher information of a publication venue*/
public class Publisher
{
    public boolean addPublisher(String identifier, Map<String,String> publisherInformation)
    {
        //establishing the connection with database
        JDBCManager jdbc=new JDBCManager();
        Connection con=jdbc.establishConnection();
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = con.prepareStatement("insert into publisher_organization values (?,?,?,?)");
            preparedStatement.setString(1,identifier);
            preparedStatement.setString(2,publisherInformation.get("contact_name"));
            preparedStatement.setString(3,publisherInformation.get("contact_email"));
            preparedStatement.setString(4,publisherInformation.get("location"));
            preparedStatement.execute("use sushank");
            preparedStatement.executeUpdate();
            preparedStatement.close();
            jdbc.closeConnection(con);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
