import java.sql.*;
import java.util.*;

/* This class has the implementation of the reporting methods*/
public class Reporter
{
    public Map<String,String> getPublications(String key)
    {
        //establishing the connection with database
        JDBCManager jdbc=new JDBCManager();
        Connection con=jdbc.establishConnection();
        PreparedStatement preparedStatement;
        ResultSet getPublicationInfo;
        ResultSet getReferences;
        ResultSet getAuthors;
        Map<String,String> journalInfo=new HashMap<>();
        Map<String,String> conferenceInfo=new HashMap<>();
        try
        {
            //getting info from publications table
            preparedStatement=con.prepareStatement("select * from publications where identifier=?;");
            preparedStatement.setString(1,key);
            preparedStatement.execute("use sushank");
            getPublicationInfo=preparedStatement.executeQuery();
            getPublicationInfo.next();
            String journal=getPublicationInfo.getString("journal");
            String volume=getPublicationInfo.getString("volume");
            String issue=getPublicationInfo.getString("issue_number");
            //getting references from references table
            preparedStatement=con.prepareStatement("select publication_reference from publication_references where identifier=?;");
            preparedStatement.setString(1,key);
            preparedStatement.execute("use sushank");
            getReferences=preparedStatement.executeQuery();
            getReferences.next();
            //getting authors from authors table
            preparedStatement=con.prepareStatement("select group_concat(author_name) as authors from authors \n" +
                    "where identifier=?;");
            preparedStatement.setString(1,key);
            preparedStatement.execute("use sushank");
            getAuthors=preparedStatement.executeQuery();
            getAuthors.next();
            //checking for the null values to differentiate whether its a journal or a conference
            if(journal==null||volume==null||issue==null) //then the identifier is tagged to a conference paper
            {
                conferenceInfo.put("identifier",getPublicationInfo.getString("identifier"));
                conferenceInfo.put("authors",getAuthors.getString("authors"));
                conferenceInfo.put("title",getPublicationInfo.getString("title"));
                conferenceInfo.put("conference",getPublicationInfo.getString("conference"));
                conferenceInfo.put("pages",getPublicationInfo.getString("pages"));
                conferenceInfo.put("year",getPublicationInfo.getString("publish_year"));
                conferenceInfo.put("location",getPublicationInfo.getString("location"));
                conferenceInfo.put("references",getReferences.getString("publication_reference"));
                getAuthors.close();
                getReferences.close();
                getPublicationInfo.close();
                preparedStatement.close();
                jdbc.closeConnection(con);
                return conferenceInfo;

            }
            else //the identifier is tagged a journal
            {
                journalInfo.put("identifier",getPublicationInfo.getString("identifier"));
                journalInfo.put("authors",getAuthors.getString("authors"));
                journalInfo.put("title",getPublicationInfo.getString("title"));
                journalInfo.put("journal",getPublicationInfo.getString("journal"));
                journalInfo.put("pages",getPublicationInfo.getString("pages"));
                journalInfo.put("volume",getPublicationInfo.getString("volume"));
                journalInfo.put("issue",getPublicationInfo.getString("issue_number"));
                journalInfo.put("month",getPublicationInfo.getString("publish_month"));
                journalInfo.put("year",getPublicationInfo.getString("publish_year"));
                journalInfo.put("references",getReferences.getString("publication_reference"));
                getAuthors.close();
                getReferences.close();
                getPublicationInfo.close();
                preparedStatement.close();
                jdbc.closeConnection(con);
                return journalInfo;
            }


        }
        catch(SQLException e)
        {
            return null;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    public int authorCitations(String author)
    {
        //establishing the connection with database
        JDBCManager jdbc=new JDBCManager();
        Connection con=jdbc.establishConnection();
        PreparedStatement preparedStatement;
        ResultSet getNumberOfCitations;
        int numberOfCitations;
        try
        {
            preparedStatement=con.prepareStatement("select count(identifier) as author_citations from publication_references\n" +
                    "where publication_reference like (select group_concat(identifier)  from authors where author_name=?);");
            preparedStatement.setString(1,author);
            preparedStatement.execute("use sushank");
            getNumberOfCitations=preparedStatement.executeQuery();
            getNumberOfCitations.next();
            numberOfCitations=getNumberOfCitations.getInt("author_citations");
            getNumberOfCitations.close();
            preparedStatement.close();
            jdbc.closeConnection(con);
        }
        catch (SQLException e)
        {
            return -1;
        }
        catch (Exception e)
        {
            return -1;
        }
        
        return numberOfCitations;


    }

}
