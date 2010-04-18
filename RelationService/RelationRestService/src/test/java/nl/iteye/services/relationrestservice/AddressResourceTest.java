/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.iteye.services.relationrestservice;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;
import junit.framework.Assert;
import org.junit.Test;

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
    public void testGetAddress() {
        System.out.println("testGetAddress");
        WebResource r = resource();
        System.out.println("resource: " + r);
        String s = r.path("address/1").get(String.class);
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><address><city>The Hague</city><country>The Netherlands</country><id>1</id><streetName>Scheveningseweg</streetName><streetNumber>4a</streetNumber><zipCode>2580AB</zipCode></address>";
        System.out.println("address 1 = " + s);
        Assert.assertEquals(expected, s);
    }
}
