/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.iteye.services.mailservice;

import org.apache.camel.builder.RouteBuilder;
import static org.apache.camel.builder.xml.XPathBuilder.xpath;

/**
 *
 * @author andrej
 *
 *  curl -d "<mail><to>andrej@koelewijn.net</to><subject>Test</subject><body>HelloWorld</body></mail>" -X POST http://localhost:8786/mail/outbox

 */
public class MailRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("restlet://http://localhost:8786/mail/outbox?restletMethods=post").
                to("log:nl.iteye.services.mailservice.MailRouteBuilder").
                to("file:///tmp/mail").
                setHeader("to",xpath("/mail/to").stringResult()).
                setHeader("subject",xpath("/mail/subject").stringResult()).
                setBody(xpath("/mail/body")).
                to("smtps://smtp.gmail.com?password=c4m3lt3st&username=cmltst01@gmail.com");
    }

}
