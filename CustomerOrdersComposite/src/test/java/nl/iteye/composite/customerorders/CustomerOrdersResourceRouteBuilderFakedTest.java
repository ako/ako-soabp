package nl.iteye.composite.customerorders;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Test;

/**
 *
 * @author andrej
 */
public class CustomerOrdersResourceRouteBuilderFakedTest extends CamelTestSupport {

    @Override
    protected CamelContext createCamelContext() throws Exception {
        System.setProperty("config-file", "faked-camel-config.properties");
        CamelContext ctx = super.createCamelContext();
        return ctx;
    }

    @Override
    protected RouteBuilder[] createRouteBuilders() throws Exception {
        RouteBuilder[] routes = {new CustomerOrdersResourceRouteBuilder(), new FakeCustomerResourceRouteBuilder(), new FakeOrdersResourceRouteBuilder()};
        return routes;
    }

    @Test
    public void testConfigure() throws Exception {
        Configuration config = new PropertiesConfiguration(System.getProperty(
                "config-file"));
        /*
         * Send message to endpoint
         */
        Exchange ex = template.send(config.getString(
                "customerOrdersResource.endpoint"), new Processor() {

            @Override
            public void process(Exchange exchng) throws Exception {
                exchng.getIn().setHeader(Exchange.HTTP_QUERY, "custNo=123");
                exchng.getIn().setHeader(Exchange.HTTP_METHOD, "GET");
                exchng.getIn().setHeader(Exchange.CONTENT_TYPE, "text/xml");
                exchng.getIn().setHeader(Exchange.ACCEPT_CONTENT_TYPE,
                                         "text/xml");
            }
        });
        String body = ex.getOut().getBody(String.class);
        System.out.println("body received: " + body);
        assertEquals("<customer><id>123</id><orders><order><id>1</id><product name=\"Car\"/></order></orders></customer>",body);
    }
}
