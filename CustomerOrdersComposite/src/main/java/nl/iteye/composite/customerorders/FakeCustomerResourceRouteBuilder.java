package nl.iteye.composite.customerorders;

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

    @Override
    public void configure() throws Exception {
        Configuration config = new PropertiesConfiguration(System.getProperty(
                "config-file"));
        from(config.getString("serviceOne.endpoint")).process(new Processor() {

            @Override
            public void process(Exchange exchng) throws Exception {
                exchng.getOut().setBody("one");
            }
        });
    }
}
