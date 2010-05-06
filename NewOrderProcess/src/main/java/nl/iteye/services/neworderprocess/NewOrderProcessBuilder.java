/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.iteye.services.neworderprocess;

import java.util.logging.Logger;
import org.apache.camel.bam.ProcessBuilder;
import org.apache.camel.processor.interceptor.DefaultTraceFormatter;
import org.apache.camel.processor.interceptor.Tracer;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author andrej
 *
 * Demo
 * - business process through routes
 * - monitoring business activity, timing rules, etc
 * - monitoring process progress
 * - handling errors, error hospital
 * - tracing messages
 * - process audit...
 * 
 */
public class NewOrderProcessBuilder extends ProcessBuilder {

    public NewOrderProcessBuilder() {
    }

    public NewOrderProcessBuilder(JpaTemplate jpaTemplate, TransactionTemplate transactionTemplate) {
        super(jpaTemplate, transactionTemplate);
    }

    @Override
    public void configure() throws Exception {
        log.info("configuring routes for process");
//        ActivityBuilder act1 = activity("file:/tmp/newOrders").to("seda:begin");
//        act1.name("proc1");


        // enable tracing
        Tracer tracer = new Tracer();
        tracer.setTraceOutExchanges(true);
        DefaultTraceFormatter formatter = new DefaultTraceFormatter();
        formatter.setShowOutBody(true);
        formatter.setShowOutBodyType(true);
        formatter.setShowBreadCrumb(true);
        formatter.setShowNode(true);
        tracer.setDestinationUri("direct:traced");
        tracer.setFormatter(formatter);

        getContext().addInterceptStrategy(tracer);
        from("direct:traced").to("log:trace");

        // create process states
        from("file:///tmp/newOrders").to("seda:state1");
        from("seda:state1").to("log:state1").to("seda:state2");
        from("seda:state2").to("log:state2").to("seda:state3");
        from("seda:state3").to("log:state3").to("file:///tmp/processedOrders");


        // add some bam rules
    }
    private static final Logger log = Logger.getLogger(NewOrderProcessBuilder.class.
            getName());
}
