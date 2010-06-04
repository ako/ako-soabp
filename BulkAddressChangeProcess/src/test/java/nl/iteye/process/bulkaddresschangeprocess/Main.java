package nl.iteye.process.bulkaddresschangeprocess;

import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author andrej
 *
 * Used for testing with curl, eg:
 *
 * curl -X POST -d "<?xml version='1.0' encoding='UTF-8' standalone='yes'?><bulkAddressChangeRequest><changes><newStreet>oldsquare</newStreet><oldAddress rel='oldAddress' href='http://localhost:9002/addresses/1'/></changes></bulkAddressChangeRequest>" -H "Content-Type: application/xml" http://localhost:9000/AddressChangeRequest
 *
 * curl -X POST -d "<?xml version='1.0' encoding='UTF-8' standalone='yes'?><bulkAddressChangeRequest><changes><newStreet>oldsquare</newStreet><oldAddress rel='oldAddress' href='http://localhost:9002/addresses/1'/></changes></bulkAddressChangeRequest>" -H "Content-Type: application/xml" -H "Accept: application/json" -v http://localhost:9000/AddressChangeRequest
 * 
 */
public class Main {

    private static AbstractXmlApplicationContext springCtx;

    public static void main(String[] args) {
        springCtx = new ClassPathXmlApplicationContext(
                "META-INF/spring/camel-context.xml");
        springCtx.start();
    }
}
