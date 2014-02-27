package Database;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

/**
 * Created by NiklasBegley on 20/02/2014.
 */
public class DatabaseInitializer
{

    public static boolean initialize()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Database connector not found.");
            e.printStackTrace();
        }

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:data-storage/database.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            System.out.println("Creating admin table...");
            statement.execute(getQueryFromFile("create_table_admin"));
            System.out.println("Creating patient table...");
            statement.execute(getQueryFromFile("create_table_patient"));
            System.out.println("Creating questionnaire table...");
            statement.execute(getQueryFromFile("create_table_questionnaire"));
            System.out.println("Creating patient_questionnaire table...");
            statement.execute(getQueryFromFile("create_table_patient_questionnaire"));
//            System.out.println("Altering patient_questionnaire table...");
//            statement.execute(getQueryFromFile("alter_table_patient_questionnaire"));
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        catch (FileNotFoundException e)
        {
            System.err.println("SQL file not found.");
            System.err.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.err.println("Problem reading SQL file.");
            System.err.println(e.getMessage());
        }

        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
        }

        return true;
    }

    private static String getQueryFromFile(String filename) throws FileNotFoundException, IOException
    {
        BufferedReader in = new BufferedReader(new FileReader("src/Database/Schema/" + filename + ".sql"));
        String queryString = "";

        StringBuffer sb = new StringBuffer();
        while ((queryString = in.readLine()) != null)
        {
            sb.append(queryString + "\n ");
        }
        return sb.toString();
    }
}