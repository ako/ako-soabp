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
    Configuration config = new Configuration();

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
        log.info(smtpUrl);
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
                to("log:sent.mail") //                .setOutHeader("location", simple(
                //                "http://localhost:8786/mail/sent/${id}")).
                //                setOutHeader("Content-Location", simple(
                //                "http://localhost:8786/mail/sent/${id}")).
                //                setOutHeader("Content-Type", constant("application/xml")) /// .setBody(xpath("/mail/body")).
                .process(
                new Processor() {

                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setBody(exchange.getIn().getBody());
                    }
                }) //.setOutHeader("CamelHttpResponseCode", constant(201))
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
