package nl.iteye.service.camelconvertertest;

import java.io.InputStream;
import org.apache.camel.CamelContext;
import org.apache.camel.TypeConverter;
import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author andrej
 */
public class SpringConverterTest extends CamelSpringTestSupport {

    @Override
    protected AbstractXmlApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("META-INF/spring/spring-config.xml");
    }

    @Test
    public void testSpringConfigConverters() {
        CamelContext ctx = (CamelContext) super.applicationContext.getBean(
                "camel");
        TypeConverter converter = ctx.getTypeConverterRegistry().lookup(
                InputStream.class, Employee.class);
        assertNotNull(converter);
    }
}
