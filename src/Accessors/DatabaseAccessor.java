package Accessors;

import ModelObjects.Questionnaire;

import java.sql.*;
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
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Questionnaire insertQuestionnaireRecord(Questionnaire questionnaire) throws SQLException
    {
        Statement statement = createStatement();
        statement.execute("INSERT INTO Questionnaire (Title) VALUES ('" + questionnaire.getTitle() + "');");
        ResultSet keys = statement.getGeneratedKeys();
        if(keys.next())
        {
            questionnaire.set_id(keys.getInt(1));
            return questionnaire;
        }
        else
        {
            throw new SQLException("Error inserting Questionnaire record.");
        }
    }

    private Statement createStatement()
    {
        Statement statement = null;
        try
        {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return statement;
    }
}
