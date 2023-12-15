import java.sql.*;
import java.util.*;

/*
  This is a helper class that adds the conference publication into the
  publications table.The authors for a given conference publication are added in a separate table called authors.
 */
public class Conference
{

    public boolean addConference(String identifier, Map<String,String> publicationInformation)
    {
        //establishing the connection with database
        JDBCManager jdbc=new JDBCManager();
        Connection con=jdbc.establishConnection();
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = con.prepareStatement("insert into publications (identifier,title,conference,pages," +
                    "publish_month,publish_year,location) " +
                    "values(?,?,?,?,?,?,?)");
            preparedStatement.setString(1,identifier);
            preparedStatement.setString(2,publicationInformation.get("title"));
            preparedStatement.setString(3,publicationInformation.get("conference"));
            preparedStatement.setString(4,publicationInformation.get("pages"));
            preparedStatement.setString(5,publicationInformation.get("month"));
            preparedStatement.setString(6,publicationInformation.get("year"));
            preparedStatement.setString(7,publicationInformation.get("location"));
            preparedStatement.execute("use sushank");
            preparedStatement.executeUpdate();
            //adding authors for that publication with identifier as identifier into the table authors
            String authors[]=publicationInformation.get("authors").split(",");
            for(int i=0;i<= authors.length-1;i++)
            {
                preparedStatement.execute("use sushank");
                preparedStatement = con.prepareStatement("insert into authors values(?,?)");
                preparedStatement.setString(1,identifier);
                preparedStatement.setString(2,authors[i]);
                preparedStatement.executeUpdate();
            }
          preparedStatement.close();
        }
        catch (SQLException e)
        {
            return false;
        }
        catch(Exception e)
        {
            return  false;
        }
        jdbc.closeConnection(con);
        return true;
    }
}
