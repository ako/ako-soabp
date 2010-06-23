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
                .removeHeaders("CamelHttp*")
                .setHeader("customer",header("custNo"))
                .setHeader(Exchange.HTTP_METHOD,constant("GET"))
                .to(customerUrl)
                .removeHeaders("CamelHttp*")
                .setHeader("relId", xpath("/customer/@relationId",String.class))
                .setHeader(Exchange.HTTP_METHOD,constant("GET"))
                .enrich(ordersUrl,aggregate)
                .to("xquery:nl/iteye/composite/customerorders/merge-customer-orders.xquery")
                .setHeader(Exchange.CONTENT_TYPE,constant("text/xml"));
    }
}
