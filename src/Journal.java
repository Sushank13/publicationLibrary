import java.sql.*;
import java.util.*;
/*
  This is a helper class that adds the journal publication into the
  publications table.The authors for a given journal publication are added in a separate table called authors.
 */
public class Journal
{
    public boolean addJournal(String identifier, Map<String,String> publicationInformation)
    {
        //establishing the connection with database
        JDBCManager jdbc=new JDBCManager();
        Connection con=jdbc.establishConnection();
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = con.prepareStatement("insert into publications (identifier,title,journal,pages,volume," +
                    "issue_number,publish_month,publish_year) " +
                    "values(?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1,identifier);
            preparedStatement.setString(2,publicationInformation.get("title"));
            preparedStatement.setString(3,publicationInformation.get("journal"));
            preparedStatement.setString(4,publicationInformation.get("pages"));
            preparedStatement.setString(5,publicationInformation.get("volume"));
            preparedStatement.setString(6,publicationInformation.get("issue"));
            preparedStatement.setString(7,publicationInformation.get("month"));
            preparedStatement.setString(8,publicationInformation.get("year"));
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
            return false;
        }
        jdbc.closeConnection(con);
        return true;
    }



}
