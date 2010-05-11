/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.iteye.services.mailservice;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import static org.apache.camel.builder.xml.XPathBuilder.xpath;
import static org.apache.camel.language.simple.SimpleLanguage.simple;

/**
 *
 * @author andrej
 *
 *  curl -d "<mail><to>andrej@koelewijn.net</to><subject>Test</subject><body>HelloWorld</body></mail>" -X POST http://localhost:8786/mail/outbox

 */
public class MailRouteBuilder extends RouteBuilder {

    private static final Logger log = Logger.getLogger(MailRouteBuilder.class.
            getName());

    @Override
    public void configure() throws Exception {
        Properties config = getConfig();

        String smtpHost = config.getProperty("smtp.host", "localhost");
        String smtpUsername = config.getProperty("smtp.username", null);
        String smtpPassword = config.getProperty("smtp.password", null);
        String smtpPort = config.getProperty("smtp.port", null);
        boolean smtpUseSSL = config.getProperty("smtp.useSSL", "false").equals(
                "true");

        String smtpUrl = "smtp" + (smtpUseSSL ? "s" : "")
                + "://" + smtpHost + ":" + smtpPort
                + "?password=" + smtpPassword + "&username=" + smtpUsername;
        log.info(smtpUrl);

        from("restlet://http://localhost:8786/mail/outbox?restletMethods=post").
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
                setOutHeader("location",simple("http://localhost:8786/mail/sent/${id}")).
                setOutHeader("Content-Location",simple("http://localhost:8786/mail/sent/${id}")).
                setOutHeader("Content-Type",constant("application/xml")).
                setOutHeader("CamelHttpResponseCode",constant(201))
                //setBody(xpath("/mail/body"))

                ;
        from("restlet://http://localhost:8786/mail/sent/{msgId}?restletMethods=get").
                to("log:outbox.get").
                process(new Processor(){
                    @Override
                    public void process(Exchange exchng) throws Exception {
                        log.info("Received exchange: " + exchng.getIn().getHeader("msgId"));
                        CamelContext context = exchng.getContext();
                        ConsumerTemplate template = context.createConsumerTemplate();
                        Object fileBody = template.receiveBody("file:/tmp/sent?fileName=" + exchng.getIn().getHeader("msgId"));
                        log.info("file body: " + fileBody);
                        exchng.getIn().setBody(fileBody);
                    }
                }).
                to("log:file contents");

    }

    private Properties getConfig() throws IOException {
        String userHome = System.getProperty("user.home");
        String fileSeparator = System.getProperty("file.separator");
        String configFilename = System.getProperty("soabp.config.path",
                                                   userHome + fileSeparator + ".soabp" + fileSeparator + "config.properties");
        Properties config = new Properties();
        config.load(new FileInputStream(configFilename));
        return config;
    }
}
