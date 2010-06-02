package nl.iteye.services.relationrestservice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import nl.iteye.services.relationrestservice.model.Address;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import static org.mockito.Mockito.*;

/**
 *
 * @author andrej
 */
public class AddressDaoTest {

    public AddressDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testGetAddress() throws SQLException {
        /*
         * setup mocking
         */
        DataSource ds = mock(DataSource.class);
        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        ResultSet rset = mock(ResultSet.class);
        when(ds.getConnection()).thenReturn(conn);
        when(conn.prepareStatement("select * from addresses where id = ?")).
                thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rset);
        when(rset.next()).thenReturn(Boolean.TRUE);
        when(rset.getLong("id")).thenReturn(1L);
        when(rset.getString("street")).thenReturn("oldstreet");
        /*
         * test the code
         */
        AddressDao dao = new AddressDao();
        dao.relationDb = ds;
        Address address = dao.getAddress(new Long(1));
        Assert.assertEquals("oldstreet",address.getStreetName());
    }
}
