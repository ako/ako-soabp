package nl.iteye.service.configurableservice;

import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.camel.Exchange;
import org.apache.camel.spi.Synchronization;
import org.apache.camel.test.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author andrej
 */
public class InfoRouteBuilderTest extends CamelSpringTestSupport {

    private static final Logger log = Logger.getLogger(InfoRouteBuilderTest.class.
            getName());

    public InfoRouteBuilderTest() {
    }

    @Test
    public void testConfigure() throws InterruptedException {
        Exchange exchange = this.createExchangeWithBody(
                "Please send more info asap!");
        Synchronization callback = new Synchronization() {

            @Override
            public void onComplete(Exchange exchange) {
                log.log(Level.INFO, "Callback onComplete: {0}", exchange);
            }

            @Override
            public void onFailure(Exchange exchange) {
                log.log(Level.INFO, "Callback onFailure: {0}", exchange);
            }
        };
        Future future = template.asyncCallback(
                "http://localhost:8008/infoService", exchange, callback);
        log.info("After infoService call");
        Thread.sleep(5000);
    }

    @Override
    protected AbstractXmlApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext(
                "META-INF/spring/camel-context.xml");
    }
}
