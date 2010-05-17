package nl.iteye.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.logging.Logger;
import org.apache.commons.configuration.ConfigurationException;

/**
 *
 * @author andrej
 */
public class Configuration {

    private static final Logger log = Logger.getLogger(Configuration.class.
            getName());
    private org.apache.commons.configuration.Configuration configuration;

    public Configuration() {
        try {
            configuration = loadConfig();
        } catch (Exception e) {
            log.warning("Failed to load configuration");
            //e.printStackTrace();
        }
    }

    public org.apache.commons.configuration.Configuration loadConfig() throws IOException {
        String userHome = System.getProperty("user.home");
        String fileSeparator = System.getProperty("file.separator");
        String configFilename = System.getProperty("soabp.config",
                                                   userHome + fileSeparator + ".soabp" + fileSeparator + "config.properties");
        log.info("Loading configuration from: " + configFilename);
        org.apache.commons.configuration.PropertiesConfiguration config = new org.apache.commons.configuration.PropertiesConfiguration();
        try {
            // need to refactor this now that we're using commons configuration
            // i think there's some unnescessary code here
            if (configFilename.startsWith("classpath:")) {
                String resourceName = configFilename.split("\\:")[1];
                log.info("Loading resource config: " + resourceName);
                InputStream is = Configuration.class.getResourceAsStream(
                        resourceName);
                config.load(is);
            } else if (configFilename.startsWith("file:")) {
                String filename = configFilename.split("\\:")[1];
                log.info("Loading file config: " + filename);
                config.load(new FileInputStream(filename));
            } else if (configFilename.startsWith("http://")) {
                log.info("Loading file config: " + configFilename);
                config.load(new URL(configFilename));
            } else {
                log.info("Loading config: " + configFilename);
                config.load(new FileInputStream(configFilename));
            }
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            config.save(pw);
            log.info("configuration properties loaded: " + sw.toString());
        } catch (ConfigurationException e) {
            log.warning("Failed to load configuration: " + e.getMessage());
        }
        return config;
    }

    public String getProperty(String key) {
        return configuration.getString(key);
    }
}
