package nl.iteye.process.bulkaddresschangeprocess;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.Main;

import static org.apache.camel.builder.xml.XPathBuilder.xpath;

/**
 * A Camel Router
 *
 * @version $
 */
public class BulkAddressChangeRouteBuilder extends RouteBuilder {

    /**
     * Lets configure the Camel routing rules using Java code...
     */
    @Override
    public void configure() {
        from("cxfrs://bean://rsServer").to("log:cfxr?showAll=true");
    }
}
