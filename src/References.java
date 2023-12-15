import java.sql.*;
import java.util.*;
/* This is a helper class that adds  and updates the references of a given publication*/
public class References
{
    public boolean addReferences(String identifier, Set<String> references)
    {
        //converting the set into a comma separated string so that references can be stored as multivalued attribute
        String publication_references=String.join(",",references);
        //establishing the connection with database
        JDBCManager jdbc=new JDBCManager();
        Connection con=jdbc.establishConnection();
        PreparedStatement preparedStatement;
        ResultSet getIdentifier;
        ResultSet getReferences;
        try
        {
            preparedStatement = con.prepareStatement("select identifier from publication_references where identifier=?;");
            preparedStatement.setString(1,identifier);
            preparedStatement.execute("use sushank");
            getIdentifier=preparedStatement.executeQuery();
            boolean resultSetStatus=getIdentifier.next();
            if(resultSetStatus==false) //result set is empty for that identifier, then make the first insertion for that identifier
            {
                preparedStatement=con.prepareStatement("insert into publication_references values(?,?);");
                preparedStatement.setString(1,identifier);
                preparedStatement.setString(2,publication_references);
                preparedStatement.execute("use sushank");
                preparedStatement.executeUpdate();
            }
            else //result set is not empty for that identifier so the references have already been added for a given publication identifier
                 // and now references need to be updated
            {
                preparedStatement = con.prepareStatement("select publication_reference from publication_references where identifier=?;");
                preparedStatement.setString(1,identifier);
                preparedStatement.execute("use sushank");
                getReferences=preparedStatement.executeQuery();
                getReferences.next();
                String ref[]=getReferences.getString("publication_reference").split(",");//split the multivalued references attribute
                Set<String> existingReferences=new HashSet<>(Arrays.asList(ref)); //converting the string array of references into a set
                references.addAll(existingReferences); //performing union of the existing references and the updated ones
                String updatedReferences= String.join(",",references); //converting the updated set into a comma separated string
                preparedStatement = con.prepareStatement("update publication_references set publication_reference=? where identifier=?;");
                preparedStatement.setString(1,updatedReferences);
                preparedStatement.setString(2,identifier);
                preparedStatement.execute("use sushank");
                preparedStatement.executeUpdate();
                getReferences.close();
                preparedStatement.close();
            }
            jdbc.closeConnection(con);
        }
        catch(SQLException e)
        {
            return false;
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }
}
