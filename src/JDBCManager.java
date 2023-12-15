import java.io.*;
import java.sql.*;
import java.util.*;
/*
This class is a manager class that handles the
opening and closing of a database connection
*/

public class JDBCManager
{
     //getting the credential information for the JDBC connection
     Properties identity = new Properties();
     String username = "";
     String password = "";
     String propertyFilename = "credentials.prop";
     //creating a method which when called will establish the connection with the MySQL Database
     public Connection establishConnection()
     {
          try
          {
               //retrieving the credentials to login into the database
               InputStream stream = new FileInputStream(propertyFilename);
               identity.load(stream);
               username = identity.getProperty("username");
               password = identity.getProperty("password");
          }
          catch (IOException ioe)
          {
               ioe.getMessage();
          }
          Connection connect = null;
          try
          {
               Class.forName("com.mysql.cj.jdbc.Driver");
               connect = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false", username, password);

          }
          catch (ClassNotFoundException e)
          {
               e.getMessage();
          }
           catch (SQLException e)
          {
               e.getMessage();
          }
          catch (Exception e)
          {
               e.getMessage();
          }
          return connect;
     }
     //creating a method which will close the connection to the MySQL Database
     public void closeConnection(Connection connection)
     {
         try
         {
              connection.close();
         }
         catch(SQLException e)
          {
               e.getMessage();
          }
         catch (Exception e)
         {
              e.getMessage();
         }
     }
}

