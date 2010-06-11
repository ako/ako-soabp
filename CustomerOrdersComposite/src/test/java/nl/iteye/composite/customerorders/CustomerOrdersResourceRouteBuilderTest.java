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
public class CustomerOrdersResourceRouteBuilderTest extends CamelTestSupport {

    @Override
    protected CamelContext createCamelContext() throws Exception {
        System.setProperty("config-file", "mocked-camel-config.properties");
        CamelContext ctx = super.createCamelContext();
        return ctx;
    }

    @Override
    protected RouteBuilder[] createRouteBuilders() throws Exception {
        RouteBuilder[] routes = {new CustomerOrdersResourceRouteBuilder()};
        return routes;
    }

    @Test
    public void testConfigure() throws Exception {
        Configuration config = new PropertiesConfiguration(System.getProperty(
                "config-file"));
        /*
         * Setup service mocks
         */
        getMockEndpoint(config.getString("customerResource.endpoint")).
                whenAnyExchangeReceived(new Processor() {

            @Override
            public void process(Exchange exchng) throws Exception {
                String custNo = exchng.getIn().getHeader("customer").toString();
                exchng.getOut().setHeader(Exchange.CONTENT_TYPE, "text/xml");
                exchng.getOut().setBody(
                        "<customer custNo='" + custNo + "' relationId='1000" + custNo + "' firstName='Peter' lastName='Semper'/>");
            }
        });
        getMockEndpoint(config.getString("orderResource.endpoint")).
                whenAnyExchangeReceived(new Processor() {

            @Override
            public void process(Exchange exchng) throws Exception {
                String relationId = exchng.getIn().getHeader("relId").toString();
                exchng.getOut().setBody(
                        "<orders relationId='" + relationId + "'><order id='1' product='Car'/></orders>");
            }
        });
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
        System.out.println("body received: " + ex.getOut().getBody());
        assertEquals("<customer><id>123</id><orders><order><id>1</id><product name=\"Car\"/></order></orders></customer>",ex.getOut().getBody());
    }
}
