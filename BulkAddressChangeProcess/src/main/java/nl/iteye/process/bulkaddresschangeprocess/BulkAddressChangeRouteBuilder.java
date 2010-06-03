package nl.iteye.process.bulkaddresschangeprocess;

import java.util.logging.Logger;
import nl.iteye.process.bulkaddresschangeprocess.model.BulkAddressChangeRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * A Camel Router
 *
 * @version $
 */
public class BulkAddressChangeRouteBuilder extends RouteBuilder {
private static final Logger log = Logger.getLogger(BulkAddressChangeRouteBuilder.class.
            getName());
    /**
     * Lets configure the Camel routing rules using Java code...
     */
    @Override
    public void configure() {
        from("cxfrs://bean://rsServer").to("log:cfxr?showAll=true").process(new Processor() {

            @Override
            public void process(Exchange exchange) throws Exception {
               BulkAddressChangeRequest req =  exchange.getIn().getBody(BulkAddressChangeRequest.class);
               log.info("request: " + req);
               exchange.getOut().setBody("Hi there!");
            }
        });
    }
}
