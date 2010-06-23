package nl.iteye.composite.customerorders;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

/**
 *
 * @author andrej
 */
public class Main {

    public static void main(String args[]) throws Exception {
        System.setProperty("config-file", "faked-camel-config.properties");
        CamelContext ctx = new DefaultCamelContext();
        ctx.addRoutes(new CustomerOrdersResourceRouteBuilder());
        ctx.addRoutes(new FakeCustomerResourceRouteBuilder());
        ctx.addRoutes(new FakeOrdersResourceRouteBuilder());
        ctx.start();
    }
}
