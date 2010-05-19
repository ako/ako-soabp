/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.iteye.services.mailservice;

import java.util.logging.Logger;
import nl.iteye.utils.Configuration;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.restlet.data.Form;
import static org.apache.camel.builder.xml.XPathBuilder.xpath;

/**
 *
 * @author andrej
 *
 *  curl -d "<mail><to>andrej@koelewijn.net</to><subject>Test</subject><body>HelloWorld</body></mail>" -X POST http://localhost:8786/mail/outbox

 */
public class MailRouteBuilder extends RouteBuilder {

    private static final Logger log = Logger.getLogger(MailRouteBuilder.class.
            getName());
    // there seems to be a problem with @inject of classes defined in jars in
    // the war/web-inf/lib folder, so for now we'll just instantiate the object
    private Configuration config = new Configuration();

    public Configuration getConfig() {
        return config;
    }

    public void setConfig(Configuration config) {
        log.info(">setConfig: " + config);
        this.config = config;
    }

    @Override
    public void configure() throws Exception {
        String smtpHost = config.getProperty("smtp.host");
        String smtpUsername = config.getProperty("smtp.username");
        String smtpPassword = config.getProperty("smtp.password");
        String smtpPort = config.getProperty("smtp.port");
        boolean smtpUseSSL = config.getProperty("smtp.useSSL").equals(
                "true");
        String mailServiceBaseUrl = "http://" + config.getProperty(
                "soabp.services.mail.host") + ":"
                + config.getProperty("soabp.services.mail.port")
                + config.getProperty("soabp.services.mail.contextRoot");
        String mailServiceImplBaseUrl = "http://" + config.getProperty(
                "soabp.services.mail.host.impl") + ":"
                + config.getProperty("soabp.services.mail.port.impl")
                + config.getProperty("soabp.services.mail.contextRoot.impl");

        String smtpUrl = "smtp" + (smtpUseSSL ? "s" : "")
                + "://" + smtpHost + ":" + smtpPort
                + "?password=" + smtpPassword + "&username=" + smtpUsername;
        log.info("Using mail endpoint: " + smtpUrl);
        log.info("Create mail route: " + mailServiceImplBaseUrl);
        from("restlet://" + mailServiceImplBaseUrl + "/outbox?restletMethod=post").
                to("log:outbox.received?showAll=true").
                to("file:/tmp/outboxfile").
                multicast().
                to("direct:sendMail", "direct:storeSentMail");
        from("direct:sendMail").
                setHeader("to", xpath("/mail/to")).
                setHeader("subject", xpath("/mail/subject")).
                setBody(xpath("/mail/body")).
                to(smtpUrl);
        from("direct:storeSentMail").
                to("file:/tmp/sent").
                to("log:sent.mail")
                .process( new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String mailLocation = "http://localhost:8786/mail/sent/" + exchange.
                                getIn().getMessageId();
                        exchange.getOut().setBody(exchange.getIn().getBody() );
                        exchange.getOut().setHeader(Exchange.CONTENT_TYPE,
                                                    "application/xml");
                        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 201);
                        Form responseHeaders = new Form();
                        responseHeaders.add("X-Content-Location" , mailLocation);
                        responseHeaders.add("Location" , mailLocation);
                        responseHeaders.add("Content-Location" , mailLocation);
                        responseHeaders.add("response.locationRef" , mailLocation);
                        responseHeaders.add("entity.identifier" , mailLocation);
                        exchange.getOut().setHeader("org.restlet.http.headers", responseHeaders );
                        exchange.getOut().setHeader("org.restlet.entity.identifier", responseHeaders );
                        exchange.getOut().setHeader("response.locationRef", responseHeaders );
                    }
                })
                ;
        from("restlet://" + mailServiceImplBaseUrl + "/sent/{id}?restletMethod=get").
                to("log:outbox.get").
                process(new Processor() {

            @Override
            public void process(Exchange exchng) throws Exception {
                log.info("Received exchange: " + exchng.getIn().getHeader(
                        "msgId"));
                CamelContext context = exchng.getContext();
                ConsumerTemplate template = context.createConsumerTemplate();
                Object fileBody = template.receiveBody("file:/tmp/sent?fileName=" + exchng.
                        getIn().getHeader("msgId"));
                log.info("file body: " + fileBody);
                exchng.getIn().setBody(fileBody);
            }
        }).
                to("log:file contents");

    }
}
