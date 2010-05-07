/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.iteye.services.mailservice;

import java.util.logging.Logger;
import javax.annotation.Resource;
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

    @Override
    public void configure() throws Exception {
        String smtpHost = System.getProperty("smtp.host", "localhost");
        String smtpUsername = System.getProperty("smtp.username", null);
        String smtpPassword = System.getProperty("smtp.password", null);
        String smtpPort = System.getProperty("smtp.port", null);
        boolean smtpUseSSL = System.getProperty("smtp.useSSL", "false").equals(
                "true");
        log.info(
                "smtp" + (smtpUseSSL ? "s": "") + "://" + smtpHost + ":" + smtpPort + "?password=" + smtpPassword + "&username=" + smtpUsername);
        from("restlet://http://localhost:8786/mail/outbox?restletMethods=post").
                to("log:nl.iteye.services.mailservice.MailRouteBuilder").
                to("file:///tmp/mail").
                setHeader("to", xpath("/mail/to")).
                setHeader("subject", xpath("/mail/subject")).
                setBody(xpath("/mail/body")).
                to(
                "smtp" + (smtpUseSSL ? "s": "") + "://" + smtpHost + ":" + smtpPort + "?password=" + smtpPassword + "&username=" + smtpUsername);
    }
}
