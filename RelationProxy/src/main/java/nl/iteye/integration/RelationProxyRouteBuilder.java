package nl.iteye.integration;

import java.util.logging.Logger;
import org.apache.camel.builder.RouteBuilder;

/**
 *
 * @author akoelewijn
 */
public class RelationProxyRouteBuilder extends RouteBuilder {

    private final int relationServicePort = 8080; /*12573*/

    private final String relationServiceHost = "localhost";
    private static final transient Logger log = Logger.getLogger(RelationProxyRouteBuilder.class.getName());

    public void configure() {
        log.info("configuring RelationproxyRouteBuilder routes");
        from("restlet:http://localhost:9080/relation/{resourcePath}?restletMethods=get,post,put,delete").
                to("log:relation.address.proxy.in").
                to("restlet:http://" + relationServiceHost + ":" + relationServicePort + "/RelationRestService/webresources/{resourcePath}").
                to("log:relation.address.proxy.out").
                setHeader("Content-Type", constant("text/xml"));
        /*
        from("restlet:http://localhost:9080/relation/customer/{id}?restletMethods=get,post,put,delete").
                to("log:relation.customer.proxy.in").
                to("restlet:http://" + relationServiceHost + ":" + relationServicePort + "/RelationRestService/webresources/customer/1").
                to("log:relation.customer.proxy.out").
                setHeader("Content-Type", constant("text/xml"));
         */
    }
}
