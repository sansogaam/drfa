package acceptance.com.drfa.db;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Sanjiv on 9/20/2015.
 */
public class DerbyServer {

    private static Logger LOG = Logger.getLogger(DerbyServer.class);

    private String FRAMEWORK = "embedded";
    private String PROTOCOL = "jdbc:derby:";
    
    public Connection initialDerbyDatabase(String databaseName) {
        try {
            LOG.info(String.format("Starting the Derby DB Server in %s mode for database %s", FRAMEWORK, databaseName));
            Properties props = new Properties(); // connection properties
            props.put("user", "drfa");
            props.put("password", "drfa");
            Connection connection= DriverManager.getConnection(PROTOCOL + databaseName + ";create=true", props);
            LOG.info(String.format("Database %s is created successfully", databaseName));
            return connection;
        }catch (SQLException sqlException){
            printSQLException(sqlException);
            return null;
        }
    }
    

    public void shutDownDerbyDatabase(String databaseName){
        try {
            DriverManager.getConnection(PROTOCOL+databaseName+";shutdown=true");
        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
    }
    
    

    public static void printSQLException(SQLException e) {
        while (e != null) {
            LOG.info("\n----- SQLException (Ignore for DB shutdown-----)");
            LOG.error("  SQL State:  " + e.getSQLState());
            LOG.error("  Error Code: " + e.getErrorCode());
            LOG.error("  Message:    " + e.getMessage());
            e = e.getNextException();
        }
    }

}

