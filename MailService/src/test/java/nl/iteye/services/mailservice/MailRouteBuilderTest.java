/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.iteye.services.mailservice;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;
import junit.framework.TestCase;
import nl.iteye.services.mailservicemodel.MailMessage;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

/**
 *
 * @author andrej
 */
public class MailRouteBuilderTest extends TestCase {

    private static final Logger log = Logger.getLogger(MailRouteBuilderTest.class.
            getName());
    private CamelContext ctx;
    private Wiser smtpServer;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // setup mail server
        System.setProperty("smtp.host", "localhost");
        System.setProperty("smtp.username", "");
        System.setProperty("smtp.password", "");
        System.setProperty("smtp.port", "44552");
        System.setProperty("smtp.useSSL", "false");
        smtpServer = new Wiser();
        smtpServer.setPort(44552);
        smtpServer.start();

        // setup camel routes
        ctx = new DefaultCamelContext();
        ctx.addRoutes(new MailRouteBuilder());
        ctx.start();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        ctx.stop();
        smtpServer.stop();
    }

    public MailRouteBuilderTest(String testName) {
        super(testName);
    }

    public void xtestConfigure() throws Exception {
        Client client = new Client();
        String mailMsg = "<mail><to>andrej@koelewijn.net</to><subject>Test</subject><body>HelloWorld</body></mail>";
        WebResource resource = client.resource(
                "http://localhost:8786/mail/outbox");
        ClientResponse response = resource.post(ClientResponse.class, mailMsg);
        Status status = response.getClientResponseStatus();
        log.info("Response status: " + status);
        assertEquals(status.CREATED,status);
        String responseData = response.getEntity(String.class);
        log.info("result from post to outbox:" + responseData);
        assertEquals(mailMsg, responseData);
            
        // check mail received
        WiserMessage receivedMail = smtpServer.getMessages().get(0);
        log.info("wiser message: " + smtpServer.getMessages().get(0));
        Object mailContent = receivedMail.getMimeMessage().getContent();
        log.info("mail content: " + mailContent);

        // test sentMail resources
        WebResource resource2 = client.resource(response.getLocation());
        String result2 = resource2.get(String.class);
        log.info("result 2: " + result2);
    }

    public void testJaxbData() throws Exception {
        Client client = new Client();
 //       String mailMsg = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<mail><body>HelloWorld!</body><subject>test</subject><to>andrej@koelewijn.net</to></mail>";
 //       String mailMsg = "<mail><body>HelloWorld!</body><subject>test</subject><to>andrej@koelewijn.net</to></mail>";
        MailMessage msg = new MailMessage();
        msg.setTo("andrej@koelewijn.net");
        msg.setSubject("test");
        msg.setBody("HelloWorld!");
        WebResource resource = client.resource(
                "http://localhost:8786/mail/outbox");
        //        "http://localhost:7676/mail/outbox");
        ClientResponse response = resource.type(MediaType.APPLICATION_XML).post(ClientResponse.class, msg);
        Status status = response.getClientResponseStatus();
        log.info("Response status: " + status);
    }
}
