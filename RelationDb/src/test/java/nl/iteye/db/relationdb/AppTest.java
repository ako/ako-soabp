package nl.iteye.db.relationdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

    public void testRelations() throws Exception {
        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost/dev", "dev", "dev");

        Statement stmt = connection.createStatement();
        stmt.executeUpdate("delete from rel_relations");
        stmt.executeUpdate("insert into rel_relations values (1,'Koos Koets')");

        ResultSet rset = stmt.executeQuery("select * from rel_relations");
        while (rset.next()) {
            long id = rset.getLong("ID");
            String name = rset.getString("NAME");
            System.out.println("Record: " + id + ", " + name);
        }
        stmt.close();
        connection.close();
    }
}
