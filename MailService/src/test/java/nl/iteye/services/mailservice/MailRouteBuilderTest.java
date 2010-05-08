/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.iteye.services.mailservice;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import java.util.logging.Logger;
import junit.framework.TestCase;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.subethamail.wiser.Wiser;

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

    public void testConfigure() throws Exception {
        Client client = new Client();
        WebResource resource = client.resource(
                "http://localhost:8786/mail/outbox");
        String result = resource.post(String.class,
                                      "<mail><to>andrej@koelewijn.net</to><subject>Test</subject><body>HelloWorld</body></mail>");
        log.info("result from post to outbox:" + result);
        log.info("wiser message: " + smtpServer.getMessages().get(0));

        // test sentMail resources
        WebResource resource2 = client.resource( result );
        String result2 = resource2.get(String.class);
        log.info("result 2: " + result2);
    }
}
