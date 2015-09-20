package acceptance.com.drfa.db;

import acceptance.com.drfa.helper.DBSeeder;
import acceptance.com.drfa.helper.DerbyServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;

/**
 * Created by Sanjiv on 9/20/2015.
 */
public class EndToEndDatabaseReconciliationTest {
    static final String BASE_DB = "baseDB";
    static final String TARGET_DB = "targetDB";
    static Connection baseConnection;
    static Connection targetConnection;
    @BeforeClass
    public static void setUpTheDatabasesForComparision(){
        baseConnection = new DerbyServer().initialDerbyDatabase(BASE_DB);
        new DBSeeder(baseConnection).seedBaseDB();
        targetConnection = new DerbyServer().initialDerbyDatabase(TARGET_DB);
        new DBSeeder(targetConnection).seedTargetDB();
    }

    @AfterClass
    public static void shutDownTheDatabasesAfterComparision(){
        new DBSeeder(baseConnection).tearBaseDB();
        new DBSeeder(targetConnection).tearTargetDB();
        new DerbyServer().shutDownDerbyDatabase(BASE_DB);
        new DerbyServer().shutDownDerbyDatabase(TARGET_DB);
    }
    
    @Test
    public void shouldTestEndToEndDatabaseReconciliationTest(){

    }
}
