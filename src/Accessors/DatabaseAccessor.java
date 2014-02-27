package Accessors;

import ModelObjects.Questionnaire;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Created by NiklasBegley on 27/02/2014.
 */
public class DatabaseAccessor {

    private Connection connection;
    private Statement statement;


    public DatabaseAccessor()
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
        try
        {
            this.connection = DriverManager.getConnection("jdbc:sqlite:data-storage/database.db");
            this.statement = connection.createStatement();
            this.statement.setQueryTimeout(30);  // set timeout to 30 sec.
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public int insertQuestionnaireRecord(Questionnaire questionnaire)
    {
        try
        {
            statement.execute("INSERT INTO Questionnaire (Anything) VALUES ('" + questionnaire.getTitle() + "');");
            System.out.println("Inserting questionnaire");
            return 1;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return -1;
        }
    }
}
