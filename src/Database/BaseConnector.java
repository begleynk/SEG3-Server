package Database;

import java.sql.*;

/**
 * Created by NiklasBegley on 20/02/2014.
 */
public class BaseConnector {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/SEG3";

    //Database credentials (ahmet :: hereiam)
    static final String USER = "ahmet";
    static final String PASS = "hereiam";

    //Connection and statement declarations
    static Connection connection = null;
    static Statement statementPatientAll = null;
}
