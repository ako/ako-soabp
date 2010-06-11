package nl.iteye.composite.customerorders;

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
    @Override
    public void configure() throws Exception {
        Configuration config = new PropertiesConfiguration(System.getProperty(
                "config-file"));
        String customerOrdersUrl = config.getString("customerOrdersResource.endpoint");
        String customerUrl = config.getString("customerResource.endpoint");
        String ordersUrl = config.getString("orderResource.endpoint");

        AggregationStrategy aggregate = new AggregationStrategy(){
            @Override
            public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
                newExchange.getOut().setBody("<all>" + oldExchange.getIn().getBody(String.class) + newExchange.getIn().getBody(String.class) + "</all>");
                return newExchange;
            }
        };
        from(customerOrdersUrl)
                .to("log:composite.received?showAll=true")
                .setHeader("customer",header("custNo"))
                .to(customerUrl)
                .to("log:composite.one?showAll=true")
                .setHeader("relId", xpath("/customer/@relationId",String.class))
                .enrich(ordersUrl,aggregate)
                .to("log:composite.enrich?showAll=true")
                .to("xquery:nl/iteye/composite/customerorders/merge-customer-orders.xquery")
                .to("log:composite.xquery?showAll=true");
    }
}
