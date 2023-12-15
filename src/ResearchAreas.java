import java.sql.*;
import java.util.*;

/*
  This is a helper class that adds the research area and its corresponding parent areas into the
  research_areas table.
 */
public class ResearchAreas
{
  boolean addArea(String researchArea, Set<String> parentArea)
  {
      //establishing the connection with database
      JDBCManager jdbc=new JDBCManager();
      Connection con=jdbc.establishConnection();
      PreparedStatement preparedStatement;
      try
      {
          String parentAreas= String.join(",",parentArea); //converting the set values into a string separated by commas
          //so that the attribute parent_area will be a multivalued attribute
          preparedStatement=con.prepareStatement("insert into research_areas values (?,?)");
          preparedStatement.setString(1,researchArea);
          preparedStatement.setString(2,parentAreas);
          preparedStatement.execute("use sushank");
          preparedStatement.executeUpdate();
          preparedStatement.close();
          jdbc.closeConnection(con);
      }
      catch (SQLException e)
      {
          return false;
      }
      catch (Exception e)
      {
          return  false;
      }
      return true;
  }
}
