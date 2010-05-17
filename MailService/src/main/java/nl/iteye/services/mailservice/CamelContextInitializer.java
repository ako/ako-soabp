package nl.iteye.services.mailservice;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

/**
 *
 * @author andrej
 */
@Singleton
@Startup
public class CamelContextInitializer {

    private static final Logger log = Logger.getLogger(CamelContextInitializer.class.
            getName());
    CamelContext context = null;

    public CamelContextInitializer() {
    }

    @PostConstruct
    public void initCamelContext() {
        log.info("Starting default camel context");
        // this should not be needed for a @singleton
        if (context == null) {
            context = new DefaultCamelContext();
            try {
                context.addRoutes(new MailRouteBuilder());
                context.start();
            } catch (Exception e) {
                log.warning("Failed to initialize routes: " + e.getMessage());
            }
        }
    }
}
