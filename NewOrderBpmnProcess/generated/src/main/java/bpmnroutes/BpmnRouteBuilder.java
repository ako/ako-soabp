//BPMN file /Users/andrej/NetBeansProjects/soablueprint/NewOrderBpmnProcess/src/main/bpmn/neworder.bpmn.xml
package bpmnroutes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

class MyRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
from("seda:1")
    .process(new Processor(){ /* validateOrder */
         @Override
         public void process(Exchange exchange) throws Exception {
         }
    })
    .to("seda:2");

from("seda:1")
    .process(new Processor(){ /* StoreOrder */
         @Override
         public void process(Exchange exchange) throws Exception {
         }
    })
    .to("seda:2");

from("seda:1")
    .process(new Processor(){ /* SendConfirmation */
         @Override
         public void process(Exchange exchange) throws Exception {
         }
    })
    .to("seda:2");

    }
}
