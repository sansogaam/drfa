package acceptance.com.drfa.db;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;

/**
 * Created by Sanjiv on 9/20/2015.
 */
public class DatabaseReconciliationEndToEndTest {


    static final String BASE_DB = "baseDB";
    static final String TARGET_DB = "targetDB";
    static Connection baseConnection;
    static Connection targetConnection;
    @BeforeClass
    public static void setUpTheDatabasesForComparision(){
        baseConnection = new DerbyServer().initialDerbyDatabase(BASE_DB);
        targetConnection = new DerbyServer().initialDerbyDatabase(TARGET_DB);
    }

    @AfterClass
    public static void shutDownTheDatabasesAfterComparision(){
        new DerbyServer().shutDownDerbyDatabase(BASE_DB);
        new DerbyServer().shutDownDerbyDatabase(TARGET_DB);
    }
    
    @Test
    public void shouldTestEndToEndDatabaseReconciliationTest(){

    }
}
