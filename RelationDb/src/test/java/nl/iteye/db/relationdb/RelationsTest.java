package nl.iteye.db.relationdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import junit.framework.TestCase;
import org.junit.Assert;

/**
 * Unit test for simple App.
 */
public class RelationsTest extends TestCase {

    public void testRelations() throws Exception {
        Connection connection = DriverManager.getConnection(
                System.getProperty("relation.db.url"), System.getProperty("relation.db.username"), System.getProperty("relation.db.password"));

        Statement stmt = connection.createStatement();
        stmt.executeUpdate("delete from relations");
        stmt.executeUpdate("insert into relations (id,firstname, lastname) values (1,'Koos','Koets')");

        ResultSet rset = stmt.executeQuery("select * from relations");
        while (rset.next()) {
            long id = rset.getLong("ID");
            String firstname = rset.getString("FIRSTNAME");
            String lastname = rset.getString("LASTNAME");
            System.out.println("Record: " + id + ", " + firstname +", " + lastname);
            Assert.assertEquals("Koos", firstname);
            Assert.assertEquals("Koets",lastname);
        }
        stmt.close();
        connection.close();
    }
}
