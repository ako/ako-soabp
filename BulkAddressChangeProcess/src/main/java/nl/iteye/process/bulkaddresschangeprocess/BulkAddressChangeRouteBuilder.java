package nl.iteye.process.bulkaddresschangeprocess;

import java.util.logging.Logger;
import nl.iteye.process.bulkaddresschangeprocess.model.BulkAddressChangeRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
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

    @Override
    public void configure() {
        /*
         * Using jsxrs
         */
        from("cxfrs://bean://rsServer").to("log:cfxr?showAll=true").process(new Processor() {

            @Override
            public void process(Exchange exchange) throws Exception {
                // Message body contains array of parameters
                Object[] body = (Object[]) exchange.getIn().getBody();
                BulkAddressChangeRequest bac = (BulkAddressChangeRequest) body[0];
                log.info("received address change request: " + bac);
                Message outMsg = exchange.getOut();
                outMsg.setBody(bac);
                outMsg.setHeader("CamelHttpLocation",
                                 "http://localhost:9000/newAddressOfRequest/123123");
            }
        }).to("seda:storeBulkRequest");

        from("seda:storeBulkRequest").to("file:///tmp").to(
                "seda:splitBulkRequest");
        from("seda:splitBulkRequest").to("log:done.splitting");
        /*
         * Using jetty
         */
    }
}
