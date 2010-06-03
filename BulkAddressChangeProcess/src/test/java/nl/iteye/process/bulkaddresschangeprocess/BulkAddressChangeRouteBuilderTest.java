/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.iteye.process.bulkaddresschangeprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.security.auth.login.Configuration;
import nl.iteye.process.bulkaddresschangeprocess.model.AddressChange;
import nl.iteye.process.bulkaddresschangeprocess.model.BulkAddressChangeRequest;
import nl.iteye.process.bulkaddresschangeprocess.model.Link;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author andrej
 */
public class BulkAddressChangeRouteBuilderTest {

    public BulkAddressChangeRouteBuilderTest() {
    }
    private static final Logger log = Logger.getLogger(BulkAddressChangeRouteBuilderTest.class.
            getName());
    //   private static CamelContext ctx;
    //   private static Configuration config = null;
    private static AbstractXmlApplicationContext springCtx;

    @BeforeClass
    public static void setUpClass() throws Exception {
        // setup camel routes

        springCtx = new ClassPathXmlApplicationContext(
                "META-INF/spring/camel-context.xml");
        springCtx.start();
        /*
        ctx = new DefaultCamelContext();
        BulkAddressChangeRouteBuilder mrb = new BulkAddressChangeRouteBuilder();
        System.setProperty("soabp.config",
        "classpath:/soabp-config.properties");
        //     config = new Configuration();
        //     mrb.setConfig(config);
        ctx.addRoutes(mrb);
        ctx.start();
         */

    }

    @AfterClass
    public static void tearDownClass() throws Exception {

        springCtx.stop();

    }

    @Test
    public void testConfigure() {
        // create some test data
        BulkAddressChangeRequest req = new BulkAddressChangeRequest();
        List<AddressChange> changes = new ArrayList<AddressChange>();
        AddressChange change1 = new AddressChange();
        change1.setNewStreet("oldsquare");
        Link oldAddress = new Link();
        oldAddress.setHref("http://localhost:9002/addresses/1");
        oldAddress.setRelationship("oldAddress");
        oldAddress.setType(null);
        change1.setOldAddress(oldAddress);
        changes.add(change1);
        req.setChanges(changes);
        // post bulk change to endpoint
        CamelContext ctx = (CamelContext) springCtx.getBean("camelContext");
        ProducerTemplate pt = ctx.createProducerTemplate();
        String response = pt.requestBody(
                "cxfrs://http://localhost:9000/AddressChangeRequest", req,
                                        String.class);
        log.info("Response: " + response);
        // expect: accepted
    }
}
