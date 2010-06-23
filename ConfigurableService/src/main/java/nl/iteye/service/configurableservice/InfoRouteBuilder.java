package nl.iteye.service.configurableservice;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.camel.builder.RouteBuilder;

/**
 * A Camel Router
 *
 * @version $
 */
public class InfoRouteBuilder extends RouteBuilder {

    private String svcEndpointUrl = "jetty:http://localhost:8008/infoService";

    public String getSvcEndpointUrl() {
        return svcEndpointUrl;
    }

    public void setSvcEndpointUrl(String svcEndpointUrl) {
        this.svcEndpointUrl = svcEndpointUrl;
    }

    public InfoRouteBuilder(){
        super();
        Logger.getLogger(InfoRouteBuilder.class.getName()).log(Level.INFO,
                                                               "InfoRouteBuilder instantiated");
    }

    @Override
    public void configure() {
        from(svcEndpointUrl)
                .to("log:received-request")
                .delay(1000)
                .to("log:creating-response")
                .setHeader("My-Status-code", constant("oki-doki"))
                .setBody(constant("Here's your info request"));
    }
}
