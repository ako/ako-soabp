package nl.iteye.db.orderdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class OrdersTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public OrdersTest( String testName )
    {
        super( testName );
    }

 


        public void testOrders() throws Exception {
        Connection connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@//localhost/xe", "test", "test");

        Statement stmt = connection.createStatement();
        stmt.executeUpdate("delete from orders");
        stmt.executeUpdate("insert into orders values (1,'1 ei',sysdate)");

        ResultSet rset = stmt.executeQuery("select * from orders");
        while (rset.next()) {
            long id = rset.getLong("ID");
            String desc = rset.getString("DESCRIPTION");
            System.out.println("Record: " + id + ", " + desc);
        }
        stmt.close();
        connection.close();
    }
}
