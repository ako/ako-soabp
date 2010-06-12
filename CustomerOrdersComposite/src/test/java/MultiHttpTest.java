
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class MultiHttpTest extends CamelTestSupport {

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from("jetty:http://localhost:9004/svc1").process(new Processor() {

                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setHeader(Exchange.CONTENT_TYPE,
                                                    "text/xml");
                        exchange.getOut().setBody("<svc1/>");
                    }
                });
                from("jetty:http://localhost:9004/svc2").process(new Processor() {

                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setHeader(Exchange.CONTENT_TYPE,
                                                    "text/xml");
                        exchange.getOut().setBody("<svc2/>");
                    }
                });
                from("jetty:http://localhost:9004/svc1svc2") // receive call
                        .to("log:svc1svc2.received?showAll=true") // log
                        .removeHeader("CamelHttpUri") // workaround
                        .removeHeader("CamelHttpUrl")// workaround
                        .removeHeader("CamelHttpPath")// workaround
                        .removeHeader("CamelHttpQuery")// workaround
                        .to("log:svc1.before?showAll=true") // log
                        .to("http://localhost:9004/svc1?httpClient.soTimeout=5000") // call service 1
                        .to("log:svc1.after?showAll=true") // log
                        .removeHeader("CamelHttpUri")// workaround
                        .removeHeader("CamelHttpUrl")// workaround
                        .removeHeader("CamelHttpPath")// workaround
                        .removeHeader("CamelHttpQuery")// workaround
                        .to("log:svc2.before?showAll=true") // log
                        .to("http://localhost:9004/svc2?httpClient.soTimeout=5000")// call service 2
                        .to("log:svc2.after?showAll=true"); //log
            }
        };
    }

    @Test
    public void testRoute() {
        Exchange result = template.send("http://localhost:9004/svc1svc2", new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                exchange.getIn().setHeader(Exchange.ACCEPT_CONTENT_TYPE,
                                           "text/xml");
                exchange.getIn().setHeader(Exchange.HTTP_METHOD, "GET");
            }
        });
        String body = result.getOut().getBody(String.class);
        System.out.println("Result: " + body);
        assertEquals("<svc2/>",body);
    }
}
