package nl.iteye.services.relationrestservice;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import static org.junit.Assert.*;
import nl.iteye.services.relationrestservice.dao.AddressDao;
import nl.iteye.services.relationrestservice.model.Address;
import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;
/**
 *
 * @author andrej
 */
public class AddressResourceMockTest {

    public AddressResourceMockTest() {
       
    }


    /**
     * Test of getIt method, of class AddressResource.
     */
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
        AddressResource resource = new AddressResource();
        resource.addressDao = dao;
        Address address = resource.getAddress(new Long(1));
        assertEquals("oldstreet",address.getStreetName());

    }
}
