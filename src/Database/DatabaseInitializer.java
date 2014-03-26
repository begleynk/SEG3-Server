package Database;
import Accessors.DataLayer;
import Helpers.OSHelper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

/**
 * Created by Niklas Begley on 20/02/2014.
 *
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
            connection = DriverManager.getConnection("jdbc:sqlite:" + OSHelper.getStoragePath() + "/database.db");

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.


            statement.execute("CREATE TABLE IF NOT EXISTS 'Admin' (\n" +
                    "  'A_passcode' VARCHAR (12) PRIMARY KEY NOT NULL\n" +
                    ");");
            statement.execute("CREATE TABLE IF NOT EXISTS 'Patient' (\n" +
                    "    'P_NHS_number' varchar(10) NOT NULL,\n" +
                    "    'P_first_name' varchar(20) NOT NULL,\n" +
                    "    'P_middle_name' varchar(20) NOT NULL,\n" +
                    "    'P_surname' varchar(20) NOT NULL,\n" +
                    "    'P_date_of_birth' date NOT NULL,\n" +
                    "    'P_postcode' varchar(8) NOT NULL,\n" +
                    "    PRIMARY KEY ('P_NHS_number')\n" +
                    ");");
            statement.execute("CREATE TABLE IF NOT EXISTS 'Questionnaire' (\n" +
                    "  'Q_id' INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "  'Q_title' varchar(250) DEFAULT NULL,\n" +
                    "  'Q_state' TEXT CHECK (Q_state IN ('Draft', 'Deployed', 'Archived')) NOT NULL\n" +
                    ");\n");
            statement.execute("CREATE TABLE IF NOT EXISTS 'Patient_Questionnaire' (\n" +
                    "    'P_NHS_number' int(10) NOT NULL,\n" +
                    "    'Q_id' int(8) NOT NULL,\n" +
                    "    'Completed' tinyint(1) NOT NULL,\n" +
                    "    PRIMARY KEY ('P_NHS_number','Q_id'),\n" +
                    "    FOREIGN KEY ('P_NHS_number') REFERENCES 'Patient'('P_NHS_number') ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                    "    FOREIGN KEY ('Q_id') REFERENCES 'Questionnaire'('Q_id') ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ");");
            statement.execute("CREATE TABLE IF NOT EXISTS 'Patient_Log' (\n" +
                    "   'plkey' INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "   'P_NHS_number_OLD' int(10),\n" +
                    "   'P_NHS_number_NEW' int(10),\n" +
                    "   'P_first_name_OLD' varchar(20),\n" +
                    "   'P_first_name_NEW' varchar(20),\n" +
                    "   'P_middle_name_OLD' varchar(20),\n" +
                    "   'P_middle_name_NEW' varchar(20),\n" +
                    "   'P_surname_OLD' varchar(20),\n" +
                    "   'P_surname_NEW' varchar(20),\n" +
                    "   'P_date_of_birth_OLD' DATE,\n" +
                    "   'P_date_of_birth_NEW' DATE,\n" +
                    "   'P_postcode_OLD' varchar(8),\n" +
                    "   'P_postcode_NEW' varchar(8),\n" +
                    "   'SQL_action' varchar(15),\n" +
                    "   'Time_enter' DATE\n" +
                    ");");
            statement.execute("CREATE TABLE IF NOT EXISTS 'Questionnaire_Log' (\n" +
                    "   'qlkey' INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "   'Q_id_OLD' INTEGER,\n" +
                    "   'Q_id_NEW' INTEGER,\n" +
                    "   'Q_title_OLD' VARCHAR(250),\n" +
                    "   'Q_title_NEW' VARCHAR(250),\n" +
                    "   'Q_state_OLD' TEXT,\n" +
                    "   'Q_state_NEW' TEXT,\n" +
                    "   'SQL_action' varchar(15),\n" +
                    "   'Time_enter' DATE\n" +
                    ");\n");
            statement.execute("CREATE TABLE IF NOT EXISTS 'Patient_Questionnaire_Log' (\n" +
                    "   'pqlkey' INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "   'P_NHS_number_OLD' INT(10),\n" +
                    "   'P_NHS_number_NEW' INT(10),\n" +
                    "   'Q_id_OLD' INT(8),\n" +
                    "   'Q_id_NEW' INT (8),\n" +
                    "   'Completed_OLD' tinyint(1),\n" +
                    "   'Completed_NEW' tinyint(1),\n" +
                    "   'SQL_action' varchar(15),\n" +
                    "   'Time_enter' DATE\n" +
                    ");");
            statement.execute("CREATE TABLE IF NOT EXISTS 'Removed_Patient' (\n" +
                    "    'P_NHS_number' int(10) NOT NULL,\n" +
                    "    'P_first_name' varchar(20) NOT NULL,\n" +
                    "    'P_middle_name' varchar(20) NOT NULL,\n" +
                    "    'P_surname' varchar(20) NOT NULL,\n" +
                    "    'P_date_of_birth' date NOT NULL,\n" +
                    "    'P_postcode' varchar(8) NOT NULL,\n" +
                    "    PRIMARY KEY ('P_NHS_number')\n" +
                    ");");
            statement.execute("CREATE TABLE IF NOT EXISTS 'Removed_Patient_Questionnaire' (\n" +
                    "    'P_NHS_number' int(10) NOT NULL,\n" +
                    "    'Q_id' int(8) NOT NULL,\n" +
                    "    'Completed' tinyint(1) NOT NULL,\n" +
                    "    PRIMARY KEY ('P_NHS_number','Q_id')\n" +
                    ");");

            if(!isAdminCreated())
            {
                DataLayer.setAdminPasscode("1234");
            }

            System.out.println("All tables are created");

        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        /*catch (FileNotFoundException e)
        {
            System.err.println("SQL file not found.");
            System.err.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.err.println("Problem reading SQL file.");
            System.err.println(e.getMessage());
        }
        */

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
        String queryString;

        StringBuffer sb = new StringBuffer();
        while ((queryString = in.readLine()) != null)
        {
            sb.append(queryString + "\n ");
        }
        return sb.toString();
    }

    private static boolean isAdminCreated()
    {
        try
        {
            return DataLayer.doesPasscodeExist();
        }
        catch(SQLException e)
        {
            return true;
        }
    }
}
