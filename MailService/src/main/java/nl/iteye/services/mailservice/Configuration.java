package nl.iteye.services.mailservice;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.logging.Logger;

/**
 *
 * @author andrej
 */
public class Configuration {

    private static final Logger log = Logger.getLogger(Configuration.class.
            getName());
    private Properties configuration;

    public Configuration() {
        try {
            configuration = loadConfig();
        } catch (Exception e) {
            log.warning("Failed to load configuration");
           //e.printStackTrace();
        }
    }

    public Properties loadConfig() throws IOException {
        String userHome = System.getProperty("user.home");
        String fileSeparator = System.getProperty("file.separator");
        String configFilename = System.getProperty("soabp.config",
                                                   userHome + fileSeparator + ".soabp" + fileSeparator + "config.properties");
        log.info("Loading configuration from: " + configFilename);
        Properties config = new Properties();
        if (configFilename.startsWith("classpath:")) {
            String resourceName = configFilename.split("\\:")[1];
            log.info("Loading resource config: " + resourceName);
            InputStream is = Configuration.class.getResourceAsStream(resourceName);
            config.load(is);
        } else if (configFilename.startsWith("file:")) {
            String filename = configFilename.split("\\:")[1];
            log.info("Loading file config: " + filename);
            config.load(new FileInputStream(filename));
        } else {
            log.info("Loading config: " + configFilename);
            config.load(new FileInputStream(configFilename));
        }
        config.list(System.out);
        return config;
    }

    public String getProperty(String key) {
        return configuration.getProperty(key);
    }
}
