/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.iteye.services.relationrestservice;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import com.sun.jersey.test.framework.spi.container.embedded.glassfish.EmbeddedGlassFishTestContainerFactory;
import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import nl.iteye.services.relationrestservice.model.Address;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author andrej
 */
public class AddressResourceTest extends JerseyTest {

    public AddressResourceTest() {

        super(new WebAppDescriptor.Builder(
                "nl.iteye.services.relationrestservice").contextPath(
                "relation-service").initParam(
                "com.sun.jersey.config.feature.Redirect", "true").initParam(
                "com.sun.jersey.config.feature.ImplicitViewables", "true").
                initParam("com.sun.jersey.config.property.WebPageContentRegex",
                          "/(images|css|jsp)/.*").build());
        Map<String, String> INIT_PARAMS = new HashMap<String, String>();
        INIT_PARAMS.put("com.sun.jersey.config.feature.Redirect", "true");
        INIT_PARAMS.put("com.sun.jersey.config.feature.ImplicitViewables",
                        "true");
        INIT_PARAMS.put("com.sun.jersey.config.property.WebPageContentRegex",
                        "/(images|css|jsp)/.*");
    }

    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new EmbeddedGlassFishTestContainerFactory();
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getIt method, of class AddressResource.
     */
    @Test
    public void testGetAddress() {
        System.out.println("testGetAddress");
//        int id = 1;
//        AddressResource instance = new AddressResource();
//        Address expResult = null;
//        Address result = instance.getAddress(id);
//        System.out.println("result = " + result);
        //assertEquals(expResult, result);
        //fail("The test case is a prototype.");
        WebResource r = resource().path("address/1");
        System.out.println("resource: " + r);
        String s = r.get(String.class);
        System.out.println("address 1 = " + s);
        //Assert.assertEquals("GET", s);

    }
}
