package nl.iteye.service.configurableservice;

import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author andrej
 */
public class Main {

    public static void main(String[] args) {
        AbstractXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "META-INF/spring/camel-context.xml");
        ctx.start();
    }
}
