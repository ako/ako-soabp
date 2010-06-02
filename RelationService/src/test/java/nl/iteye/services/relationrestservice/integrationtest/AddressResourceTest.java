package nl.iteye.services.relationrestservice.integrationtest;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import nl.iteye.services.relationrestservice.AddressResource;
import static org.junit.Assert.*;
import nl.iteye.services.relationrestservice.dao.AddressDao;
import nl.iteye.services.relationrestservice.model.Address;
import org.junit.Test;
import static org.mockito.Mockito.*;
/**
 *
 * @author andrej
 */
public class AddressResourceTest extends JerseyTest {

    public AddressResourceTest() {
        super("nl.iteye.services.relationrestservice");
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
        when(conn.prepareStatement("select * from address where id = ?")).
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
        //AddressResource resource = new AddressResource();
        //resource.addressDao = dao;
        AddressResource ar  = spy(new AddressResource());
        when(ar.getAddressDao()).thenReturn(dao);

        //when()
        /*
         * test using http call
         */
        System.out.println("testGetAddress");
        WebResource r = resource();
        System.out.println("resource: " + r);
        String s = r.path("address/1").get(String.class);
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><address><city>The Hague</city><country>The Netherlands</country><id>1</id><streetName>Scheveningseweg</streetName><streetNumber>4a</streetNumber><zipCode>2580AB</zipCode></address>";
        System.out.println("address 1 = " + s);
        assertEquals(expected, s);
    }
}
