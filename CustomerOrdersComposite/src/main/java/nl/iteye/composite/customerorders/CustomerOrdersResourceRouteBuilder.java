package nl.iteye.composite.customerorders;

import java.util.logging.Logger;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import static org.apache.camel.builder.xml.XPathBuilder.xpath;

/**
 *
 * @author andrej
 */
public class CustomerOrdersResourceRouteBuilder extends RouteBuilder {
    private final static Logger LOG = Logger.getLogger(CustomerOrdersResourceRouteBuilder.class.
            getName());
    @Override
    public void configure() throws Exception {
        Configuration config = new PropertiesConfiguration(System.getProperty(
                "config-file"));
        String customerOrdersUrl = config.getString("customerOrdersResource.service.endpoint");
        String customerUrl = config.getString("customerResource.endpoint");
        String ordersUrl = config.getString("orderResource.endpoint");

        AggregationStrategy aggregate = new AggregationStrategy(){
            @Override
            public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
                LOG.info("aggregate");
                newExchange.getOut().setBody("<all>" + oldExchange.getIn().getBody(String.class) + newExchange.getIn().getBody(String.class) + "</all>");
                return newExchange;
            }
        };
        from(customerOrdersUrl)
                .to("log:customerOrders.1?showAll=true")
                .removeHeader("CamelHttpUri")
                .removeHeader("CamelHttpUrl")
                .removeHeader("CamelHttpPath")
                .removeHeader("CamelHttpQuery")
                .setHeader("customer",header("custNo"))
                .to("log:customerOrders.2?showAll=true")
                .setHeader(Exchange.HTTP_METHOD,constant("GET"))
                .to("log:customerOrders.3?showAll=true")
                .to(customerUrl)
                .to("log:customerOrders.4?showAll=true")
                .removeHeader("CamelHttpUri")
                .removeHeader("CamelHttpUrl")
                .removeHeader("CamelHttpPath")
                .removeHeader("CamelHttpQuery")
                .setHeader("relId", xpath("/customer/@relationId",String.class))
                .to("log:customerOrders.5?showAll=true")
                .setHeader(Exchange.HTTP_METHOD,constant("GET"))
                .to("log:customerOrders.6?showAll=true")
                .enrich(ordersUrl,aggregate)
                .to("log:customerOrders.7?showAll=true")
                .to("xquery:nl/iteye/composite/customerorders/merge-customer-orders.xquery")
                .setHeader(Exchange.CONTENT_TYPE,constant("text/xml"));
    }
}
