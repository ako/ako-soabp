package nl.iteye.composite.customerorders;

import java.util.logging.Logger;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *
 * @author andrej
 */
public class FakeCustomerResourceRouteBuilder extends RouteBuilder {
    private final static Logger LOG = Logger.getLogger(FakeCustomerResourceRouteBuilder.class.
            getName());
    @Override
    public void configure() throws Exception {
        Configuration config = new PropertiesConfiguration(System.getProperty(
                "config-file"));
        from(config.getString("customerResource.service.endpoint")).process(new Processor() {

            @Override
            public void process(Exchange exchng) throws Exception {
                LOG.info("process: " + exchng);
                String custNo = exchng.getIn().getHeader("customer").toString();
                exchng.getOut().setHeader(Exchange.CONTENT_TYPE, "text/xml");
                exchng.getOut().setBody(
                        "<customer custNo='" + custNo + "' relationId='1000" + custNo + "' firstName='Peter' lastName='Semper'/>");
            }
        });
    }
}
