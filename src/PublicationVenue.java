import java.sql.*;
import java.util.*;
/*
  This is a helper class that adds the publication venue information into the
  publication_venue table. However, the research areas information for a publication venue is stored in a separate table
  called venue_research_areas for a given venue_name.
 */
public class PublicationVenue
{
   public boolean addVenue(String venueName, Map<String,String> venueInformation, Set<String> researchAreas)
    {
        //establishing the connection with database
        JDBCManager jdbc=new JDBCManager();
        Connection con=jdbc.establishConnection();
        PreparedStatement preparedStatement;
        //checking the mandatory fields for when a venue is a conference
        if(venueInformation.containsKey("location")||venueInformation.containsKey("conference_year"))//publication venue is a conference
        {
            try
            {
                preparedStatement = con.prepareStatement("insert into publication_venue (venue_name,publisher_organization,organizer_editor" +
                        ",editor_contact,conference_year,conference_location) values(?,?,?,?,?,?)");
                preparedStatement.setString(1,venueName);
                preparedStatement.setString(2,venueInformation.get("publisher"));
                preparedStatement.setString(3,venueInformation.get("editor"));
                preparedStatement.setString(4,venueInformation.get("editor_contact"));
                preparedStatement.setString(5,venueInformation.get("conference_year"));
                preparedStatement.setString(6,venueInformation.get("location"));
                preparedStatement.execute("use sushank");
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            catch(SQLException e)
            {
                return false;
            }
            catch (Exception e)
            {
                return  false;
            }

        }
        else //publication venue is a journal
        {
            try
            {
                preparedStatement = con.prepareStatement("insert into publication_venue (venue_name,publisher_organization," +
                        "organizer_editor,editor_contact)values(?,?,?,?)");
                preparedStatement.setString(1,venueName);
                preparedStatement.setString(2,venueInformation.get("publisher"));
                preparedStatement.setString(3,venueInformation.get("editor"));
                preparedStatement.setString(4,venueInformation.get("editor_contact"));
                preparedStatement.execute("use sushank");
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            catch(SQLException e)
            {
                return false;
            }
            catch (Exception e)
            {
                return  false;
            }

        }
        //storing the research areas of the publication venue
        List<String> researchArea=new ArrayList<>(researchAreas); //converting the set into a string arraylist
        try
        {   //adding the values of research areas for that venue into the table venue_research_areas
            for(int i=0;i<=researchArea.size()-1;i++)
            {
                preparedStatement = con.prepareStatement("insert into venue_research_areas values(?,?)");
                preparedStatement.setString(1,venueName);
                preparedStatement.setString(2,researchArea.get(i));
                preparedStatement.execute("use sushank");
                preparedStatement.executeUpdate();
            }
            preparedStatement.close();
        }
        catch(SQLException e)
        {
            return false;
        }
        catch (Exception e)
        {
            return  false;
        }
        jdbc.closeConnection(con);
        return true;
    }
}
