package nl.iteye.service.camelconvertertest;

import java.io.InputStream;
import org.apache.camel.CamelContext;
import org.apache.camel.TypeConverter;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 *
 * @author andrej
 */
public class ConverterTest extends CamelTestSupport {

    @Test
    public void testPlainCamelContext() {
        CamelContext ctx = new DefaultCamelContext();
        TypeConverter converter = ctx.getTypeConverterRegistry().lookup(
                InputStream.class, Employee.class);
        assertNotNull(converter);
    }
}
