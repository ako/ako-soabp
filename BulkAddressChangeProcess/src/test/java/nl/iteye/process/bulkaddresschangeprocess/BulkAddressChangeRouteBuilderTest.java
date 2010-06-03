/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.iteye.process.bulkaddresschangeprocess;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import nl.iteye.process.bulkaddresschangeprocess.model.AddressChange;
import nl.iteye.process.bulkaddresschangeprocess.model.BulkAddressChangeRequest;
import nl.iteye.process.bulkaddresschangeprocess.model.Link;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
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

    @Test
    public void testHttpClient() throws Exception {
        /*
         * create some test data
         *
         */
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
        /*
         * marshall to xml
         *
         */
        JAXBContext jaxbContext = JAXBContext.newInstance(req.getClass());
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty("jaxb.formatted.output", new Boolean(true));
        StringWriter xmlWriter = new StringWriter();
        marshaller.marshal(req, xmlWriter);
        log.info("xml document: " + xmlWriter);
        /*
         * call rest service using http client
         *
         */
        PostMethod post = new PostMethod(
                "http://localhost:9000/AddressChangeRequest");
        post.addRequestHeader("Accept", "text/xml");
        RequestEntity entity = new StringRequestEntity(xmlWriter.toString(),
                                                       "text/xml", "UTF-8");
        post.setRequestEntity(entity);
        HttpClient httpclient = new HttpClient();
        int statusCode = httpclient.executeMethod(post);
        String body = post.getResponseBodyAsString();
        log.info("status: " + statusCode);
        log.info("body: " + body);
    }
}
