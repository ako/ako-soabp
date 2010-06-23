package nl.iteye.service.camelconvertertest;

import java.io.InputStream;
import org.apache.camel.Converter;

/**
 *
 * @author andrej
 */
@Converter
public class EmployeeConverter {

    @Converter
    public static InputStream getInputStream(Employee emp) {
        return null;
    }
}
