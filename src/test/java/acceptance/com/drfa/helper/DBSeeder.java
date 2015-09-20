package acceptance.com.drfa.helper;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sanjiv on 9/20/2015.
 */
public class DBSeeder {

    private static Logger LOG = Logger.getLogger(DBSeeder.class);

    private static String tableBaseQuery = "CREATE TABLE EMPLOYEE(\n" +
            "    ID INT NOT NULL PRIMARY KEY,\n" +
            "    first_name VARCHAR(20),\n" +
            "    last_name  VARCHAR(20),\n" +
            "    email_address VARCHAR(100),\n" +
            "    date_of_joining DATE\n" +
            ")";

    private static String tableTargetQuery = "CREATE TABLE PERSON(\n" +
            "    ID INT NOT NULL PRIMARY KEY,\n" +
            "    name VARCHAR(20),\n" +
            "    address  VARCHAR(20),\n" +
            "    email_detail VARCHAR(100),\n" +
            "    joining_date DATE\n" +
            ")\n";
    Connection connection;

    public DBSeeder(Connection connection) {
        this.connection = connection;
    }

    public void seedBaseDB() {
        createBaseDBTable();
        insertBaseRecords();
    }

    public void seedTargetDB() {
        createTargetDBTable();
        insertTargetRecords();
    }

    private void createBaseDBTable() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(tableBaseQuery);
            LOG.info(String.format("Created a base table with %s", "EMPLOYEE"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(statement);
        }
    }

    private String addDays(int numberOfDays, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.
        c.add(Calendar.DATE, numberOfDays); // Adding 5 days
        return sdf.format(c.getTime());
    }

    private void insertBaseRecords() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            for (int i = 1; i < 100; i++) {
                StringBuffer sb = new StringBuffer("INSERT INTO EMPLOYEE VALUES(");
                sb.append(i).append(",");
                sb.append("'").append("FIRST-").append(i).append("'").append(",");
                sb.append("'").append("LAST-").append(i).append("'").append(",");
                sb.append("'").append("test.drfa-").append(i).append("@drfa.com").append("'").append(",");
                sb.append("'").append(addDays(i, "yyyy-MM-dd")).append("'").append(")");
                LOG.info(String.format("Inserting the query %s", sb.toString()));
                statement.execute(sb.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(statement);
        }
    }

    public void tearBaseDB() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute("DROP TABLE EMPLOYEE");
            LOG.info(String.format("Dropped table successfully %s'", "EMPLOYEE"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(statement);
        }

    }

    private void createTargetDBTable() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(tableTargetQuery);
            LOG.info(String.format("Created a targeted table with %s", "PERSON"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(statement);
        }
    }

    private void insertTargetRecords() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            for (int i = 1; i < 100; i++) {
                StringBuffer sb = new StringBuffer("INSERT INTO PERSON VALUES(");
                sb.append(i).append(",");
                sb.append("'").append("FIRST-").append(i).append("'").append(",");
                sb.append("'").append("ADDRESS-").append(i).append("'").append(",");
                sb.append("'").append("test.drfa-").append(i).append("@drfa.com").append("'").append(",");
                sb.append("'").append(addDays(i,"yyyy-MM-dd")).append("'").append(")");
                LOG.info(String.format("Inserting the query %s", sb.toString()));
                statement.execute(sb.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(statement);
        }
    }

    public void tearTargetDB() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute("DROP TABLE PERSON");
            LOG.info(String.format("Dropped table successfully %s'", "PERSON"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(statement);
        }

    }

    private void close(Statement s) {
        try {
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
