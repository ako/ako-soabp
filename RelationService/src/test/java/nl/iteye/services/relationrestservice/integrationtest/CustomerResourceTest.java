/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.iteye.services.relationrestservice.integrationtest;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author andrej
 */
public class CustomerResourceTest extends JerseyTest {

    public CustomerResourceTest() {
        super("nl.iteye.services.relationrestservice");
    }


    /**
     * Test of getIt method, of class AddressResource.
     */
    @Test
    public void testGetCustomer() {
        System.out.println("testGetCustomer");
        WebResource r = resource();
        System.out.println("resource: " + r);
        String s = r.path("customer/1").get(String.class);
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><customer><link type=\"text/xml\" rel=\"address\" href=\"http://localhost:9998/address/1\"/><firstName>Pete</firstName><id>1</id><lastName>Stevens</lastName></customer>";
        System.out.println("customer 1 = " + s);
        Assert.assertEquals(expected, s);
    }

}